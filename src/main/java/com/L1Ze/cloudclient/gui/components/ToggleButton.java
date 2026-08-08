package com.yourname.cloudclient.gui.components;

import com.yourname.cloudclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

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