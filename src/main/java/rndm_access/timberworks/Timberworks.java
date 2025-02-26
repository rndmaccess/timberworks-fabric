package rndm_access.timberworks;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rndm_access.timberworks.core.*;

public class Timberworks implements ModInitializer {
	public static final String MOD_ID = "timberworks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        // General registration
        ModBlocks.register();
        ModSoundEvents.register();

        // Block entity registration
        ModScreenHandlerTypes.register();
        ModRecipeTypes.register();
        ModRecipeSerializers.register();
        ModRecipeBookCategories.register();

        // Entity registration
        ModPointOfInterestTypes.register();
        ModVillagerProfessions.register();
        ModVillagerOffers.register();
        ModVillagerTypes.register();

        Timberworks.addItemGroups();
	}

    public static Identifier makeModId(String path) {
        return Identifier.of(MOD_ID, path);
    }

    private static void addItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((entries) ->
                entries.addAfter(Blocks.STONECUTTER.asItem(), ModBlocks.WOODCUTTER.asItem()));
    }
}