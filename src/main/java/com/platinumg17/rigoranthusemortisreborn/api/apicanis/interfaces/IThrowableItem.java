package com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces;

import net.minecraft.world.item.ItemStack;

public interface IThrowableItem {
    /**
     * The stack the canis drops upon return
     * @param stack The stack the canis fetched
     * @param type The type fetched stack
     */
    public ItemStack getReturnStack(ItemStack stack);

    /**
     * The stack the canis renders in mouth
     * @param stack The stack the canis fetched
     */
    public ItemStack getRenderStack(ItemStack stack);
}