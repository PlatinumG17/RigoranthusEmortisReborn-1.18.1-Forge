package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.SmelteryTileEntityBase;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

/**
 * @author Qelifern
 * https://github.com/Qelifern/IronFurnaces
 * */
public abstract class MasterfulSmelteryContainerBase extends AbstractContainerMenu {

    protected SmelteryTileEntityBase te;
    protected ContainerData fields;
    protected Player playerEntity;
    protected IItemHandler playerInventory;
    protected final Level world;
    private RecipeType<? extends AbstractCookingRecipe> recipeType;

    public MasterfulSmelteryContainerBase(MenuType<?> containerType, int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this(containerType, windowId, world, pos, playerInventory, player, new SimpleContainerData(5));
    }

    public MasterfulSmelteryContainerBase(MenuType<?> containerType, int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, ContainerData fields) {
        super(containerType, windowId);
        this.te = (SmelteryTileEntityBase) world.getBlockEntity(pos);
        this.recipeType = te.recipeType;

        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerInventory.player.level;
        this.fields = fields;

        this.addDataSlots(this.fields);


        this.addSlot(new Slot(te, 0, 56, 17));
        this.addSlot(new SlotMasterfulSmelteryFuel(this.te, 1, 56, 53));
        this.addSlot(new SlotMasterfulSmeltery(playerEntity, te, 2, 116, 35));
        this.addSlot(new SlotMasterfulSmelteryAugment(te, 3, 26, 35));
        layoutPlayerInventorySlots(8, 84);
        checkContainerSize(this.te, 4);
        checkContainerDataCount(this.fields, 5);
    }

    public boolean stillValid(Player player) {
        return this.te.stillValid(player);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean showInventoryButtons() {
        return this.te.fields.get(4) == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public int getRedstoneMode() {
        return this.te.getRedstoneSetting();
    }

    @OnlyIn(Dist.CLIENT)
    public int getComSub() {
        return this.te.getRedstoneComSub();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getAutoInput() {
        return this.te.getAutoInput() == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getAutoOutput() {
        return this.te.getAutoOutput() == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public Component getTooltip(int index) {
        switch (te.smelterySettings.get(index))
        {
            case 1:
                return new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".gui_input");
            case 2:
                return new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".gui_output");
            case 3:
                return new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".gui_input_output");
            case 4:
                return new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".gui_fuel");
            default:
                return new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".gui_none");
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingTop() { return this.te.getSettingTop(); }

    @OnlyIn(Dist.CLIENT)
    public int getSettingBottom() { return this.te.getSettingBottom(); }

    @OnlyIn(Dist.CLIENT)
    public int getSettingFront() { return this.te.getSettingFront(); }

    @OnlyIn(Dist.CLIENT)
    public int getSettingBack() { return this.te.getSettingBack(); }

    @OnlyIn(Dist.CLIENT)
    public int getSettingLeft() { return this.te.getSettingLeft(); }

    @OnlyIn(Dist.CLIENT)
    public int getSettingRight() { return this.te.getSettingRight(); }

    @OnlyIn(Dist.CLIENT)
    public int getIndexFront() { return this.te.getIndexFront(); }

    @OnlyIn(Dist.CLIENT)
    public int getIndexBack() { return this.te.getIndexBack(); }

    @OnlyIn(Dist.CLIENT)
    public int getIndexLeft() { return this.te.getIndexLeft(); }

    @OnlyIn(Dist.CLIENT)
    public int getIndexRight() { return this.te.getIndexRight(); }

    @OnlyIn(Dist.CLIENT)
    public BlockPos getPos() { return this.te.getBlockPos(); }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() { return this.te.isBurning(); }

    @OnlyIn(Dist.CLIENT)
    public int getCookScaled(int pixels) {
        int i = this.fields.get(2);
        int j = this.fields.get(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled(int pixels) {
        int i = this.fields.get(1);
        if (i == 0) {
            i = 200;
        }
        return this.fields.get(0) * pixels / i;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 1 && index != 0 && index != 3) {
                if (this.te.hasRecipe(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (SmelteryTileEntityBase.isItemFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (SmelteryTileEntityBase.isItemAugment(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }


    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
/*
        this.addSlot(new SlotMasterfulSmelteryInput(te, 0, 56, 17));
        this.addSlot(new SlotMasterfulSmelteryFuel(this.te, 1, 56, 53));
        this.addSlot(new SlotMasterfulSmeltery(playerEntity, te, 2, 116, 35));
        this.addSlot(new SlotMasterfulSmelteryAugment(te, 3, 26, 35));
        layoutPlayerInventorySlots(8, 84);
        checkContainerSize(this.te, 4);
        checkContainerDataCount(this.fields, 5);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean showInventoryButtons() {
        return this.te.fields.get(4) == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public int getRedstoneMode() {
        return this.te.getRedstoneSetting();
    }

    @OnlyIn(Dist.CLIENT)
    public int getComSub() {
        return this.te.getRedstoneComSub();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getAutoInput() {
        return this.te.getAutoInput() == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getAutoOutput() {
        return this.te.getAutoOutput() == 1;
    }

    @OnlyIn(Dist.CLIENT)
    public Component getTooltip(int index) {
        switch (te.smelterySettings.get(index))
        {
            case 1:
                return new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_input");
            case 2:
                return new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_output");
            case 3:
                return new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_input_output");
            case 4:
                return new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_fuel");
            default:
                return new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_none");
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingTop()
    {
        return this.te.getSettingTop();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingBottom()
    {
        return this.te.getSettingBottom();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingFront()
    {
        return this.te.getSettingFront();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingBack()
    {
        return this.te.getSettingBack();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingLeft()
    {
        return this.te.getSettingLeft();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSettingRight()
    {
        return this.te.getSettingRight();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexFront()
    {
        return this.te.getIndexFront();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexBack()
    {
        return this.te.getIndexBack();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexLeft()
    {
        return this.te.getIndexLeft();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIndexRight()
    {
        return this.te.getIndexRight();
    }

    @OnlyIn(Dist.CLIENT)
    public BlockPos getPos() 
    {
        return this.te.getBlockPos();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() 
    {
        return this.te.isBurning();
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookScaled(int pixels) { //public int getBurnProgress() {
        int i = this.fields.get(2);
        int j = this.fields.get(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled(int pixels) {  //public int getLitProgress() {
        int i = this.fields.get(1);
        if (i == 0) {
            i = 200;
        }
        return this.fields.get(0) * pixels / i;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 1 && index != 0 && index != 3) {
                if (this.te.hasRecipe(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (SmelteryTileEntityBase.isItemFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (SmelteryTileEntityBase.isItemAugment(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }*/
}