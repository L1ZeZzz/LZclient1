package com.L1Ze.cloudclient.module;

import com.L1Ze.cloudclient.config.Config;

public class TranslationModule extends Module {
    public TranslationModule() {
        super("聊天翻译", Config.translationEnabled);
    }

    @Override
    protected void onStateChanged() {
        Config.translationEnabled = isEnabled();
        Config.save();
    }
}
