package rndm_access.timberworks.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.core.ModBlocks;
import rndm_access.timberworks.core.ModRecipeTypes;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

import java.util.Objects;

public class ReiTimberworksClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.addWorkstations(ReiIds.WOOD_CUTTING, EntryStacks.of(ModBlocks.WOODCUTTER.asItem()));
        registry.add(new WoodcutterCategory());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        var recipes = Objects.requireNonNull(Minecraft.getInstance().getConnection()).recipes().getSynchronizedRecipes()
                .getAllOfType(ModRecipeTypes.WOODCUTTING);

        Timberworks.LOGGER.info("Registering woodcutting recipes for REI, count: {}", recipes.size());

        for (RecipeHolder<WoodcuttingRecipe> recipe : recipes) {
            registry.add(new WoodcuttingDisplay(recipe));
        }
    }
}
