package com.L1Ze.cloudclient.module;

import com.L1Ze.cloudclient.config.Config;

public class MotionBlurModule extends Module {
    public MotionBlurModule() {
        super("动态模糊", Config.motionBlurEnabled);
    }

    @Override
    protected void onStateChanged() {
        Config.motionBlurEnabled = isEnabled();
        Config.save();
    }

    public int getIntensity() {
        return Config.motionBlurIntensity;
    }

    public void setIntensity(int value) {
        Config.motionBlurIntensity = value;
        Config.save();
    }
}
