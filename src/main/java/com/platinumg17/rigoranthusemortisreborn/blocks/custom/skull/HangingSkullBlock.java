package com.platinumg17.rigoranthusemortisreborn.blocks.custom.skull;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.HangingSkullTile;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

/**
 * @author PlatinumG17
 * https://github.com/PlatinumG17/RigoranthusEmortisReborn
 * */
public class HangingSkullBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HangingSkullBlock(String registry) {
        super(Block.Properties.of(Material.DECORATION).sound(SoundType.CHAIN).strength(1.0f, 6.0f).noOcclusion().noCollission().lightLevel(state -> 11));
        setRegistryName(registry);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(blockpos).getType() == Fluids.WATER).setValue(FACING, this.getDirectionForPlacement(context));
    }

    private Direction getDirectionForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        Direction playerAim = context.getNearestLookingDirection().getOpposite();
        if (face.getAxis() != Direction.Axis.Y) {
            return face;
        } else {
            Vec3 difference = context.getClickLocation().add(Vec3.atCenterOf(context.getClickedPos())).add(0.5D, 0.0D, 0.5D);
            return Direction.fromYRot(Math.toDegrees(Math.atan2(difference.x(), difference.z()))).getClockWise();
        }
    }
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter blockReader, BlockPos pos) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(4, 4, 4, 12, 12, 12);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        return Block.canSupportCenter(reader, pos.above(), Direction.DOWN);
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState state, LevelAccessor world, BlockPos currentPos, BlockPos newPos) {
        return direction == Direction.UP && !this.canSurvive(blockState, world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, state, world, currentPos, newPos);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
    @Override public FluidState getFluidState(BlockState state) {return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);}
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(FACING, WATERLOGGED); }
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot) { return state.setValue(FACING, rot.rotate(state.getValue(FACING))); }
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn) { return state.rotate(mirrorIn.getRotation(state.getValue(FACING))); }
    @SuppressWarnings("deprecation")
    @Override public PushReaction getPistonPushReaction(BlockState blockState) {return PushReaction.DESTROY;}
    @SuppressWarnings("deprecation")
    @Override public boolean isPathfindable(BlockState blockState, BlockGetter blockReader, BlockPos pos, PathComputationType pathType) {return false;}
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HangingSkullTile(pos, state);
    }
}