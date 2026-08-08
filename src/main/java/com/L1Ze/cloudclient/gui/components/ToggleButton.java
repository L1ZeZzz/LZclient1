package com.L1Ze.cloudclient.gui.components;

import com.L1Ze.cloudclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ToggleButton extends ModuleButton {
    public ToggleButton(Module module, int x, int y, int width, int height) {
        super(module, x, y, width, height);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            module.toggle();
            updateDisplay();
            return true;
        }
        return false;
    }
}
