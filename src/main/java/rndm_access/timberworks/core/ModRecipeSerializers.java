package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

public final class ModRecipeSerializers {
    public static void register() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Timberworks.makeModId("woodcutting"), WoodcuttingRecipe.SERIALIZER);
        Timberworks.LOGGER.info("Registered recipe serializers");
    }
}
