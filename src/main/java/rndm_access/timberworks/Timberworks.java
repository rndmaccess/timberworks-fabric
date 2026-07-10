package rndm_access.timberworks;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
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
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    private static void addItemGroups() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register((entries) ->
                entries.insertAfter(Blocks.STONECUTTER.asItem(), ModBlocks.WOODCUTTER.asItem()));
    }
}