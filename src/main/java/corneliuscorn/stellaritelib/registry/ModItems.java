package corneliuscorn.stellaritelib.registry;

import corneliuscorn.stellaritelib.StellariteLib;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
        DeferredRegister.createItems(StellariteLib.MOD_ID);

    public static final DeferredItem<BlockItem> MOON_TEST_ORE = ITEMS.registerSimpleBlockItem(
        ModBlocks.MOON_TEST_ORE);
    
    public static final DeferredItem<BlockItem> RAW_MOON_TEST_ORE_BLOCK =
        ITEMS.registerSimpleBlockItem(
        ModBlocks.RAW_MOON_TEST_ORE_BLOCK);

    public static final DeferredItem<BlockItem> MOON_TEST_ORE_BLOCK = ITEMS.registerSimpleBlockItem(
        ModBlocks.MOON_TEST_ORE_BLOCK);

    public static final DeferredItem<Item> RAW_MOON_TEST_ORE = ITEMS.registerSimpleItem(
        "raw_moon_test_ore");

    public static final DeferredItem<Item> MOON_TEST_ORE_INGOT = ITEMS.registerSimpleItem(
        "moon_test_ore_ingot");

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(ModItems::addCreativeTabItems);
    }

    private static void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(RAW_MOON_TEST_ORE);
            event.accept(MOON_TEST_ORE_INGOT);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS || event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(MOON_TEST_ORE);
            event.accept(RAW_MOON_TEST_ORE_BLOCK);
            event.accept(MOON_TEST_ORE_BLOCK);
        }
    }
}
