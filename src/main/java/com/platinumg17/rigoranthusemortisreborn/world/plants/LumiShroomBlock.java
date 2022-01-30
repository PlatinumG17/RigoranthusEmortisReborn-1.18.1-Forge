package com.platinumg17.rigoranthusemortisreborn.world.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class LumiShroomBlock extends BushBlock {

    public LumiShroomBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.isSolidRender(worldIn, pos);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if(canSpread(worldIn, pos, state) && random.nextInt(25) == 0) {
            int count = 0;
            Iterable<BlockPos> blocks = BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4));

            for(BlockPos checkPos : blocks)
                if(worldIn.getBlockState(checkPos).getBlock() == this) {
                    count++;
                    if (count >= 5)
                        return;
                }

            for (int i = 0; i < 5; ++i) {
                BlockPos spreadPos = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
                if (worldIn.isEmptyBlock(spreadPos) && this.canSpread(worldIn, spreadPos, this.defaultBlockState())) {
                    worldIn.setBlock(spreadPos, this.defaultBlockState(), Block.UPDATE_ALL);
                    return;
                }
            }
        }
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CAVE;
    }

    public boolean canSpread(Level world, BlockPos pos, BlockState state) {
        BlockState soil = world.getBlockState(pos.below());
        return soil.getBlock().equals(Blocks.DIRT);
    }
}