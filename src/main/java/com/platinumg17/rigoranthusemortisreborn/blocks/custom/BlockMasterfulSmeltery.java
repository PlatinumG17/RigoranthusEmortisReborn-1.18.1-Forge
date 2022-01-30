package com.platinumg17.rigoranthusemortisreborn.blocks.custom;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.MasterfulSmelteryTile;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

/**
 * @author PlatinumG17
 * https://github.com/PlatinumG17/RigoranthusEmortisReborn
 * */
public class BlockMasterfulSmeltery extends BlockMasterfulSmelteryBase {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape SHAPE_NORTH = Stream.of(
            Block.box(3, 0, 16, 13, 7, 17),
            Block.box(-1, 0, 0, 2, 3, 3),
            Block.box(-1, 0, 12, 3, 6, 17),
            Block.box(13, 0, 12, 17, 6, 17),
            Block.box(14, 0, 0, 17, 3, 3),
            Block.box(14, 3, 0, 16, 4, 2),
            Block.box(14, 6, 6, 16, 8, 16),
            Block.box(0, 6, 6, 2, 8, 16),
            Block.box(0, 3, 0, 2, 4, 2),
            Block.box(2, 0, 0, 14, 6, 1),
            Block.box(3, 1, 5, 13, 6, 7),
            Block.box(14, 0, 2, 16, 6, 13),
            Block.box(0, 0, 2, 2, 6, 13),
            Block.box(1, 0, 1, 15, 1, 16),
            Block.box(13, 1, 1, 14, 6, 16),
            Block.box(2, 1, 1, 3, 6, 16),
            Block.box(2, 6, 6, 14, 15, 16),
            Block.box(4, 15, 6, 12, 16, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_EAST = Stream.of(
            Block.box(-1, 0, 3, 0, 7, 13),
            Block.box(13, 0, -1, 16, 3, 2),
            Block.box(-1, 0, -1, 4, 6, 3),
            Block.box(-1, 0, 13, 4, 6, 17),
            Block.box(13, 0, 14, 16, 3, 17),
            Block.box(14, 3, 14, 16, 4, 16),
            Block.box(0, 6, 14, 10, 8, 16),
            Block.box(0, 6, -0, 10, 8, 2),
            Block.box(14, 3, -0, 16, 4, 2),
            Block.box(15, 0, 2, 16, 6, 14),
            Block.box(9, 1, 3, 11, 6, 13),
            Block.box(3, 0, 14, 14, 6, 16),
            Block.box(3, 0, -0, 14, 6, 2),
            Block.box(0, 0, 1, 15, 1, 15),
            Block.box(0, 1, 13, 15, 6, 14),
            Block.box(0, 1, 2, 15, 6, 3),
            Block.box(0, 6, 2, 10, 15, 14),
            Block.box(4, 15, 4, 10, 16, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_SOUTH = Stream.of(
            Block.box(3, 0, -1, 13, 7, 0),
            Block.box(14, 0, 13, 17, 3, 16),
            Block.box(13, 0, -1, 17, 6, 4),
            Block.box(-1, 0, -1, 3, 6, 4),
            Block.box(-1, 0, 13, 2, 3, 16),
            Block.box(0, 3, 14, 2, 4, 16),
            Block.box(0, 6, 0, 2, 8, 10),
            Block.box(14, 6, 0, 16, 8, 10),
            Block.box(14, 3, 14, 16, 4, 16),
            Block.box(2, 0, 15, 14, 6, 16),
            Block.box(3, 1, 9, 13, 6, 11),
            Block.box(0, 0, 3, 2, 6, 14),
            Block.box(14, 0, 3, 16, 6, 14),
            Block.box(1, 0, 0, 15, 1, 15),
            Block.box(2, 1, 0, 3, 6, 15),
            Block.box(13, 1, 0, 14, 6, 15),
            Block.box(2, 6, 0, 14, 15, 10),
            Block.box(4, 15, 4, 12, 16, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_WEST = Stream.of(
            Block.box(16, 0, 3, 17, 7, 13),
            Block.box(-0, 0, 14, 3, 3, 17),
            Block.box(12, 0, 13, 17, 6, 17),
            Block.box(12, 0, -1, 17, 6, 3),
            Block.box(-0, 0, -1, 3, 3, 2),
            Block.box(-0, 3, -0, 2, 4, 2),
            Block.box(6, 6, -0, 16, 8, 2),
            Block.box(6, 6, 14, 16, 8, 16),
            Block.box(-0, 3, 14, 2, 4, 16),
            Block.box(-0, 0, 2, 1, 6, 14),
            Block.box(5, 1, 3, 7, 6, 13),
            Block.box(2, 0, -0, 13, 6, 2),
            Block.box(2, 0, 14, 13, 6, 16),
            Block.box(1, 0, 1, 16, 1, 15),
            Block.box(1, 1, 2, 16, 6, 3),
            Block.box(1, 1, 13, 16, 6, 14),
            Block.box(6, 6, 2, 16, 15, 14),
            Block.box(6, 15, 4, 12, 16, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext ctx) {
        switch(state.getValue(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
            default:
                return SHAPE_NORTH;
        }
    }

    public static final String MASTERFUL_SMELTERY = "masterful_smeltery";

    public BlockMasterfulSmeltery(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createFurnaceTicker(level, type, Registration.MASTERFUL_SMELTERY_TILE.get());
    }
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MasterfulSmelteryTile(pos, state);
    }
}