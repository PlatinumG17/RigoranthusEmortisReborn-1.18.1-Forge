package com.platinumg17.rigoranthusemortisreborn.entity.mobs;

import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai.MovementHandler;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.entity.goals.FeralCanisAttackGoal;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.IAnimationListener;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class FeralCanisEntity extends Monster implements IAnimatable, IAnimationListener {

    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_213697_0_) -> {return p_213697_0_ == Difficulty.HARD;};
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
    private boolean canBreakDoors;

    public FeralCanisEntity(EntityType<FeralCanisEntity> entity, Level worldIn) {
        super(entity, worldIn);
        maxUpStep = 1.2f;
        this.moveControl = new MovementHandler(this);
        this.noCulling = true;
    }

    public FeralCanisEntity(Level world) {
        super(ModEntities.FERAL_CANIS, world);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.FERAL_CANIS;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 1.15F;
    }

    private <E extends IAnimatable> PlayState walkPredicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends Entity> PlayState attackPredicate(AnimationEvent event) {
        return PlayState.CONTINUE;
    }

    private <E extends Entity> PlayState idlePredicate(AnimationEvent event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.feralCanisChordataMaxHealth.get())
                .add(Attributes.MOVEMENT_SPEED, Config.feralCanisChordataMovementSpeed.get())
                .add(Attributes.ATTACK_DAMAGE, Config.feralCanisChordataAttackDamage.get())
                .add(Attributes.ARMOR, Config.feralCanisChordataArmorValue.get())
                .add(Attributes.ATTACK_KNOCKBACK, Config.feralCanisChordataAttackKnockback.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, Config.feralCanisChordataKnockbackResistance.get())
                .add(Attributes.FOLLOW_RANGE, 25.0D);
    }

    public boolean canAttack(){
        return getTarget() != null && this.getHealth() >= 1;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "walkController", 0, this::walkPredicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 1, this::attackPredicate));
        animationData.addAnimationController(new AnimationController<>(this, "idleController", 0, this::idlePredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }
    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new FeralCanisAttackGoal(this,true));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 1.0f, 8));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FloatGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(this.getClass()));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    public void setCanBreakDoors(boolean canBreak) {
        if (this.supportsBreakDoorGoal() && GoalUtils.hasGroundPathNavigation(this)) {
            if (this.canBreakDoors != canBreak) {
                this.canBreakDoors = canBreak;
                ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(canBreak);
                if (canBreak) {
                    this.goalSelector.addGoal(1, this.breakDoorGoal);
                } else {
                    this.goalSelector.removeGoal(this.breakDoorGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.removeGoal(this.breakDoorGoal);
            this.canBreakDoors = false;
        }
    }

    protected boolean supportsBreakDoorGoal() {
        return true;
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("CanBreakDoors", this.canBreakDoors());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setCanBreakDoors(nbt.getBoolean("CanBreakDoors"));
    }

    private int ticks = 0;
    private static int waitTicks = 60;

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        BlockPos blockpos = this.getOnPos();
        ItemStack stack = player.getItemInHand(hand);
        SunderedCadaverEntity sunderedCadaver = ModEntities.SUNDERED_CADAVER.create(level);
        SunderedCadaverEntity sunderedCadaver2 = ModEntities.SUNDERED_CADAVER.create(level);
        CanisEntity canis = SpecializedEntityTypes.CANIS.get().create(level);

        if (stack.getItem() == ItemInit.PACT_OF_SERVITUDE.get()) {
            if (!player.abilities.instabuild) {
                stack.shrink(1);
            }
            player.swing(hand);
            if (!player.level.isClientSide) {

                level.playSound(player, blockpos, SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, (level.random.nextFloat() - level.random.nextFloat()) * 0.8F + 1.0F);
                if ((Math.random() <= 0.15) && !Config.DISABLE_TAMING.get()) {

                    level.addParticle(ParticleTypes.SOUL, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
                    level.addParticle(ParticleTypes.SOUL, this.getRandomX(1.5D), this.getRandomY() + 0.8D, this.getRandomZ(1.5D), 0.0D, 0.0D, 0.0D);
                    level.playSound(null, blockpos, SoundEvents.WOLF_HOWL, SoundSource.NEUTRAL, 1f, 0.8f);

                    this.navigation.stop();
                    this.setSecondsOnFire(3);
                    PortUtil.sendMessageCenterScreen(player, (new TranslatableComponent("rigoranthusemortisreborn.canis.successfully_tamed")));

                    waitTicks = 60;

                    if (this.ticks <= this.waitTicks)
                        canis.setTame(true);
                        canis.setOwnerUUID(player.getUUID());
                        canis.setHealth(canis.getMaxHealth());
                        canis.setOrderedToSit(false);
                        canis.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
                        level.addFreshEntity(canis);
                    this.remove(RemovalReason.DISCARDED);
                }
                else if ((Math.random() <= 0.15)) {
                    if (level instanceof ServerLevel) {
                        LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(level);

                        lightningBoltEntity.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
                        lightningBoltEntity.setVisualOnly(true);

                        level.addFreshEntity(lightningBoltEntity);

                        sunderedCadaver.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
                        sunderedCadaver2.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);

                        sunderedCadaver.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(player.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        sunderedCadaver2.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(player.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);

                        level.addFreshEntity(sunderedCadaver);
                        level.addFreshEntity(sunderedCadaver2);

                        sunderedCadaver.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.summoned_servant").withStyle(Style.EMPTY.withItalic(true)));
                        sunderedCadaver2.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.summoned_servant").withStyle(Style.EMPTY.withItalic(true)));

                        sunderedCadaver.setCustomNameVisible(true);
                        sunderedCadaver2.setCustomNameVisible(true);
                    }
                    this.level.playSound(null, blockpos, SoundEvents.WOLF_GROWL, SoundSource.NEUTRAL, 1f, 0.8f);

                    PortUtil.sendMessageCenterScreen(player, (new TranslatableComponent("rigoranthusemortisreborn.canis.failed_to_tame")));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        SunderedCadaverEntity sunderedCadaver = ModEntities.SUNDERED_CADAVER.create(level);
        SunderedCadaverEntity sunderedCadaver2 = ModEntities.SUNDERED_CADAVER.create(level);
//        Mob sunderedCadaver = new SunderedCadaverEntity(ModEntities.SUNDERED_CADAVER, level);

        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!super.hurt(source, amount)) {
            return false;
        }
        if (this.getLastHurtByMob() instanceof Player && this.lastHurtByPlayer != null) {
            if ((Math.random() < 0.1)) {
                PortUtil.sendMessageCenterScreen((Player) lastHurtByPlayer, (new TranslatableComponent("rigoranthusemortisreborn.canis.failed_to_tame")));
                if (level instanceof ServerLevel) {

                    LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(level);

                    lightningBoltEntity.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
                    lightningBoltEntity.setVisualOnly(true);

                    level.addFreshEntity(lightningBoltEntity);

                    sunderedCadaver.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
                    sunderedCadaver2.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);

                    sunderedCadaver.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(lastHurtByPlayer.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    sunderedCadaver2.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(lastHurtByPlayer.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);

                    level.addFreshEntity(sunderedCadaver);
                    level.addFreshEntity(sunderedCadaver2);

                    sunderedCadaver.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.summoned_servant").withStyle(Style.EMPTY.withItalic(true)));
                    sunderedCadaver2.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.summoned_servant").withStyle(Style.EMPTY.withItalic(true)));

                    sunderedCadaver.setCustomNameVisible(true);
                    sunderedCadaver2.setCustomNameVisible(true);
                }
            }
        }
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void startAnimation(int arg) {
        try{
            if(arg == FeralCanisEntity.Animations.BITING.ordinal()){
                AnimationController controller = this.animationFactory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("attackController");
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("attack", false));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @Override
//    public int tickTimer() {
//        return tickCount;
//    }

    public enum Animations{ BITING }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevel serverWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        entityData = super.finalizeSpawn(serverWorld, difficulty, spawnReason, entityData, nbt);
        float f = difficulty.getSpecialMultiplier();

        this.setCanBreakDoors(this.supportsBreakDoorGoal() && this.random.nextFloat() < f * 0.1F);
        return entityData;
    }
}