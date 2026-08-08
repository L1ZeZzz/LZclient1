package com.L1Ze.cloudclient.keybind;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
    public static KeyBinding openGui;
    public static KeyBinding freeLook;
    public static KeyBinding translate; // 默认 Tab，但会被监听

    public static void register() {
        openGui = new KeyBinding("打开设置界面", Keyboard.KEY_RSHIFT, "CloudClient");
        freeLook = new KeyBinding("自由视角开关", Keyboard.KEY_F4, "CloudClient");
        // 翻译使用 Tab，但 Tab 也被用于补全，我们用 KeyInputEvent 单独处理
        // 为了可配置，我们仍注册一个，但默认用 Tab
        translate = new KeyBinding("翻译聊天输入", Keyboard.KEY_TAB, "CloudClient");

        ClientRegistry.registerKeyBinding(openGui);
        ClientRegistry.registerKeyBinding(freeLook);
        ClientRegistry.registerKeyBinding(translate);
    }
}
