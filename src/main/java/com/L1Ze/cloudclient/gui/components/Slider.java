package com.L1Ze.cloudclient.gui.components;

import com.L1Ze.cloudclient.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Slider extends GuiButton {
    private int value;
    private final int min;
    private final int max;
    private final String format;
    private boolean dragging = false;

    public Slider(int x, int y, int width, int height, int initial, int min, int max, String format) {
        super(0, x, y, width, height, "");
        this.value = initial;
        this.min = min;
        this.max = max;
        this.format = format;
        updateDisplay();
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (dragging) {
            float percent = (float) (mouseX - xPosition) / width;
            percent = Math.max(0, Math.min(1, percent));
            value = Math.round(min + (max - min) * percent);
            // 直接修改配置
            Config.motionBlurIntensity = value;
            Config.save();
            updateDisplay();
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        dragging = false;
    }

    private void updateDisplay() {
        displayString = String.format(format, value);
    }

    public int getValue() {
        return value;
    }
}
