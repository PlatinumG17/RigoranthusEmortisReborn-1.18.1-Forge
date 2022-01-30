package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.CraftingPressRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class CraftingPressRecipeBuilder {

    CraftingPressRecipe recipe;
    public CraftingPressRecipeBuilder() { this.recipe = new CraftingPressRecipe(); }

    public static CraftingPressRecipeBuilder builder() { return new CraftingPressRecipeBuilder(); }

    public CraftingPressRecipeBuilder withResult(ItemLike output) {
        this.recipe.output = new ItemStack(output);
        return this;
    }
    public CraftingPressRecipeBuilder withResult(ItemStack output){
        this.recipe.output = output;
        return this;
    }

//    public CraftingPressRecipeBuilder withCategory(RigoranthusEmortisRebornAPI.PatchouliCategories category){
//        this.recipe.category = category.name();
//        return this;
//    }

    public CraftingPressRecipeBuilder withReagent(ItemLike reagentProvider){
        this.recipe.reagent = Ingredient.of(reagentProvider);
        return this;
    }

    public CraftingPressRecipeBuilder withReagent(Ingredient reagentItem){
        this.recipe.reagent = reagentItem;
        return this;
    }

    public CraftingPressRecipeBuilder withBaseItem(Ingredient baseItem){
        this.recipe.base = baseItem;
        return this;
    }

    public CraftingPressRecipeBuilder withBaseItem(ItemLike baseProvider){
        this.recipe.base = Ingredient.of(baseProvider);
        return this;
    }

    public CraftingPressRecipe build(){
        if(recipe.id.getPath().equals("empty"))
            recipe.id = RigoranthusEmortisReborn.rl(recipe.output.getItem().getRegistryName().getPath());
        return recipe;
    }
}