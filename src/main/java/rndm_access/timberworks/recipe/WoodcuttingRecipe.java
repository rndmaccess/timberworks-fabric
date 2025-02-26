package rndm_access.timberworks.recipe;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rndm_access.timberworks.core.ModBlocks;
import rndm_access.timberworks.core.ModRecipeBookCategories;
import rndm_access.timberworks.core.ModRecipeSerializers;
import rndm_access.timberworks.core.ModRecipeTypes;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class WoodcuttingRecipe implements Recipe<SingleStackRecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final String group;
    @Nullable
    private IngredientPlacement ingredientPlacement;

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

    public List<RecipeDisplay> getDisplays() {
        SlotDisplay ingredientDisplay = this.getIngredient().toDisplay();
        Item woodcutterItem = ModBlocks.WOODCUTTER.asItem();
        SlotDisplay.ItemSlotDisplay craftingStationDisplay = new SlotDisplay.ItemSlotDisplay(woodcutterItem);
        return List.of(new WoodcutterRecipeDisplay(ingredientDisplay, this.createResultDisplay(), craftingStationDisplay));
    }

    public SlotDisplay createResultDisplay() {
        return new SlotDisplay.StackSlotDisplay(this.getResult());
    }

    public RecipeBookCategory getRecipeBookCategory() {
        return ModRecipeBookCategories.WOODCUTTER;
    }

    public boolean matches(SingleStackRecipeInput singleStackRecipeInput, World world) {
        return this.ingredient.test(singleStackRecipeInput.item());
    }

    public String getGroup() {
        return this.group;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    protected ItemStack getResult() {
        return this.result;
    }

    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forSingleSlot(this.ingredient);
        }
        return this.ingredientPlacement;
    }

    public ItemStack craft(SingleStackRecipeInput singleStackRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.result.copy();
    }

    public static class Serializer<T extends WoodcuttingRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        private final PacketCodec<RegistryByteBuf, T> packetCodec;

        public Serializer(RecipeFactory<T> recipeFactory) {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> {
                Products.P3<RecordCodecBuilder.Mu<T>, String, Ingredient, ItemStack> var10000 =
                        instance.group(Codec.STRING.optionalFieldOf("group", "")
                        .forGetter(WoodcuttingRecipe::getGroup), Ingredient.CODEC.fieldOf("ingredient")
                        .forGetter(WoodcuttingRecipe::getIngredient), ItemStack.VALIDATED_CODEC.fieldOf("result")
                        .forGetter(WoodcuttingRecipe::getResult));

                Objects.requireNonNull(recipeFactory);
                return var10000.apply(instance, recipeFactory::create);
            });

            PacketCodec<ByteBuf, String> groupCodec = PacketCodecs.STRING;
            Function<T, String> groupFunc = WoodcuttingRecipe::getGroup;
            PacketCodec<RegistryByteBuf, Ingredient> ingredientCodec = Ingredient.PACKET_CODEC;
            Function<T, Ingredient> ingredientFunc = WoodcuttingRecipe::getIngredient;
            PacketCodec<RegistryByteBuf, ItemStack> resultCodec = ItemStack.PACKET_CODEC;
            Function<T, ItemStack> resultFunc = WoodcuttingRecipe::getResult;
            Objects.requireNonNull(recipeFactory);
            this.packetCodec = PacketCodec.tuple(groupCodec, groupFunc, ingredientCodec, ingredientFunc, resultCodec,
                    resultFunc, recipeFactory::create);
        }

        public Serializer(MapCodec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {
            this.codec = codec;
            this.packetCodec = packetCodec;
        }

        public MapCodec<T> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, T> packetCodec() {
            return this.packetCodec;
        }
    }

    @FunctionalInterface
    public interface RecipeFactory<T extends WoodcuttingRecipe> {
        T create(String group, Ingredient ingredient, ItemStack result);
    }
}
