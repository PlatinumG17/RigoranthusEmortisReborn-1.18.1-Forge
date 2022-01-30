package com.platinumg17.rigoranthusemortisreborn.api.apimagic.client.renderer;

import net.minecraft.world.item.ItemStack;

public interface IDisplayDominion {
    /**
     * If the held itemstack should display the player's dominion bar
     */
    default boolean shouldDisplay(ItemStack stack){
        return true;
    }
}