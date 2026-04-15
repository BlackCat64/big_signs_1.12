package net.blackcat64.bigsigns;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = BigSignsMod.MOD_ID)
public class BigSignsMod {
    
    public static final String MOD_ID = "big_signs";
    public static final Logger LOGGER = LogManager.getLogger("Big Signs");
    
    public BigSignsMod() {
        
//    	final ModContainer container = Loader.instance().getIndexedModList().get(MOD_ID);
//    	LOGGER.info("Loaded {} v{}", container.getName(), container.getDisplayVersion());
        LOGGER.info("Example Mod");
    }
}