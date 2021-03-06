package com.platinumg17.rigoranthusemortisreborn.entity;

import com.mojang.math.Vector3d;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.core.init.network.DelphicBloomPacket;
import com.platinumg17.rigoranthusemortisreborn.core.init.network.REPacketHandler;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import java.util.Collections;

public class DelphicBloomEntity extends LivingEntity implements IAnimatable, IEntityAdditionalSpawnData {
    // Animation lengths
    private static final int OPENING_LENGTH = 120;     //   6       sec open animation   *  20 ticks/sec            = 120
    private static final int OPEN_IDLE_LENGTH = 320;   //   4       sec idle animation   *  4 loops * 20 ticks/sec  = 320
    private static final int VANISHING_LENGTH = 13;    //   0.65    sec vanish animation *  20 ticks/sec            = 13

    // Animation start times
    private static final int IDLE_TIME = -1;
    private static final int OPEN_START = 0;
    private static final int OPEN_IDLE_START = OPEN_START + OPENING_LENGTH;
    private static final int VANISH_START = OPEN_IDLE_START + OPEN_IDLE_LENGTH;
    private static final int ANIMATION_END = VANISH_START + VANISHING_LENGTH;

    private final AnimationFactory factory = new AnimationFactory(this);

    //Only used server-side. Used to track the flower state and the progression of the animation
    private int eventTimer = IDLE_TIME;

    public static final String REGROW = "rigoranthusemortisreborn.delphicbloomentity.regrow";

    @Nonnull
    private Animation animation = Animation.IDLE;

    public DelphicBloomEntity(EntityType<? extends DelphicBloomEntity> type, Level worldIn) {
        super(type, worldIn);

        setInvulnerable(true);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(animation.animationName, true));

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {return this.factory;}

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(isAlive() && !player.isShiftKeyDown() && animation == Animation.IDLE) {
            startDelphicBloomAnimation();
            return InteractionResult.SUCCESS;
        }
        if(isAlive() && animation == Animation.EMPTY) {
            ItemStack itemstack = player.getItemInHand(hand);

            if(player.distanceToSqr(this) < 36 && itemstack.getItem() == Items.BONE_MEAL && player.isCreative()) {
                restoreFromBonemeal();
            }
            else if(level.isClientSide && player.distanceToSqr(this) < 36) {
                player.sendMessage(new TranslatableComponent(REGROW), Util.NIL_UUID);
            }
            return InteractionResult.SUCCESS;
        } else
            return super.interact(player, hand);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(!level.isClientSide) {
            if(animation != Animation.IDLE)
                setEventTimer(eventTimer + 1);

            if(eventTimer == OPEN_IDLE_START)
                spawnLoot();
            else
//                if(eventTimer >= Config.delphicRestorationTime.get() * 20) //600(default) seconds from animation start to flower restoration
                setEventTimer(IDLE_TIME);
        }
    }

    private void startDelphicBloomAnimation() {
        if(!level.isClientSide) {
            setEventTimer(OPEN_START);

            Vec3 posVec = position;
            level.playSound(null, posVec.x(), posVec.y(), posVec.z(), SoundEvents.COMPOSTER_READY, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }

    private void restoreFromBonemeal() {
        if(!level.isClientSide)
            setEventTimer(IDLE_TIME);

        Vec3 posVec = position;
        for(int i = 0; i < 10; i++)
            this.level.addParticle(ParticleTypes.COMPOSTER, posVec.x, posVec.y + 0.5, posVec.z, 0.5 - random.nextDouble(), 0.5 - random.nextDouble(), 0.5 - random.nextDouble());
    }

    private void setEventTimer(int time) {
        if(level.isClientSide)
            throw new IllegalStateException("Shouldn't call setEventTimer client-side!");

        eventTimer = time;

        Animation newAnimation = animationFromEventTimer();
        if(newAnimation != animation)
            updateAndSendAnimation(newAnimation);
    }

    private Animation animationFromEventTimer() {
        if(eventTimer >= ANIMATION_END)
            return Animation.EMPTY;
        else if(eventTimer >= VANISH_START)
            return Animation.VANISH; //TODO Delphic Bloom petals may spaz out underneath the petals for just a tick here
        else if(eventTimer >= OPEN_IDLE_START)
            return Animation.OPEN_IDLE;
        else if(eventTimer >= OPEN_START)
            return Animation.OPEN;
        else
            return Animation.IDLE; //TODO relevant to all animation steps but looping of animations is choppy
    }

    protected void updateAndSendAnimation(Animation animation) {
        this.animation = animation;
        DelphicBloomPacket packet = DelphicBloomPacket.createPacket(this, animation); //this packet allows information to be exchanged between server and client where one side cant access the other easily or reliably
        REPacketHandler.sendToTracking(packet, this);
    }

    protected void spawnLoot() {
        Level worldIn = this.level;
        Vec3 posVec = this.position;

        ItemEntity blightIchorItemEntity = new ItemEntity(worldIn, posVec.x(), posVec.y() + 1D, posVec.z(), new ItemStack(ItemInit.BLIGHT_ICHOR.get(), 1));
        worldIn.addFreshEntity(blightIchorItemEntity);

        ItemEntity psyglyphicScriptItemEntity = new ItemEntity(worldIn, posVec.x(), posVec.y() + 1D, posVec.z(), new ItemStack(ItemInit.PSYGLYPHIC_SCRIPT.get(), 1));
        worldIn.addFreshEntity(psyglyphicScriptItemEntity);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("EventTimer", eventTimer);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if(compound.contains("EventTimer", Tag.TAG_ANY_NUMERIC)) {
            eventTimer = compound.getInt("EventTimer");
            animation = animationFromEventTimer();
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(animation.ordinal());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        animation = Animation.values()[additionalData.readInt()];
    }

    @Override
    public Packet<?> getAddEntityPacket() {return NetworkHooks.getEntitySpawningPacket(this);}

    public void setAnimationFromPacket(Animation newAnimation)
    {
        if(level.isClientSide) {
            animation = newAnimation;
            if(animation == Animation.IDLE)
                addRestoreEffects();
            if(animation == Animation.OPEN_IDLE)
                addLootSpawnEffects();
        }
    }

    protected void addRestoreEffects() {
        Vec3 posVec = this.position;
        this.level.addParticle(ParticleTypes.FLASH, posVec.x, posVec.y + 0.5D, posVec.z, 0.0D, 0.0D, 0.0D);
        this.level.playLocalSound(posVec.x(), posVec.y(), posVec.z(), SoundEvents.BEEHIVE_EXIT, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }

    protected void addLootSpawnEffects() {
        Vec3 posVec = this.position;
        this.level.addParticle(ParticleTypes.FLASH, posVec.x, posVec.y + 0.5D, posVec.z, 0.0D, 0.0D, 0.0D);
        this.level.playLocalSound(posVec.x(), posVec.y(), posVec.z(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.3F, false);
        this.level.playLocalSound(posVec.x(), posVec.y(), posVec.z(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 0.7F, false);
        this.level.playLocalSound(posVec.x(), posVec.y(), posVec.z(), SoundEvents.BOAT_PADDLE_LAND, SoundSource.NEUTRAL, 2.0F, 2.0F, false);
    }

//    @Override protected boolean isMovementNoisy() {return false;}
//    @Override public void move(MoverType typeIn, Vector3d pos) {}
    @Override public Iterable<ItemStack> getArmorSlots() {return Collections.emptyList();}
    @Override public ItemStack getItemBySlot(EquipmentSlot slotIn) {return ItemStack.EMPTY;}
    @Override public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {}
    @Override public HumanoidArm getMainArm() {return HumanoidArm.RIGHT;}

    public enum Animation {
        IDLE("bloom.idle"),
        OPEN("bloom.open"),
        OPEN_IDLE("bloom.open.idle"),
        VANISH("bloom.vanish"),
        EMPTY("bloom.empty");

        private final String animationName;

        Animation(String animationName) {
            this.animationName = animationName;
        }
    }
}
