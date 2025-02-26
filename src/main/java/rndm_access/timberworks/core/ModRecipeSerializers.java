package rndm_access.timberworks.core;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

public final class ModRecipeSerializers {
    public static final RecipeSerializer<WoodcuttingRecipe> WOODCUTTING
            = register("woodcutting", new WoodcuttingRecipe.Serializer<>(WoodcuttingRecipe::new));

    public static void register() {
        Timberworks.LOGGER.info("Registered recipe serializers");
    }

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        RegistryKey<RecipeSerializer<?>> serializerKey = RegistryKey.of(RegistryKeys.RECIPE_SERIALIZER, Timberworks.makeModId(id));
        return Registry.register(Registries.RECIPE_SERIALIZER, serializerKey, serializer);
    }
}
