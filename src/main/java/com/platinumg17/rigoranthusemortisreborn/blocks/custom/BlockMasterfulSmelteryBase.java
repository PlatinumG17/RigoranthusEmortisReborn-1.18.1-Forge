package com.platinumg17.rigoranthusemortisreborn.blocks.custom;

import com.mojang.math.Vector3d;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery.SmelteryTileEntityBase;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.items.specialized.smeltery.ItemAugment;
import com.platinumg17.rigoranthusemortisreborn.items.specialized.smeltery.ItemSmelteryCopy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * @author Qelifern
 * https://github.com/Qelifern/IronFurnaces
 * */
public abstract class BlockMasterfulSmelteryBase extends Block implements EntityBlock {

    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 2);
//    public static final IntegerProperty JOVIAL = IntegerProperty.create("jovial", 0, 2);

    public BlockMasterfulSmelteryBase(Properties properties) {
        super(properties.destroyTime(3F));
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false).setValue(TYPE, 0)/*.setValue(JOVIAL, 0)*/);
    }

    public MenuProvider getMenuProvider(BlockState p_49234_, Level p_49235_, BlockPos p_49236_) {
        BlockEntity blockentity = p_49235_.getBlockEntity(p_49236_);
        return blockentity instanceof MenuProvider ? (MenuProvider) blockentity : null;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(BlockStateProperties.LIT) ? 14 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return (BlockState) this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState p_180633_3_, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            SmelteryTileEntityBase te = (SmelteryTileEntityBase) world.getBlockEntity(pos);
            if (stack.hasCustomHoverName()) {
                te.setCustomName(stack.getDisplayName());
            }
            te.totalCookTime = te.getCookTimeConfig();
            te.placeConfig();
        }
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
        ItemStack stack = player.getItemInHand(handIn).copy();
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (player.getItemInHand(handIn).getItem() instanceof ItemAugment && !(player.isCrouching())) {
                return this.interactAugment(world, pos, player, handIn, stack);
//            } else if (player.getItemInHand(handIn).isEmpty() && player.isCrouching()) {
//                return this.interactJovial(world, pos, player, handIn, 0);
            } else if (player.getItemInHand(handIn).getItem() instanceof ItemSmelteryCopy && !(player.isCrouching())) {
                return this.interactCopy(world, pos, player, handIn);
            } else {
                this.interactWith(world, pos, player);
            }
            return InteractionResult.SUCCESS;
        }
    }

    private InteractionResult interactCopy(Level world, BlockPos pos, Player player, InteractionHand handIn) {
        int j = player.getInventory().selected;
        ItemStack stack = player.getInventory().getItem(j);
        if (!(stack.getItem() instanceof ItemSmelteryCopy)) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity te = world.getBlockEntity(pos);
        if (!(te instanceof SmelteryTileEntityBase)) {
            return InteractionResult.SUCCESS;
        }

        int[] settings = new int[((SmelteryTileEntityBase) te).smelterySettings.size()];
        for (int i = 0; i < ((SmelteryTileEntityBase) te).smelterySettings.size(); i++) {
            settings[i] = ((SmelteryTileEntityBase) te).smelterySettings.get(i);
        }
        stack.getOrCreateTag().putIntArray("settings", settings);

        ((SmelteryTileEntityBase) te).onUpdateSent();
        player.sendMessage(new TextComponent("Settings copied"), player.getUUID());
        return InteractionResult.SUCCESS;
    }

    private InteractionResult interactAugment(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
        if (!(player.getItemInHand(handIn).getItem() instanceof ItemAugment)) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity te = world.getBlockEntity(pos);
        if (!(te instanceof SmelteryTileEntityBase)) {
            return InteractionResult.SUCCESS;
        }
        if (!(((WorldlyContainer) te).getItem(3).isEmpty())) {
            if (!player.isCreative()) {
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), ((WorldlyContainer) te).getItem(3)));
            }
        }
        ItemStack newStack = new ItemStack(stack.getItem(), 1);
        newStack.setTag(stack.getTag());
        ((WorldlyContainer) te).setItem(3, newStack);
        world.playSound(null, te.getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!player.isCreative()) {
            player.getItemInHand(handIn).shrink(1);
        }
        ((SmelteryTileEntityBase) te).onUpdateSent();
        return InteractionResult.SUCCESS;
    }

//    private InteractionResult interactJovial(Level world, BlockPos pos, Player player, InteractionHand handIn, int jovial) {
//        if (!(player.getItemInHand(handIn).isEmpty()))) {
//            return InteractionResult.SUCCESS;
//        }
//        BlockEntity te = world.getBlockEntity(pos);
//        if (!(te instanceof SmelteryTileEntityBase)) {
//            return InteractionResult.SUCCESS;
//        }
//        ((SmelteryTileEntityBase) te).setJovial(jovial);
//        return InteractionResult.SUCCESS;
//    }

    private void interactWith(Level world, BlockPos pos, Player player) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof MenuProvider) {
            NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
        if (state.getValue(BlockStateProperties.LIT)) {
            if (world.getBlockEntity(pos) == null) {
                return;
            }
            if (!(world.getBlockEntity(pos) instanceof SmelteryTileEntityBase)) {
                return;
            }
            SmelteryTileEntityBase tile = ((SmelteryTileEntityBase) world.getBlockEntity(pos));
            if (tile.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
                double lvt_5_1_ = (double) pos.getX() + 0.5D;
                double lvt_7_1_ = (double) pos.getY();
                double lvt_9_1_ = (double) pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }

                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);

            } else if (tile.getItem(3).getItem() == Registration.BLASTING_AUGMENT.get()) {
                double lvt_5_1_ = (double) pos.getX() + 0.5D;
                double lvt_7_1_ = (double) pos.getY();
                double lvt_9_1_ = (double) pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }

                Direction lvt_11_1_ = (Direction) state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis lvt_12_1_ = lvt_11_1_.getAxis();
                double lvt_13_1_ = 0.52D;
                double lvt_15_1_ = rand.nextDouble() * 0.6D - 0.3D;
                double lvt_17_1_ = lvt_12_1_ == Direction.Axis.X ? (double) lvt_11_1_.getStepX() * 0.52D : lvt_15_1_;
                double lvt_19_1_ = rand.nextDouble() * 9.0D / 16.0D;
                double lvt_21_1_ = lvt_12_1_ == Direction.Axis.Z ? (double) lvt_11_1_.getStepZ() * 0.52D : lvt_15_1_;
                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_ + lvt_17_1_, lvt_7_1_ + lvt_19_1_, lvt_9_1_ + lvt_21_1_, 0.0D, 0.0D, 0.0D);

            } else {
                double d0 = (double) pos.getX() + 0.5D;
                double d1 = (double) pos.getY();
                double d2 = (double) pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);

                }

                Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
                double d6 = rand.nextDouble() * 6.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
                world.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);

            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof SmelteryTileEntityBase) {
                Containers.dropContents(world, pos, (SmelteryTileEntityBase) te);
                ((SmelteryTileEntityBase) te).grantStoredRecipeExperience(world, new Vector3d(pos.getX(), pos.getY(), pos.getZ()));
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, oldState, p_196243_5_);
        }
    }

    public int getComparatorInputOverride(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer((WorldlyContainer) world.getBlockEntity(pos));

    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return (BlockState) p_185499_1_.setValue(BlockStateProperties.HORIZONTAL_FACING, p_185499_2_.rotate((Direction) p_185499_1_.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation((Direction) p_185471_1_.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    private int calculateOutput(Level worldIn, BlockPos pos, BlockState state) {
        SmelteryTileEntityBase tile = ((SmelteryTileEntityBase) worldIn.getBlockEntity(pos));
        int i = this.getComparatorInputOverride(state, worldIn, pos);
        if (tile != null) {
            int j = tile.smelterySettings.get(9);
            return tile.smelterySettings.get(8) == 4 ? Math.max(i - j, 0) : i;
        }
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }


    @Override
    public int getSignal(BlockState p_180656_1_, BlockGetter p_180656_2_, BlockPos p_180656_3_, Direction p_180656_4_) {
        return super.getDirectSignal(p_180656_1_, p_180656_2_, p_180656_3_, p_180656_4_);
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter world, BlockPos pos, Direction direction) {
        SmelteryTileEntityBase furnace = ((SmelteryTileEntityBase) world.getBlockEntity(pos));
        if (furnace != null) {
            int mode = furnace.smelterySettings.get(8);
            if (mode == 0) {
                return 0;
            } else if (mode == 1) {
                return 0;
            } else if (mode == 2) {
                return 0;
            } else {
                return calculateOutput(furnace.getLevel(), pos, blockState);
            }
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT, TYPE/*, JOVIAL*/);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createFurnaceTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends SmelteryTileEntityBase> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, SmelteryTileEntityBase::tick);
    }
}

/*
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 2);

    public SmelteryTileEntityBase(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false).setValue(TYPE, 0));
    }

    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        BlockEntity blockentity = world.getBlockEntity(pos);
        return blockentity instanceof MenuProvider ? (MenuProvider)blockentity : null;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(BlockStateProperties.LIT) ? 14 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            SmelteryTileEntityBase te = (SmelteryTileEntityBase) world.getBlockEntity(pos);
            if (stack.hasCustomHoverName()) {
                te.setCustomName(stack.getDisplayName());
            }
            te.totalCookTime = te.getCookTimeConfig();
            te.placeConfig();
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
        ItemStack stack = player.getItemInHand(handIn).copy();
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (player.getItemInHand(handIn).getItem() instanceof ItemAugment && !(player.isCrouching())) {
                return this.interactAugment(world, pos, player, handIn, stack);
            } else if (player.getItemInHand(handIn).getItem() instanceof ItemSmelteryCopy && !(player.isCrouching())) {
                return this.interactCopy(world, pos, player, handIn);
            } else {
                this.interactWith(world, pos, player);
            }
            return InteractionResult.SUCCESS;
        }
    }

    private InteractionResult interactCopy(Level world, BlockPos pos, Player player, InteractionHand handIn) {
        int j = player.inventory.selected;
        ItemStack stack = player.inventory.getItem(j);
        if (!(stack.getItem() instanceof ItemSmelteryCopy)) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity te = world.getBlockEntity(pos);
        if (!(te instanceof SmelteryTileEntityBase)) {
            return InteractionResult.SUCCESS;
        }

        int[] settings = new int[((SmelteryTileEntityBase) te).smelterySettings.size()];
        for (int i = 0; i < ((SmelteryTileEntityBase) te).smelterySettings.size(); i++)
        {
            settings[i] = ((SmelteryTileEntityBase) te).smelterySettings.get(i);
        }
        stack.getOrCreateTag().putIntArray("settings", settings);

        ((SmelteryTileEntityBase)te).onUpdateSent();
        player.sendMessage(new TextComponent("Settings copied"), player.getUUID());
        return InteractionResult.SUCCESS;
    }
    private InteractionResult interactAugment(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
        if (!(player.getItemInHand(handIn).getItem() instanceof ItemAugment)) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity te = world.getBlockEntity(pos);
        if (!(te instanceof SmelteryTileEntityBase)) {
            return InteractionResult.SUCCESS;
        }
        if (!(((WorldlyContainer) te).getItem(3).isEmpty())) {
            if (!player.isCreative()) {
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), ((WorldlyContainer) te).getItem(3)));
            }
        }
        ItemStack newStack = new ItemStack(stack.getItem(), 1);
        newStack.setTag(stack.getTag());
        ((WorldlyContainer) te).setItem(3, newStack);
        world.playSound(null, te.getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.9F, 0.9F);
        if (!player.isCreative()) {
            player.getItemInHand(handIn).shrink(1);
        }
        ((SmelteryTileEntityBase)te).onUpdateSent();
        return InteractionResult.SUCCESS;
    }

    private void interactWith(Level world, BlockPos pos, Player player) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof MenuProvider) {
            NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
        if (state.getValue(BlockStateProperties.LIT)) {
            if (world.getBlockEntity(pos) == null) {
                return;
            }
            if (!(world.getBlockEntity(pos) instanceof SmelteryTileEntityBase)) {
                return;
            }
            SmelteryTileEntityBase tile = ((SmelteryTileEntityBase) world.getBlockEntity(pos));
            if (tile.getItem(3).getItem() == Registration.SMOKING_AUGMENT.get()) {
                double lvt_5_1_ = (double)pos.getX() + 0.5D;
                double lvt_7_1_ = (double)pos.getY();
                double lvt_9_1_ = (double)pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }
                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_, lvt_7_1_ + 1.1D, lvt_9_1_, 0.0D, 0.0D, 0.0D);
            }
            else if (tile.getItem(3).getItem() == Registration.BLASTING_AUGMENT.get()) {
                double lvt_5_1_ = (double)pos.getX() + 0.5D;
                double lvt_7_1_ = (double)pos.getY();
                double lvt_9_1_ = (double)pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 0.9F, 0.9F, false);
                }
                Direction lvt_11_1_ = (Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis lvt_12_1_ = lvt_11_1_.getAxis();
                double lvt_13_1_ = 0.52D;
                double lvt_15_1_ = rand.nextDouble() * 0.6D - 0.3D;
                double lvt_17_1_ = lvt_12_1_ == Direction.Axis.X ? (double)lvt_11_1_.getStepX() * 0.52D : lvt_15_1_;
                double lvt_19_1_ = rand.nextDouble() * 9.0D / 16.0D;
                double lvt_21_1_ = lvt_12_1_ == Direction.Axis.Z ? (double)lvt_11_1_.getStepZ() * 0.52D : lvt_15_1_;
                world.addParticle(ParticleTypes.SMOKE, lvt_5_1_ + lvt_17_1_, lvt_7_1_ + lvt_19_1_, lvt_9_1_ + lvt_21_1_, 0.0D, 0.0D, 0.0D);
            }
            else
            {
                double d0 = (double) pos.getX() + 0.5D;
                double d1 = (double) pos.getY() + 0.2D;
                double d2 = (double) pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    world.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 0.9F, 0.9F, false);
                }
                Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
                double d6 = rand.nextDouble() * 6.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.26D : d4;
                world.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public void onReplaced(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof SmelteryTileEntityBase) {
                Containers.dropContents(world, pos, (SmelteryTileEntityBase) te);
                ((SmelteryTileEntityBase)te).grantStoredRecipeExperience(world, Vec3.atCenterOf(pos));
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, oldState, p_196243_5_);
        }
    }

    public boolean hasComparatorInputOverride(BlockState p_149740_1_) {
        return true;
    }

    public int getComparatorInputOverride(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer((Container) world.getBlockEntity(pos));
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(BlockStateProperties.HORIZONTAL_FACING, rot.rotate((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    private int calculateOutput(Level worldIn, BlockPos pos, BlockState state) {
        SmelteryTileEntityBase tile = ((SmelteryTileEntityBase)worldIn.getBlockEntity(pos));
        int i = this.getComparatorInputOverride(state, worldIn, pos);
        if (tile != null) {
            int j = tile.smelterySettings.get(9);
            return tile.smelterySettings.get(8) == 4 ? Math.max(i - j, 0) : i;
        }
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }

    @Override
    public int getSignal(BlockState p_180656_1_, BlockGetter p_180656_2_, BlockPos p_180656_3_, Direction p_180656_4_) {
        return super.getDirectSignal(p_180656_1_, p_180656_2_, p_180656_3_, p_180656_4_);
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter world, BlockPos pos, Direction direction) {
        SmelteryTileEntityBase furnace = ((SmelteryTileEntityBase) world.getBlockEntity(pos));
        if (furnace != null) {
            int mode = furnace.smelterySettings.get(8);
            if (mode == 0) {
                return 0;
            }
            else if (mode == 1) {
                return 0;
            }
            else if (mode == 2) {
                return 0;
            }
            else {
                return calculateOutput(furnace.getLevel(), pos, blockState);
            }
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT, TYPE);
    }
}*/