package com.platinumg17.rigoranthusemortisreborn.magica.common.entity;

import net.minecraft.client.multiplayer.PlayerInfo;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.ISummon;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class EntityEminentialProjection extends PathfinderMob implements ISummon {
    private PlayerInfo playerInfo;
    public int ticksLeft;
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(EntityEminentialProjection.class, EntityDataSerializers.OPTIONAL_UUID);

    public EntityEminentialProjection(EntityType<? extends PathfinderMob> entity, Level worldIn) {
        super(entity, worldIn);
    }

    public EntityEminentialProjection(Level world){
        super(ModEntities.EMINENTIAL_ENTITY, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID, Optional.of(Util.NIL_UUID));
    }
    @Override public Iterable<ItemStack> getArmorSlots() {return new ArrayList<>();}
    @Override public boolean isSpectator() {return false;}

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide){
            if(level.getGameTime() % 10 == 0 && level.getPlayerByUUID(getOwnerID()) == null){
                ParticleUtil.spawnPoof((ServerLevel) level, blockPosition());
                this.remove(RemovalReason.DISCARDED);
                onSummonDeath(level, null, false);
                return;
            }

            ticksLeft--;
            if(ticksLeft <= 0) {
                ParticleUtil.spawnPoof((ServerLevel) level, blockPosition());
                this.remove(RemovalReason.DISCARDED);
                onSummonDeath(level, null, true);
            }
        }
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        onSummonDeath(level, cause, false);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if(!level.isClientSide)
            return ItemStack.EMPTY;
        return level.getPlayerByUUID(getOwnerID()) != null ? level.getPlayerByUUID(getOwnerID()).getItemBySlot(slot) : ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slotType, ItemStack stack) { }

    public ResourceLocation getSkinTextureLocation() {
        PlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo == null ? DefaultPlayerSkin.getDefaultSkin(getOwnerID()) : networkplayerinfo.getSkinLocation();
    }

    @Nullable
    protected PlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(getOwnerID());
        }
        return this.playerInfo;
    }
    public Component getName() {
        return this.level.getPlayerByUUID(getOwnerID()) == null ? new TextComponent("") : this.level.getPlayerByUUID(getOwnerID()).getName();
    }
    public Component getDisplayName() {
        MutableComponent iformattabletextcomponent = new TextComponent("");
        iformattabletextcomponent = iformattabletextcomponent.append(PlayerTeam.formatNameForTeam(this.getTeam(), this.getName()));
        return iformattabletextcomponent;
    }
    @Override public HumanoidArm getMainArm() {return HumanoidArm.RIGHT;}

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("left", ticksLeft);
        writeOwner(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.ticksLeft = tag.getInt("left");
        if(getOwnerID() != null)
            setOwnerID(tag.getUUID("owner"));
    }
    @Override public int getTicksLeft() {return ticksLeft;}
    @Override public void setTicksLeft(int ticks) {this.ticksLeft = ticks;}
    @Nullable @Override public UUID getOwnerID() {return !this.getEntityData().get(OWNER_UUID).isPresent() ? this.getUUID() : this.getEntityData().get(OWNER_UUID).get();}
    @Override public void setOwnerID(UUID uuid) {this.getEntityData().set(OWNER_UUID, Optional.ofNullable(uuid));}
}