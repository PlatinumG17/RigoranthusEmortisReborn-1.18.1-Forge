package com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.EmorticCraftingPressTile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;

public interface IIchoricRecipe extends Recipe<EmorticCraftingPressTile> {

    boolean isMatch(ItemStack base, ItemStack reagent, EmorticCraftingPressTile emorticCraftingPressTile, @Nullable Player player);
    /**
     * Tile sensitive result
     */
    ItemStack getResult(ItemStack base, ItemStack reagent, EmorticCraftingPressTile emorticCraftingPressTile);

    default boolean consumesDominion(){
        return dominionCost() > 0;
    }
    int dominionCost();
}