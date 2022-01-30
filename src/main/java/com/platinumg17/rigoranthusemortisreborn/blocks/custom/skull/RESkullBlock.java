package com.platinumg17.rigoranthusemortisreborn.blocks.custom.skull;

import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @author PlatinumG17
 * https://github.com/PlatinumG17/RigoranthusEmortisReborn
 * */
public class RESkullBlock extends Block implements SimpleWaterloggedBlock, Vanishable {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RESkullBlock(String name) {
        super(BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.PODZOL).strength(1.0f, 6.0f).noOcclusion().sound(SoundType.BONE_BLOCK));
        setRegistryName(name);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

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

    public VoxelShape getOcclusionShape(BlockState state, BlockGetter blockReader, BlockPos pos) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(4, 0, 4, 12, 8, 12);
    }

    @SuppressWarnings("deprecation")
    @Override
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

    @Override
    public RenderShape getRenderShape(BlockState blockState) {return RenderShape.MODEL;}
    @Override
    public PushReaction getPistonPushReaction(BlockState blockState) {return PushReaction.DESTROY;}
    public boolean isPathfindable(BlockState blockState, BlockGetter blockReader, BlockPos pos, PathComputationType pathType) {return false;}
}