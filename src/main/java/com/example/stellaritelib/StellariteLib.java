package com.example.stellaritelib;

import com.example.stellaritelib.registry.ModBlocks;
import com.example.stellaritelib.registry.ModItems;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

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
