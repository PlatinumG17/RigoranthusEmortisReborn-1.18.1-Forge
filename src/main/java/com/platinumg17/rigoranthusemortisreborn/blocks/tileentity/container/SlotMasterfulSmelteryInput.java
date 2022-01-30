package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.SmelteryTileEntityBase;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotMasterfulSmelteryInput extends Slot {

	private SmelteryTileEntityBase te;

	public SlotMasterfulSmelteryInput(SmelteryTileEntityBase te, int slotIndex, int xPosition, int yPosition) {
		super(te, slotIndex, xPosition, yPosition);
		this.te = te;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return te.hasRecipe(stack);
	}
}