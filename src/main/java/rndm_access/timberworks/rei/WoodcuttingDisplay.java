package rndm_access.timberworks.rei;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

import java.util.List;
import java.util.Optional;

public class WoodcuttingDisplay extends BasicDisplay {
    public static final DisplaySerializer<WoodcuttingDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(WoodcuttingDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(WoodcuttingDisplay::getOutputEntries),
                    Identifier.CODEC.optionalFieldOf("location").forGetter(WoodcuttingDisplay::getDisplayLocation)
            ).apply(instance, WoodcuttingDisplay::new)),
            StreamCodec.composite(
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    WoodcuttingDisplay::getInputEntries,
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    WoodcuttingDisplay::getOutputEntries,
                    ByteBufCodecs.optional(Identifier.STREAM_CODEC),
                    WoodcuttingDisplay::getDisplayLocation,
                    WoodcuttingDisplay::new
            ));

    public WoodcuttingDisplay(RecipeHolder<WoodcuttingRecipe> recipe) {
        this(List.of(EntryIngredients.ofIngredient(recipe.value().input())),
                List.of(EntryIngredients.ofSlotDisplay(recipe.value().resultDisplay())),
                Optional.of(recipe.id().identifier()));
    }

    public WoodcuttingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ReiIds.WOOD_CUTTING;
    }

    @Override
    public DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
