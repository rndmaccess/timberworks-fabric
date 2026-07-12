package rndm_access.timberworks;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rndm_access.timberworks.core.*;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

public class Timberworks implements ModInitializer {
	public static final String MOD_ID = "timberworks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        // General registration
        ModBlocks.register();
        ModSoundEvents.register();

        // Block entity registration
        ModRecipeDisplays.register();

        ModMenus.register();
        ModRecipeTypes.register();
        ModRecipeSerializers.register();
        RecipeSynchronization.synchronizeRecipeSerializer(WoodcuttingRecipe.SERIALIZER);
        ModRecipeBookCategories.register();

        // Entity registration
        ModPoiTypes.register();
        ModVillagerProfessions.register();
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