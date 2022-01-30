package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeDominionJarTile extends DominionJarTile {

    public CreativeDominionJarTile(BlockPos pos, BlockState state){
        super(BlockRegistry.CREATIVE_DOMINION_JAR_TILE, pos, state);
    }
    @Override
    public int getCurrentDominion() {
        return this.getMaxDominion();
    }
}