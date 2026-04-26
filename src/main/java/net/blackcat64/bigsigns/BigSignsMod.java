package net.blackcat64.bigsigns;

import net.blackcat64.bigsigns.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = BigSignsMod.MODID, name = BigSignsMod.NAME, version = BigSignsMod.VERSION)
public class BigSignsMod
{
    public static final String MODID = "big_signs";
    public static final String NAME = "Big Signs (1.12 Port)";
    public static final String VERSION = "1.2";

    private static Logger logger;

    @SidedProxy(clientSide = "net.blackcat64.bigsigns.proxy.ClientProxy", serverSide = "net.blackcat64.bigsigns.proxy.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        logger.info("Big Signs Mod loaded");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
