package corneliuscorn.stellaritelib;

import com.mojang.logging.LogUtils;
import corneliuscorn.stellaritelib.registry.ModBlocks;
import corneliuscorn.stellaritelib.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(StellariteLib.MOD_ID)
public final class StellariteLib {
    public static final String MOD_ID = "stellarite_lib";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public StellariteLib(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.register(modEventBus);
        LOGGER.info("Stellarite Lib initialized");
    }
}
