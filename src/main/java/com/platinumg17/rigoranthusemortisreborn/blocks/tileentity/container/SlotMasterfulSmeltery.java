package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.SmelteryTileEntityBase;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotMasterfulSmeltery extends Slot {

    private final Player player;
    private int removeCount;
    private SmelteryTileEntityBase te;

    public SlotMasterfulSmeltery(Player player, SmelteryTileEntityBase te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
        this.player = player;
        this.te = te;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    public void onTake(Player thePlayer, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(thePlayer, stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int p_75210_2_) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide && this.te != null) {
            ((SmelteryTileEntityBase)this.te).unlockRecipes(this.player);
        }
        this.removeCount = 0;
    }

    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (this.player instanceof ServerPlayer && this.container instanceof SmelteryTileEntityBase) {
            ((SmelteryTileEntityBase)this.container).unlockRecipes(this.player);
        }
        this.removeCount = 0;
        net.minecraftforge.event.ForgeEventFactory.firePlayerSmeltedEvent(this.player, stack);
    }
}