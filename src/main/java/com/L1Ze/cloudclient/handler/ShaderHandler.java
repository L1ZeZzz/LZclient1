package com.L1Ze.cloudclient.handler;

import com.L1Ze.cloudclient.config.Config;
import com.L1Ze.cloudclient.module.MotionBlurModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ShaderHandler {
    private static final MotionBlurModule module = new MotionBlurModule();
    private static ShaderGroup shaderGroup = null;
    private static boolean loaded = false;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return;

        boolean enabled = module.isEnabled() && Config.motionBlurEnabled;
        if (enabled) {
            if (!loaded) {
                loadShader();
            }
            if (shaderGroup != null) {
                // 使用反射获取 listShaders
                try {
                    Field listShadersField = ShaderGroup.class.getDeclaredField("listShaders");
                    listShadersField.setAccessible(true);
                    List<?> listShaders = (List<?>) listShadersField.get(shaderGroup);
                    if (listShaders != null) {
                        for (Object shaderObj : listShaders) {
                            net.minecraft.client.shader.Shader shader = (net.minecraft.client.shader.Shader) shaderObj;
                            if (shader.getShaderManager() != null) {
                                shader.getShaderManager().getShaderUniform("Intensity")
                                    .set(Config.motionBlurIntensity / 100f);
                            }
                        }
                    }
                } catch (Exception e) {
                    // 忽略
                }
            }
        } else {
            if (loaded && shaderGroup != null) {
                shaderGroup.deleteShaderGroup();
                shaderGroup = null;
                loaded = false;
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null) return;

        if (module.isEnabled() && Config.motionBlurEnabled && shaderGroup != null) {
            // 1.8.9 中 render() 方法无参数
            shaderGroup.render();
        }
    }

    private void loadShader() {
        Minecraft mc = Minecraft.getMinecraft();
        ResourceLocation location = new ResourceLocation("cloudclient", "shaders/program/motionblur.json");
        try {
            shaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), location);
            loaded = true;
        } catch (IOException e) {
            e.printStackTrace();
            loaded = false;
        }
    }
}
