package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.RitualTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class RitualVesselBlock extends ModBlock implements EntityBlock {
    public RitualVesselBlock(String registryName) {
        super(defaultProperties().noOcclusion().lightLevel((b) -> b.getValue(LIT) ? 15 : 0), registryName);
    }

    public static final Property<Boolean> LIT = BooleanProperty.create("lit");

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!(worldIn.getBlockEntity(pos) instanceof RitualTile) || handIn != InteractionHand.MAIN_HAND || !player.getMainHandItem().isEmpty())
            return InteractionResult.PASS;//super.use(state, worldIn, pos, player, handIn, hit);

        if(!worldIn.isClientSide) {
            RitualTile tile = (RitualTile) worldIn.getBlockEntity(pos);

            if (tile.stack != null && player.getItemInHand(handIn).isEmpty()) {
                if(worldIn.getBlockState(pos.above()).getMaterial() != Material.AIR)
                    return InteractionResult.SUCCESS;
                ItemEntity item = new ItemEntity(worldIn, player.getX(), player.getY(), player.getZ(), tile.stack);
                worldIn.addFreshEntity(item);
                tile.stack = null;
            }

            else if (!player.inventory.getSelected().isEmpty()) {
                if(tile.stack != null){
                    ItemEntity item = new ItemEntity(worldIn, player.getX(), player.getY(), player.getZ(), tile.stack);
                    worldIn.addFreshEntity(item);
                }
                tile.stack = player.inventory.removeItem(player.inventory.selected, 1);
            }
            worldIn.sendBlockUpdated(pos, state, state, 2);
            if (tile.ritual != null && !tile.isRitualDone()) {
                tile.startRitual();
            }
        }
        return  InteractionResult.SUCCESS;
        //return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, blockIn, fromPos, isMoving);
        if (!world.isClientSide() && world.getBlockEntity(pos) instanceof RitualTile) {
            ((RitualTile) world.getBlockEntity(pos)).isOff = world.hasNeighborSignal(pos);
            BlockUtil.safelyUpdateState(world, pos);
        }
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (worldIn.getBlockEntity(pos) instanceof RitualTile) {
            RitualTile tile = (RitualTile) worldIn.getBlockEntity(pos);
            if (tile.stack != null){
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.stack));
            }
            if (tile.ritual != null && !tile.ritual.isRunning() && !tile.ritual.isDone()) {
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RigoranthusEmortisRebornAPI.getInstance().getRitualItemMap().get(tile.ritual.getID()))));
            }
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            world.setBlock(pos, state.setValue(LIT, false), 2);
        }
    }

    private static final VoxelShape VESSEL_SHAPE = Stream.of(
            Block.box(4, 14.5, 4, 12, 14.7, 12),
            Block.box(2, 0, 2, 4, 3, 4),
            Block.box(12, 0, 2, 14, 3, 4),
            Block.box(2, 0, 12, 4, 3, 14),
            Block.box(12, 0, 12, 14, 3, 14),
            Block.box(12, 13, 12, 14, 16, 14),
            Block.box(2, 13, 12, 4, 16, 14),
            Block.box(2, 13, 2, 4, 16, 4),
            Block.box(12, 13, 2, 14, 16, 4),
            Block.box(2, 0, 10, 3, 1, 12),
            Block.box(2, 0, 4, 3, 1, 6),
            Block.box(13, 0, 4, 14, 1, 6),
            Block.box(13, 0, 10, 14, 1, 12),
            Block.box(4, 0, 2, 6, 1, 3),
            Block.box(10, 0, 2, 12, 1, 3),
            Block.box(10, 0, 13, 12, 1, 14),
            Block.box(4, 0, 13, 6, 1, 14),
            Block.box(13, 14, 10, 14, 15, 12),
            Block.box(13, 14, 4, 14, 15, 6),
            Block.box(12, 14, 10, 13, 15, 11),
            Block.box(12, 14, 5, 13, 15, 6),
            Block.box(4, 14, 13, 6, 15, 14),
            Block.box(2, 14, 10, 3, 15, 12),
            Block.box(4, 14.7, 5, 5, 16, 11),
            Block.box(11, 14.7, 5, 12, 16, 11),
            Block.box(4, 14, 2, 6, 15, 3),
            Block.box(10, 14, 2, 12, 15, 3),
            Block.box(4, 14.7, 4, 12, 16, 5),
            Block.box(4, 14.7, 11, 12, 16, 12),
            Block.box(10, 14, 3, 11, 15, 4),
            Block.box(5, 14, 3, 6, 15, 4),
            Block.box(10, 14, 13, 12, 15, 14),
            Block.box(10, 14, 12, 11, 15, 13),
            Block.box(5, 14, 12, 6, 15, 13),
            Block.box(2, 14, 4, 3, 15, 6),
            Block.box(3, 14, 10, 4, 15, 11),
            Block.box(3, 14, 5, 4, 15, 6),
            Block.box(4, 0, 4, 12, 1, 12),
            Block.box(4.95, 1, 4.95, 11.05, 2, 11.05)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public VoxelShape getShape(BlockState blockState, BlockGetter reader, BlockPos blockPos, CollisionContext context) {
        return VESSEL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RitualTile(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}