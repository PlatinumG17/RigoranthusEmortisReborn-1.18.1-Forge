package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container.MasterfulSmelteryContainer;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class MasterfulSmelteryTile extends SmelteryTileEntityBase {
    public MasterfulSmelteryTile(BlockPos pos, BlockState state) {
        super(Registration.MASTERFUL_SMELTERY_TILE.get(), pos, state);
    }

    @Override
    public int getCookTimeConfig() {
        return Config.ClientConfig.masterfulSmelterySpeed.get();
    }

    @Override
    public String IgetName() {
        return "container.rigoranthusemortisreborn.masterful_smeltery";
    }

    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new MasterfulSmelteryContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }
}