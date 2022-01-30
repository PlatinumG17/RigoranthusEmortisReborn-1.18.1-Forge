package com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.WetSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface ICanisTransmogrification {
    /**
     * Called when ever this instance is first added to a canis, this is called when
     * the level is first set on the canis or when it is loaded from NBT and when the
     * skills are synced to the client
     *
     * @param canisIn The canis
     */
    default void init(AbstractCanisEntity canisIn) {}
    default void remove(AbstractCanisEntity canisIn) {}
    default void onWrite(AbstractCanisEntity canisIn, CompoundTag compound) {}
    default void onRead(AbstractCanisEntity canisIn, CompoundTag compound) {}
    /**
     * Called at the end of tick
     */
    default void tick(AbstractCanisEntity canisIn) {
    }
    /**
     * Called at the end of livingTick
     */
    default void livingTick(AbstractCanisEntity canisIn) {
    }
    default InteractionResultHolder<Integer> hungerTick(AbstractCanisEntity canisIn, int hungerTick) {return InteractionResultHolder.pass(hungerTick);}
    default InteractionResultHolder<Integer> healingTick(AbstractCanisEntity canisIn, int healingTick) {return InteractionResultHolder.pass(healingTick);}
    default InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {return InteractionResult.PASS;}
    default InteractionResult canBeRiddenInWater(AbstractCanisEntity canisIn, Entity rider) {return InteractionResult.PASS;}
    default InteractionResult canTrample(AbstractCanisEntity canisIn, BlockState state, BlockPos pos, float fallDistance) {return InteractionResult.PASS;}
    default InteractionResultHolder<Float> calculateFallDistance(AbstractCanisEntity canisIn, float distance) {return InteractionResultHolder.pass(0F);}
    default InteractionResult canBreatheUnderwater(AbstractCanisEntity canisIn) {return InteractionResult.PASS;}
    default InteractionResult canAttack(AbstractCanisEntity canisIn, LivingEntity target) {return InteractionResult.PASS;}
    default InteractionResult canAttack(AbstractCanisEntity canisIn, EntityType<?> entityType) {return InteractionResult.PASS;}
    default InteractionResult shouldAttackEntity(AbstractCanisEntity canis, LivingEntity target, LivingEntity owner) {return InteractionResult.PASS;}
    default InteractionResult hitByEntity(AbstractCanisEntity canis, Entity entity) {return InteractionResult.PASS;}
    default InteractionResult attackEntityAsMob(AbstractCanisEntity canisIn, Entity target) {return InteractionResult.PASS;}
    default InteractionResultHolder<Float> attackEntityFrom(AbstractCanisEntity canis, DamageSource source, float damage) {return InteractionResultHolder.pass(damage);}
    default InteractionResult canBlockDamageSource(AbstractCanisEntity canis, DamageSource source) {return InteractionResult.PASS;}
    default void onDeath(AbstractCanisEntity canis, DamageSource source) {}
    default void spawnDrops(AbstractCanisEntity canis, DamageSource source) {}
    default void dropLoot(AbstractCanisEntity canis, DamageSource source, boolean recentlyHitIn) {}
    default void dropInventory(AbstractCanisEntity canisIn) {}
    default InteractionResultHolder<Float> attackEntityFrom(AbstractCanisEntity canisIn, float distance, float damageMultiplier) {return InteractionResultHolder.pass(distance);}
    default InteractionResultHolder<Integer> decreaseAirSupply(AbstractCanisEntity canisIn, int air) {return InteractionResultHolder.pass(air);}
    default InteractionResultHolder<Integer> determineNextAir(AbstractCanisEntity canisIn, int currentAir) {return InteractionResultHolder.pass(currentAir);}
    default InteractionResultHolder<Integer> setFire(AbstractCanisEntity canisIn, int second) {return InteractionResultHolder.pass(second);}
    default InteractionResult isImmuneToFire(AbstractCanisEntity canisIn) {return InteractionResult.PASS;}
    default InteractionResult isInvulnerableTo(AbstractCanisEntity canisIn, DamageSource source) {return InteractionResult.PASS;}
    default InteractionResult isInvulnerable(AbstractCanisEntity canisIn) {return InteractionResult.PASS;}
    default InteractionResult onLivingFall(AbstractCanisEntity canisIn, float distance, float damageMultiplier) {return InteractionResult.PASS;}
    default <T> LazyOptional<T> getCapability(AbstractCanisEntity canisIn, Capability<T> cap, Direction side) {return null;}
    default void invalidateCapabilities(AbstractCanisEntity canisIn) {}
    default InteractionResultHolder<Float> getMaxHunger(AbstractCanisEntity canisIn, float currentMax) {return InteractionResultHolder.pass(currentMax);}
    default InteractionResultHolder<Float> setCanisHunger(AbstractCanisEntity canisIn, float hunger, float diff) {return InteractionResultHolder.pass(hunger);}
    default InteractionResult isPotionApplicable(AbstractCanisEntity canisIn, MobEffectInstance effectIn) {return InteractionResult.PASS;}
    /**
     * Only called serverside
     * @param canisIn The canis
     * @param source How the canis initially got wet
     */
    default void onShakingDry(AbstractCanisEntity canisIn, WetSource source) {
    }
}