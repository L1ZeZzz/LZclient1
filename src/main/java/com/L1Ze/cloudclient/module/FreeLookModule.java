package com.L1Ze.cloudclient.module;

import com.L1Ze.cloudclient.config.Config;

public class FreeLookModule extends Module {
    public FreeLookModule() {
        super("自由视角", Config.freeLookEnabled);
    }

    @Override
    protected void onStateChanged() {
        Config.freeLookEnabled = isEnabled();
        Config.save();
    }
}
