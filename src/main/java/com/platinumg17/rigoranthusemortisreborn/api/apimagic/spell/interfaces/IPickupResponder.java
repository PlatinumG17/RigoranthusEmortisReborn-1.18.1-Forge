package com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Used by spellcasting entities or blocks that support receiving items.
 * See #EffectPickup
 */
public interface IPickupResponder extends IInventoryResponder {
    /**
     * Called when an attempt to pickup loot is made. This is primarily used by EffectPickup for giving items to objects and non-player entities.
     * @param stack Itemstack that will attempt to be put into the inventory.
     * @return Returns the resulting itemstack
     */
    @Nonnull ItemStack onPickup(ItemStack stack);
}