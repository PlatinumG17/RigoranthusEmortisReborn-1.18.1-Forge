package com.platinumg17.rigoranthusemortisreborn.canis.common.block;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.IBeddingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;
import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.block.tileentity.CanisBedTileEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage.CanisRespawnData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage.CanisRespawnStorage;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.CanisBedUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.EntityUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.WorldUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author ProPerciliv
 */
public class CanisBedBlock extends Block implements EntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    protected static final VoxelShape SHAPE_COLLISION = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public CanisBedBlock() {
        super(Block.Properties.of(Material.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {builder.add(FACING, WATERLOGGED);}
    @Override public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {return SHAPE;}
    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {return SHAPE_COLLISION;}

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {return new CanisBedTileEntity(pos, state);}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        state = state.setValue(FACING, placer.getDirection().getOpposite());
        CanisBedTileEntity canisBedTileEntity = WorldUtil.getTileEntity(worldIn, pos, CanisBedTileEntity.class);
        if (canisBedTileEntity != null) {
            CanisBedUtil.setBedVariant(canisBedTileEntity, stack);
            canisBedTileEntity.setPlacer(placer);
            CompoundTag tag = stack.getTagElement("rigoranthusemortisreborn");
            if (tag != null) {
                Component name = NBTUtilities.getTextComponent(tag, "name");
                UUID ownerId = NBTUtilities.getUniqueId(tag, "ownerId");
                canisBedTileEntity.setBedName(name);
                canisBedTileEntity.setOwner(ownerId);
            }
        }
        worldIn.setBlock(pos, state, Block.UPDATE_CLIENTS);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
    @Override public FluidState getFluidState(BlockState state) {return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);}
    @Override public BlockState rotate(BlockState state, Rotation rot) {return state.setValue(FACING, rot.rotate(state.getValue(FACING)));}
    @Override public BlockState mirror(BlockState state, Mirror mirrorIn) {return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));}

    @Override
    @Deprecated
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            CanisBedTileEntity canisBedTileEntity = WorldUtil.getTileEntity(worldIn, pos, CanisBedTileEntity.class);
            if (canisBedTileEntity != null) {
                ItemStack stack = player.getItemInHand(handIn);
                if (stack.getItem() == Items.NAME_TAG && stack.hasCustomHoverName()) {
                    canisBedTileEntity.setBedName(stack.getHoverName());
                    if (!player.abilities.instabuild) {
                        stack.shrink(1);
                    }
                    worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    return InteractionResult.SUCCESS;
                } else if (player.isShiftKeyDown() && canisBedTileEntity.getOwnerUUID() == null) {
                    List<CanisEntity> caniss = worldIn.getEntities(SpecializedEntityTypes.CANIS.get(), new AABB(pos).inflate(10D), (canis) -> canis.isAlive() && canis.isOwnedBy(player));
                    Collections.sort(caniss, new EntityUtil.Sorter(new Vec3(pos.getX(), pos.getY(), pos.getZ())));
                    CanisEntity closestStanding = null;
                    CanisEntity closestSitting = null;
                    for (CanisEntity canis : caniss) {
                        if (closestSitting != null && closestSitting != null) {
                            break;
                        }
                        if (closestSitting == null && canis.isInSittingPose()) {
                            closestSitting = canis;
                        } else if (closestStanding == null && !canis.isInSittingPose()) {
                            closestStanding = canis;
                        }
                    }
                    CanisEntity closests = closestStanding != null ? closestStanding : closestSitting;
                    if (closests != null) {
                        closests.setTargetBlock(pos);
                    }
                } else if (canisBedTileEntity.getOwnerUUID() != null) {
                    CanisRespawnData storage = CanisRespawnStorage.get(worldIn).remove(canisBedTileEntity.getOwnerUUID());
                    if (storage != null) {
                        CanisEntity canis = storage.respawn((ServerLevel) worldIn, player, pos.above());
                        canisBedTileEntity.setOwner(canis);
                        canis.setBedPos(canis.level.dimension(), pos);
                        return InteractionResult.SUCCESS;
                    } else {
                        Component name = canisBedTileEntity.getOwnerName();
                        player.sendMessage(new TranslatableComponent("block.rigoranthusemortisreborn.canis_bed.owner", name != null ? name : "someone"), Util.NIL_UUID);
                        return InteractionResult.FAIL;
                    }
                } else {
                    player.sendMessage(new TranslatableComponent("block.rigoranthusemortisreborn.canis_bed.set_owner_help"), Util.NIL_UUID);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        Pair<ICasingMaterial, IBeddingMaterial> materials = CanisBedUtil.getMaterials(stack);
        tooltip.add(materials.getLeft() != null
                ? materials.getLeft().getTooltip()
                : new TranslatableComponent("canisbed.casing.null").withStyle(ChatFormatting.RED));
        tooltip.add(materials.getRight() != null
                ? materials.getRight().getTooltip()
                : new TranslatableComponent("canisbed.bedding.null").withStyle(ChatFormatting.RED));
        if (materials.getLeft() == null && materials.getRight() == null) {
            tooltip.add(new TranslatableComponent("canisbed.explain.missing").withStyle(ChatFormatting.ITALIC));
        }
        CompoundTag tag = stack.getTagElement("rigoranthusemortisreborn");
        if (tag != null) {
            UUID ownerId = NBTUtilities.getUniqueId(tag, "ownerId");
            Component name = NBTUtilities.getTextComponent(tag, "name");
            Component ownerName = NBTUtilities.getTextComponent(tag, "ownerName");
            if (name != null) {
                tooltip.add(new TextComponent("Bed Name: ").withStyle(ChatFormatting.WHITE).append(name));
            }
            if (ownerName != null) {
                tooltip.add(new TextComponent("Name: ").withStyle(ChatFormatting.DARK_AQUA).append(ownerName));
            }
            if (ownerId != null && (flagIn.isAdvanced() || Screen.hasShiftDown())) {
                tooltip.add(new TextComponent("UUID: ").withStyle(ChatFormatting.AQUA).append(new TextComponent(ownerId.toString())));
            }
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        for (IBeddingMaterial beddingId : RigoranthusEmortisRebornAPI.BEDDING_MATERIAL.getValues()) {
            for (ICasingMaterial casingId : RigoranthusEmortisRebornAPI.CASING_MATERIAL.getValues()) {
                items.add(CanisBedUtil.createItemStack(casingId, beddingId));
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        CanisBedTileEntity canisBedTileEntity = WorldUtil.getTileEntity(world, pos, CanisBedTileEntity.class);
        if (canisBedTileEntity != null) {
            return CanisBedUtil.createItemStack(canisBedTileEntity.getCasing(), canisBedTileEntity.getBedding());
        }
        RigoranthusEmortisReborn.LOGGER.debug("Unable to pick block on canis bed.");
        return ItemStack.EMPTY;
    }
}

/*


     public static final VoxelShape NORTH_SHAPE = Stream.of(
        Block.box(0.1, 3.2, -1.847, 15.9, 6.4, 15.8),
        Block.box(0.1, 3.2, 15.8, 15.9, 6.4, 31.6),
        Block.box(0, 0, 16, 16, 3.2, 32),
        Block.box(0, 0, -1.847, 16, 3.2, 16),
        Block.box(1.3, 8.3, -1, 3.45, 11.6, 0.6),
        Block.box(1.3, 4.3, -1, 14.7, 6.6, 0.6),
        Block.box(12.55, 8.3, -1, 14.7, 11.6, 0.6),
        Block.box(0, 3.2, 30.4, 16, 11.6, 32),
        Block.box(-0.3, 8.2, -1, 1.3, 11.6, 15),
        Block.box(14.7, 8.2, -1, 16.3, 11.6, 15),
        Block.box(-0.3, 3.2, -1, 1.3, 6.6, 15),
        Block.box(-0.3, 8.2, 15, 1.3, 11.6, 31),
        Block.box(-0.3, 3.2, 15, 1.3, 6.6, 31),
        Block.box(14.7, 3.2, -1, 16.3, 6.6, 1502),
        Block.box(14.7, 8.2, 15, 16.3, 11.6, 31),
        Block.box(14.7, 3.2, 15, 16.3, 6.6, 31)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape EAST_SHAPE = Stream.of(
            Block.box(0.2, 3.2, 0.1, 16, 6.4, 15.9),
            Block.box(-15.6, 3.2, 0.1, 0.2, 6.4, 15.9),
            Block.box(-16, 0, 0, 1.9, 3.2, 16),
            Block.box(1.9, 0, 0, 16, 3.2, 16),
            Block.box(15.4, 8.3, 1.3, 17, 11.6, 3.45),
            Block.box(15.4, 4.3, 1.3, 17, 6.6, 14.7),
            Block.box(15.4, 8.3, 12.55, 17, 11.6, 14.7),
            Block.box(-16, 3.2, 0, -14.4, 11.6, 16),
            Block.box(1, 8.2, -0.3, 17, 11.6, 1.3),
            Block.box(1, 8.2, 14.7, 17, 11.6, 16.3),
            Block.box(1, 3.2, -0.3, 17, 6.6, 1.3),
            Block.box(-15, 8.2, -0.3, 1, 11.6, 1.3),
            Block.box(-15, 3.2, -0.3, 1, 6.6, 1.3),
            Block.box(1, 3.2, 14.7, 17, 6.6, 16.3),
            Block.box(-15, 8.2, 14.7, 1, 11.6, 16.3),
            Block.box(-15, 3.2, 14.7, 1, 6.6, 16.3)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SOUTH_SHAPE = Stream.of(
            Block.box(0.1, 3.2, 0.2, 15.9, 6.4, 16),
            Block.box(0.1, 3.2, -15.6, 15.9, 6.4, 0.2),
            Block.box(0, 0, -16, 16, 3.2, 1.9),
            Block.box(0, 0, 1.9, 16, 3.2, 16),
            Block.box(12.55, 8.3, 15.4, 14.7, 11.6, 17),
            Block.box(1.3, 4.3, 15.4, 14.7, 6.6, 17),
            Block.box(1.3, 8.3, 15.4, 3.45, 11.6, 17),
            Block.box(0, 3.2, -16, 16, 11.6, -14.4),
            Block.box(14.7, 8.2, 1, 16.3, 11.6, 17),
            Block.box(-0.3, 8.2, 1, 1.3, 11.6, 17),
            Block.box(14.7, 3.2, 1, 16.3, 6.6, 17),
            Block.box(14.7, 8.2, -15, 16.3, 11.6, 1),
            Block.box(14.7, 3.2, -15, 16.3, 6.6, 1),
            Block.box(-0.3, 3.2, 1, 1.3, 6.6, 17),
            Block.box(-0.3, 8.2, -15, 1.3, 11.6, 1),
            Block.box(-0.3, 3.2, -15, 1.3, 6.6, 1)
            ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape WEST_SHAPE = Stream.of(
            Block.box(4.77, 3.2, 0.1, 15.8, 6.4, 15.9),
            Block.box(15.8, 3.2, 0.1, 31.6, 6.4, 15.9),
            Block.box(16, 0, 0, 32, 3.2, 16),
            Block.box(4.77, 0, 0, 16, 3.2, 16),
            Block.box(-1, 8.3, 12.55, 0.6, 11.6, 14.7),
            Block.box(-1, 4.3, 1.3, 0.6, 6.6, 14.7),
            Block.box(-1, 8.3, 1.3, 0.6, 11.6, 3.45),
            Block.box(30.4, 3.2, 0, 32, 11.6, 16),
            Block.box(-1, 8.2, 14.7, 15, 11.6, 16.3),
            Block.box(-1, 8.2, -0.3, 15, 11.6, 1.3),
            Block.box(-1, 3.2, 14.7, 15, 6.6, 16.3),
            Block.box(15, 8.2, 14.7, 31, 11.6, 16.3),
            Block.box(15, 3.2, 14.7, 31, 6.6, 16.3),
            Block.box(-1, 3.2, -0.3, 15, 6.6, 1.3),
            Block.box(15, 8.2, -0.3, 31, 11.6, 1.3),
            Block.box(15, 3.2, -0.3, 31, 6.6, 1.3)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    */