package com.platinumg17.rigoranthusemortisreborn.blocks.custom;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.ITickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;
import java.util.Random;

public class OpulentMagmaBlock extends Block implements ITickable {

    public OpulentMagmaBlock(String name) {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.GOLD).lightLevel((value) -> { return 15; }).strength(8f, 10f)
                .requiresCorrectToolForDrops().sound(SoundType.LANTERN));
        setRegistryName(name);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState pState,Entity entityIn) {
        if (!entityIn.fireImmune() || !(entityIn instanceof Player) || !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.hurt(DamageSource.HOT_FLOOR, 1.0F);
        }
        super.stepOn(world, pos, pState, entityIn);
    }

    @Override
    public void tick(BlockState state, ServerLevel server, BlockPos pos, Random random) {
        BubbleColumnBlock.updateColumn(server, pos.above(), state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (direction == Direction.UP && otherState.is(Blocks.WATER)) {
            world.scheduleTick(currentPos, this, 20);
        }

        return super.updateShape(state, direction, otherState, world, currentPos, facingPos);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel server, BlockPos pos, Random random) {
        BlockPos blockpos = pos.above();
        if (server.getFluidState(pos).is(FluidTags.WATER)) {
            server.playSound((Player)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (server.random.nextFloat() - server.random.nextFloat()) * 0.8F);
            server.sendParticles(ParticleTypes.LARGE_SMOKE, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
        }
    }

    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DAMAGE_OTHER;
    }

    @Override
    public void onPlace(BlockState blockState, Level worldIn, BlockPos pos, BlockState state, boolean bool) {
        worldIn.scheduleTick(pos, this, 20);
    }
}