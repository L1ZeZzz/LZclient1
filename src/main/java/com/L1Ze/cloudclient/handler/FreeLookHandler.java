package com.L1Ze.cloudclient.handler;

import com.L1Ze.cloudclient.keybind.KeyBindings;
import com.L1Ze.cloudclient.module.FreeLookModule;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class FreeLookHandler {
    private static final FreeLookModule module = new FreeLookModule();
    private static float cameraYaw = 0;
    private static float cameraPitch = 0;
    private static boolean active = false;
    private static float lastMouseX, lastMouseY;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;

        boolean keyDown = KeyBindings.freeLook.isKeyDown();
        if (keyDown && module.isEnabled() && mc.currentScreen == null) {
            if (!active) {
                active = true;
                cameraYaw = mc.thePlayer.rotationYaw;
                cameraPitch = mc.thePlayer.rotationPitch;
                mc.mouseHelper.grabMouseCursor();
                lastMouseX = Mouse.getX();
                lastMouseY = Mouse.getY();
            } else {
                float dx = Mouse.getX() - lastMouseX;
                float dy = Mouse.getY() - lastMouseY;
                lastMouseX = Mouse.getX();
                lastMouseY = Mouse.getY();

                float sens = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                float scale = sens * 0.15f;
                cameraYaw += dx * scale;
                cameraPitch -= dy * scale;
                cameraPitch = Math.max(-90, Math.min(90, cameraPitch));
            }
        } else {
            if (active) {
                active = false;
                if (mc.currentScreen == null) {
                    mc.mouseHelper.grabMouseCursor();
                }
            }
        }
    }

    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        // 1.8.9 中 CameraSetup 没有 setYaw/setPitch/setRoll
        // 需要 Mixin 或直接修改 entity 来实现 FreeLook
        // 暂时留空，FreeLook 功能未完全实现
    }
}
