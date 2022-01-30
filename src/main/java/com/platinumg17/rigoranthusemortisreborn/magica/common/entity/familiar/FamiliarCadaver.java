package com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar;

import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class FamiliarCadaver extends FamiliarEntity {

    public FamiliarCadaver(EntityType<? extends PathfinderMob> ent, Level world) {
        super(ent, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide && level.getGameTime() % 60 == 0 && getOwner() != null){
            getOwner().addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 1, false, false, true));
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(!player.level.isClientSide && player.equals(getOwner())){
            ItemStack stack = player.getItemInHand(hand);
            if(Tags.Items.NUGGETS_GOLD.contains(stack.getItem())){
                stack.shrink(1);
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public PlayState walkPredicate(AnimationEvent event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sundered_cadaver.walking"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.FAMILIAR_CADAVER;
    }




//    public FamiliarCadaver(EntityType<? extends SunderedCadaverEntity> p_i50190_1_, Level p_i50190_2_) {
//        super(ModEntities.FAMILIAR_CADAVER, p_i50190_2_);
//    }
//
//    public FamiliarCadaver(Level p_i50190_2_, LivingEntity owner) {
//        super(RigoranthusEntityTypes.SUNDERED_CADAVER.get(), p_i50190_2_);
//        this.owner = owner;
//        this.limitedLifespan = false;
//        setOwnerId(owner.getUUID());
//        this.moveControl = new MoveHelperController(this);
//    }
//
//    @Override
//    public EntityType<?> getType() {
//        return ModEntities.FAMILIAR_CADAVER;
//    }
//
//    @Nullable
//    public SpawnGroupData finalizeSpawn(IServerLevel worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
////        this.populateDefaultEquipmentSlots(difficultyIn);
////        this.populateDefaultEquipmentEnchantments(difficultyIn);
//        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
//    }
//
////    /**
////     * Gives armor or weapon for entity based on given DifficultyInstance
////     */
////    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
////        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
////        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
////    }
//
//    @Override
//    protected void registerGoals() {
//
//        this.goalSelector.addGoal(0, new SwimGoal(this));
//        this.goalSelector.addGoal(1, new ChargeAttackGoal());
//
//        this.goalSelector.addGoal(9, new LookAtGoal(this, Player.class, 3.0F, 1.0F));
//        this.goalSelector.addGoal(10, new LookAtGoal(this, Mob.class, 8.0F));
//        this.goalSelector.addGoal(2, new FollowSummonerGoal(this, this.owner, 1.0, 6.0f, 3.0f));
//        this.targetSelector.addGoal(1, new CopyOwnerTargetGoal(this));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, true,
//                (entity ) -> (entity instanceof Mob && ((Mob) entity).getTarget() != null &&
//                        ((Mob) entity).getTarget().equals(this.owner)) || (entity instanceof LivingEntity && entity.getKillCredit() != null && entity.getKillCredit().equals(this.owner))
//        ));
//    }
//    protected PathNavigation createNavigation(Level worldIn) {
//        GroundPathNavigation pathNav = new GroundPathNavigation(this, worldIn);
//        pathNav.setCanOpenDoors(false);
//        pathNav.setCanFloat(true);
//        return pathNav;
//    }
//
//    public void setOwner(LivingEntity owner) {
//        this.owner = owner;
//    }
//
//    /**
//     * Called to update the entity's position/logic.
//     */
//    public void tick() {
//        super.tick();
//    }
//
//    @Override
//    public Level getWorld() {
//        return this.level;
//    }
//
//    @Override
//    public PathNavigation getPathNav() {
//        return this.navigation;
//    }
//
//    @Override
//    public Mob getSelfEntity() {
//        return this;
//    }
//
//    public LivingEntity getSummoner() {
//        return this.getOwnerFromID();
//    }
//
//    public LivingEntity getActualOwner() {
//        return owner;
//    }
//
//    class ChargeAttackGoal extends Goal {
//        public ChargeAttackGoal() {
//            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
//        }
//
//        /**
//         * Returns whether the EntityAIBase should begin execution.
//         */
//        public boolean canUse() {
//            if (this.getTarget() != null && !this.getMoveControl().hasWanted() && this.random.nextInt(7) == 0) {
//                return this.distanceToSqr(this.getTarget()) > 4.0D;
//            } else {
//                return false;
//            }
//        }
//
//        /**
//         * Returns whether an in-progress EntityAIBase should continue executing
//         */
//        public boolean canContinueToUse() {
//            return this.getMoveControl().hasWanted() && this.isCharging() && this.getTarget() != null && this.getTarget().isAlive();
//        }
//
//        /**
//         * Execute a one shot task or start executing a continuous task
//         */
//        public void start() {
//            LivingEntity livingentity = this.getTarget();
//            Vector3d vec3d = livingentity.getEyePosition(1.0F);
//            this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.0D);
//            this.setIsCharging(true);
//            this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
//        }
//
//        /**
//         * Reset the task's internal state. Called when this task is interrupted by another one
//         */
//        public void stop() {
//            this.setIsCharging(false);
//        }
//
//        /**
//         * Keep ticking a continuous task that has already been started
//         */
//        public void tick() {
//            LivingEntity livingentity = this.getTarget();
//            if (this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
//                this.doHurtTarget(livingentity);
//                this.setIsCharging(false);
//            } else {
//                double d0 = this.distanceToSqr(livingentity);
//                if (d0 < 9.0D) {
//                    Vector3d vec3d = livingentity.getEyePosition(1.0F);
//                    this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.0D);
//                }
//            }
//
//        }
//    }
//
//    @Override
//    protected int getExperienceReward(Player player) {
//        return 0;
//    }
//
//    /**
//     * (abstract) Protected helper method to read subclass entity data from NBT.
//     */
//    public void readAdditionalSaveData(CompoundTag compound) {
//        super.readAdditionalSaveData(compound);
//        if (compound.contains("BoundX")) {
//            this.boundOrigin = new BlockPos(compound.getInt("BoundX"), compound.getInt("BoundY"), compound.getInt("BoundZ"));
//        }
//
//        if (compound.contains("LifeTicks")) {
//            this.setLimitedLife(compound.getInt("LifeTicks"));
//        }
//        UUID s;
//        if (compound.contains("OwnerUUID", 8)) {
//            s = compound.getUUID("OwnerUUID");
//        } else {
//            String s1 = compound.getString("Owner");
//            s = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), s1);
//        }
//
//        if (s != null) {
//            try {
//                this.setOwnerId(s);
//
//            } catch (Throwable ignored) {
//            }
//        }
//
//    }
//
//    public LivingEntity getOwnerFromID(){
//        try {
//            UUID uuid = this.getOwnerId();
//
//            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
//        } catch (IllegalArgumentException var2) {
//            return null;
//        }
//    }
//
//
//    @Nullable
//    public UUID getOwnerId() {
//        return this.entityData.get(OWNER_UNIQUE_ID).orElse(null);
//    }
//
//    public void setOwnerId(@Nullable UUID p_184754_1_) {
//        this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
//    }
//
//
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
//    }
//
//    public void addAdditionalSaveData(CompoundTag compound) {
//        super.addAdditionalSaveData(compound);
//        if (this.boundOrigin != null) {
//            compound.putInt("BoundX", this.boundOrigin.getX());
//            compound.putInt("BoundY", this.boundOrigin.getY());
//            compound.putInt("BoundZ", this.boundOrigin.getZ());
//        }
//
//        if (this.limitedLifespan) {
//            compound.putInt("LifeTicks", this.limitedLifeTicks);
//        }
//        if (this.getOwnerId() == null) {
//            compound.putUUID("OwnerUUID", Util.NIL_UUID);
//        } else {
//            compound.putUUID("OwnerUUID", this.getOwnerId());
//        }
//
//    }
//
//
//    @Override
//    public void die(DamageSource cause) {
//        super.die(cause);
//        onSummonDeath(level, cause, false);
//    }
//
//    @Override
//    public int getTicksLeft() {
//        return limitedLifeTicks;
//    }
//
//    @Override
//    public void setTicksLeft(int ticks) {
//        this.limitedLifeTicks = ticks;
//    }
//
//    @Nullable
//    @Override
//    public UUID getOwnerID() {
//        return null;
//    }
//
//    @Override
//    public void setOwnerID(UUID uuid) {
//
//    }
//
//    class MoveHelperController extends MovementController {
//        public MoveHelperController(SunderedCadaverEntity vex) {
//            super(vex);
//        }
//
//        public void tick() {
//            if (this.operation == MovementController.Action.MOVE_TO) {
//                Vector3d vec3d = new Vector3d(this.wantedX - this.getX(), this.wantedY - this.getY(), this.wantedZ - this.getZ());
//                double d0 = vec3d.length();
//                if (d0 < this.getBoundingBox().getSize()) {
//                    this.operation = MovementController.Action.WAIT;
//                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
//                } else {
//                    this.setDeltaMovement(this.getDeltaMovement().add(vec3d.scale(this.speedModifier * 0.05D / d0)));
//                    if (this.getTarget() == null) {
//                        Vector3d vec3d1 = this.getDeltaMovement();
//                        this.yRot = -((float) Mth.atan2(vec3d1.x, vec3d1.z)) * (180F / (float)Math.PI);
//                        this.yBodyRot = this.yRot;
//                    } else {
//                        double d2 = this.getTarget().getX() - this.getX();
//                        double d1 = this.getTarget().getZ() - this.getZ();
//                        this.yRot = -((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI);
//                        this.yBodyRot = this.yRot;
//                    }
//                }
//
//            }
//        }
//    }
//
//    class CopyOwnerTargetGoal extends TargetGoal {
//        private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
//
//        public CopyOwnerTargetGoal(PathfinderMob creature) {
//            super(creature, false);
//        }
//
//        /**
//         * Returns whether the EntityAIBase should begin execution.
//         */
//        public boolean canUse() {
//            return this.owner != null && this.owner.getLastHurtMob() != null;
//        }
//
//        /**
//         * Execute a one shot task or start executing a continuous task
//         */
//        public void start() {
//            this.setTarget(this.owner.getLastHurtMob());
//            super.start();
//        }
//    }
//
//    class MoveRandomGoal extends Goal {
//        public MoveRandomGoal() {
//            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
//        }
//
//        /**
//         * Returns whether the EntityAIBase should begin execution.
//         */
//        public boolean canUse() {
//            return !this.getMoveControl().hasWanted() && this.random.nextInt(7) == 0;
//        }
//
//        /**
//         * Returns whether an in-progress EntityAIBase should continue executing
//         */
//        public boolean canContinueToUse() {
//            return false;
//        }
//
//        /**
//         * Keep ticking a continuous task that has already been started
//         */
//        public void tick() {
//            BlockPos blockpos = this.getBoundOrigin();
//            if (blockpos == null) {
//                blockpos = new BlockPos(this.blockPosition());
//            }
//
//            for(int i = 0; i < 3; ++i) {
//                BlockPos blockpos1 = blockpos.offset(this.random.nextInt(15) - 7, this.random.nextInt(11) - 5, this.random.nextInt(15) - 7);
//                if (this.level.isEmptyBlock(blockpos1)) {
//                    this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
//                    if (this.getTarget() == null) {
//                        this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
//                    }
//                    break;
//                }
//            }
//        }
//    }
}