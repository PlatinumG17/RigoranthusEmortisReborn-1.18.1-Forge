package com.platinumg17.rigoranthusemortisreborn.canis.common.inventory;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.FoodHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TreatBagItemHandler extends ItemStackHandler {
    private ItemStack bag;
    public TreatBagItemHandler(ItemStack bag) {
        super(7);
        this.bag = bag;
        CompoundTag inventoryNBT = bag.getTagElement("inventory");
        if (inventoryNBT != null) {
            this.deserializeNBT(inventoryNBT);
        }
    }
    @Override
    protected void onContentsChanged(int slot) {
        this.bag.getOrCreateTagElement("inventory").merge(this.serializeNBT());
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return CanisTags.TREATS.contains(stack.getItem()) || FoodHandler.isFood(stack).isPresent();
    }
}