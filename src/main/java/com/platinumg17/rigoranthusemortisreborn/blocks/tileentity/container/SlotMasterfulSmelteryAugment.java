package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container;
import com.platinumg17.rigoranthusemortisreborn.items.specialized.smeltery.ItemAugment;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.SmelteryTileEntityBase;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotMasterfulSmelteryAugment extends Slot {

    private SmelteryTileEntityBase te;

    public SlotMasterfulSmelteryAugment(SmelteryTileEntityBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof ItemAugment;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {
        te.onUpdateSent();
    }
}