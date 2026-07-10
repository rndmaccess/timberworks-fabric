package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

public final class ModRecipeSerializers {
    public static final RecipeSerializer<WoodcuttingRecipe> WOODCUTTING
            = register("woodcutting", new WoodcuttingRecipe.Serializer<>(WoodcuttingRecipe::new));

    public static void register() {
        Timberworks.LOGGER.info("Registered recipe serializers");
    }

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        ResourceKey<RecipeSerializer<?>> serializerKey = ResourceKey.create(Registries.RECIPE_SERIALIZER, Timberworks.makeModId(id));
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, serializerKey, serializer);
    }
}
