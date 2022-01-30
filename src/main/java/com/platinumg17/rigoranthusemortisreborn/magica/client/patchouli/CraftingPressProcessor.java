package com.platinumg17.rigoranthusemortisreborn.magica.client.patchouli;

//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.CraftingPressRecipe;
//import net.minecraft.client.Minecraft;
//import net.minecraft.item.crafting.RecipeManager;
//import net.minecraft.resources.ResourceLocation;
//import vazkii.patchouli.api.IComponentProcessor;
//import vazkii.patchouli.api.IVariable;
//import vazkii.patchouli.api.IVariableProvider;
//
//public class CraftingPressProcessor implements IComponentProcessor {
//    CraftingPressRecipe recipe;
//    @Override
//    public void setup(IVariableProvider variables) {
//        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
//        String recipeID = variables.get("recipe").asString();
//        recipe = (CraftingPressRecipe) manager.byKey(new ResourceLocation(recipeID)).orElse(null);
//    }
//    @Override
//    public IVariable process(String key) {
//        if(recipe == null)
//            return null;
//        if(key.equals("base"))
//            return IVariable.from(recipe.base);
//        if(key.equals("reagent"))
//            return IVariable.from(recipe.reagent);
//        if (key.equals("footer")) {
//            return IVariable.wrap(recipe.output.getItem().getDescriptionId());
//        }
//        if(key.equals("dominion_cost") ){
//            return IVariable.wrap("");
//        }
//        return null;
//    }
//}