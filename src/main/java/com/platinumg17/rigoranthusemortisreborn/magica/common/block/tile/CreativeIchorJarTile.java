package com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeIchorJarTile extends IchorJarTile {
    public CreativeIchorJarTile(BlockPos pos, BlockState state){
        super(BlockRegistry.CREATIVE_ICHOR_JAR_TILE, pos, state);
    }
    @Override
    public int getCurrentIchor() {
        return this.getMaxIchor();
    }
}