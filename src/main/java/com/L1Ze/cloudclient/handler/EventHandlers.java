package com.yourname.cloudclient.handler;

import net.minecraftforge.common.MinecraftForge;

public class EventHandlers {
    public static void register() {
        MinecraftForge.EVENT_BUS.register(new FreeLookHandler());
        MinecraftForge.EVENT_BUS.register(new ShaderHandler());
        MinecraftForge.EVENT_BUS.register(new TranslationHandler());
        MinecraftForge.EVENT_BUS.register(new GeneralKeyHandler());
    }
}