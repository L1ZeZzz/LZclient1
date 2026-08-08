package com.yourname.cloudclient.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class Config {
    private static Configuration config;

    // 各功能开关
    public static boolean motionBlurEnabled = true;
    public static boolean freeLookEnabled = true;
    public static boolean translationEnabled = true;

    // 动态模糊强度 (0~100)
    public static int motionBlurIntensity = 50;

    // 百度翻译 API 配置
    public static String baiduAppId = "";
    public static String baiduSecret = "";

    public static void load(File file) {
        config = new Configuration(file);
        config.load();

        Property propMotionBlur = config.get(Configuration.CATEGORY_CLIENT, "motionBlurEnabled", true);
        motionBlurEnabled = propMotionBlur.getBoolean();

        Property propFreeLook = config.get(Configuration.CATEGORY_CLIENT, "freeLookEnabled", true);
        freeLookEnabled = propFreeLook.getBoolean();

        Property propTranslation = config.get(Configuration.CATEGORY_CLIENT, "translationEnabled", true);
        translationEnabled = propTranslation.getBoolean();

        Property propIntensity = config.get(Configuration.CATEGORY_CLIENT, "motionBlurIntensity", 50);
        propIntensity.setMinValue(0).setMaxValue(100);
        motionBlurIntensity = propIntensity.getInt();

        Property propAppId = config.get(Configuration.CATEGORY_CLIENT, "baiduAppId", "");
        baiduAppId = propAppId.getString();

        Property propSecret = config.get(Configuration.CATEGORY_CLIENT, "baiduSecret", "");
        baiduSecret = propSecret.getString();

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void save() {
        config.get(Configuration.CATEGORY_CLIENT, "motionBlurEnabled", true).set(motionBlurEnabled);
        config.get(Configuration.CATEGORY_CLIENT, "freeLookEnabled", true).set(freeLookEnabled);
        config.get(Configuration.CATEGORY_CLIENT, "translationEnabled", true).set(translationEnabled);
        config.get(Configuration.CATEGORY_CLIENT, "motionBlurIntensity", 50).set(motionBlurIntensity);
        config.get(Configuration.CATEGORY_CLIENT, "baiduAppId", "").set(baiduAppId);
        config.get(Configuration.CATEGORY_CLIENT, "baiduSecret", "").set(baiduSecret);
        config.save();
    }
}