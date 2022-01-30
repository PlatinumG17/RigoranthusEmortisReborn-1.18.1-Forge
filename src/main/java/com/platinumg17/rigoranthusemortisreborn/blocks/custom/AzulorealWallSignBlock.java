package com.platinumg17.rigoranthusemortisreborn.blocks.custom;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.RESignEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AzulorealWallSignBlock extends WallSignBlock {
    public AzulorealWallSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RESignEntity(pos, state);
    }
}
