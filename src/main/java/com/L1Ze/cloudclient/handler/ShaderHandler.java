package com.yourname.cloudclient.handler;

import com.yourname.cloudclient.config.Config;
import com.yourname.cloudclient.module.MotionBlurModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

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
                // 🔥 1.8.9 中获取 Uniform 的正确方式：通过 listShaders 遍历
                try {
                    if (shaderGroup.listShaders != null) {
                        for (net.minecraft.client.shader.Shader shader : shaderGroup.listShaders) {
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
            // 🔥 1.8.9 中 render 只接受一个参数 (partialTicks)
            shaderGroup.render(event.partialTicks);
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
