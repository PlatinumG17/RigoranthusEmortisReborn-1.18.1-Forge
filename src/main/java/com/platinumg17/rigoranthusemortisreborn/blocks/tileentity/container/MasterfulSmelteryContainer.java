package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container;

import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

public class MasterfulSmelteryContainer extends MasterfulSmelteryContainerBase {

    public MasterfulSmelteryContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.MASTERFUL_SMELTERY_CONTAINER.get(), windowId, world, pos, playerInventory, player);
    }

    public MasterfulSmelteryContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, ContainerData fields) {
        super(Registration.MASTERFUL_SMELTERY_CONTAINER.get(), windowId, world, pos, playerInventory, player, fields);
    }
//    @Override
//    public boolean stillValid(Player playerIn) {
//        return stillValid(ContainerLevelAccess.create(te.getLevel(), te.getBlockPos()), playerEntity, Registration.MASTERFUL_SMELTERY.get());
//    }
}