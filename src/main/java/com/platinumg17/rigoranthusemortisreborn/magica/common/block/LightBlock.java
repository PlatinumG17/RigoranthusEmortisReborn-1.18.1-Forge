package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.LightTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public class LightBlock extends ModBlock implements EntityBlock {

    public static final Property<Integer> LIGHT_LEVEL = IntegerProperty.create("level", 0, 15);

    protected static final VoxelShape SHAPE = box(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D);

    public LightBlock() {
        super(defaultProperties().lightLevel((bs)-> bs.getValue(LIGHT_LEVEL) == 0 ? 14 :  bs.getValue(LIGHT_LEVEL)).noCollission().noOcclusion().dynamicShape().strength(0f,0f), "light_block");
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LightTile(pos, state);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if(context.getLevel().getBlockEntity(context.getClickedPos()) instanceof LightTile) {
            Random random = context.getLevel().random;
            LightTile tile = (LightTile) context.getLevel().getBlockEntity(context.getClickedPos());
            tile.red = Math.max(10, random.nextInt(255));
            tile.green = Math.max(10, random.nextInt(255));
            tile.blue = Math.max(10, random.nextInt(255));
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}