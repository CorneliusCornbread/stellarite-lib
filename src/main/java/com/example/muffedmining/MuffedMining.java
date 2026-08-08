package com.example.muffedmining;

import com.example.muffedmining.registry.ModBlocks;
import com.example.muffedmining.registry.ModItems;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(MuffedMining.MOD_ID)
public final class MuffedMining {
    public static final String MOD_ID = "muffed_mining";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MuffedMining(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.register(modEventBus);
        LOGGER.info("Muffed Mining initialized");
    }
}
