package rndm_access.timberworks.screen;

import net.minecraft.recipe.RecipePropertySet;
import net.minecraft.recipe.display.CuttingRecipeDisplay;
import net.minecraft.registry.RegistryKey;
import rndm_access.timberworks.recipe.ModRecipeManager;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

import java.util.Map;

public class ModClientRecipeManager implements ModRecipeManager {
    private final Map<RegistryKey<RecipePropertySet>, RecipePropertySet> propertySets;
    private final CuttingRecipeDisplay.Grouping<WoodcuttingRecipe> recipes;

    public ModClientRecipeManager(Map<RegistryKey<RecipePropertySet>, RecipePropertySet> propertySets,
                                  CuttingRecipeDisplay.Grouping<WoodcuttingRecipe> recipes) {
        this.propertySets = propertySets;
        this.recipes = recipes;
    }

    public RecipePropertySet getPropertySet(RegistryKey<RecipePropertySet> key) {
        return this.propertySets.getOrDefault(key, RecipePropertySet.EMPTY);
    }

    public CuttingRecipeDisplay.Grouping<WoodcuttingRecipe> getWoodcutterRecipes() {
        return this.recipes;
    }
}
