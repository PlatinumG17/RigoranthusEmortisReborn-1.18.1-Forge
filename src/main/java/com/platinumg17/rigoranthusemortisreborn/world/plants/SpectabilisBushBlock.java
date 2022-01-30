package com.platinumg17.rigoranthusemortisreborn.world.plants;

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
import net.minecraft.world.level.block.BonemealableBlock;
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
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class SpectabilisBushBlock extends BushBlock implements BonemealableBlock, IPlantable {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape SAPLING_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    private static final VoxelShape MID_GROWTH_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 14.0D, 10.0D, 14.0D);
    private static final VoxelShape END_GROWTH_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);

    public SpectabilisBushBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
        setRegistryName(LibBlockNames.SPECTABILIS_BUSH);
    }
    @Override
    public ItemStack getCloneItemStack(BlockGetter reader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(BlockRegistry.SPECTABILIS_BUSH);
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter reader, BlockPos blockPos, CollisionContext ctx) {
        if (blockState.getValue(AGE) <= 1) {
            return SAPLING_SHAPE;
        } else if (blockState.getValue(AGE) == 2) {
            return MID_GROWTH_SHAPE;
        } else {
            return blockState.getValue(AGE) == 3 ? END_GROWTH_SHAPE : super.getShape(blockState, reader, blockPos, ctx);
        }
    }
    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(AGE) < 3;
    }
    @Override
    public void randomTick(BlockState blockState, ServerLevel server, BlockPos blockPos, Random random) {
        int i = blockState.getValue(AGE);
        if (i < 3 && server.getRawBrightness(blockPos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(server, blockPos, blockState,random.nextInt(5) == 0)) {
            server.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(i + 1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(server, blockPos, blockState);
        }
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }
    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DAMAGE_OTHER;
    }
//    @Override
//    public void entityInside(BlockState blockState, Level world, BlockPos blockPos, Entity entityIn) {
//        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE) {
//            entityIn.makeStuckInBlock(blockState, new Vector3d((double)0.95F, 0.95D, (double)0.95F));
//            if (!world.isClientSide && blockState.getValue(AGE) > 2 && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ())) {
//                double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
//                double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
//                if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
//                    entityIn.setNoGravity(true);
//                }
//            }
//        }
//    }
    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos blockPos, Player playerIn, InteractionHand hand, BlockHitResult ray) {
        int i = blockState.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && playerIn.getItemInHand(hand).getItem() == Items.BONE_MEAL) {
            return InteractionResult.PASS;
        } else if (i > 1) {
            int j = 1 + world.random.nextInt(2);
            popResource(world, blockPos, new ItemStack(BlockRegistry.SPECTABILIS_BUSH, j + (flag ? 1 : 0)));
            world.playSound((Player)null, blockPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(1)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(blockState, world, blockPos, playerIn, hand, ray);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(AGE);
    }
    @Override
    public boolean isValidBonemealTarget(BlockGetter reader, BlockPos blockPos, BlockState blockState, boolean valid) {
        return blockState.getValue(AGE) < 3;
    }
    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }
    @Override
    public void performBonemeal(ServerLevel server, Random random, BlockPos blockPos, BlockState blockState) {
        int i = Math.min(3, blockState.getValue(AGE) + 1);
        server.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(i)), 2);
    }
}