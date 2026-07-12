package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.recipe.WoodcutterRecipeDisplay;

public class ModRecipeDisplays {
    public static void register() {
        Registry.register(BuiltInRegistries.RECIPE_DISPLAY, Timberworks.makeModId("woodcutter"), WoodcutterRecipeDisplay.TYPE);
    }
}
