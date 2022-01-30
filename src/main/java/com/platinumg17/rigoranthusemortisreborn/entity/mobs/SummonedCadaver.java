package com.platinumg17.rigoranthusemortisreborn.entity.mobs;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai.MovementHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.IAnimationListener;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketAnimEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class SummonedCadaver extends TamableAnimal implements IAnimatable, IAnimationListener, IAnimationTickable {

    private static final EntityDataAccessor<Optional<Component>> LAST_KNOWN_NAME = SynchedEntityData.defineId(SummonedCadaver.class, EntityDataSerializers.OPTIONAL_COMPONENT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SummonedCadaver.class, EntityDataSerializers.BYTE);
    private final AnimationFactory animationFactory = new AnimationFactory(this);

    public ParticleColor color = ParticleUtil.defaultParticleColor();

    public SummonedCadaver(EntityType<SummonedCadaver> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new MovementHandler(this);
        this.noCulling = true;
    }

    public SummonedCadaver(Level world) {
        super(ModEntities.SUMMONED_CADAVER, world);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.SUMMONED_CADAVER;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {super.onSyncedDataUpdated(data);}

    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            this.setClimbing(this.horizontalCollision);
            // Every 2 seconds
            if (this.tickCount % 40 == 0) {
                if (this.getOwner() != null) {
                    this.setOwnersName(this.getOwner().getName());
                }
                if (this.tickCount >= 5000) {
                    ParticleUtil.spawnPoof((ServerLevel) level, blockPosition());
                    this.remove(RemovalReason.DISCARDED);
                }
            }
            if (level.getGameTime() % 20 == 0 && !this.isDeadOrDying() && this.hasCustomName()) {
                this.heal(0.1f);
            }
        }
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    @Override
    public void die(DamageSource source) {
        if(!level.isClientSide){
            refreshDimensions();
            ParticleUtil.spawnPoof((ServerLevel) level, blockPosition());
            if(source.getEntity() != null && source.getEntity() instanceof Mob)
                ((Mob) source.getEntity()).setTarget(null);
            return;
        }
        super.die(source);
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

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
        this.entityData.define(LAST_KNOWN_NAME, Optional.empty());
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    @Override public AnimationFactory getFactory() {
        return this.animationFactory;
    }
    @Override public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }
    protected boolean isSunSensitive() {
        return !this.hasCustomName();
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.broadcastBreakEvent(EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                    flag = false;
                }
                if (flag) { this.setSecondsOnFire(8); }
            }
        }
        super.aiStep();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.sunderedCadaverMaxHealth.get())
                .add(Attributes.MOVEMENT_SPEED, Config.sunderedCadaverMovementSpeed.get())
                .add(Attributes.ATTACK_DAMAGE, Config.sunderedCadaverAttackDamage.get())
                .add(Attributes.ARMOR, Config.sunderedCadaverArmorValue.get())
                .add(Attributes.ATTACK_KNOCKBACK, Config.sunderedCadaverAttackKnockback.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, Config.sunderedCadaverKnockbackResistance.get())
                .add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if(source == DamageSource.FALL || source == DamageSource.IN_WALL || source == DamageSource.SWEET_BERRY_BUSH || source == DamageSource.CACTUS)
            return false;
        return super.hurt(source, amount);
    }

    public enum Animations{ POUNCING }

    @Override
    public void startAnimation(int arg) {
        try{
            if (arg == SummonedCadaver.Animations.POUNCING.ordinal()) {
                AnimationController controller = this.animationFactory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("attackController");
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("attack", false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new SummonedCadaverAttackGoal(this, false));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 8.0F, 2.0F, false));
        this.goalSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 1.0f, 8));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0f));
        this.goalSelector.addGoal(8, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, false));
    }

    @Override
    public void tame(Player player) {
        super.tame(player);
        // When tamed by player this caches their display name
        this.setOwnersName(player.getName());
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Config.sunderedCadaverMaxHealth.get());
            this.setHealth(30.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Config.sunderedCadaverMaxHealth.get());
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Config.sunderedCadaverAttackDamage.get() + 1);
    }

    @Override
    public void setOwnerUUID(@Nullable UUID uuid) {
        super.setOwnerUUID(uuid);
        if (uuid == null) {
            this.setOwnersName((Component) null);
        }
    }

    @Override // blockAttackFromPlayer
    public boolean skipAttackInteraction(Entity entityIn) {
        return entityIn == this.getOwner();
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(CanisItems.CADAVER_SUMMONING_CHARM.get());
    }

    @Override
    public boolean canMate(Animal otherAnimal) { return false; }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        this.getOwnersName().ifPresent((comp) -> {
            NBTUtilities.putTextComponent(nbt, "lastKnownOwnerName", comp);
        });
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setOwnersName(NBTUtilities.getTextComponent(nbt, "lastKnownOwnerName"));
    }

    public boolean canInteract(LivingEntity livingEntity) {
        return this.isOwnedBy(livingEntity);
    }

    public Optional<Component> getOwnersName() { return this.entityData.get(LAST_KNOWN_NAME); }

    public void setOwnersName(@Nullable Component comp) { this.setOwnersName(Optional.ofNullable(comp)); }

    public void setOwnersName(Optional<Component> collar) { this.entityData.set(LAST_KNOWN_NAME, collar); }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        entityData = super.finalizeSpawn(serverWorld, difficulty, spawnReason, entityData, nbt);
        float f = difficulty.getSpecialMultiplier();
        this.setCanPickUpLoot(false);
        if (entityData == null) {
            entityData = new SummonedCadaver.GroupData();
            if (difficulty.getDifficulty() == Difficulty.HARD && serverWorld.getRandom().nextFloat() < 0.1F * difficulty.getSpecialMultiplier()) {
                ((SummonedCadaver.GroupData)entityData).setRandomEffect(serverWorld.getRandom());
            }
        }
        if (entityData instanceof SummonedCadaver.GroupData) {
            SummonedCadaver.GroupData cadaverGroupData = (SummonedCadaver.GroupData)entityData;
            MobEffect effect = (cadaverGroupData).effect;

            if (effect != null) { this.addEffect(new MobEffectInstance(effect, Integer.MAX_VALUE)); }
        }
        this.handleAttributes(f);
        return entityData;
    }

    public boolean canBreed() { return false; }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob entity) {
        return null;
    }

    @Override
    public boolean onClimbable() { return this.isClimbing(); }

    public boolean isClimbing() { return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0; }

    public void setClimbing(boolean climbing) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (climbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }
        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) { return 0.85F; }

    public static class GroupData implements SpawnGroupData {

        public MobEffect effect;
        public void setRandomEffect(Random rand) {
            int i = rand.nextInt(5);
            if (i <= 1) {
                this.effect = MobEffects.MOVEMENT_SPEED;
            } else if (i <= 2) {
                this.effect = MobEffects.DAMAGE_BOOST;
            } else if (i <= 3) {
                this.effect = MobEffects.REGENERATION;
            }
        }
    }

    protected void handleAttributes(float multiplier) {
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * (double)0.05F, AttributeModifier.Operation.ADDITION));
        double d0 = this.random.nextDouble() * 1.5D * (double)multiplier;
        if (d0 > 1.0D) {
            this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random Cadaver-spawn bonus", d0, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (this.random.nextFloat() < multiplier * 0.05F) {
            this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Leader Cadaver bonus", this.random.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    @Override protected int getExperienceReward(Player player) {return 0;}
    @Override protected SoundEvent getAmbientSound() {return RigoranthusSoundRegistry.CADAVER_AMBIENT.get();}
    @Override protected SoundEvent getDeathSound() {return RigoranthusSoundRegistry.CADAVER_DEATH.get();}
    @Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return RigoranthusSoundRegistry.CADAVER_HURT.get();}
    @Override protected void playStepSound(BlockPos pos, BlockState blockIn) {this.playSound(RigoranthusSoundRegistry.UNDEAD_STEP.get(), 0.20F, 0.5F);}

    public class SummonedCadaverAttackGoal extends Goal {

        protected final SummonedCadaver mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;

        public int timeAnimating = 0;
        public boolean arrived = false;
        public boolean done = false;

        public SummonedCadaverAttackGoal(SummonedCadaver cadaver, boolean followUnseen) {
            this.mob = cadaver;
            this.speedModifier = 1.2f;
            this.followingTargetEvenIfNotSeen = followUnseen;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean isInterruptable() {
            return true;
        }

        public boolean canUse() {
            long i = this.mob.level.getGameTime();
            if(!this.mob.canAttack())
                return false;
            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    if (canPenalize) {
                        if (--this.ticksUntilNextPathRecalculation <= 0) {
                            this.path = this.mob.getNavigation().createPath(livingentity, 0);
                            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                            return this.path != null;
                        } else {
                            return true;
                        }
                    }
                    this.path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                    }
                }
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if(!this.mob.canAttack())
                return false;
            if (livingentity == null || done) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
            }
        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            timeAnimating = 0;
            arrived = false;
            done = false;
        }

        public void stop() {
        }

        public void tick() {
            LivingEntity livingentity = this.mob.getTarget();
            this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            if(arrived){
                timeAnimating++;
                if(timeAnimating == 24){
                    this.attack(livingentity);
                }
                if(timeAnimating >= 44){
                    this.attack(livingentity);
                    this.done = true;
                }
                return;
            }

            double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if(BlockUtil.distanceFrom(this.mob.position, livingentity.position) <= 3){
                this.arrived = true;
                Networking.sendToNearby(mob.level, mob, new PacketAnimEntity(mob.getId(), Animations.POUNCING.ordinal()));
            }
            if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                    if (this.mob.getNavigation().getPath() != null) {
                        Node finalNode = this.mob.getNavigation().getPath().getEndNode();
                        if (finalNode != null && livingentity.distanceToSqr(finalNode.x, finalNode.y, finalNode.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }
                if (d0 > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d0 > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }
                if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }

        protected void attack(LivingEntity target) {
            double d0 = 4;
            if (BlockUtil.distanceFrom(target.position, this.mob.position) <= d0 ) {
                this.ticksUntilNextAttack = 20;
                this.mob.doHurtTarget(target);
            }
        }

        protected double getAttackReachSqr(LivingEntity targetEntity) {
            return (double)(targetEntity.getBbWidth() * 2);
        }
    }
}