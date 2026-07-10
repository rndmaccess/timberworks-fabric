package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import rndm_access.timberworks.Timberworks;

public final class ModRecipeBookCategories {
    public static final RecipeBookCategory WOODCUTTER = register(new RecipeBookCategory());

    public static void register() {
        Timberworks.LOGGER.info("Registered recipe book categories");
    }

    private static RecipeBookCategory register(RecipeBookCategory category) {
        Identifier recipeBookId = Timberworks.makeModId("woodcutter");
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, recipeBookId, category);
    }
}
