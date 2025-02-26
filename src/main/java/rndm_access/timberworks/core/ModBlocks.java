package rndm_access.timberworks.core;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.block.WoodcutterBlock;

public final class ModBlocks {
    public static final RegistryKey<Block> WOODCUTTER_KEY = makeRegistryKey("woodcutter");
    public static final Block WOODCUTTER = register(new WoodcutterBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.SPRUCE_BROWN).strength(2.5F).sounds(BlockSoundGroup.WOOD).burnable()
            .registryKey(WOODCUTTER_KEY)), WOODCUTTER_KEY, true);

    public static void register() {
        Timberworks.LOGGER.info("Registered blocks!");
    }

    private static RegistryKey<Block> makeRegistryKey(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Timberworks.makeModId(name));
    }

    private static Block register(Block block, RegistryKey<Block> blockKey, boolean shouldRegisterItem) {
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
            Item.BLOCK_ITEMS.put(block, blockItem);
        }
        return Registry.register(Registries.BLOCK, blockKey, block);
    }
}
