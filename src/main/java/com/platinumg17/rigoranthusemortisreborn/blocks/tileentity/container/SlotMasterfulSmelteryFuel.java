package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.SmelteryTileEntityBase;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SlotMasterfulSmelteryFuel extends Slot {
	SmelteryTileEntityBase te;

    public SlotMasterfulSmelteryFuel(SmelteryTileEntityBase te, int index, int x, int y) {
        super(te, index, x, y);
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return SmelteryTileEntityBase.isItemFuel(stack) || isBucket(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}