package rndm_access.timberworks.recipe;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.recipe.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import rndm_access.timberworks.core.ModBlocks;
import rndm_access.timberworks.core.ModRecipeBookCategories;
import rndm_access.timberworks.core.ModRecipeSerializers;
import rndm_access.timberworks.core.ModRecipeTypes;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class WoodcuttingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final String group;
    @Nullable
    private PlacementInfo ingredientPlacement;

    public WoodcuttingRecipe(String group, Ingredient ingredient, ItemStack result) {
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public RecipeType<WoodcuttingRecipe> getType() {
        return ModRecipeTypes.WOODCUTTING;
    }

    @Override
    public RecipeSerializer<WoodcuttingRecipe> getSerializer() {
        return ModRecipeSerializers.WOODCUTTING;
    }

    public List<RecipeDisplay> display() {
        SlotDisplay ingredientDisplay = this.getIngredient().display();
        Item woodcutterItem = ModBlocks.WOODCUTTER.asItem();
        SlotDisplay.ItemSlotDisplay craftingStationDisplay = new SlotDisplay.ItemSlotDisplay(woodcutterItem);
        return List.of(new WoodcutterRecipeDisplay(ingredientDisplay, this.createResultDisplay(), craftingStationDisplay));
    }

    public SlotDisplay createResultDisplay() {
        return new SlotDisplay.ItemStackSlotDisplay(this.getResult());
    }

    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.WOODCUTTER;
    }

    public boolean matches(SingleRecipeInput singleStackRecipeInput, Level world) {
        return this.ingredient.test(singleStackRecipeInput.item());
    }

    public @NonNull String group() {
        return this.group;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    protected ItemStack getResult() {
        return this.result;
    }

    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.create(this.ingredient);
        }
        return this.ingredientPlacement;
    }

    public ItemStack craft(SingleRecipeInput singleStackRecipeInput, HolderLookup.Provider wrapperLookup) {
        return this.result.copy();
    }

    public static class Serializer<T extends WoodcuttingRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, T> packetCodec;

        public Serializer(RecipeFactory<T> recipeFactory) {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> {
                Products.P3<RecordCodecBuilder.Mu<T>, String, Ingredient, ItemStack> var10000 =
                        instance.group(Codec.STRING.optionalFieldOf("group", "")
                        .forGetter(WoodcuttingRecipe::group), Ingredient.CODEC.fieldOf("ingredient")
                        .forGetter(WoodcuttingRecipe::getIngredient), ItemStack.STRICT_CODEC.fieldOf("result")
                        .forGetter(WoodcuttingRecipe::getResult));

                Objects.requireNonNull(recipeFactory);
                return var10000.apply(instance, recipeFactory::create);
            });

            StreamCodec<ByteBuf, String> groupCodec = ByteBufCodecs.STRING_UTF8;
            Function<T, String> groupFunc = WoodcuttingRecipe::group;
            StreamCodec<RegistryFriendlyByteBuf, Ingredient> ingredientCodec = Ingredient.CONTENTS_STREAM_CODEC;
            Function<T, Ingredient> ingredientFunc = WoodcuttingRecipe::getIngredient;
            StreamCodec<RegistryFriendlyByteBuf, ItemStack> resultCodec = ItemStack.STREAM_CODEC;
            Function<T, ItemStack> resultFunc = WoodcuttingRecipe::getResult;
            Objects.requireNonNull(recipeFactory);
            this.packetCodec = StreamCodec.composite(groupCodec, groupFunc, ingredientCodec, ingredientFunc, resultCodec,
                    resultFunc, recipeFactory::create);
        }

        public Serializer(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
            this.codec = codec;
            this.packetCodec = packetCodec;
        }

        public MapCodec<T> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return this.packetCodec;
        }
    }

    @FunctionalInterface
    public interface RecipeFactory<T extends WoodcuttingRecipe> {
        T create(String group, Ingredient ingredient, ItemStack result);
    }
}
