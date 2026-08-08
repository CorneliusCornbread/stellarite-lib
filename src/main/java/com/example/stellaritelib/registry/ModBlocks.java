package com.example.stellaritelib.registry;

import com.example.stellaritelib.StellariteLib;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(StellariteLib.MOD_ID);

    public static final DeferredBlock<Block> MOON_TEST_ORE = BLOCKS.registerSimpleBlock("moon_test_ore",
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<Block> RAW_MOON_TEST_ORE_BLOCK = BLOCKS.registerSimpleBlock("raw_moon_test_ore_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final DeferredBlock<Block> MOON_TEST_ORE_BLOCK = BLOCKS.registerSimpleBlock("moon_test_ore_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));

    private ModBlocks() {}
}
