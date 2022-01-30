package com.platinumg17.rigoranthusemortisreborn.addon.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.CraftingPressRecipe;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CraftingPressRecipeCategory implements IRecipeCategory<CraftingPressRecipe> {

    public IDrawable background;
    public IDrawable icon;
    IGuiHelper helper;
    public final static ResourceLocation UID = RigoranthusEmortisReborn.rl("crafting_press_recipe");
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public CraftingPressRecipeCategory(IGuiHelper helper){
        this.helper = helper;
        background = helper.createDrawable(JEIConstants.RECIPE_GUI_PRESS, 0, 0, 117, 46);
        icon = helper.createDrawableIngredient(new ItemStack(BlockRegistry.EMORTIC_CRAFTING_PRESS_BLOCK));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<Integer, IDrawableAnimated>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return helper.drawableBuilder(JEIConstants.RECIPE_GUI_PRESS, 132, 0, 22, 15)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends CraftingPressRecipe> getRecipeClass() {
        return CraftingPressRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.rigoranthusemortisreborn.emortic_crafting_press");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(CraftingPressRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        IDrawableAnimated arrow = this.cachedArrows.getUnchecked(40);
        arrow.draw( matrixStack, 57, 8);
        Font renderer = Minecraft.getInstance().font;
        if(recipe.consumesDominion())
            renderer.draw(matrixStack, new TranslatableComponent("rigoranthusemortisreborn.dominion", recipe.dominionCost), 6f, 35f, 0);
    }

    @Override
    public void setIngredients(CraftingPressRecipe craftingPressRecipe, IIngredients iIngredients) {
        List<List<ItemStack>> itemStacks = new ArrayList<>();
        itemStacks.add(Arrays.asList(craftingPressRecipe.base.getItems()));
        itemStacks.add(Arrays.asList(craftingPressRecipe.reagent.getItems()));
        itemStacks.add(Collections.singletonList(craftingPressRecipe.output));
        iIngredients.setInputLists(VanillaTypes.ITEM, itemStacks);
        iIngredients.setOutput(VanillaTypes.ITEM, craftingPressRecipe.output);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CraftingPressRecipe o, IIngredients ingredients) {
        int index = 0;
        recipeLayout.getItemStacks().init(index, true, 10, 7);
        recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        index++;
        recipeLayout.getItemStacks().init(index, true, 30, 7);
        recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(1));
        index++;
        recipeLayout.getItemStacks().init(index, true, 88, 7);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}