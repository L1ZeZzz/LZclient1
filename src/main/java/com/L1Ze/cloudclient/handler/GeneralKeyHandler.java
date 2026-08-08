package com.L1Ze.cloudclient.handler;

import com.L1Ze.cloudclient.gui.ClickGUI;
import com.L1Ze.cloudclient.keybind.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class GeneralKeyHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        // 按下右 Shift (默认) 打开 ClickGUI
        if (KeyBindings.openGui.isPressed()) {
            mc.displayGuiScreen(new ClickGUI());
        }
        // 注意：FreeLook 按键由 FreeLookHandler 内部持续监听，
        // 翻译按键由 TranslationHandler 单独监听。
        // 这里只负责处理按下一次的 GUI 打开事件。
    }
}
