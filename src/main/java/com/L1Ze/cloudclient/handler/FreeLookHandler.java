package com.yourname.cloudclient.handler;

import com.yourname.cloudclient.keybind.KeyBindings;
import com.yourname.cloudclient.module.FreeLookModule;
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
                // 保存当前玩家朝向作为初始
                cameraYaw = mc.thePlayer.rotationYaw;
                cameraPitch = mc.thePlayer.rotationPitch;
                // 让鼠标不再控制玩家朝向
                mc.mouseHelper.grabMouseCursor();
                // 记录鼠标位置增量
                lastMouseX = Mouse.getX();
                lastMouseY = Mouse.getY();
            } else {
                // 计算鼠标移动增量
                float dx = Mouse.getX() - lastMouseX;
                float dy = Mouse.getY() - lastMouseY;
                lastMouseX = Mouse.getX();
                lastMouseY = Mouse.getY();

                // 灵敏度缩放
                float sens = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                float scale = sens * 0.15f;
                cameraYaw += dx * scale;
                cameraPitch -= dy * scale;
                cameraPitch = Math.max(-90, Math.min(90, cameraPitch));
            }
        } else {
            if (active) {
                active = false;
                // 恢复鼠标抓取（如果之前被抓取）
                if (mc.currentScreen == null) {
                    mc.mouseHelper.grabMouseCursor();
                }
            }
        }
    }

    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        if (active && module.isEnabled()) {
            // 覆盖相机的旋转
            event.setYaw(cameraYaw);
            event.setPitch(cameraPitch);
            event.setRoll(0);
        }
    }
}