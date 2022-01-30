package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.client.ITooltipProvider;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.AbstractDominionTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.DominionJar;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class DominionJarTile extends AbstractDominionTile implements ITooltipProvider {

    public DominionJarTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.DOMINION_JAR_TILE, pos, state);
    }

    public DominionJarTile(BlockEntityType<? extends DominionJarTile> tileBlockEntityType, BlockPos pos, BlockState state){
        super(tileBlockEntityType, pos, state);
    }

    @Override
    public int getMaxDominion() {
        return 10000;
    }

    @Override
    public void tick() {
        if(level.isClientSide) {
            return;
        }
        BlockState state = level.getBlockState(worldPosition);
        int fillState = 0;
        if(this.getCurrentDominion() > 0 && this.getCurrentDominion() < 1000)
            fillState = 1;
        else if(this.getCurrentDominion() != 0){
            fillState = (this.getCurrentDominion() / 1000) + 1;
        }
        level.setBlock(worldPosition, state.setValue(DominionJar.fill, fillState),3);
    }

    @Override
    public int getTransferRate() {
        return getMaxDominion();
    }

    @Override
    public void getTooltip(List<Component> tooltip) {
        tooltip.add(new TranslatableComponent("block.rigoranthusemortisreborn.dominion_jar"));
        tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.dominion_jar.fullness", (getCurrentDominion()*100) / this.getMaxDominion()));
    }
}