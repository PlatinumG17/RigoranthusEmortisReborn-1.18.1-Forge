package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.gui;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container.MasterfulSmelteryContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MasterfulSmelteryScreen extends SmelteryScreenBase<MasterfulSmelteryContainer> {
    public MasterfulSmelteryScreen(MasterfulSmelteryContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }
}