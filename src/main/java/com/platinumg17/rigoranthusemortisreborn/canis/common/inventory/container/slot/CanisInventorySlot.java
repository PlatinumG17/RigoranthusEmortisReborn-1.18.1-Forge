package com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.slot;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CanisInventorySlot extends SlotItemHandler {

    private boolean enabled = true;
    private Player player;
    private CanisEntity canis;
    private int overallColumn, row, col;

    public CanisInventorySlot(CanisEntity canisIn, Player playerIn, IItemHandler itemHandler, int overallColumn, int row, int col, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = playerIn;
        this.overallColumn = overallColumn;
        this.row = row;
        this.col = col;
        this.canis = canisIn;
    }

    public CanisInventorySlot(CanisInventorySlot prev, int newX) {
        super(prev.getItemHandler(), prev.getSlotIndex(), newX, prev.y);
        this.player = prev.player;
        this.overallColumn = prev.overallColumn;
        this.row = prev.row;
        this.col = prev.col;
        this.canis = prev.canis;
        // Work around to set Slot#slotNumber (MCP name) which is Slot#index in official
        // mappings. Needed because SlotItemHandler#index shadows the latter.
        {
            Slot n = this;
            Slot o = prev;
            n.index = o.index;
        }
    }
    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }
    // Don't accept items when disabled, this means disabled slots cannot be shift clicked into
    @Override public boolean mayPlace(ItemStack stack) {
        return this.isActive() && super.mayPlace(stack);
    }
//    @Override
//    public boolean canTakeStack(Player playerIn) {
//        return super.canTakeStack(playerIn);
//    }
    @Override public boolean isActive() {return this.enabled && this.canis.isAlive() && this.canis.distanceToSqr(this.player) < 400;}
    public CanisEntity getCanis() {
        return this.canis;
    }
    public Player getPlayer() {
        return this.player;
    }
    public int getOverallColumn() {
        return this.overallColumn;
    }
    public int getRow() {
        return this.row;
    }
    public int getColumn() {
        return this.col;
    }
}