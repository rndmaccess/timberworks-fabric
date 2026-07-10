package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.block.WoodcutterBlock;

public final class ModBlocks {
    public static final ResourceKey<Block> WOODCUTTER_KEY = createKey();
    public static final Block WOODCUTTER = register(new WoodcutterBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PODZOL).strength(2.5F).sound(SoundType.WOOD).ignitedByLava()
            .setId(WOODCUTTER_KEY)));

    public static void register() {
        Timberworks.LOGGER.info("Registered blocks!");
    }

    private static ResourceKey<Block> createKey() {
        return ResourceKey.create(Registries.BLOCK, Timberworks.makeModId("woodcutter"));
    }

    private static Block register(Block block) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ModBlocks.WOODCUTTER_KEY.identifier());
        BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        Item.BY_BLOCK.put(block, blockItem);
        return Registry.register(BuiltInRegistries.BLOCK, ModBlocks.WOODCUTTER_KEY, block);
    }
}
