package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.canis.common.util.WorldUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicCipherTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibBlockNames;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

//import static com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicCipherTile.UTILIZED;

public class CipherBlock extends ModBlock implements EntityBlock, SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CipherBlock() {
        super(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).sound(SoundType.STONE).strength(10f, 15f).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> 10), LibBlockNames.PSYGLYPHIC_CIPHER);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override public FluidState getFluidState(BlockState state) {return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);}
    @Override public BlockState rotate(BlockState state, Rotation rot) {return state.setValue(FACING, rot.rotate(state.getValue(FACING)));}
    @Override public BlockState mirror(BlockState state, Mirror mirrorIn) {return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));}
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {builder.add(FACING, WATERLOGGED/*, UTILIZED*/);}

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
//        CompoundTag tag = context.getItemInHand().getTag();
//        if (tag != null && tag.contains("BlockEntityTag")) {
//            tag = tag.getCompound("BlockEntityTag");
//            if (tag.contains("transcribed") && tag.getBoolean("transcribed")) {
//                return state.setValue(UTILIZED, true);
//            }
//        }
        if (context.getNearestLookingDirection().getAxis().isHorizontal()) {
            return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(false));
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return state.setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState state, LevelAccessor world, BlockPos blockPos1, BlockPos blockPos2) {
//        PsyglyphicCipherTile cipherTile = (PsyglyphicCipherTile)world.getBlockEntity(blockPos1);
//        if(cipherTile.hasBeenUsed){
//            state.setValue(UTILIZED, true);
//        }
        if (!blockState.canSurvive(world, blockPos1)) {
            return direction.getOpposite() == blockState.getValue(FACING) && !blockState.canSurvive(world, blockPos1) ? Blocks.AIR.defaultBlockState() : blockState;
        }else {
            if (blockState.getValue(WATERLOGGED)) {
                world.scheduleTick(blockPos1, Fluids.WATER, Fluids.WATER.getTickDelay(world));
            }
            return super.updateShape(blockState, direction, state, world, blockPos1, blockPos2);
        }
    }

    protected boolean mayPlaceOn(BlockState blockState, BlockGetter reader, BlockPos blockPos) {
        return !blockState.getCollisionShape(reader, blockPos).getFaceShape(Direction.UP).isEmpty() || blockState.isFaceSturdy(reader, blockPos, Direction.UP);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState blockState, LevelReader reader, BlockPos blockPos) {
        BlockPos blockpos = blockPos.below();
        return this.mayPlaceOn(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
        PsyglyphicCipherTile cipher = WorldUtil.getTileEntity(world, pos, PsyglyphicCipherTile.class);
        if (cipher != null) {
//        if (!cipher.hasBeenUsed) {
            double lvt_5_1_ = (double) pos.getX() + 0.5D;
            double lvt_7_1_ = (double) pos.getY();
            double lvt_9_1_ = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                world.playLocalSound(lvt_5_1_, lvt_7_1_, lvt_9_1_, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.4F, 0.4F, false);
            }
            for (int i = 0; i < 4; ++i) {
                double d0 = (double) pos.getX() + (double) rand.nextFloat();
                double d1 = (double) pos.getY() + (double) rand.nextFloat();
                double d2 = (double) pos.getZ() + (double) rand.nextFloat();
                double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
                double d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
                double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
                int j = rand.nextInt(2) * 2 - 1;
                if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
                    d0 = (double) pos.getX() + 0.5D + 0.25D * (double) j;
                    d3 = rand.nextFloat() * 2.0F * (float) j;
                } else {
                    d2 = (double) pos.getZ() + 0.5D + 0.25D * (double) j;
                    d5 = rand.nextFloat() * 2.0F * (float) j;
                }
                world.addParticle(ParticleTypes.ENCHANT, d0, d1, d2, d3, d4, d5);
            }
        }
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public InteractionResultHolder use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
//        if(worldIn.isClientSide || handIn != InteractionHand.MAIN_HAND)
//            return InteractionResult.SUCCESS;
//        ItemStack stack = player.getItemInHand(handIn);
//        if (!stack.isEmpty() && stack.getItem() == Items.PAPER) {
//            PsyglyphicCipherTile cipher = WorldUtil.getTileEntity(worldIn, pos, PsyglyphicCipherTile.class);
//            if (cipher != null) {
//                if (cipher.hasBeenUsed) {
//                    PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.tooltip.cipher_used"));
//                    return InteractionResult.SUCCESS;
//                } else {
//                    worldIn.playSound(player, pos, SoundEvents.VILLAGER_WORK_CARTOGRAPHER, SoundSource.BLOCKS, 0.8F, 0.7F);
//                    player.inventory.add(ItemInit.PSYGLYPHIC_SCRIPT.get().getDefaultInstance());
//                    PortUtil.sendMessage(player, new TranslatableComponent("tooltip.rigoranthusemortisreborn.cipher.not_used"));
//                    state.setValue(UTILIZED, true);
//                    if (!player.abilities.instabuild) {
//                        stack.shrink(1);
//                    }
//                    Networking.sendToNearby(worldIn, pos, new PacketREEffect(PacketREEffect.MobEffectCategory.BURST, pos, new ParticleColor.IntWrapper(255, 0, 0)));
//                }
//            }
//        }
//        worldIn.sendBlockUpdated(pos, state, state, 3);
//        return InteractionResult.SUCCESS;
//    }
//    public InteractionResultHolder use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
//        ItemStack stack = player.getItemInHand(handIn);
//        if (!stack.isEmpty() && stack.getItem() == Items.PAPER) {
//            if(!worldIn.isClientSide) {
//                PsyglyphicCipherTile cipher = WorldUtil.getTileEntity(worldIn, pos, PsyglyphicCipherTile.class);
//                if (cipher != null) {
//                    if (!cipher.hasBeenUsed) {
//                        worldIn.playSound(player, pos, SoundEvents.VILLAGER_WORK_CARTOGRAPHER, SoundSource.BLOCKS, 0.8F, 0.7F);
//                        player.inventory.add(ItemInit.PSYGLYPHIC_SCRIPT.get().getDefaultInstance());
//                        PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.cipher.used"));
//                        state.setValue(UTILIZED, true);
//                        if (!player.abilities.instabuild) {
//                            stack.shrink(1);
//                        }
//                        Networking.sendToNearby(worldIn, pos, new PacketREEffect(PacketREEffect.MobEffectCategory.BURST, pos, new ParticleColor.IntWrapper(255, 0, 0)));
//                    }
//                }
//            }
//        }
//        worldIn.sendBlockUpdated(pos, state, state, 2);
//        return InteractionResult.SUCCESS;//else { return InteractionResult.FAIL;
//    }

//    @Override
//    public InteractionResultHolder use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
//        ItemStack stack = player.getItemInHand(handIn);
//        PsyglyphicCipherTile cipherTile = (PsyglyphicCipherTile)worldIn.getBlockEntity(pos);
//        if (!worldIn.isClientSide && stack.getItem() == Items.PAPER) {
//            if (!(cipherTile.hasBeenUsed)) {
//                state.setValue(UTILIZED, true);
//                PortUtil.sendMessage(player, new TranslatableComponent("rigoranthusemortisreborn.cipher.used"));
//                if (!player.abilities.instabuild){
//                    stack.shrink(1);
//                }
//            }
//            return InteractionResult.SUCCESS;
//        } else
//            return InteractionResult.PASS;
////        return super.use(state, worldIn, pos, player, handIn, hit);
//    }
//    @Override
//    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
//        super.setPlacedBy(worldIn, pos, state, placer, stack);
//    } //TODO  is this needed? does this do anything?

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.BLOCK;
    }
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(2, 0, 2, 14, 15, 14);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PsyglyphicCipherTile(pos, state);
    }
}