package com.platinumg17.rigoranthusemortisreborn.world.plants;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class VerdurousLeavesBlock extends LeavesBlock {

    public static final int LEAF_SUSTAIN_DISTANCE = 10;

    public VerdurousLeavesBlock(Properties properties) {super(properties);}

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(DISTANCE) > LEAF_SUSTAIN_DISTANCE && !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if(!state.getValue(PERSISTENT) && state.getValue(DISTANCE) > LEAF_SUSTAIN_DISTANCE) {
            dropResources(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistance(facingState) + 1;
        if(i != 1 || stateIn.getValue(DISTANCE) != i)
            worldIn.scheduleTick(currentPos, this, 1);
        return stateIn;
    }

    protected BlockState updateDistance(BlockState state, LevelAccessor world, BlockPos pos) {
        int i = 7;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for(Direction facing : Direction.values()) {
            mutablePos.set(pos).move(facing);
            int axisDecrease = facing.getAxis() == Direction.Axis.X ? 2 : 1;
            i = Math.min(i, getDistance(world.getBlockState(mutablePos)) + axisDecrease);
            if(i == 1)
                break;
        }
        return state.setValue(DISTANCE, i);
    }

    protected int getDistance(BlockState neighbor) {
        if((neighbor.getBlock() == BlockRegistry.JESSIC_LOG) || (neighbor.getBlock() == BlockRegistry.AZULOREAL_LOG)) {return 0;}

        else {return neighbor.getBlock() == this ? neighbor.getValue(DISTANCE) : 7;}
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, true), context.getLevel(), context.getClickedPos());
    }

//    @Override
//    public boolean canBeReplacedByLeaves(BlockState state, LevelAccessor world, BlockPos pos) {return false;}

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {return 1;}

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {return 250;}
}