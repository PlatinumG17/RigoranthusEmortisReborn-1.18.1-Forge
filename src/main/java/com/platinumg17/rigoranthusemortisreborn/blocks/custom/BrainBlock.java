package com.platinumg17.rigoranthusemortisreborn.blocks.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

/**
 * @author PlatinumG17
 * https://github.com/PlatinumG17/RigoranthusEmortisReborn
 * */
public class BrainBlock extends Block implements SimpleWaterloggedBlock {

    public BrainBlock(Properties properties, String name) {
        super(properties);
        setRegistryName(name);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    private static final Map<Direction, VoxelShape> AABBS =
            Maps.newEnumMap(ImmutableMap.of(
                    Direction.NORTH, box(5.75, 0, 4.5, 10.25, 3.025, 11.5),
                    Direction.SOUTH, box(5.75, 0, 4.5, 10.25, 3.025, 11.5),
                    Direction.WEST, box(4.5, 0, 5.75, 11.5, 3.025, 10.25),
                    Direction.EAST, box(4.5, 0, 5.75, 11.5, 3.025, 10.25)));
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getNearestLookingDirection().getAxis().isHorizontal()) {
            return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(false));
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter reader, BlockPos blockPos, CollisionContext context) {
        return getShape(blockState);
    }

    public static VoxelShape getShape(BlockState blockState) {
        return AABBS.get(blockState.getValue(FACING));
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState state, LevelAccessor world, BlockPos blockPos1, BlockPos blockPos2) {
        if (!blockState.canSurvive(world, blockPos1)) {
            return direction.getOpposite() == blockState.getValue(FACING) && !blockState.canSurvive(world, blockPos1) ? Blocks.AIR.defaultBlockState() : blockState;
        }else {
            if (blockState.getValue(WATERLOGGED)) {
                world.scheduleTick(blockPos1, Fluids.WATER, Fluids.WATER.getTickDelay(world));
            }
            return super.updateShape(blockState, direction, state, world, blockPos1, blockPos2);
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

//    protected boolean mayPlaceOn(BlockState blockState, BlockGetter reader, BlockPos blockPos) {
//        return !blockState.getCollisionShape(reader, blockPos).getFaceShape(Direction.UP).isEmpty() || blockState.isFaceSturdy(reader, blockPos, Direction.UP);
//    }
//    public boolean canSurvive(BlockState blockState, IWorldReader reader, BlockPos blockPos) {
//        BlockPos blockpos = blockPos.below();
//        return this.mayPlaceOn(reader.getBlockState(blockpos), reader, blockpos);
//    }
    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        return canSupportCenter(reader, pos.below(), Direction.UP);
    }
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {builder.add(FACING, WATERLOGGED);}
    public BlockState rotate(BlockState state, Rotation rot) {return state.setValue(FACING, rot.rotate(state.getValue(FACING)));}
    public BlockState mirror(BlockState state, Mirror mirrorIn) {return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));}

    public RenderShape getRenderShape(BlockState blockState) {return RenderShape.MODEL;}
    @Override
    public PushReaction getPistonPushReaction(BlockState blockState) {return PushReaction.DESTROY;}
    public boolean isPathfindable(BlockState blockState, BlockGetter blockReader, BlockPos pos, PathComputationType pathType) {return false;}
}