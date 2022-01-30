package com.platinumg17.rigoranthusemortisreborn.addon.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicAmalgamatorRecipe;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PsyglyphicAmalgamatorRecipeCategory implements IRecipeCategory<PsyglyphicAmalgamatorRecipe> {
    public final static ResourceLocation UID = RigoranthusEmortisReborn.rl("amalgamator");

    IGuiHelper helper;
    public IDrawable background;
    public IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public PsyglyphicAmalgamatorRecipeCategory(IGuiHelper helper){
        this.helper = helper;
        background = helper.createDrawable(JEIConstants.RECIPE_GUI_AMALGAMATOR, 0, 0, 132, 84);
        icon = helper.createDrawableIngredient(new ItemStack(BlockRegistry.PSYGLYPHIC_AMALG_BLOCK));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<Integer, IDrawableAnimated>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return helper.drawableBuilder(JEIConstants.RECIPE_GUI_AMALGAMATOR, 132, 0, 22, 15)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class getRecipeClass() {
        return PsyglyphicAmalgamatorRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.rigoranthusemortisreborn.psyglyphic_amalgamator");
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
    public void draw(PsyglyphicAmalgamatorRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        IDrawableAnimated arrow = this.cachedArrows.getUnchecked(40);
        arrow.draw( matrixStack, 72, 27);
        Font renderer = Minecraft.getInstance().font;
        if(recipe.consumesDominion())
            renderer.draw(matrixStack, new TranslatableComponent("rigoranthusemortisreborn.dominion", recipe.dominionCost), 5f, 73f, 0);
    }

    @Override
    public void setIngredients(PsyglyphicAmalgamatorRecipe o, IIngredients iIngredients) {
        List<List<ItemStack>> itemStacks = new ArrayList<>();
        itemStacks.add(Arrays.asList(o.reagent.getItems()));
        itemStacks.add(Collections.singletonList(o.result));
        for(Ingredient i : o.pedestalItems){
            itemStacks.add(Arrays.asList(i.getItems()));
        }
        iIngredients.setInputLists(VanillaTypes.ITEM, itemStacks);
        iIngredients.setOutput(VanillaTypes.ITEM, o.result);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PsyglyphicAmalgamatorRecipe o, IIngredients ingredients) {
//        int index = 0;
        recipeLayout.getItemStacks().init(0, true, 48, 45);
        recipeLayout.getItemStacks().set(0, Arrays.asList(o.reagent.getItems()));

        int index = 1;
        double angleBetweenEach = 360.0 / ingredients.getInputs(VanillaTypes.ITEM).size();
        Vec2 point = new Vec2(48, 13), center = new Vec2(48, 45);

        for (List<ItemStack> s : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks().init(index, true, (int) point.x, (int) point.y);
            recipeLayout.getItemStacks().set(index, s);
            index += 1;
            point = rotatePointAbout(point, center, angleBetweenEach);
        }
        recipeLayout.getItemStacks().init(index, false, 86, 10);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }
//        recipeLayout.getItemStacks().init(index, true, 26, 26);
//        recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));
//        index++;
//        recipeLayout.getItemStacks().init(index, true, 105, 26);
//        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
//        try {
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 6, 6);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(2));
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 26, 6);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(3));
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 46, 6);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(4));
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 6, 26);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(5));
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 46, 26);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(6));
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 6, 46);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(7));
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 26, 46);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(8));
//            index++;
//            recipeLayout.getItemStacks().init(index, true, 46, 46);
//            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(9));
//        }catch (Exception e){}
//    }
}