package com.platinumg17.rigoranthusemortisreborn.entity.mobs;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai.MovementHandler;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.entity.goals.DwellerAttackGoal;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.IAnimationListener;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
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

public class LanguidDwellerEntity extends Monster implements IAnimatable, IAnimationListener, IAnimationTickable {

    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_213697_0_) -> {return p_213697_0_ == Difficulty.HARD;};
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
    private boolean canBreakDoors;
    public boolean isDormant;

    public LanguidDwellerEntity(EntityType<LanguidDwellerEntity> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new MovementHandler(this);
        this.noCulling = true;
    }

    public LanguidDwellerEntity(Level world) {
        super(ModEntities.LANGUID_DWELLER, world);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.LANGUID_DWELLER;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 1.9F;
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

    public boolean canAttack(){
        return getTarget() != null && this.getHealth() >= 1;
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, Config.languidDwellerMaxHealth.get())
            .add(Attributes.MOVEMENT_SPEED, Config.languidDwellerMovementSpeed.get())
            .add(Attributes.ATTACK_DAMAGE, Config.languidDwellerAttackDamage.get())
            .add(Attributes.ARMOR, Config.languidDwellerArmorValue.get())
            .add(Attributes.ATTACK_KNOCKBACK, Config.languidDwellerAttackKnockback.get())
            .add(Attributes.KNOCKBACK_RESISTANCE, Config.languidDwellerKnockbackResistance.get())
            .add(Attributes.FOLLOW_RANGE, 16.0D);
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
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
//        this.goalSelector.addGoal( 1, new NearestAttackableTargetGoal<>( this, Player.class, true));
        goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 1, true, false, (entity) -> !isDormant || distanceTo(entity) < getAttribute(Attributes.FOLLOW_RANGE).getValue() / 2.5));
        this.goalSelector.addGoal(2, new DwellerAttackGoal(this,true));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 1.0f, 8));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(this.getClass()));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 1, true, false, (entity) -> !isDormant || distanceTo(entity) < getAttribute(Attributes.FOLLOW_RANGE).getValue() / 2.5));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));

    }
    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
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

    public void setDormant(boolean isDormant) {
        this.isDormant = isDormant;
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("isDormant", isDormant);
        nbt.putBoolean("CanBreakDoors", this.canBreakDoors());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setCanBreakDoors(nbt.getBoolean("CanBreakDoors"));
        setDormant(nbt.getBoolean("isDormant"));
    }

    @Override
    public void tick() {
        super.tick();
        if (isInWater()) {
            if (isDormant) {
                setDormant(false);
            }
        }
    }

    @Override
    public void setTarget(LivingEntity entity) {
        setDormant(false);
        super.setTarget(entity);
    }

    @Override protected int getExperienceReward(Player player)  { return 30 + this.level.random.nextInt(5); }
    @Override protected SoundEvent getAmbientSound() { return RigoranthusSoundRegistry.DWELLER_AMBIENT.get(); }
    @Override protected SoundEvent getDeathSound() { return RigoranthusSoundRegistry.DWELLER_DEATH.get(); }
    @Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return RigoranthusSoundRegistry.DWELLER_HURT.get(); }
    @Override protected void playStepSound(BlockPos pos, BlockState blockIn) { this.playSound(RigoranthusSoundRegistry.UNDEAD_STEP.get(), 0.20F, 0.5F); }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (!super.doHurtTarget(entityIn)) {
            return false;
        }
        else if (entityIn instanceof Player) {
            ((Player) entityIn).addEffect(new MobEffectInstance(MobEffects.UNLUCK, 200));
        }
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
    if (source.getEntity() instanceof Player) {
        if ((Math.random() < 0.1)) {
            if (level instanceof ServerLevel) {

                Mob silverFish = new Silverfish(EntityType.SILVERFISH, level);

                silverFish.moveTo(this.getX(), this.getY(), this.getZ(), level.getRandom().nextFloat() * 360F, 0);

                silverFish.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                level.addFreshEntity(silverFish);
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

    public void aiStep() {
        super.aiStep();
        for(int i = 0; i < 2; i++){
            this.level.addParticle(ParticleTypes.ENCHANT, this.getRandomX(0.75), this.getRandomY() + 0.3, this.getRandomZ(0.75), 0.0D, 0.0D, 0.0D);
        }
        for(int i = 0; i < 1; i++){
            this.level.addParticle(ParticleTypes.REVERSE_PORTAL, this.getRandomX(0.75), this.getRandomY() + 0.3, this.getRandomZ(0.75), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void startAnimation(int arg) {
        try{
            if(arg == LanguidDwellerEntity.Animations.LUNGING.ordinal()){
                AnimationController controller = this.animationFactory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("attackController");
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("attack", false));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    public enum Animations{ LUNGING }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        entityData = super.finalizeSpawn(serverWorld, difficulty, spawnReason, entityData, nbt);
        float f = difficulty.getSpecialMultiplier();

        this.setCanBreakDoors(this.supportsBreakDoorGoal() && this.random.nextFloat() < f * 0.1F);
        return entityData;
    }
}
