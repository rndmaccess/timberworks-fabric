package rndm_access.timberworks.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.StonecutterRecipeDisplay;
import org.jspecify.annotations.NonNull;
import rndm_access.timberworks.core.ModBlocks;
import rndm_access.timberworks.core.ModRecipeBookCategories;
import rndm_access.timberworks.core.ModRecipeTypes;

import java.util.List;

public class WoodcuttingRecipe extends SingleItemRecipe {
    public static final MapCodec<WoodcuttingRecipe> MAP_CODEC = simpleMapCodec(WoodcuttingRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, WoodcuttingRecipe> STREAM_CODEC = simpleStreamCodec(WoodcuttingRecipe::new);
    public static final RecipeSerializer<WoodcuttingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public WoodcuttingRecipe(final Recipe.CommonInfo commonInfo, final Ingredient ingredient, final ItemStackTemplate result) {
        super(commonInfo, ingredient, result);
    }

    @Override
    public @NonNull RecipeType<WoodcuttingRecipe> getType() {
        return ModRecipeTypes.WOODCUTTING;
    }

    @Override
    public @NonNull RecipeSerializer<WoodcuttingRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull String group() {
        return "";
    }

    @Override
    public @NonNull List<RecipeDisplay> display() {
        return List.of(new StonecutterRecipeDisplay(this.input().display(), this.resultDisplay(),
                new SlotDisplay.ItemSlotDisplay(ModBlocks.WOODCUTTER.asItem())));
    }

    public SlotDisplay resultDisplay() {
        return new SlotDisplay.ItemStackSlotDisplay(this.result());
    }

    public @NonNull RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.WOODCUTTER;
    }
}
