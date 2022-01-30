package com.platinumg17.rigoranthusemortisreborn.canis.common.entity;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.*;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisFoodHandler;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisTransmogrification;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.IThrowableItem;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.*;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.*;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.CanisInfoScreen;
import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage.CanisLocationStorage;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage.CanisRespawnStorage;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.ai.*;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.serializers.DimensionDependantArg;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.stats.StatsTracker;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisTags;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.Cache;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.WorldUtil;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusSoundRegistry;
import com.platinumg17.rigoranthusemortisreborn.entity.goals.TameCanisAttackGoal;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.FeralCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.IAnimationListener;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketAnimEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author PlatinumG17 edit of ProPercivalalb
 */
public class CanisEntity extends AbstractCanisEntity implements IAnimatable, IAnimationListener {

    private final AnimationFactory animationFactory = new AnimationFactory(this);

    private static final EntityDataAccessor<Optional<Component>> LAST_KNOWN_NAME = SynchedEntityData.defineId(CanisEntity.class, EntityDataSerializers.OPTIONAL_COMPONENT);
    private static final EntityDataAccessor<Byte> CANIS_FLAGS = SynchedEntityData.defineId(CanisEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Float> HUNGER_INT = SynchedEntityData.defineId(CanisEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(CanisEntity.class, EntityDataSerializers.STRING);
//    private static final EntityDataAccessor<Byte> SIZE = SynchedEntityData.defineId(CanisEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> BONE_VARIANT = SynchedEntityData.defineId(CanisEntity.class, EntityDataSerializers.ITEM_STACK);
    // Use Cache.make to ensure static fields are not initialised too early (before Serializers have been registered)
    private static final Cache<EntityDataAccessor<List<AccoutrementInstance>>> ACCOUTERMENTS =  Cache.make(() -> (EntityDataAccessor<List<AccoutrementInstance>>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.ACCOUTREMENT_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<List<SkillInstance>>> SKILLS = Cache.make(() -> (EntityDataAccessor<List<SkillInstance>>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.SKILL_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<CanisLevel>> CANIS_LEVEL = Cache.make(() -> (EntityDataAccessor<CanisLevel>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.CANIS_LEVEL_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<EnumGender>> GENDER = Cache.make(() -> (EntityDataAccessor<EnumGender>) SynchedEntityData.defineId(CanisEntity.class,  CanisSerializers.GENDER_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<EnumMode>> MODE = Cache.make(() -> (EntityDataAccessor<EnumMode>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.MODE_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<EnumClothColor>> CLOTH_COLOR = Cache.make(() -> (EntityDataAccessor<EnumClothColor>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.CLOTH_COLOR_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<EnumShadesColor>> SHADES_COLOR = Cache.make(() -> (EntityDataAccessor<EnumShadesColor>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.SHADES_COLOR_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> CANIS_BED_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.BED_LOC_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> CANIS_BOWL_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(CanisEntity.class, CanisSerializers.BED_LOC_SERIALIZER.get().getSerializer()));

    public static final void initEntityDataAccessors() {
        ACCOUTERMENTS.get();
        SKILLS.get();
        CANIS_LEVEL.get();
        GENDER.get();
        SHADES_COLOR.get();
        CLOTH_COLOR.get();
        MODE.get();
        CANIS_BED_LOCATION.get();
        CANIS_BOWL_LOCATION.get();
    }

    // Cached values
    private final Cache<Integer> spendablePoints = Cache.make(this::getSpendablePointsInternal);
    private final List<ICanisTransmogrification> transmogrifications = new ArrayList<>(4);
    private final List<ICanisFoodHandler> foodHandlers = new ArrayList<>(4);
    public final Map<Integer, Object> objects = new HashMap<>();
    public final StatsTracker statsTracker = new StatsTracker();
    private int hungerTick;
    private int prevHungerTick;
    private int healingTick;
    private int prevHealingTick;
    private float headRotationCourse;
    private float headRotationCourseOld;
    private WetSource wetSource;
    public boolean isShaking;
    private float timeCanisIsShaking;
    private float prevTimeCanisIsShaking;
    protected boolean canisJumping;
    protected float jumpPower;

    protected BlockPos targetBlock;
    public BlockPos jukeboxPos;
    public boolean partyCanis;

    public CanisEntity(EntityType<? extends CanisEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.setGender(EnumGender.random(this.getRandom()));
        this.moveControl = new MovementHandler(this);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "walkController", 0, this::walkPredicate));
//        animationData.addAnimationController(new AnimationController<>(this, "runController", 0, this::runPredicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 1, this::attackPredicate));
        animationData.addAnimationController(new AnimationController<>(this, "idleController", 0, this::idlePredicate));
        animationData.addAnimationController(new AnimationController<>(this, "danceController", 1, this::dancePredicate));
        animationData.addAnimationController(new AnimationController<>(this, "shakeController", 1, this::shakePredicate));
    }

    private PlayState shakePredicate(AnimationEvent event) { return PlayState.CONTINUE; }

    private PlayState dancePredicate(AnimationEvent event) {
        if (this.partyCanis && this.jukeboxPos != null && BlockUtil.distanceFrom(this.position, jukeboxPos) <= 8) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("party_pupper"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

//    private <E extends IAnimatable> PlayState runPredicate(AnimationEvent<E> event) { //return PlayState.CONTINUE; }
//        if (event.isMoving() && this.getSpeed() >= 0.34F) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
//            return PlayState.CONTINUE;
//        }
//        return PlayState.STOP;
//    }

    private <E extends IAnimatable> PlayState walkPredicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
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

    @Override public AnimationFactory getFactory() { return this.animationFactory; }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    @Override
    public void startAnimation(int arg) {
        try {
            if (arg == Animations.BITING.ordinal()) {
                AnimationController controller = this.animationFactory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("attackController");
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("attack", false));
            }
            if (arg == Animations.SHAKING.ordinal()) {
                AnimationController controller = this.animationFactory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("shakeController");
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("soggy_boi", false));
            }
//            if (arg == Animations.RUNNING.ordinal()) {
//                AnimationController controller = this.animationFactory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("runController");
//                controller.markNeedsReload();
//                controller.setAnimation(new AnimationBuilder().addAnimation("run", true));
//            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public enum Animations{ BITING, SHAKING/*, RUNNING*/ }

    public boolean canAttack() {
        return getTarget() != null && this.getHealth() >= 1 && !this.isMode(EnumMode.DOCILE) && !this.isOrderedToSit();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACCOUTERMENTS.get(), new ArrayList<>(4));
        this.entityData.define(SKILLS.get(), new ArrayList<>(4));
        this.entityData.define(LAST_KNOWN_NAME, Optional.empty());
        this.entityData.define(CANIS_FLAGS, (byte) 0);
        this.entityData.define(GENDER.get(), EnumGender.UNISEX);
        this.entityData.define(CLOTH_COLOR.get(), EnumClothColor.RED);
        this.entityData.define(SHADES_COLOR.get(), EnumShadesColor.BLACK);
        this.entityData.define(MODE.get(), EnumMode.DOCILE);
        this.entityData.define(HUNGER_INT, 60F);
        this.entityData.define(COLOR, COLORS.WHITE.name());
        this.entityData.define(CANIS_LEVEL.get(), new CanisLevel(0, 0));
//        this.entityData.define(SIZE, (byte) 3);
        this.entityData.define(BONE_VARIANT, ItemStack.EMPTY);
        this.entityData.define(CANIS_BED_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        this.entityData.define(CANIS_BOWL_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
//        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
//        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new TameCanisAttackGoal(this, true));
        this.goalSelector.addGoal(5, new CanisMoveToBlockGoal(this));
        this.goalSelector.addGoal(5, new CanisWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FetchGoal(this, 1.0D, 32.0F));
        this.goalSelector.addGoal(6, new CanisFollowMasterGoal(this, 1.0D, 10.0F, 5.0F));
            //        this.goalSelector.addGoal(7, new CanisBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new CanisBegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new MasterHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MasterHurtTargetGoal(this));
            //        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(6, new BerserkerModeGoal<>(this, Monster.class, false));
        this.targetSelector.addGoal(6, new SentinelModeGoal(this, false));
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void setRecordPlayingNearby(BlockPos pos, boolean hasSound) {
        super.setRecordPlayingNearby(pos, hasSound);
        this.jukeboxPos = pos;
        this.partyCanis = hasSound;
    }

    @Override
    protected float getWaterSlowDown() {
        int skillLevel = this.getCanisLevel(CanisSkills.NEPTUNES_BANE.get());
        if (skillLevel == 1) { return 0.6f; }
        else if (skillLevel == 2) { return 0.5f; }
        else if (skillLevel == 3) { return 0.4f; }
        else if (skillLevel == 4) { return 0.3f; }
        else if (skillLevel == 5) { return 0.2f; }
        else return 0.8f;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) { this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F); }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return RigoranthusSoundRegistry.CANIS_AMBIENT.get();
        }
    }
//    protected SoundEvent getAngrySound() {super.getAngrySound();return RigoranthusSoundRegistry.CANIS_HUFF.get();}
    @Override protected SoundEvent getDeathSound() {return RigoranthusSoundRegistry.CANIS_DEATH.get();}
    @Override protected SoundEvent getHurtSound(DamageSource damageSourceIn) {return RigoranthusSoundRegistry.CANIS_HURT.get();}
    protected void playChestEquipsSound() {this.playSound(SoundEvents.MULE_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);}
    protected void playEatingSound() {this.playSound(SoundEvents.GENERIC_EAT, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);}

    @Override
    public float getSoundVolume() { return 0.4F; }

    public boolean isCanisWet() { return this.wetSource != null; }

    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float partialTicks) {
        return Math.min(0.5F + Mth.lerp(partialTicks, this.prevTimeCanisIsShaking, this.timeCanisIsShaking) / 2.0F * 0.5F, 1.0F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float partialTicks, float offset) {
        float f = (Mth.lerp(partialTicks, this.prevTimeCanisIsShaking, this.timeCanisIsShaking) + offset) / 1.8F;
        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }
        return Mth.sin(f * (float)Math.PI) * Mth.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getInterestedAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.headRotationCourseOld, this.headRotationCourse) * 0.15F * (float)Math.PI;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EmortisConstants.EntityState.CANIS_START_SHAKING) {
            this.startShaking();
        } else if (id == EmortisConstants.EntityState.CANIS_INTERRUPT_SHAKING) {
            this.finishShaking();
        } else {
            super.handleEntityEvent(id);
        }
    }

    public float getTailRotation() { return this.isTame() ? (1.55F - (this.getMaxHealth() - (this.getHealth())) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F); }

    @Override
    public float getWagAngle(float limbSwing, float limbSwingAmount, float partialTickTime) { return Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount; }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) { return 1.15F; }

    @Override
    public Vec3 getLeashOffset() { return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F); }

    @Override
    public int getMaxHeadXRot() { return this.isInSittingPose() ? 20 : super.getMaxHeadXRot(); }

    @Override
    public double getMyRidingOffset() { return this.getVehicle() instanceof Player ? 0.65D : 0.3D; }

    @Override
    public double getPassengersRidingOffset() { return super.getPassengersRidingOffset() + 0.15D; }

    @Override
    public void tick() {
        super.tick();

        if (this.isAlive()) {
            if (!level.isClientSide && level.getGameTime() % 10 == 0 && this.getName().getString().toLowerCase(Locale.ROOT).equals("jeb_")) {
                this.entityData.set(COLOR, canisColors[level.random.nextInt(canisColors.length)]);
            }

            this.headRotationCourseOld = this.headRotationCourse;
            if (this.isBegging()) {
                this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
            } else {
                this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
            }

            boolean inWater = this.isInWater();
            // If inWater is false then isInRain is true in the or statement
            boolean inRain = inWater ? false : this.isInWaterOrRain();
            boolean inBubbleColumn = this.isInWaterOrBubble(); // TODO -->  was  isInBubbleColumn

            if (inWater || inRain || inBubbleColumn) {
                if (this.wetSource == null) {
                    this.wetSource = WetSource.of(inWater, inBubbleColumn, inRain);
                }
                if (this.isShaking && !this.level.isClientSide) {
                    this.finishShaking();
                    this.level.broadcastEntityEvent(this, EmortisConstants.EntityState.CANIS_INTERRUPT_SHAKING);
                }
            } else if ((this.wetSource != null || this.isShaking) && this.isShaking) {
                if (this.timeCanisIsShaking == 0.0F) {
                    Networking.sendToNearby(this.level, this, new PacketAnimEntity(this.getId(), Animations.SHAKING.ordinal()));
                    this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }

                this.prevTimeCanisIsShaking = this.timeCanisIsShaking;
                this.timeCanisIsShaking += 0.05F;
                if (this.prevTimeCanisIsShaking >= 2.0F) {

                    //TODO check if only called server side
                    if (this.wetSource != null) {
                        for (ICanisTransmogrification alter : this.transmogrifications) {
                            alter.onShakingDry(this, this.wetSource);
                        }
                    }
                    this.wetSource = null;
                    this.finishShaking();
                }

                if (this.timeCanisIsShaking > 0.4F) {
                    float f = (float)this.getY();
                    int i = (int)(Mth.sin((this.timeCanisIsShaking - 0.4F) * (float)Math.PI) * 7.0F);
                    Vec3 vec3d = this.getDeltaMovement();

                    for (int j = 0; j < i; ++j) {
                        float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                        float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                        this.level.addParticle(ParticleTypes.SPLASH, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                    }
                }
            }
            // On server side
            if (!this.level.isClientSide) {
                // Every 2 seconds
                if (this.tickCount % 40 == 0) {
                    CanisLocationStorage.get(this.level).getOrCreateData(this).update(this);

                    if (this.getOwner() != null) {
                        this.setOwnersName(this.getOwner().getName());
                    }
                }
            }
        }
        this.transmogrifications.forEach((alter) -> alter.tick(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.wetSource != null && !this.isShaking && !this.isPathFinding() && this.isOnGround()) {
            this.startShaking();
            this.level.broadcastEntityEvent(this, EmortisConstants.EntityState.CANIS_START_SHAKING);
        }

        if (!this.level.isClientSide) {
            if (Config.DISABLE_HUNGER.get() == false) { /// TODO does this cause problems?
                this.prevHungerTick = this.hungerTick;

                if (!this.isVehicle() && !this.isInSittingPose()) {
                    this.hungerTick += 1;
                }
                for (ICanisTransmogrification alter : this.transmogrifications) {
                    InteractionResultHolder<Integer> result = alter.hungerTick(this, this.hungerTick - this.prevHungerTick);

                    if (result.getResult().shouldSwing()) {
                        this.hungerTick = result.getObject() + this.prevHungerTick;
                    }
                }
                if (this.hungerTick > 400) {
                    this.setCanisHunger(this.getCanisHunger() - 1);
                    this.hungerTick -= 400;
                }
            }
            this.prevHealingTick = this.healingTick;
            this.healingTick += 8;

            if (this.isInSittingPose()) {
                this.healingTick += 4;
            }

            for (ICanisTransmogrification alter : this.transmogrifications) {
                InteractionResultHolder<Integer> result = alter.healingTick(this, this.healingTick - this.prevHealingTick);
                if (result.getResult().shouldSwing()) {
                    this.healingTick = result.getObject() + this.prevHealingTick;
                }
            }
            if (this.healingTick >= 6000) {
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(1);
                }
                this.healingTick = 0;
            }
        }
        if(Config.ClientConfig.HOMINI_PARTICLES.get() && this.level.isClientSide && this.getCanisLevel().isHominiCanis()) {
            for (int i = 0; i < 2; i++) {
                this.level.addParticle(ParticleTypes.ENCHANT, this.getRandomX(0.5D), this.getRandomY() + 1.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2D);
            }
        }
        // Check if canis bowl still exists every 50t/2.5s, if not remove
        if (this.tickCount % 50 == 0) {
            ResourceKey<Level> dimKey = this.level.dimension();
            Optional<BlockPos> bowlPos = this.getBowlPos(dimKey);

            // If the canis has a food bowl in this dimension then check if it is still there
            // Only check if the chunk it is in is loaded
            if (bowlPos.isPresent() && this.level.hasChunkAt(bowlPos.get()) && !this.level.getBlockState(bowlPos.get()).is(CanisBlocks.FOOD_BOWL.get())) {
                this.setBowlPos(dimKey, Optional.empty());
            }
        }
        this.transmogrifications.forEach((alter) -> alter.livingTick(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (this.isTame()) {
            if (stack.getItem() == ItemInit.PACT_OF_SERVITUDE.get() && this.canInteract(player)) {
                if (this.level.isClientSide) {
                    CanisInfoScreen.open(this);
                }
                return InteractionResult.SUCCESS;
            }
        }
        ///       NOT NEEDED SINCE A TAME CANIS CANNOT SPAWN ON ITS OWN      /////
        else { // Not tamed
            if (stack.getItem() == CanisItems.TRAINING_TREAT.get()) {
                if (!this.level.isClientSide) {
                    this.usePlayerItem(player, hand, stack);
                    if (stack.getItem() == CanisItems.TRAINING_TREAT.get() || this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.navigation.stop();
                        this.setTarget((LivingEntity) null);
                        this.setOrderedToSit(true);
                        this.setHealth(80.0F);
                        this.level.broadcastEntityEvent(this, EmortisConstants.EntityState.CANIS_HEARTS);
                    } else {
                        this.level.broadcastEntityEvent(this, EmortisConstants.EntityState.CANIS_SMOKE);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }

        if (player.getMainHandItem().is(Tags.Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color == null || this.entityData.get(COLOR).equals(color.getName()) || !Arrays.asList(canisColors).contains(color.getName()))
                return InteractionResult.SUCCESS;
            this.entityData.set(COLOR, color.getName());
            player.getMainHandItem().shrink(1);
            return InteractionResult.SUCCESS;
        }

        Optional<ICanisFoodHandler> foodHandler = FoodHandler.getMatch(this, stack, player);
        if (foodHandler.isPresent() && (((this.getCanisHunger() < this.getMaxHunger()) || (this.getHealth() < this.getMaxHealth())))) {
            this.playEatingSound();
            return foodHandler.get().consume(this, stack, player);
        }
        InteractionResult interactResult = InteractionHandler.getMatch(this, stack, player, hand);
        if (interactResult != InteractionResult.PASS) {
            return interactResult;
        }
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.processInteract(this, this.level, player, hand);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        InteractionResult actionresulttype = super.mobInteract(player, hand);
        if ((!actionresulttype.consumesAction())/* || this.isBaby()) */ && this.canInteract(player)) {
            this.setOrderedToSit((!this.isOrderedToSit()) && (!this.isVehicle()));
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        }
        return actionresulttype;
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.canBeRiddenInWater(this, rider);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.canBeRiddenInWater(rider);
    }

    @Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.canTrample(this, state, pos, fallDistance);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.canTrample(state, pos, fallDistance);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource pSource) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.onLivingFall(this, distance, damageMultiplier);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        // Start: Logic copied from the super call and altered to apply the reduced fall damage to passengers too. #358
        float[] ret = net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier);
        if (ret == null) return false;
        distance = ret[0];
        damageMultiplier = ret[1];

        int i = this.calculateFallDamage(distance, damageMultiplier);

        if (i > 0) {
            if (this.isVehicle()) {
                for(Entity e : this.getPassengers()) {
                    e.hurt(DamageSource.FALL, i);
                }
            }

            // Sound selection is copied from Entity#getFallDamageSound()
            this.playSound(i > 4 ? this.getFallSounds().big() : this.getFallSounds().small(), 1.0F, 1.0F);
            this.playBlockFallSound();
            this.hurt(DamageSource.FALL, (float)i);
            return true;
        } else {
            return false;
        }
        // End: Logic copied from the super call and altered to apply the reduced fall damage to passengers too. #358
    }

    @Override public int getMaxFallDistance() {return super.getMaxFallDistance();}

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        MobEffectInstance effectInst = this.getEffect(MobEffects.JUMP);
        float f = effectInst == null ? 0.0F : effectInst.getAmplifier() + 1;
        distance -= f;
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResultHolder<Float> result = alter.calculateFallDistance(this, distance);
            if (result.getResult().shouldSwing()) {
                distance = result.getObject();
                break;
            }
        }
        return Mth.ceil((distance - 3.0F - f) * damageMultiplier);
    }

    @Override
    public boolean canBreatheUnderwater() {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.canBreatheUnderwater(this);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.canBreatheUnderwater();
    }

    @Override
    protected int decreaseAirSupply(int air) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResultHolder<Integer> result = alter.decreaseAirSupply(this, air);
            if (result.getResult().shouldSwing()) {
                return result.getObject();
            }
        }
        return super.decreaseAirSupply(air);
    }

    @Override
    protected int increaseAirSupply(int currentAir) {
        currentAir += 4;
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResultHolder<Integer> result = alter.determineNextAir(this, currentAir);
            if (result.getResult().shouldSwing()) {
                currentAir = result.getObject();
                break;
            }
        }
        return Math.min(currentAir, this.getMaxAirSupply());
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (this.isMode(EnumMode.DOCILE)) {
            return false;
        }
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.canAttack(this, target);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        // Stops Cani being able to attack creepers if their health is too low
        if (target instanceof Creeper && this.getHealth() <= 10) {
            return false;
        }
        return super.canAttack(target);
    }

    @Override
    public boolean canAttackType(EntityType<?> entityType) {
        if (this.isMode(EnumMode.DOCILE)) {
            return false;
        }
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.canAttack(this, entityType);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        } //  TODO -->  Make Cani hesitant to attack Sundered Cadavers
        // Stops Cani being able to attack creepers if their health is too low
        if (entityType == EntityType.CREEPER && this.getHealth() <= 10) {
            return false;
        }
        return super.canAttackType(entityType);
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (this.isMode(EnumMode.DOCILE)) {
            return false;
        }

        //TODO make wolves not able to attack cani
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.shouldAttackEntity(this, target, owner);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        if (target instanceof Ghast) {
            return false;
        }
        // Stops Cani being able to attack creepers if thier health is too low.
        if (target instanceof Creeper && this.getHealth() <= 10) {
            return false;
        }
        if (target instanceof Wolf) {
            Wolf wolfentity = (Wolf)target;
            return !wolfentity.isTame() || wolfentity.getOwner() != owner;
        } else if (target instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)target)) {
            return false;
        } else if (target instanceof AbstractHorse && ((AbstractHorse)target).isTamed()) {
            return false;
        } else {
            return !(target instanceof TamableAnimal) || !((TamableAnimal)target).isTame();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResultHolder<Float> result = alter.attackEntityFrom(this, source, amount);

            // TODO
            if (result.getResult() == InteractionResult.FAIL) {
                return false;
            } else {
                amount = result.getObject();
            }
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            // Must be checked here too as hitByEntity only applies to when the Canis is
            // directly hit not indirect damage like sweeping effect etc
            if (entity instanceof Player && !this.canPlayersAttack()) {
                return false;
            }
            this.setOrderedToSit(false);
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 2.0F;
            }
            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.attackEntityAsMob(this, target);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        AttributeInstance attackDamageInst = this.getAttribute(Attributes.ATTACK_DAMAGE);
        Set<AttributeModifier> critModifiers = null;
        if (this.getAttribute(CanisAttributes.CRIT_CHANCE.get()).getValue() > this.getRandom().nextDouble()) {
            critModifiers = this.getAttribute(CanisAttributes.CRIT_BONUS.get()).getModifiers();
            critModifiers.forEach(attackDamageInst::addTransientModifier);
        }
        int damage = ((int) attackDamageInst.getValue());
        if (critModifiers != null) {
            critModifiers.forEach(attackDamageInst::removeModifier);
        }
        boolean flag = target.hurt(DamageSource.mobAttack(this), damage);
        if (flag) {
            this.doEnchantDamageEffects(this, target);
            this.statsTracker.increaseDamageDealt(damage);

            if (critModifiers != null) {
                // TODO Might want to make into a packet
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().particleEngine.createTrackingEmitter(target, ParticleTypes.CRIT));
            }
        }
        return flag;
    }

    @Override
    public void awardKillScore(Entity killed, int scoreValue, DamageSource damageSource) {
        super.awardKillScore(killed, scoreValue, damageSource);
        this.statsTracker.incrementKillCount(killed);
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource source) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.canBlockDamageSource(this, source);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.isDamageSourceBlocked(source);
    }

    @Override
    public void setSecondsOnFire(int second) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResultHolder<Integer> result = alter.setFire(this, second);
            if (result.getResult().shouldSwing()) {
                second = result.getObject();
            }
        }
        super.setSecondsOnFire(second);
    }

    @Override
    public boolean fireImmune() {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.isImmuneToFire(this);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.fireImmune();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.isInvulnerableTo(this, source);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isInvulnerable() {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.isInvulnerable(this);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.isInvulnerable();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectIn) {
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.isPotionApplicable(this, effectIn);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return super.canBeAffected(effectIn);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return this.canInteract(player) && super.canBeLeashed(player);
    }

    @Override
    public void setUUID(UUID uniqueIdIn) {
        // If the UUID is changed remove old one and add new one
        UUID oldUniqueId = this.getUUID();
        if (uniqueIdIn.equals(oldUniqueId)) {
            return; // No change do nothing
        }
        super.setUUID(uniqueIdIn);
        if (this.level != null && !this.level.isClientSide) {
            CanisLocationStorage.get(this.level).remove(oldUniqueId);
            CanisLocationStorage.get(this.level).getOrCreateData(this).update(this);
        }
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
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80.0D);
            this.setHealth(70.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80.0D); // was 8
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D); // was 4
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
        if (entityIn instanceof Player && !this.canPlayersAttack()) {
            return true;
        }
        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResult result = alter.hitByEntity(this, entityIn);
            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }
        return false;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(CanisItems.CANIS_SUMMONING_CHARM.get());
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return CanisTags.BREEDING_ITEMS.contains(stack.getItem());
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(otherAnimal instanceof CanisEntity)) {
            return false;
        } else {
            CanisEntity entitycanis = (CanisEntity) otherAnimal;
            if (!entitycanis.isTame()) {
                return false;
            } else if (entitycanis.isInSittingPose()) {
                return false;
            } else if (Config.CANIS_GENDER.get() && !this.getGender().canMateWith(entitycanis.getGender())) {
                return false;
            } else {
                return this.isInLove() && entitycanis.isInLove();
            }
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel worldIn, AgeableMob partner) {
        CanisEntity child = SpecializedEntityTypes.CANIS.get().create(worldIn);
        UUID uuid = this.getOwnerUUID();

        if (uuid != null) {
            child.setOwnerUUID(uuid);
            child.setTame(true);
        }
        if (partner instanceof CanisEntity && Config.CANIS_PUPS_GET_PARENT_LEVELS.get()) {
            child.setLevel(this.getCanisLevel().combine(((CanisEntity) partner).getCanisLevel()));
        }
        return child;
    }

    @Override
    public boolean shouldShowName() {
        return (Config.ClientConfig.ALWAYS_SHOW_CANIS_NAME.get() && this.hasCustomName() && !this.isVehicle()) || super.shouldShowName();
    }

//    @Override
//    public float getScale() {
//        if (this.isBaby()) { return 0.5F;
//        } else { return this.getCanisSize() * 0.3F + 0.1F; }
//    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    /*  Any mod that tries to access capabilities from entity size/entity
        creation event will crash here because of the order java inits the
        classes fields and so will not have been initialised and are
        accessed during the classes super() call.
        Since this.transmogrifications will be empty anyway as we have not read
        NBT data at this point just avoid silent error   */
        if (this.transmogrifications == null) { return super.getCapability(cap, side); }
        for (ICanisTransmogrification alter : this.transmogrifications) {
            LazyOptional<T> result = alter.getCapability(this, cap, side);
                if (result != null) { return result; }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public Entity changeDimension(ServerLevel worldIn, ITeleporter teleporter) {
        Entity transportedEntity = super.changeDimension(worldIn, teleporter);
        if (transportedEntity instanceof CanisEntity) {
            CanisLocationStorage.get(this.level).getOrCreateData(this).update((CanisEntity) transportedEntity);
        }
        return transportedEntity;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld(); // When the entity is added to tracking list
        if (this.level != null && !this.level.isClientSide) {
            //CanisLocationData locationData = CanisLocationStorage.get(this.world).getOrCreateData(this);
            //locationData.update(this);
        }
    }
    @Override public void onRemovedFromWorld() {super.onRemovedFromWorld();}

    /**
     * When the canis is brought back to life
     */
    @Override public void revive() {super.revive();}

    @Override
    protected void tickDeath() {
        if (this.deathTime == 19) { // 1 second after death
            if (this.level != null && !this.level.isClientSide) {
//                CanisRespawnStorage.get(this.world).putData(this);
//                RigoranthusEmortisReborn.LOGGER.debug("Saved canis as they died {}", this);
//                CanisLocationStorage.get(this.world).remove(this);
//                RigoranthusEmortisReborn.LOGGER.debug("Removed canis location as they were removed from the world {}", this);
            }
        }
        super.tickDeath();
    }

    private void startShaking() {
        this.isShaking = true;
        this.timeCanisIsShaking = 0.0F;
        this.prevTimeCanisIsShaking = 0.0F;
    }

    private void finishShaking() {
        this.isShaking = false;
        this.timeCanisIsShaking = 0.0F;
        this.prevTimeCanisIsShaking = 0.0F;
    }

    @Override
    public void die(DamageSource cause) {
        this.wetSource = null;
        this.finishShaking();

        this.transmogrifications.forEach((alter) -> alter.onDeath(this, cause));
        super.die(cause);
        // Save inventory after onDeath is called so that wayward-traveller inventory can be dropped and not saved
        if (this.level != null && !this.level.isClientSide) {
            CanisRespawnStorage.get(this.level).putData(this);
            CanisLocationStorage.get(this.level).remove(this);
        }
    }

    @Override
    public void dropEquipment() {
        super.dropEquipment();
        this.transmogrifications.forEach((alter) -> alter.dropInventory(this));
    }

    /**
     * When the canis is removed
     */
    @Override
    public void remove(Entity.RemovalReason removalReason) {
        super.remove(removalReason);

        if (removalReason == RemovalReason.DISCARDED || removalReason == RemovalReason.KILLED) {
            if (this.level != null && !this.level.isClientSide) {
                CanisRespawnStorage.get(this.level).putData(this);
                CanisLocationStorage.get(this.level).remove(this);
            }
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.transmogrifications.forEach((alter) -> alter.invalidateCapabilities(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        ListTag skillList = new ListTag();
        List<SkillInstance> skills = this.getSkillMap();

        for (int i = 0; i < skills.size(); i++) {
            CompoundTag skillTag = new CompoundTag();
            skills.get(i).writeInstance(this, skillTag);
            skillList.add(skillTag);
        }
        compound.put("skills", skillList);

        ListTag accoutrementList = new ListTag();
        List<AccoutrementInstance> accouterments = this.getAccouterments();

        for (int i = 0; i < accouterments.size(); i++) {
            CompoundTag accoutrementTag = new CompoundTag();
            accouterments.get(i).writeInstance(accoutrementTag);
            accoutrementList.add(accoutrementTag);
        }
        compound.put("accouterments", accoutrementList);
        compound.putString("clothColor", this.getClothColor().getSaveName());
        compound.putString("shadesColor", this.getShadesColor().getSaveName());
        compound.putString("mode", this.getMode().getSaveName());
        compound.putString("canisGender", this.getGender().getSaveName());
        compound.putFloat("canisHunger", this.getCanisHunger());
        this.getOwnersName().ifPresent((comp) -> {
            NBTUtilities.putTextComponent(compound, "lastKnownOwnerName", comp);
        });

        compound.putBoolean("displayCloth", this.doDisplayCloth());
        compound.putBoolean("willObey", this.willObeyOthers());
        compound.putBoolean("friendlyFire", this.canPlayersAttack());
//        compound.putInt("canisSize", this.getCanisSize());
        compound.putInt("level_normal", this.getCanisLevel().getLevel(CanisLevel.Type.CHORDATA));
        compound.putInt("level_homini", this.getCanisLevel().getLevel(CanisLevel.Type.HOMINI));
        NBTUtilities.writeItemStack(compound, "fetchItem", this.getBoneVariant());

        DimensionDependantArg<Optional<BlockPos>> bedsData = this.entityData.get(CANIS_BED_LOCATION.get());

        if (!bedsData.isEmpty()) {
            ListTag bedsList = new ListTag();

            for (Entry<ResourceKey<Level>, Optional<BlockPos>> entry : bedsData.entrySet()) {
                CompoundTag bedNBT = new CompoundTag();
                NBTUtilities.putResourceLocation(bedNBT, "dim", entry.getKey().location());
                NBTUtilities.putBlockPos(bedNBT, "pos", entry.getValue());
                bedsList.add(bedNBT);
            }
            compound.put("beds", bedsList);
        }

        DimensionDependantArg<Optional<BlockPos>> bowlsData = this.entityData.get(CANIS_BOWL_LOCATION.get());

        if (!bowlsData.isEmpty()) {
            ListTag bowlsList = new ListTag();

            for (Entry<ResourceKey<Level>, Optional<BlockPos>> entry : bowlsData.entrySet()) {
                CompoundTag bowlsNBT = new CompoundTag();
                NBTUtilities.putResourceLocation(bowlsNBT, "dim", entry.getKey().location());
                NBTUtilities.putBlockPos(bowlsNBT, "pos", entry.getValue());
                bowlsList.add(bowlsNBT);
            }
            compound.put("bowls", bowlsList);
        }
        compound.putString("color", this.entityData.get(COLOR));
        this.statsTracker.writeAdditional(compound);
        this.transmogrifications.forEach((alter) -> alter.onWrite(this, compound));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        List<SkillInstance> skillMap = this.getSkillMap();
        skillMap.clear();

        if (compound.contains("skills", Tag.TAG_LIST)) {
            ListTag skillList = compound.getList("skills", Tag.TAG_COMPOUND);

            for (int i = 0; i < skillList.size(); i++) {
                // Add directly so that nothing is lost, if number allowed on changes
                SkillInstance.readInstance(this, skillList.getCompound(i)).ifPresent(skillMap::add);
            }
        }
        this.markEntityDataAccessorDirty(SKILLS.get(), false); // Mark dirty so data is synced to client

        List<AccoutrementInstance> accouterments = this.getAccouterments();
        accouterments.clear();

        if (compound.contains("accouterments", Tag.TAG_LIST)) {
            ListTag accoutrementList = compound.getList("accouterments", Tag.TAG_COMPOUND);

            for (int i = 0; i < accoutrementList.size(); i++) {
                // Add directly so that nothing is lost, if number allowed on changes
                AccoutrementInstance.readInstance(accoutrementList.getCompound(i)).ifPresent(accouterments::add);
            }
        }
        this.markEntityDataAccessorDirty(ACCOUTERMENTS.get(), false); // Mark dirty so data is synced to client
        // Does what notifyDataManagerChange would have done but this way only does it once
        this.recalculateTransmogrificationsCache();
        this.spendablePoints.markForRefresh();

        try {
            for (ICanisTransmogrification inst : this.transmogrifications) {
                inst.init(this);
            }
        } catch (Exception e) {
            RigoranthusEmortisReborn.LOGGER.error("Failed to init transmogrification: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            this.setGender(EnumGender.bySaveName(compound.getString("canisGender")));
            if (compound.contains("mode", Tag.TAG_STRING)) {
                this.setMode(EnumMode.bySaveName(compound.getString("mode")));
            }
            if (compound.contains("clothColor", Tag.TAG_STRING)) {
                this.setClothColor(EnumClothColor.bySaveName(compound.getString("clothColor")));
            }
            if (compound.contains("shadesColor", Tag.TAG_STRING)) {
                this.setShadesColor(EnumShadesColor.bySaveName(compound.getString("shadesColor")));
            }
            if (compound.contains("fetchItem", Tag.TAG_COMPOUND)) {
                this.setBoneVariant(NBTUtilities.readItemStack(compound, "fetchItem"));
            }
            this.setHungerDirectly(compound.getFloat("canisHunger"));
            this.setOwnersName(NBTUtilities.getTextComponent(compound, "lastKnownOwnerName"));
            this.setDisplayCloth(compound.getBoolean("displayCloth"));
            this.setWillObeyOthers(compound.getBoolean("willObey"));
            this.setCanPlayersAttack(compound.getBoolean("friendlyFire"));
//            if (compound.contains("canisSize", Tag.TAG_ANY_NUMERIC)) { this.setCanisSize(compound.getInt("canisSize")); }
        } catch (Exception e) {
            RigoranthusEmortisReborn.LOGGER.error("Failed to load levels: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            if (compound.contains("level_normal", Tag.TAG_ANY_NUMERIC)) {
                this.getCanisLevel().setLevel(CanisLevel.Type.CHORDATA, compound.getInt("level_normal"));
                this.markEntityDataAccessorDirty(CANIS_LEVEL.get());
            }
            if (compound.contains("level_homini", Tag.TAG_ANY_NUMERIC)) {
                this.getCanisLevel().setLevel(CanisLevel.Type.HOMINI, compound.getInt("level_homini"));
                this.markEntityDataAccessorDirty(CANIS_LEVEL.get());
            }
        } catch (Exception e) {
            RigoranthusEmortisReborn.LOGGER.error("Failed to load levels: " + e.getMessage());
            e.printStackTrace();
        }

        DimensionDependantArg<Optional<BlockPos>> bedsData = this.entityData.get(CANIS_BED_LOCATION.get()).copyEmpty();

        try {
            if (compound.contains("beds", Tag.TAG_LIST)) {
                ListTag bedsList = compound.getList("beds", Tag.TAG_COMPOUND);

                for (int i = 0; i < bedsList.size(); i++) {
                    CompoundTag bedNBT = bedsList.getCompound(i);
                    ResourceLocation loc = NBTUtilities.getResourceLocation(bedNBT, "dim");
                    ResourceKey<Level> type = ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
                    Optional<BlockPos> pos = NBTUtilities.getBlockPos(bedNBT, "pos");
                    bedsData.put(type, pos);
                }
            }
        } catch (Exception e) {
            RigoranthusEmortisReborn.LOGGER.error("Failed to load beds: " + e.getMessage());
            e.printStackTrace();
        }
        this.entityData.set(CANIS_BED_LOCATION.get(), bedsData);

        DimensionDependantArg<Optional<BlockPos>> bowlsData = this.entityData.get(CANIS_BOWL_LOCATION.get()).copyEmpty();

        try {
            if (compound.contains("bowls", Tag.TAG_LIST)) {
                ListTag bowlsList = compound.getList("bowls", Tag.TAG_COMPOUND);

                for (int i = 0; i < bowlsList.size(); i++) {
                    CompoundTag bowlsNBT = bowlsList.getCompound(i);
                    ResourceLocation loc = NBTUtilities.getResourceLocation(bowlsNBT, "dim");
                    ResourceKey<Level> type = ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
                    Optional<BlockPos> pos = NBTUtilities.getBlockPos(bowlsNBT, "pos");
                    bowlsData.put(type, pos);
                }
            }
        } catch (Exception e) {
            RigoranthusEmortisReborn.LOGGER.error("Failed to load bowls: " + e.getMessage());
            e.printStackTrace();
        }

        this.entityData.set(CANIS_BOWL_LOCATION.get(), bowlsData);

        if (compound.contains("color"))
            this.entityData.set(COLOR, compound.getString("color"));

        try {
            this.statsTracker.readAdditional(compound);
        } catch (Exception e) {
            RigoranthusEmortisReborn.LOGGER.error("Failed to load stats tracker: " + e.getMessage());
            e.printStackTrace();
        }
        this.transmogrifications.forEach((alter) -> {
            try {
                alter.onRead(this, compound);
            } catch (Exception e) {
                RigoranthusEmortisReborn.LOGGER.error("Failed to load alteration: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (SKILLS.get().equals(key) || ACCOUTERMENTS.get().equals(key)) {
            this.recalculateTransmogrificationsCache();
            for (ICanisTransmogrification inst : this.transmogrifications) {
                inst.init(this);
            }
        }
        if (SKILLS.get().equals(key)) {this.spendablePoints.markForRefresh();}
        if (CANIS_LEVEL.get().equals(key)) {this.spendablePoints.markForRefresh();}
        if (ACCOUTERMENTS.get().equals(key)) {
            // If client sort accouterments
            if (this.level.isClientSide) {
                // Does not recall this notifyDataManagerChange as list object is
                // still the same, maybe in future MC versions this will change so need to watch out
                this.getAccouterments().sort(AccoutrementInstance.RENDER_SORTER);
            }
        }
//        if (SIZE.equals(key)) {this.refreshDimensions();}
    }

    public void recalculateTransmogrificationsCache() {
        this.transmogrifications.clear();
        this.foodHandlers.clear();

        for (AccoutrementInstance inst : this.getAccouterments()) {
            if (inst instanceof ICanisTransmogrification) {
                this.transmogrifications.add((ICanisTransmogrification) inst);
            }
            if (inst instanceof ICanisFoodHandler) {
                this.foodHandlers.add((ICanisFoodHandler) inst);
            }
        };
        List<SkillInstance> skills = this.getSkillMap();
        this.transmogrifications.addAll(skills);
        for (SkillInstance inst : skills) {
            if (inst instanceof ICanisFoodHandler) {
                this.foodHandlers.add((ICanisFoodHandler) inst);
            }
        }
    }

    /**
     * If the entity can make changes to the canis
     * @param livingEntity The entity
     */
    @Override
    public boolean canInteract(LivingEntity livingEntity) {
        return this.willObeyOthers() || this.isOwnedBy(livingEntity);
    }

    @Override
    public List<AccoutrementInstance> getAccouterments() {
        return this.entityData.get(ACCOUTERMENTS.get());
    }

    @Override
    public boolean addAccoutrement(@Nonnull AccoutrementInstance accoutrementInst) {
        List<AccoutrementInstance> accouterments = this.getAccouterments();
        AccoutrementType type = accoutrementInst.getAccoutrement().getType();

        // Gets accouterments of the same type
        List<AccoutrementInstance> filtered = accouterments.stream().filter((inst) -> {
            return type == inst.getAccoutrement().getType();
        }).collect(Collectors.toList());

        if (filtered.size() >= type.numberToPutOn()) {
            return false;
        }
        accouterments.add(accoutrementInst);
        this.markEntityDataAccessorDirty(ACCOUTERMENTS.get());
        return true;
    }

    @Override
    public List<AccoutrementInstance> removeAccouterments() {
        List<AccoutrementInstance> removed = new ArrayList<>(this.getAccouterments());

        for (AccoutrementInstance inst : removed) {
            if (inst instanceof ICanisTransmogrification) {
                ((ICanisTransmogrification) inst).remove(this);
            }
        }
        this.getAccouterments().clear();
        this.markEntityDataAccessorDirty(ACCOUTERMENTS.get());
        return removed;
    }

    public Optional<AccoutrementInstance> getAccoutrement(AccoutrementType typeIn) {
        List<AccoutrementInstance> accouterments = this.getAccouterments();

        for (AccoutrementInstance inst : accouterments) {
            if (inst.getAccoutrement().getType() == typeIn) {
                return Optional.of(inst);
            }
        }
        return Optional.empty();
    }

    public Optional<AccoutrementInstance> getAccoutrement(Accoutrement typeIn) {
        List<AccoutrementInstance> accouterments = this.getAccouterments();

        for (AccoutrementInstance inst : accouterments) {
            if (inst.getAccoutrement() == typeIn) {
                return Optional.of(inst);
            }
        }
        return Optional.empty();
    }

    public Optional<Component> getOwnersName() { return this.entityData.get(LAST_KNOWN_NAME); }

    public void setOwnersName(@Nullable Component comp) { this.setOwnersName(Optional.ofNullable(comp)); }

    public void setOwnersName(Optional<Component> collar) { this.entityData.set(LAST_KNOWN_NAME, collar); }

    public EnumGender getGender() { return this.entityData.get(GENDER.get()); }

    public void setGender(EnumGender collar) { this.entityData.set(GENDER.get(), collar); }

    @Override
    public EnumMode getMode() { return this.entityData.get(MODE.get()); }

    public boolean isMode(EnumMode... modes) {
        EnumMode mode = this.getMode();
        for (EnumMode test : modes) {
            if (mode == test) {
                return true;
            }
        }
        return false;
    }

    public void setMode(EnumMode color) { this.entityData.set(MODE.get(), color); }

    @Override
    public EnumClothColor getClothColor() { return this.entityData.get(CLOTH_COLOR.get()); }

    public boolean isClothColor(EnumClothColor... colors) {
        EnumClothColor color = this.getClothColor();
        for (EnumClothColor test : colors) {
            if (color == test) {
                return true;
            }
        }
        return false;
    }

    public void setClothColor(EnumClothColor color) { this.entityData.set(CLOTH_COLOR.get(), color); }

    @Override
    public EnumShadesColor getShadesColor() { return this.entityData.get(SHADES_COLOR.get()); }

    public boolean isShadesColor(EnumShadesColor... colors) {
        EnumShadesColor color = this.getShadesColor();
        for (EnumShadesColor test : colors) {
            if (color == test) {
                return true;
            }
        }
        return false;
    }

    public void setShadesColor(EnumShadesColor color) { this.entityData.set(SHADES_COLOR.get(), color); }

    public Optional<BlockPos> getBedPos() { return this.getBedPos(this.level.dimension()); }

    public Optional<BlockPos> getBedPos(ResourceKey<Level> registryKey) { return this.entityData.get(CANIS_BED_LOCATION.get()).getOrDefault(registryKey, Optional.empty()); }

    public void setBedPos(@Nullable BlockPos pos) { this.setBedPos(this.level.dimension(), pos); }

    public void setBedPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) { this.setBedPos(registryKey, WorldUtil.toImmutable(pos)); }

    public void setBedPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) { this.entityData.set(CANIS_BED_LOCATION.get(), this.entityData.get(CANIS_BED_LOCATION.get()).copy().set(registryKey, pos)); }

    public Optional<BlockPos> getBowlPos() { return this.getBowlPos(this.level.dimension()); }

    public Optional<BlockPos> getBowlPos(ResourceKey<Level> registryKey) { return this.entityData.get(CANIS_BOWL_LOCATION.get()).getOrDefault(registryKey, Optional.empty()); }

    public void setBowlPos(@Nullable BlockPos pos) { this.setBowlPos(this.level.dimension(), pos); }

    public void setBowlPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) { this.setBowlPos(registryKey, WorldUtil.toImmutable(pos)); }

    public void setBowlPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) { this.entityData.set(CANIS_BOWL_LOCATION.get(), this.entityData.get(CANIS_BOWL_LOCATION.get()).copy().set(registryKey, pos)); }

    @Override
    public float getMaxHunger() {
        float maxHunger = Config.DEFAULT_MAX_HUNGER.get();

        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResultHolder<Float> result = alter.getMaxHunger(this, maxHunger);

            if (result.getResult().shouldSwing()) {
                maxHunger = result.getObject();
            }
        }
        return maxHunger;
    }

    @Override
    public float getCanisHunger() { return this.entityData.get(HUNGER_INT); }

    @Override
    public void addHunger(float add) { this.setCanisHunger(this.getCanisHunger() + add); }

    @Override
    public void setCanisHunger(float hunger) {
        float diff = hunger - this.getCanisHunger();

        for (ICanisTransmogrification alter : this.transmogrifications) {
            InteractionResultHolder<Float> result = alter.setCanisHunger(this, hunger, diff);

            if (result.getResult().shouldSwing()) {
                hunger = result.getObject();
                diff = hunger - this.getCanisHunger();
            }
        }
        this.setHungerDirectly(Mth.clamp(hunger, 0, this.getMaxHunger()));
    }

    private void setHungerDirectly(float hunger) { this.entityData.set(HUNGER_INT, hunger); }

    @Override
    public CanisLevel getCanisLevel() { return this.entityData.get(CANIS_LEVEL.get()); }

    public void setLevel(CanisLevel level) { this.entityData.set(CANIS_LEVEL.get(), level); }

    @Override
    public void increaseLevel(CanisLevel.Type typeIn) {
        this.getCanisLevel().incrementLevel(typeIn);
        this.markEntityDataAccessorDirty(CANIS_LEVEL.get());
    }

//    @Override public void setCanisSize(int value) { this.entityData.set(SIZE, (byte)Math.min(5, Math.max(1, value))); }
//    @Override public int getCanisSize() { return this.entityData.get(SIZE); }

    public void setBoneVariant(ItemStack stack) { this.entityData.set(BONE_VARIANT, stack); }

    public ItemStack getBoneVariant() { return this.entityData.get(BONE_VARIANT); }

    @Nullable
    public IThrowableItem getThrowableItem() {
        Item item = this.entityData.get(BONE_VARIANT).getItem();
        return item instanceof IThrowableItem ? (IThrowableItem) item : null;
    }

    public boolean hasBone() { return !this.getBoneVariant().isEmpty(); }

    private boolean getCanisFlag(int bit) { return (this.entityData.get(CANIS_FLAGS) & bit) != 0; }

    private void setCanisFlag(int bits, boolean flag) {
        byte c = this.entityData.get(CANIS_FLAGS);
        this.entityData.set(CANIS_FLAGS, (byte)(flag ? c | bits : c & ~bits));
    }

    public void setBegging(boolean begging) { this.setCanisFlag(1, begging); }
    public boolean isBegging() { return this.getCanisFlag(1); }
    public void setWillObeyOthers(boolean obeyOthers) { this.setCanisFlag(2, obeyOthers); }
    public boolean willObeyOthers() { return this.getCanisFlag(2); }
    public void setCanPlayersAttack(boolean flag) { this.setCanisFlag(4, flag); }
    public boolean canPlayersAttack() { return this.getCanisFlag(4); }
    public void set8Flag(boolean collar) { this.setCanisFlag(8, collar); }
    public boolean get8Flag() { return this.getCanisFlag(8); }
    public void setHasSunglasses(boolean sunglasses) { this.setCanisFlag(16, sunglasses); }
    public boolean hasSunglasses() { return this.getCanisFlag(16); }
    public void setLyingDown(boolean lying) { this.setCanisFlag(32, lying); }
    public boolean isLyingDown() { return this.getCanisFlag(32); }
    public void set64Flag(boolean lying) { this.setCanisFlag(64, lying); }
    public boolean get64Flag() { return this.getCanisFlag(64); }
    public void setDisplayCloth(boolean displayCloth) { this.setCanisFlag(128, displayCloth); }
    public boolean doDisplayCloth() { return this.getCanisFlag(128); }

    public static String[] canisColors = { "pink", "magenta", "purple", "blue", "light_blue", "cyan", "green", "lime", "yellow", "orange", "red", "brown", "black", "gray", "light_gray", "white" };

    public enum COLORS {
        WHITE,      LIGHT_GRAY,     GRAY,
        BLACK,      BROWN,          RED,
        ORANGE,     YELLOW,         LIME,
        GREEN,      CYAN,           LIGHT_BLUE,
        BLUE,       PURPLE,         MAGENTA,
        PINK
    }

    public List<SkillInstance> getSkillMap() { return this.entityData.get(SKILLS.get()); }
    public void setSkillMap(List<SkillInstance> map) { this.entityData.set(SKILLS.get(), map); }

    public InteractionResult setSkillLevel(Skill skill, int level) {
        if (0 > level || level > skill.getMaxLevel()) {
            return InteractionResult.FAIL;
        }
        List<SkillInstance> activeSkills = this.getSkillMap();

        SkillInstance inst = null;
        for (SkillInstance activeInst : activeSkills) {
            if (activeInst.of(skill)) {
                inst = activeInst;
                break;
            }
        }
        if (inst == null) {
            if (level == 0) {
                return InteractionResult.PASS;
            }
            inst = skill.getDefault(level);
            activeSkills.add(inst);
            inst.init(this);
        } else {
            int previousLevel = inst.level();
            if (previousLevel == level) {
                return InteractionResult.PASS;
            }

            inst.setLevel(level);
            inst.set(this, previousLevel);

            if (level == 0) {
                activeSkills.remove(inst);
            }
        }
        this.markEntityDataAccessorDirty(SKILLS.get());
        return InteractionResult.SUCCESS;
    }

/*    public <T> void markEntityDataAccessorDirty(EntityDataAccessor<T> key) { this.markEntityDataAccessorDirty(key, true); }

    public <T> void markEntityDataAccessorDirty(EntityDataAccessor<T> key, boolean notify) {
        if (notify) { this.onSyncedDataUpdated(key); }
        // Force the entry to update
        IDataManager.DataEntry<T> dataentry = this.entityData.getItem(key);
        dataentry.setDirty(true);
        this.entityData.isDirty = true;
    }*/
    public <T> void markEntityDataAccessorDirty(EntityDataAccessor<T> key) {
        this.markEntityDataAccessorDirty(key, true);
    }

    public <T> void markEntityDataAccessorDirty(EntityDataAccessor<T> key, boolean notify) {
        if (notify) {
            this.onSyncedDataUpdated(key);
        }

        // Force the entry to update
        SynchedEntityData.DataItem<T> dataentry = this.entityData.getItem(key);
        dataentry.setDirty(true);
        this.entityData.isDirty = true;
    }

    @Override
    public void markAccoutermentsDirty() {
        this.markEntityDataAccessorDirty(ACCOUTERMENTS.get());
    }

    @Override
    public Optional<SkillInstance> getSkill(Skill skillIn) {
        List<SkillInstance> activeSkills = this.getSkillMap();

        for (SkillInstance activeInst : activeSkills) {
            if (activeInst.of(skillIn)) {
                return Optional.of(activeInst);
            }
        }
        return Optional.empty();
    }

    @Override
    public int getCanisLevel(Skill skillIn) {
        return this.getSkill(skillIn).map(SkillInstance::level).orElse(0);
    }

    @Override
    public <T> void setData(DataKey<T> key, T value) {
        if (key.isFinal() && this.hasData(key)) {
            throw new RuntimeException("Key is final but was tried to be set again.");
        }
        this.objects.put(key.getIndex(), value);
    }
    /**
     * Tries to put the object in the map, does nothing if the key already exists
     */
    @Override
    public <T> void setDataIfEmpty(DataKey<T> key, T value) {
        if (!this.hasData(key)) {
            this.objects.put(key.getIndex(), value);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getData(DataKey<T> key) {
        return (T) this.objects.get(key.getIndex());
    }

    @Override
    public <T> T getDataOrGet(DataKey<T> key, Supplier<T> other) {
        if (this.hasData(key)) {
            return this.getData(key);
        }
        return other.get();
    }

    @Override
    public <T> T getDataOrDefault(DataKey<T> key, T other) {
        if (this.hasData(key)) {
            return this.getData(key);
        }
        return other;
    }

    @Override
    public <T> boolean hasData(DataKey<T> key) {
        return this.objects.containsKey(key.getIndex());
    }

    @Override
    public void untame() {
        if(!(level instanceof ServerLevel))
            return;
        FeralCanisEntity feralCanis = ModEntities.FERAL_CANIS.create(level);
        feralCanis.absMoveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
        feralCanis.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);

        this.setTame(false);
        this.navigation.stop();
        this.setOrderedToSit(false);
        this.getSkillMap().clear();
        this.markEntityDataAccessorDirty(SKILLS.get());
        this.setOwnerUUID(null);
        this.setWillObeyOthers(false);
        this.remove(RemovalReason.DISCARDED);

        level.addFreshEntity(feralCanis);
    }

    public boolean canSpendPoints(int amount) {
        return this.getSpendablePoints() >= amount || this.getAccoutrement(CanisAccouterments.GOLDEN_COLLAR.get()).isPresent();
    }

    // When this method is changed the cache may need to be updated at certain points
    private final int getSpendablePointsInternal() {
        int totalPoints = 15 + this.getCanisLevel().getLevel(CanisLevel.Type.CHORDATA) + this.getCanisLevel().getLevel(CanisLevel.Type.HOMINI);
        for (SkillInstance entry : this.getSkillMap()) {
            totalPoints -= entry.getSkill().getCummulativeCost(entry.level());
        }
        return totalPoints;
    }
    public int getSpendablePoints() { return this.spendablePoints.get(); }
    @Override public boolean canRiderInteract() { return true; }
    @Override public Entity getControllingPassenger() { return this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0); }
    @Override public boolean canBeControlledByRider() { return this.getControllingPassenger() instanceof LivingEntity && !this.isInSittingPose(); }
    //TODO
    @Override public boolean isPickable() { return super.isPickable(); }
    @Override public boolean isPushable() { return !this.isVehicle() && super.isPushable(); }
    @Override public boolean isControlledByLocalInstance() { return super.isControlledByLocalInstance() && this.canInteract((LivingEntity) this.getControllingPassenger()); }
    public boolean isCanisJumping() { return this.canisJumping; }
    public void setCanisJumping(boolean jumping) { this.canisJumping = jumping; }

//    public double getCanisJumpStrength() {
//        float verticalVelocity = 0.42F + 0.06F * this.SKILLS.getCanisLevel(ModSkills.CAVALIER);
//        if (this.SKILLS.getCanisLevel(ModSkills.CAVALIER) == 5) verticalVelocity += 0.04F;
//        return verticalVelocity;
//    }
    // 0 - 100 input
    public void setJumpPower(int jumpPowerIn) { this.jumpPower = 1.0F; }

    public boolean canJump() {
        return true;
    }

    @Override
    public void travel(Vec3 positionIn) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

                // Face the canis in the direction of the controlling passenger
                this.yRot =livingentity.yRot;
                this.yRotO = this.yRot;
                this.xRot = livingentity.xRot * 0.5F;
                this.setRot(this.yRot, this.xRot);
                this.yBodyRot = this.yRot;
                this.yHeadRot = this.yBodyRot;
                this.maxUpStep = 1.1F;

                float strafe = livingentity.xxa * 0.7F;
                float forward = livingentity.zza;

//                if (this.hasImpulse && forward >= 0.0F) { Networking.sendToNearby(this.level, this, new PacketAnimEntity(this.getId(), Animations.RUNNING.ordinal())); }
                // If moving backwards half the speed
                if (forward <= 0.0F) {
                    forward *= 0.5F;
                }
                if (this.jumpPower > 0.0F && !this.isCanisJumping() && this.isOnGround()) {
                    // Calculate jump value based of jump strength, power this jump and jump boosts
                    double jumpValue = this.getAttribute(CanisAttributes.JUMP_POWER.get()).getValue() * this.getBlockJumpFactor() * this.jumpPower; //TODO do we want getJumpFactor?
                    if (this.hasEffect(MobEffects.JUMP)) {
                        jumpValue += (this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.1F;
                    }
                    // Apply jump
                    Vec3 vec3d = this.getDeltaMovement();
                    this.setDeltaMovement(vec3d.x, jumpValue, vec3d.z);
                    this.setCanisJumping(true);
                    this.hasImpulse = true;
                    // If moving forward, propel further in the direction
                    if (forward > 0.0F) {
                        final float amount = 0.4F; // TODO Allow people to change this value
                        float compX = Mth.sin(this.yRot * ((float)Math.PI / 180F));
                        float compZ = Mth.cos(this.yRot * ((float)Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add(-amount * compX * this.jumpPower, 0.0D, amount * compZ * this.jumpPower));
                        //this.playJumpSound();
                    }
                    this.jumpPower = 0.0F; // Mark as unable jump until reset
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    // Set the move speed and move the canis in the direction of the controlling entity
                    this.setSpeed((float)this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.5F);
                    super.travel(new Vec3(strafe, positionIn.y, forward));
                    this.lerpSteps = 0;
                } else if (livingentity instanceof Player) {
                    // A player is riding and can not control them
                    this.setDeltaMovement(Vec3.ZERO);
                }

                // Once the entity reaches the ground again allow it to jump again
                if (this.isOnGround()) {
                    this.jumpPower = 0.0F;
                    this.setCanisJumping(false);
                }

                this.animationSpeedOld = this.animationSpeed;
                double changeX = this.getX() - this.xo;
                double changeY = this.getZ() - this.zo;
                float f4 = Mth.sqrt((float) (changeX * changeX + changeY * changeY)) * 4.0F;
                if (f4 > 1.0F) {
                    f4 = 1.0F;
                }
                this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
                this.animationPosition += this.animationSpeed;
            } else {
                this.maxUpStep = 0.5F; // Default
                this.flyingSpeed = 0.02F; // Default
                super.travel(positionIn);
            }
            this.addMovementStat(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        }
    }

    public void addMovementStat(double xD, double yD, double zD) {
        if (this.isVehicle()) {
            int j = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
            this.statsTracker.increaseDistanceRidden(j);
        }
        if (!this.isPassenger()) {
            if (this.isEyeInFluid(FluidTags.WATER)) {
                int j = Math.round(Mth.sqrt((float) (xD * xD + yD * yD + zD * zD)) * 100.0F);
                if (j > 0) {
                    this.statsTracker.increaseDistanceOnWater(j);
                }
            } else if (this.isInWater()) {
                int k = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                if (k > 0) {
                    this.statsTracker.increaseDistanceInWater(k);
                }
            } else if (this.isOnGround()) {
                int l = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                if (l > 0) {
                    if (this.isSprinting()) {
                        this.statsTracker.increaseDistanceSprint(l);
                    } else if (this.isCrouching()) {
                        this.statsTracker.increaseDistanceSneaking(l);
                    } else {
                        this.statsTracker.increaseDistanceWalk(l);
                    }
                }
            } else { // Time in air
                int j1 = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
            }
        }
    }

    @Override
    public TranslatableComponent getTranslationKey(java.util.function.Function<EnumGender, String> function) {
        return new TranslatableComponent(function.apply(Config.CANIS_GENDER.get() ? this.getGender() : EnumGender.UNISEX));
    }

    @Override
    public boolean isLying() {
        LivingEntity owner = this.getOwner();
        boolean ownerSleeping = owner != null && owner.isSleeping();
        if (ownerSleeping) {
            return true;
        }
        Block blockBelow = this.level.getBlockState(this.blockPosition().below()).getBlock();
        boolean onBed = blockBelow == CanisBlocks.CANIS_BED.get() || BlockTags.BEDS.contains(blockBelow);
        if (onBed) {
            return true;
        }
        return false;
    }
    @Override public List<ICanisFoodHandler> getFoodHandlers() {return this.foodHandlers;}
    public void setTargetBlock(BlockPos pos) {this.targetBlock = pos;}
    public BlockPos getTargetBlock() {return this.targetBlock;}
}




//    private void setNewStatsByLevel(CanisEvolutionLevels levels) {
//        int currentLevel = this.getCanisEvolutionData().getCanisLevel();
//        if (currentLevel == 1) {
//            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(CanisStatsByEvolutionLevel.CHORDATA.getMaxHealthForLevel(levels));
//            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(CanisStatsByEvolutionLevel.CHORDATA.getMovementSpeedForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CanisStatsByEvolutionLevel.CHORDATA.getAttackDamageForLevel(levels));
//            this.getAttribute(Attributes.ARMOR).setBaseValue(CanisStatsByEvolutionLevel.CHORDATA.getArmorForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(CanisStatsByEvolutionLevel.CHORDATA.getAttackKnockbackForLevel(levels));
//            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(CanisStatsByEvolutionLevel.CHORDATA.getKnockbackResistanceForLevel(levels));
//        }
//        if (currentLevel == 2) {
//            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(CanisStatsByEvolutionLevel.KYPHOS.getMaxHealthForLevel(levels));
//            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(CanisStatsByEvolutionLevel.KYPHOS.getMovementSpeedForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CanisStatsByEvolutionLevel.KYPHOS.getAttackDamageForLevel(levels));
//            this.getAttribute(Attributes.ARMOR).setBaseValue(CanisStatsByEvolutionLevel.KYPHOS.getArmorForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(CanisStatsByEvolutionLevel.KYPHOS.getAttackKnockbackForLevel(levels));
//            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(CanisStatsByEvolutionLevel.KYPHOS.getKnockbackResistanceForLevel(levels));
//        }
//        if (currentLevel == 3) {
//            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(CanisStatsByEvolutionLevel.CAVALIER.getMaxHealthForLevel(levels));
//            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(CanisStatsByEvolutionLevel.CAVALIER.getMovementSpeedForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CanisStatsByEvolutionLevel.CAVALIER.getAttackDamageForLevel(levels));
//            this.getAttribute(Attributes.ARMOR).setBaseValue(CanisStatsByEvolutionLevel.CAVALIER.getArmorForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(CanisStatsByEvolutionLevel.CAVALIER.getAttackKnockbackForLevel(levels));
//            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(CanisStatsByEvolutionLevel.CAVALIER.getKnockbackResistanceForLevel(levels));
//        }
//        if (currentLevel == 4) {
//            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(CanisStatsByEvolutionLevel.HOMINI.getMaxHealthForLevel(levels));
//            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(CanisStatsByEvolutionLevel.HOMINI.getMovementSpeedForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CanisStatsByEvolutionLevel.HOMINI.getAttackDamageForLevel(levels));
//            this.getAttribute(Attributes.ARMOR).setBaseValue(CanisStatsByEvolutionLevel.HOMINI.getArmorForLevel(levels));
//            this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(CanisStatsByEvolutionLevel.HOMINI.getAttackKnockbackForLevel(levels));
//            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(CanisStatsByEvolutionLevel.HOMINI.getKnockbackResistanceForLevel(levels));
//        }
//        throw new IllegalArgumentException("Invalid Evolution Level used while trying to set New Attributes for Canis Chordata");
//    }
//    @Override protected int getExperienceReward(Player player) {return 25 + this.level.random.nextInt(5);}
//}