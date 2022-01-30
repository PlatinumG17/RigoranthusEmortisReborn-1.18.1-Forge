package com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Used by block placing effects
 */
public interface IPlaceBlockResponder extends IInventoryResponder {
    /**
     * Called when an attempt to place a block is made. This is used by the PlaceBlock spell as a way to support automation entities.
     * @return Returns the itemstack that will attempt to be placed.
     */
    @Nonnull ItemStack onPlaceBlock();
}