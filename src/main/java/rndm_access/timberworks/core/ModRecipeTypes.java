package rndm_access.timberworks.core;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

public final class ModRecipeTypes {
    public static final RecipeType<WoodcuttingRecipe> WOODCUTTING = register("woodcutting");

    public static void register() {
        Timberworks.LOGGER.info("Registered recipe types.");
    }

    private static <T extends Recipe<?>> RecipeType<T> register(String path) {
        RecipeType<T> recipeType = new RecipeType<>() {
            public String toString() {
                return Timberworks.makeModId(path).toString();
            }
        };
        return Registry.register(Registries.RECIPE_TYPE, Timberworks.makeModId(path), recipeType);
    }
}