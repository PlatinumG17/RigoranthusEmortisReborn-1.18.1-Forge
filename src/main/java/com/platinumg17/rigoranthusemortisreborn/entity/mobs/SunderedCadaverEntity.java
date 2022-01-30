package com.platinumg17.rigoranthusemortisreborn.entity.mobs;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai.MovementHandler;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.entity.goals.SunderedCadaverAttackGoal;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.IAnimationListener;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.sounds.SoundEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class SunderedCadaverEntity extends PathfinderMob implements IAnimatable, IAnimationListener {

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SunderedCadaverEntity.class, EntityDataSerializers.BYTE);
    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_213697_0_) -> {return p_213697_0_ == Difficulty.HARD;};
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
    private boolean canBreakDoors;
//    public static final EntityDataAccessor<Boolean> CASTING = SynchedEntityData.defineId(SunderedCadaverEntity.class, EntityDataSerializers.BOOLEAN);
//    public static final EntityDataAccessor<Boolean> SUMMONED = SynchedEntityData.defineId(SunderedCadaverEntity.class, EntityDataSerializers.BOOLEAN);
//    public static final EntityDataAccessor<Optional<BlockPos>> HOME = SynchedEntityData.defineId(SunderedCadaverEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
//    public Spell spell = Spell.EMPTY;
    public ParticleColor color = ParticleUtil.defaultParticleColor();

    public SunderedCadaverEntity(EntityType<SunderedCadaverEntity> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new MovementHandler(this);
        this.noCulling = true;
    }

    public SunderedCadaverEntity(Level world) {
        super(ModEntities.SUNDERED_CADAVER, world);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.SUNDERED_CADAVER;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {super.onSyncedDataUpdated(data);}

    @Override
    public double getMyRidingOffset() { return (super.getMyRidingOffset() + 0.2D); }

    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            this.setClimbing(this.horizontalCollision);
            if (level.getGameTime() % 20 == 0 && !this.isDeadOrDying() && this.hasCustomName()) {
                this.heal(0.1f);
            }
        }
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
            .add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
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
        try {
            if(arg == SunderedCadaverEntity.Animations.POUNCING.ordinal()) {
                AnimationController controller = this.animationFactory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("attackController");
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("attack", false));
            }
        }catch (Exception e) {
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
        this.goalSelector.addGoal(1, new SunderedCadaverAttackGoal(this, true));
//        this.goalSelector.addGoal(2, new CastSpellGoal(this, 1.2d, 20,15f, () -> castCooldown <= 0 && !this.entityData.get(SUMMONED), Animations.CAST.ordinal(), 20));
        this.goalSelector.addGoal(2, new FollowMobGoal(this, (float) 1, 10, 5));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 1.0f, 8));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0f));
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
//        NBTUtil.storeBlockPos(tag, "home", getHome());
//        tag.putInt("cast", castCooldown);
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setCanBreakDoors(nbt.getBoolean("CanBreakDoors"));
//        if(NBTUtil.hasBlockPos(tag, "home")){
//            setHome(NBTUtil.getBlockPos(tag, "home"));}
//        this.castCooldown = tag.getInt("cast");
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevel serverWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        entityData = super.finalizeSpawn(serverWorld, difficulty, spawnReason, entityData, nbt);
        float f = difficulty.getSpecialMultiplier();
//        this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);
        if (entityData == null) {
            entityData = new SunderedCadaverEntity.GroupData(true);
            if (difficulty.getDifficulty() == Difficulty.HARD && serverWorld.getRandom().nextFloat() < 0.1F * difficulty.getSpecialMultiplier()) {
                ((SunderedCadaverEntity.GroupData)entityData).setRandomEffect(serverWorld.getRandom());
            }
        }
        if (entityData instanceof SunderedCadaverEntity.GroupData) {
            SunderedCadaverEntity.GroupData cadaverGroupData = (SunderedCadaverEntity.GroupData)entityData;
            MobEffect effect = (cadaverGroupData).effect;

            if (effect != null) {
                this.addEffect(new MobEffectInstance(effect, Integer.MAX_VALUE));
            }
            if (cadaverGroupData.canSpawnJockey) {
                if ((double)serverWorld.getRandom().nextFloat() < 0.05D) {
                    List<Chicken> list = serverWorld.getEntitiesOfClass(Chicken.class, this.getBoundingBox().inflate(5.0D, 3.0D, 5.0D), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
                    if (!list.isEmpty()) {
                        Chicken chickenentity = list.get(0);
                        chickenentity.setChickenJockey(true);
                        this.startRiding(chickenentity);
                    }
                } else if ((double)serverWorld.getRandom().nextFloat() < 0.05D) {
                    Chicken chickenentity1 = EntityType.CHICKEN.create(this.level);
                    chickenentity1.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
                    chickenentity1.finalizeSpawn(serverWorld, difficulty, MobSpawnType.JOCKEY, (SpawnGroupData)null, (CompoundTag)null);
                    chickenentity1.setChickenJockey(true);
                    this.startRiding(chickenentity1);
                    serverWorld.addFreshEntity(chickenentity1);
                }
            }
//            this.populateDefaultEquipmentSlots(difficulty);
//            this.populateDefaultEquipmentEnchantments(difficulty);
        }
//        if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
//            LocalDate localdate = LocalDate.now();
//            int i = localdate.get(ChronoField.DAY_OF_MONTH);
//            int j = localdate.get(ChronoField.MONTH_OF_YEAR);
//            if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
//                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
//                this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
//            }
//        }
        this.setCanBreakDoors(this.supportsBreakDoorGoal() && this.random.nextFloat() < f * 0.1F);
        this.handleAttributes(f);
        return entityData;
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

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
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 0.85F;
    }

    public static class GroupData implements SpawnGroupData {

        public MobEffect effect;
        public final boolean canSpawnJockey;

        public GroupData(boolean canBeJockey) {
            this.canSpawnJockey = canBeJockey;
        }
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
        this.randomizeReinforcementsChance();
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * (double)0.05F, AttributeModifier.Operation.ADDITION));
        double d0 = this.random.nextDouble() * 1.5D * (double)multiplier;
        if (d0 > 1.0D) {
            this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random Cadaver-spawn bonus", d0, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (this.random.nextFloat() < multiplier * 0.05F) {
            this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Leader Cadaver bonus", this.random.nextDouble() * 0.25D + 0.5D, AttributeModifier.Operation.ADDITION));
            this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Leader Cadaver bonus", this.random.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    protected void randomizeReinforcementsChance() {
        this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.random.nextDouble() * net.minecraftforge.common.ForgeConfig.SERVER.zombieBaseSummonChance.get());
    }

    @Override protected int getExperienceReward(Player player) {return 10 + this.level.random.nextInt(5);}
    @Override protected SoundEvent getAmbientSound() {return RigoranthusSoundRegistry.CADAVER_AMBIENT.get();}
    @Override protected SoundEvent getDeathSound() {return RigoranthusSoundRegistry.CADAVER_DEATH.get();}
    @Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return RigoranthusSoundRegistry.CADAVER_HURT.get();}
    @Override protected void playStepSound(BlockPos pos, BlockState blockIn) {this.playSound(RigoranthusSoundRegistry.UNDEAD_STEP.get(), 0.20F, 0.5F);}
}


//    @Override
//    public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
//        EntitySpellResolver resolver = new EntitySpellResolver(new SpellContext(spell, this).withColors(color.toWrapper()));
//        EntityProjectileSpell projectileSpell = new EntityProjectileSpell(level, resolver);
//        projectileSpell.setColor(color.toWrapper());
//        projectileSpell.shoot(this, this.xRot, this.yRot, 0.0F, 1.0f, 0.8f);
//        level.addFreshEntity(projectileSpell);
//        this.castCooldown = 40;
//    }