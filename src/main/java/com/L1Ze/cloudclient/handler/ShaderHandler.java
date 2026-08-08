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
                // 更新强度 uniform（通过反射或直接使用 ShaderGroup 的 uniform 设置）
                try {
                    // 获取 "Intensity" uniform 并设置
                    shaderGroup.getShaderGroupUniforms().get("Intensity").set(Config.motionBlurIntensity / 100f);
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
            // 在主帧缓冲渲染完成后，应用着色器
            // 注意：这会覆盖整个画面，但随后 GUI 会渲染在上面
            shaderGroup.render(mc.displayWidth, mc.displayHeight);
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