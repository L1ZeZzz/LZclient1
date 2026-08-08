package com.yourname.cloudclient.gui.components;

import com.yourname.cloudclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ModuleButton extends GuiButton {
    protected final Module module;

    public ModuleButton(Module module, int x, int y, int width, int height) {
        super(0, x, y, width, height, "");
        this.module = module;
        updateDisplay();
    }

    public void updateDisplay() {
        displayString = module.getName() + (module.isEnabled() ? " §a开" : " §c关");
    }

    public Module getModule() {
        return module;
    }
}