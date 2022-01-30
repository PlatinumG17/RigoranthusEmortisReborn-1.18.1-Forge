package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.EmorticCortexTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class EmorticCortex extends ModBlock implements EntityBlock {

    public EmorticCortex() {
        super(defaultProperties().noOcclusion().lightLevel((state) -> 15), "emortic_cortex");
    }

    public static final VoxelShape CORTEX_SHAPE = Stream.of(
            Block.box(1, 1.775, 1, 2, 10.775, 15),
            Block.box(14, 1.775, 1, 15, 10.775, 15),
            Block.box(1, 1.775, 1, 15, 10.775, 2),
            Block.box(1, 1.775, 14, 15, 10.775, 15),
            Block.box(0, 1.025, 0, 2.75, 11.725, 2.75),
            Block.box(13.25, 1.025, 0, 16, 11.725, 2.75),
            Block.box(13.25, 1.025, 13.25, 16, 11.725, 16),
            Block.box(0, 1.025, 13.25, 2.75, 11.725, 16),
            Block.box(0, 0.025, 0, 16, 1.025, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return CORTEX_SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EmorticCortexTile(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}