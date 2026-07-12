package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.*;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

public final class ModRecipeTypes {
    public static final RecipeType<WoodcuttingRecipe> WOODCUTTING = register("woodcutting");

    public static void register() {
        Timberworks.LOGGER.info("Registered recipe types.");
    }

    private static <T extends Recipe<?>> RecipeType<T> register(final String path) {
        RecipeType<T> recipeType = new RecipeType<>() {
            public String toString() {
                return Timberworks.makeModId(path).toString();
            }
        };
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, Timberworks.makeModId(path), recipeType);
    }
}