package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.client.ITooltipProvider;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.AbstractIchorTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.IchorJar;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class IchorJarTile extends AbstractIchorTile implements ITooltipProvider {
    public IchorJarTile(BlockPos pos, BlockState state) {
        super(BlockRegistry.ICHOR_JAR_TILE, pos, state);
    }

    public IchorJarTile(BlockEntityType<? extends IchorJarTile> tileBlockEntityType, BlockPos pos, BlockState state){
        super(tileBlockEntityType, pos, state);
    }

    @Override
    public int getMaxIchor() {
        return 10000;
    }

    @Override
    public void tick() {
        if(level.isClientSide) {
            return;
        }
        BlockState state = level.getBlockState(worldPosition);
        int fillState = 0;
        if(this.getCurrentIchor() > 0 && this.getCurrentIchor() < 1000)
            fillState = 1;
        else if(this.getCurrentIchor() != 0){
            fillState = (this.getCurrentIchor() / 1000) + 1;
        }
        level.setBlock(worldPosition, state.setValue(IchorJar.fill, fillState),3);
    }

    @Override
    public int getTransferRate() {
        return getMaxIchor();
    }

    @Override
    public void getTooltip(List<Component> tooltip) {
        tooltip.add(new TranslatableComponent("block.rigoranthusemortisreborn.ichor_jar"));
        tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.ichor_jar.fullness", (getCurrentIchor()*100) / this.getMaxIchor()));
    }
}