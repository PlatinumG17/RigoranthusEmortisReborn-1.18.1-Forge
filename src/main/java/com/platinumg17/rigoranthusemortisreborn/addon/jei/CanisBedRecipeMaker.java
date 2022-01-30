package com.platinumg17.rigoranthusemortisreborn.addon.jei;

import com.platinumg17.rigoranthusemortisreborn.canis.common.util.CanisBedUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.IBeddingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CanisBedRecipeMaker {
    public static List<IShapedRecipe<? extends Container>> createCanisBedRecipes() {
        Collection<IBeddingMaterial> beddingMaterials = RigoranthusEmortisRebornAPI.BEDDING_MATERIAL.getValues();
        Collection<ICasingMaterial>  casingMaterials  = RigoranthusEmortisRebornAPI.CASING_MATERIAL.getValues();

        List<IShapedRecipe<? extends Container>> recipes = new ArrayList<>(beddingMaterials.size() * casingMaterials.size());
        String group = "rigoranthusemortisreborn.canisbed";
        for (IBeddingMaterial beddingId : RigoranthusEmortisRebornAPI.BEDDING_MATERIAL.getValues()) {
            for (ICasingMaterial casingId : RigoranthusEmortisRebornAPI.CASING_MATERIAL.getValues()) {

                Ingredient beddingIngredient = beddingId.getIngredient();
                Ingredient casingIngredient = casingId.getIngredient();
                NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                        casingIngredient, beddingIngredient, casingIngredient,
                        casingIngredient, beddingIngredient, casingIngredient,
                        casingIngredient, casingIngredient, casingIngredient
                );
                ItemStack output = CanisBedUtil.createItemStack(casingId, beddingId);

                ResourceLocation id = REUtil.getResource("" + output.getDescriptionId()); //TODO update resource location
                ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}