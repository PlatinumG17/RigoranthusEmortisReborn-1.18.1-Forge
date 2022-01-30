package com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisContainerTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisSkills;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.WaywardTravellerItemHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.slot.CanisInventorySlot;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.WaywardTravellerSkill;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ProPercivalalb
 */
public class CanisInventoriesContainer extends AbstractContainerMenu {

    private Level world;
    private Player player;
    private DataSlot position;
    private SimpleContainerData trackableArray;
    private final List<CanisInventorySlot> canisSlots = new ArrayList<>();
    private int possibleSlots = 0;

    //Server method
    public CanisInventoriesContainer(int windowId, Inventory playerInventory, SimpleContainerData trackableArray) {
        super(CanisContainerTypes.CANIS_INVENTORIES.get(), windowId);
        this.world = playerInventory.player.level;
        this.player = playerInventory.player;
        this.position = DataSlot.standalone();
        checkContainerDataCount(trackableArray, 1);
        this.addDataSlot(this.position);
        this.trackableArray = trackableArray;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
        this.addCanisSlots();
    }

    public void addCanisSlots() {
        final int TOTAL_COLUMNS = 9;
        int page = this.position.get();
        int drawingColumn = 0;
        for (int i = 0; i < this.trackableArray.getCount(); i++) {
            int entityId = this.trackableArray.get(i);
            Entity entity = this.world.getEntity(entityId);
            if (entity instanceof CanisEntity) {
                CanisEntity canis = (CanisEntity) entity;
                WaywardTravellerItemHandler packInventory = canis.getSkill(CanisSkills.WAYWARD_TRAVELLER)
                        .map((inst) -> inst.cast(WaywardTravellerSkill.class).inventory()).orElse(null);
                if (packInventory == null) {
                    continue;
                }
                int level = Mth.clamp(canis.getCanisLevel(CanisSkills.WAYWARD_TRAVELLER), 0, 5); // Number of rows for this canis
                int numCols = Mth.clamp(level, 0, Math.max(0, TOTAL_COLUMNS)); // Number of rows to draw

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < numCols; col++) {
                        CanisInventorySlot slot = new CanisInventorySlot(canis, this.player, packInventory, drawingColumn + col, row, col, col * 3 + row, 8 + 18 * (drawingColumn + col - page), 18 * row + 18);
                        this.addCanisSlot(slot);
                        int adjustedColumn = slot.getOverallColumn() - page;
                        if (adjustedColumn - page < 0 || adjustedColumn - page >= 9) {
                            slot.setEnabled(false);
                        }
                    }
                }
                this.possibleSlots += level;
                drawingColumn += numCols;
            }
        }

    }

    @Override
    public void setData(int id, int data) {
        super.setData(id, data);
        if (id == 0) {
            for (int i = 0; i < this.canisSlots.size(); i++) {
                CanisInventorySlot slot = this.canisSlots.get(i);
                CanisInventorySlot newSlot = new CanisInventorySlot(slot, 8 + 18 * (slot.getOverallColumn() - data));
                this.replaceCanisSlot(i, newSlot);
                int adjustedColumn = slot.getOverallColumn() - data;
                if (adjustedColumn < 0 || adjustedColumn >= 9) {
                    newSlot.setEnabled(false);
                }
            }
        }

    }

    private void addCanisSlot(CanisInventorySlot slotIn) {
        this.addSlot(slotIn);
        this.canisSlots.add(slotIn);
    }

    private void replaceCanisSlot(int i, CanisInventorySlot slotIn) {
        this.canisSlots.set(i, slotIn);
        // Work around to set Slot#slotNumber (MCP name) which is Slot#index in official
        // mappings. Needed because SlotItemHandler#index shadows the latter.
        Slot s = slotIn;
        this.slots.set(s.index, slotIn);
    }

    public int getTotalNumColumns() {
        return this.possibleSlots;
    }

    public int getPage() {
        return this.position.get();
    }

    public void setPage(int page) {
        this.position.set(page);
    }

    public List<CanisInventorySlot> getSlots() {
        return this.canisSlots;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int startIndex = this.slots.size() - this.canisSlots.size() + this.position.get() * 3;
            int endIndex = Math.min(startIndex + 9 * 3, this.slots.size());
            if (i >= this.slots.size() - this.canisSlots.size() && i < this.slots.size()) {
                if (!moveItemStackTo(itemstack1, 0, this.slots.size() - this.canisSlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!moveItemStackTo(itemstack1, this.slots.size() - this.canisSlots.size(), this.slots.size(), false)) {
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
        }
        return itemstack;
    }
}