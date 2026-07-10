package rndm_access.timberworks.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record WoodcutterRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    public static final MapCodec<WoodcutterRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(SlotDisplay.CODEC.fieldOf("input")
                    .forGetter(WoodcutterRecipeDisplay::input), SlotDisplay.CODEC.fieldOf("result")
                    .forGetter(WoodcutterRecipeDisplay::result), SlotDisplay.CODEC.fieldOf("crafting_station")
                    .forGetter(WoodcutterRecipeDisplay::craftingStation)).apply(instance, WoodcutterRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, WoodcutterRecipeDisplay> PACKET_CODEC;
    public static final RecipeDisplay.Type<WoodcutterRecipeDisplay> SERIALIZER;

    public WoodcutterRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) {
        this.input = input;
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public RecipeDisplay.Type<WoodcutterRecipeDisplay> type() {
        return SERIALIZER;
    }

    public SlotDisplay input() {
        return this.input;
    }

    public SlotDisplay result() {
        return this.result;
    }

    public SlotDisplay craftingStation() {
        return this.craftingStation;
    }

    static {
        PACKET_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, WoodcutterRecipeDisplay::input,
                SlotDisplay.STREAM_CODEC, WoodcutterRecipeDisplay::result, SlotDisplay.STREAM_CODEC,
                WoodcutterRecipeDisplay::craftingStation, WoodcutterRecipeDisplay::new);
        SERIALIZER = new RecipeDisplay.Type<>(CODEC, PACKET_CODEC);
    }
}
