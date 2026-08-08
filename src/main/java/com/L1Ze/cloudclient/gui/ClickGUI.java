package com.L1Ze.cloudclient.gui;

import com.L1Ze.cloudclient.config.Config;
import com.L1Ze.cloudclient.gui.components.ModuleButton;
import com.L1Ze.cloudclient.gui.components.Slider;
import com.L1Ze.cloudclient.gui.components.ToggleButton;
import com.L1Ze.cloudclient.module.FreeLookModule;
import com.L1Ze.cloudclient.module.Module;
import com.L1Ze.cloudclient.module.MotionBlurModule;
import com.L1Ze.cloudclient.module.TranslationModule;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen {
    private final List<Module> modules = new ArrayList<>();
    private final List<ToggleButton> toggleButtons = new ArrayList<>();
    private final List<Slider> sliders = new ArrayList<>();
    private GuiButton resetButton;

    public ClickGUI() {
        // 初始化模块
        modules.add(new MotionBlurModule());
        modules.add(new FreeLookModule());
        modules.add(new TranslationModule());
    }

    @Override
    public void initGui() {
        super.initGui();
        toggleButtons.clear();
        sliders.clear();

        int y = 30;
        int x = width / 2 - 100;

        for (Module module : modules) {
            ToggleButton toggle = new ToggleButton(module, x, y, 120, 20);
            toggleButtons.add(toggle);
            buttonList.add(toggle);

            if (module instanceof MotionBlurModule) {
                Slider slider = new Slider(x + 130, y, 80, 20, ((MotionBlurModule) module).getIntensity(),
                        0, 100, "强度: %d");
                sliders.add(slider);
                buttonList.add(slider);
            }
            y += 30;
        }

        resetButton = new GuiButton(100, x, y + 10, 200, 20, "重置所有设置");
        buttonList.add(resetButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == resetButton) {
            Config.motionBlurEnabled = true;
            Config.freeLookEnabled = true;
            Config.translationEnabled = true;
            Config.motionBlurIntensity = 50;
            Config.save();
            // 重新加载界面以刷新状态
            initGui();
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "CloudClient 设置", width / 2, 10, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
