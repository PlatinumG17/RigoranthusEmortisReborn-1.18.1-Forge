package com.platinumg17.rigoranthusemortisreborn.magica.common.block;

import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibBlockNames;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class DominionBerryBush extends BushBlock implements IPlantable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape BUSHLING_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape GROWING_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public DominionBerryBush(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
        setRegistryName(LibBlockNames.DOMINION_BERRY_BUSH);
    }

    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(BlockRegistry.DOMINION_BERRY_BUSH);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) == 0) {
            return BUSHLING_SHAPE;
        } else {
            return state.getValue(AGE) < 3 ? GROWING_SHAPE : super.getShape(state, worldIn, pos, context);
        }
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        int i = state.getValue(AGE);
        if (i < 3 && worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(i + 1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }

    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DAMAGE_OTHER;
    }
//    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
//        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE && entityIn.getType() != ModEntities.LANGUID_DWELLER) {
//            entityIn.makeStuckInBlock(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
//            if (!worldIn.isClientSide && state.getValue(AGE) > 0 && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ())) {
//                double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
//                double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
//                if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
//                    entityIn.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
//                }
//            }
//        }
//    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int i = state.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
            return InteractionResult.PASS;
        } else if (i > 1) {
            int j = 1 + worldIn.random.nextInt(2);
            popResource(worldIn, pos, new ItemStack(BlockRegistry.DOMINION_BERRY_BUSH, j + (flag ? 1 : 0)));
            worldIn.playSound((Player)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(1)), 2);
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        } else {
            return super.use(state, worldIn, pos, player, handIn, hit);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * Whether this IGrowable can grow
     */
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 3;
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        int i = Math.min(3, state.getValue(AGE) + 1);
        worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(i)), 2);
    }
}