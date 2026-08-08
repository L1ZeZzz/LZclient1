package com.yourname.cloudclient.module;

import com.yourname.cloudclient.config.Config;

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