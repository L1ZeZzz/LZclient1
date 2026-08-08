package com.yourname.cloudclient;

import com.yourname.cloudclient.config.Config;
import com.yourname.cloudclient.handler.EventHandlers;
import com.yourname.cloudclient.keybind.KeyBindings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CloudClientMod.MODID, version = CloudClientMod.VERSION)
public class CloudClientMod {
    public static final String MODID = "cloudclient";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        KeyBindings.register();
        EventHandlers.register();
    }
}
