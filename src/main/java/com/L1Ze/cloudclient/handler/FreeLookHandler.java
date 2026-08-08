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
        // 🔥 1.8.9 中 CameraSetup 不支持 setYaw/setPitch/setRoll
        // 这些方法在 Forge 1.12+ 才存在，1.8.9 中需要用其他方式实现
        // 因此这里暂时空置，FreeLook 功能通过 Mixin 或直接修改实体实现
        // 如果你需要 FreeLook，建议用 Mixin 修改 EntityRenderer
        // 或者接受仅客户端视角变化（不修改实体朝向）
    }
}
