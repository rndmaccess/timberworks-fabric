package rndm_access.timberworks.recipe;

import net.minecraft.recipe.RecipePropertySet;
import net.minecraft.recipe.display.CuttingRecipeDisplay;
import net.minecraft.registry.RegistryKey;

public interface ModRecipeManager {
    RecipePropertySet getPropertySet(RegistryKey<RecipePropertySet> key);

    CuttingRecipeDisplay.Grouping<WoodcuttingRecipe> getWoodcutterRecipes();
}
