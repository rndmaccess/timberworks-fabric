package rndm_access.timberworks.recipe;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.SelectableRecipe;

public interface ModRecipeManager {
    RecipePropertySet getPropertySet(ResourceKey<RecipePropertySet> key);

    SelectableRecipe.SingleInputSet<WoodcuttingRecipe> getWoodcutterRecipes();
}
