package com.yourname.cloudclient.handler;

import com.yourname.cloudclient.config.Config;
import com.yourname.cloudclient.module.TranslationModule;
import com.yourname.cloudclient.util.TranslateUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class TranslationHandler {
    private static final TranslationModule module = new TranslationModule();

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiChat && module.isEnabled() && Config.translationEnabled) {
            if (Keyboard.getEventKey() == Keyboard.KEY_TAB && Keyboard.getEventKeyState()) {
                GuiChat chat = (GuiChat) mc.currentScreen;
                // 获取输入框内容 (通过反射或直接访问字段)
                String input = getChatInput(chat);
                if (input != null && !input.isEmpty()) {
                    // 异步翻译
                    TranslateUtil.translateAsync(input, translated -> {
                        if (translated != null && mc.currentScreen == chat) {
                            setChatInput(chat, translated);
                        }
                    });
                }
            }
        }
    }

    private String getChatInput(GuiChat chat) {
        try {
            java.lang.reflect.Field field = GuiChat.class.getDeclaredField("inputField");
            field.setAccessible(true);
            net.minecraft.client.gui.GuiTextField fieldObj = (net.minecraft.client.gui.GuiTextField) field.get(chat);
            return fieldObj.getText();
        } catch (Exception e) {
            return null;
        }
    }

    private void setChatInput(GuiChat chat, String text) {
        try {
            java.lang.reflect.Field field = GuiChat.class.getDeclaredField("inputField");
            field.setAccessible(true);
            net.minecraft.client.gui.GuiTextField fieldObj = (net.minecraft.client.gui.GuiTextField) field.get(chat);
            fieldObj.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}