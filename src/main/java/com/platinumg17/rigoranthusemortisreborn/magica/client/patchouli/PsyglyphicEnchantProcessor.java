package com.platinumg17.rigoranthusemortisreborn.magica.client.patchouli;

//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicEnchantingRecipe;
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
//public class PsyglyphicEnchantProcessor implements IComponentProcessor {
//    PsyglyphicEnchantingRecipe recipe;
//    @Override
//    public void setup(IVariableProvider variables) {
//        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
//        String recipeID = variables.get("recipe").asString();
//        recipe = (PsyglyphicEnchantingRecipe) manager.byKey(new ResourceLocation(recipeID)).orElse(null);
//    }
//    @Override
//    public IVariable process(String key) {
//        if(recipe == null)
//            return null;
//        if(key.equals("enchantment"))
//            return IVariable.wrap(recipe.enchantment.getDescriptionId());
//        if(key.equals("level"))
//            return IVariable.wrap(recipe.enchantLevel);
//        if(key.startsWith("item")) {
//            int index = Integer.parseInt(key.substring(4)) - 1;
//            if(recipe.pedestalItems.size() <= index)
//                return IVariable.from(ItemStack.EMPTY);
//            Ingredient ingredient = recipe.pedestalItems.get(Integer.parseInt(key.substring(4)) - 1);
//            return IVariable.wrapList(Arrays.asList(ingredient.getItems()).stream().map(IVariable::from).collect(Collectors.toList()));
//        }
//        return null;
//    }
//}
