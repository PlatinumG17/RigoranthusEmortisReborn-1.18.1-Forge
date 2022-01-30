package com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ICanisFoodPredicate {
    /**
     * Determines if the stack could ever be food for a canis, i.e
     * the stack could be fed to the canis under certain conditions
     * Used to check if the stack can go in food bowl or treat bag
     * @param stackIn The stack
     * @return If the start could ever be fed to a canis
     */
    public boolean isFood(ItemStack stackIn);
}