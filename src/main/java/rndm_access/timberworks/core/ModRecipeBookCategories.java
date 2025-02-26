package rndm_access.timberworks.core;

import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rndm_access.timberworks.Timberworks;

public final class ModRecipeBookCategories {
    public static final RecipeBookCategory WOODCUTTER = register("woodcutter", new RecipeBookCategory());

    public static void register() {
        Timberworks.LOGGER.info("Registered recipe book categories");
    }

    private static RecipeBookCategory register(String id, RecipeBookCategory category) {
        Identifier recipeBookId = Timberworks.makeModId(id);
        return Registry.register(Registries.RECIPE_BOOK_CATEGORY, recipeBookId, category);
    }
}
