package com.platinumg17.rigoranthusemortisreborn.blocks.bridge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class BridgeRope extends BridgeBase {
    protected static final VoxelShape NS = (VoxelShape) Stream.of(Block.box(1.41D, 0.0D, -3.0D, 6.39D, 2.0D, 19.0D), Block.box(1.4000000000000004D, 2.0D, 16.4D, 6.4D, 16.0D, 16.7D), Block.box(1.4000000000000004D, 2.0D, -0.6D, 6.4D, 16.0D, -0.3D), Block.box(9.51D, 0.0D, -3.0D, 14.49D, 2.0D, 19.0D), Block.box(0.0D, 0.5D, -1.0D, 16.0D, 1.5D, 0.0D), Block.box(0.0D, 15.5D, -1.0D, 16.0D, 16.5D, 0.0D), Block.box(0.0D, 0.5D, 16.0D, 16.0D, 1.5D, 17.0D), Block.box(0.0D, 15.5D, 16.0D, 16.0D, 16.5D, 17.0D), Block.box(9.5D, 2.0D, -0.6D, 14.5D, 16.0D, -0.3D), Block.box(9.5D, 2.0D, 16.4D, 14.5D, 16.0D, 16.7D)).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();
    protected static final VoxelShape WE = (VoxelShape)Stream.of(Block.box(-3.0100000000000007D, 0.0D, 1.46D, 18.99D, 2.0D, 6.4399999999999995D), Block.box(-0.71D, 2.0D, 1.4500000000000002D, -0.40999999999999925D, 16.0D, 6.45D), Block.box(16.29D, 2.0D, 1.4500000000000002D, 16.59D, 16.0D, 6.45D), Block.box(-3.0100000000000007D, 0.0D, 9.559999999999999D, 18.99D, 2.0D, 14.54D), Block.box(15.989999999999998D, 0.5D, 0.04999999999999982D, 16.99D, 1.5D, 16.05D), Block.box(15.989999999999998D, 15.5D, 0.04999999999999982D, 16.99D, 16.5D, 16.05D), Block.box(-1.0100000000000007D, 0.5D, 0.04999999999999982D, -0.010000000000000675D, 1.5D, 16.05D), Block.box(-1.0100000000000007D, 15.5D, 0.04999999999999982D, -0.010000000000000675D, 16.5D, 16.05D), Block.box(16.29D, 2.0D, 9.55D, 16.59D, 16.0D, 14.55D), Block.box(-0.71D, 2.0D, 9.55D, -0.40999999999999925D, 16.0D, 14.55D)).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();
    protected static final VoxelShape END_N = (VoxelShape)Stream.of(Block.box(10.0D, 2.0D, -0.7D, 15.0D, 16.0D, -0.3D), Block.box(2.5D, 2.0D, -0.7D, 7.5D, 16.0D, -0.3D), Block.box(2.5D, 2.0D, 16.3D, 7.5D, 16.0D, 16.7D), Block.box(10.0D, 2.0D, 16.3D, 15.0D, 16.0D, 16.7D), Block.box(10.01D, 0.0D, -3.0D, 14.99D, 2.0D, 19.0D), Block.box(0.0D, 0.5D, 16.0D, 16.0D, 1.5D, 17.0D), Block.box(0.0D, 15.5D, 16.0D, 16.0D, 16.5D, 17.0D), Block.box(0.0D, 0.5D, -1.0D, 16.0D, 1.5D, 0.0D), Block.box(0.0D, 15.5D, -1.0D, 16.0D, 16.5D, 0.0D), Block.box(-2.0D, 0.0D, 15.0D, 2.0D, 17.0D, 19.0D), Block.box(-2.0D, 0.0D, -3.0D, 2.0D, 17.0D, 1.0D), Block.box(2.51D, 0.0D, -3.0D, 7.489999999999998D, 2.0D, 19.0D)).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();
    protected static final VoxelShape END_E = (VoxelShape)Stream.of(Block.box(16.34166666666667D, 2.0D, 9.958333333333332D, 16.741666666666667D, 16.0D, 14.958333333333332D), Block.box(16.34166666666667D, 2.0D, 2.458333333333333D, 16.741666666666667D, 16.0D, 7.458333333333332D), Block.box(-0.6583333333333323D, 2.0D, 2.458333333333333D, -0.25833333333333375D, 16.0D, 7.458333333333332D), Block.box(-0.6583333333333323D, 2.0D, 9.958333333333332D, -0.25833333333333375D, 16.0D, 14.958333333333332D), Block.box(-2.958333333333333D, 0.0D, 9.968333333333334D, 19.041666666666668D, 2.0D, 14.948333333333334D), Block.box(-0.958333333333333D, 0.5D, -0.04166666666666696D, 0.04166666666666696D, 1.5D, 15.958333333333332D), Block.box(-0.958333333333333D, 15.5D, -0.04166666666666696D, 0.04166666666666696D, 16.5D, 15.958333333333332D), Block.box(16.041666666666668D, 0.5D, -0.04166666666666696D, 17.041666666666668D, 1.5D, 15.958333333333332D), Block.box(16.041666666666668D, 15.5D, -0.04166666666666696D, 17.041666666666668D, 16.5D, 15.958333333333332D), Block.box(-2.958333333333333D, 0.0D, -2.041666666666668D, 1.041666666666667D, 17.0D, 1.958333333333333D), Block.box(15.041666666666668D, 0.0D, -2.041666666666668D, 19.041666666666668D, 17.0D, 1.958333333333333D), Block.box(-2.958333333333333D, 0.0D, 2.468333333333333D, 19.041666666666668D, 2.0D, 7.448333333333331D)).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();
    protected static final VoxelShape END_S = (VoxelShape)Stream.of(Block.box(1.0833333333333357D, 2.0D, 16.3D, 6.083333333333336D, 16.0D, 16.7D), Block.box(8.583333333333336D, 2.0D, 16.3D, 13.583333333333336D, 16.0D, 16.7D), Block.box(8.583333333333336D, 2.0D, -0.6999999999999993D, 13.583333333333336D, 16.0D, -0.3000000000000007D), Block.box(1.0833333333333357D, 2.0D, -0.6999999999999993D, 6.083333333333336D, 16.0D, -0.3000000000000007D), Block.box(1.0933333333333337D, 0.0D, -3.0D, 6.073333333333334D, 2.0D, 19.0D), Block.box(0.0833333333333357D, 0.5D, -1.0D, 16.083333333333336D, 1.5D, 0.0D), Block.box(0.0833333333333357D, 15.5D, -1.0D, 16.083333333333336D, 16.5D, 0.0D), Block.box(0.0833333333333357D, 0.5D, 16.0D, 16.083333333333336D, 1.5D, 17.0D), Block.box(0.0833333333333357D, 15.5D, 16.0D, 16.083333333333336D, 16.5D, 17.0D), Block.box(14.083333333333336D, 0.0D, -3.0D, 18.083333333333336D, 17.0D, 0.9999999999999991D), Block.box(14.083333333333336D, 0.0D, 15.0D, 18.083333333333336D, 17.0D, 19.0D), Block.box(8.593333333333337D, 0.0D, -3.0D, 13.573333333333334D, 2.0D, 19.0D)).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();
    protected static final VoxelShape END_W = (VoxelShape)Stream.of(Block.box(-0.6583333333333314D, 2.0D, 1.0416666666666679D, -0.25833333333333286D, 16.0D, 6.041666666666668D), Block.box(-0.6583333333333314D, 2.0D, 8.541666666666668D, -0.25833333333333286D, 16.0D, 13.541666666666668D), Block.box(16.34166666666667D, 2.0D, 8.541666666666668D, 16.741666666666667D, 16.0D, 13.541666666666668D), Block.box(16.34166666666667D, 2.0D, 1.0416666666666679D, 16.741666666666667D, 16.0D, 6.041666666666668D), Block.box(-2.958333333333332D, 0.0D, 1.0516666666666659D, 19.041666666666668D, 2.0D, 6.031666666666666D), Block.box(16.041666666666668D, 0.5D, 0.04166666666666785D, 17.041666666666668D, 1.5D, 16.041666666666668D), Block.box(16.041666666666668D, 15.5D, 0.04166666666666785D, 17.041666666666668D, 16.5D, 16.041666666666668D), Block.box(-0.9583333333333321D, 0.5D, 0.04166666666666785D, 0.04166666666666785D, 1.5D, 16.041666666666668D), Block.box(-0.9583333333333321D, 15.5D, 0.04166666666666785D, 0.04166666666666785D, 16.5D, 16.041666666666668D), Block.box(15.041666666666668D, 0.0D, 14.041666666666668D, 19.041666666666668D, 17.0D, 18.041666666666668D), Block.box(-2.958333333333332D, 0.0D, 14.041666666666668D, 1.0416666666666679D, 17.0D, 18.041666666666668D), Block.box(-2.958333333333332D, 0.0D, 8.55166666666667D, 19.041666666666668D, 2.0D, 13.531666666666666D)).reduce((v1, v2) -> {
        return Shapes.join(v1, v2, BooleanOp.OR);
    }).get();
    private static final EnumProperty<PartDefinition> PART = EnumProperty.create("part", PartDefinition.class);

    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        switch((Direction)state.getValue(FACING)) {
            case NORTH:
                PartDefinition part1 = (PartDefinition)state.getValue(PART);
                if (part1 == PartDefinition.MIDDLE) {
                    return NS;
                } else {
                    if (part1 == PartDefinition.END) {
                        return END_S;
                    }

                    return END_N;
                }
            case SOUTH:
                PartDefinition part2 = (PartDefinition)state.getValue(PART);
                if (part2 == PartDefinition.MIDDLE) {
                    return NS;
                } else {
                    if (part2 == PartDefinition.END) {
                        return END_N;
                    }

                    return END_S;
                }
            case EAST:
                PartDefinition part3 = (PartDefinition)state.getValue(PART);
                if (part3 == PartDefinition.MIDDLE) {
                    return WE;
                } else {
                    if (part3 == PartDefinition.END) {
                        return END_W;
                    }

                    return END_E;
                }
            case WEST:
            default:
                PartDefinition part4 = (PartDefinition)state.getValue(PART);
                if (part4 == PartDefinition.MIDDLE) {
                    return WE;
                } else {
                    return part4 == PartDefinition.END ? END_E : END_W;
                }
        }
    }

    public BridgeRope(Properties properties) {
        super(Properties.of(Material.WOOD).noOcclusion().sound(SoundType.WOOD).lightLevel(getLightLevel(15)).strength(0.5F, 2.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(PART, PartDefinition.MIDDLE).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }
}