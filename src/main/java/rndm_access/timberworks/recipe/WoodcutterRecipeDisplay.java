package rndm_access.timberworks.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;

public record WoodcutterRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    public static final MapCodec<WoodcutterRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(SlotDisplay.CODEC.fieldOf("input")
                    .forGetter(WoodcutterRecipeDisplay::input), SlotDisplay.CODEC.fieldOf("result")
                    .forGetter(WoodcutterRecipeDisplay::result), SlotDisplay.CODEC.fieldOf("crafting_station")
                    .forGetter(WoodcutterRecipeDisplay::craftingStation)).apply(instance, WoodcutterRecipeDisplay::new));
    public static final PacketCodec<RegistryByteBuf, WoodcutterRecipeDisplay> PACKET_CODEC;
    public static final RecipeDisplay.Serializer<WoodcutterRecipeDisplay> SERIALIZER;

    public WoodcutterRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) {
        this.input = input;
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public RecipeDisplay.Serializer<WoodcutterRecipeDisplay> serializer() {
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
        PACKET_CODEC = PacketCodec.tuple(SlotDisplay.PACKET_CODEC, WoodcutterRecipeDisplay::input,
                SlotDisplay.PACKET_CODEC, WoodcutterRecipeDisplay::result, SlotDisplay.PACKET_CODEC,
                WoodcutterRecipeDisplay::craftingStation, WoodcutterRecipeDisplay::new);
        SERIALIZER = new RecipeDisplay.Serializer<>(CODEC, PACKET_CODEC);
    }
}
