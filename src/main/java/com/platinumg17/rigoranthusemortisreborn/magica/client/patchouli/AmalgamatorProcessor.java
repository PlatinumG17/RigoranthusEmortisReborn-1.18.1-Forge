package com.platinumg17.rigoranthusemortisreborn.magica.client.patchouli;
//
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicAmalgamatorRecipe;
//import net.minecraft.client.Minecraft;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.item.crafting.RecipeManager;
//import net.minecraft.resources.ResourceLocation;
//import vazkii.patchouli.api.IComponentProcessor;
//import vazkii.patchouli.api.IVariable;
//import vazkii.patchouli.api.IVariableProvider;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//public class AmalgamatorProcessor implements IComponentProcessor {
//    PsyglyphicAmalgamatorRecipe recipe;
//
//    @Override
//    public void setup(IVariableProvider variables) {
//        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
//        String recipeID = variables.get("recipe").asString();
//        recipe = (PsyglyphicAmalgamatorRecipe) manager.byKey(new ResourceLocation(recipeID)).orElse(null);
//    }
//    @Override
//    public IVariable process(String key) {
//        if (recipe == null)
//            return null;
//        if (key.equals("reagent"))
//            return IVariable.wrapList(Arrays.asList(recipe.reagent.getItems()).stream().map(IVariable::from).collect(Collectors.toList()));
//        if (key.startsWith("item")) {
//            int index = Integer.parseInt(key.substring(4)) - 1;
//            if (recipe.pedestalItems.size() <= index)
//                return IVariable.from(ItemStack.EMPTY);
//            Ingredient ingredient = recipe.pedestalItems.get(Integer.parseInt(key.substring(4)) - 1);
//            return IVariable.wrapList(Arrays.asList(ingredient.getItems()).stream().map(IVariable::from).collect(Collectors.toList()));
//        }
//        if (key.equals("footer")) {
//            return IVariable.wrap(recipe.result.getItem().getDescriptionId());
//        }
//        return null;
//    }
//}