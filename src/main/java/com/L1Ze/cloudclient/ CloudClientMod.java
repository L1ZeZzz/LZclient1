package dev.l1zezz.lzclient;

import dev.l1zezz.lzclient.config.Config;
import dev.l1zezz.lzclient.handler.EventHandlers;
import dev.l1zezz.lzclient.keybind.KeyBindings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CloudClientMod.MODID, name = "LZ Client", version = CloudClientMod.VERSION, clientSideOnly = true)
public class CloudClientMod {
    public static final String MODID = "lzclient";
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
