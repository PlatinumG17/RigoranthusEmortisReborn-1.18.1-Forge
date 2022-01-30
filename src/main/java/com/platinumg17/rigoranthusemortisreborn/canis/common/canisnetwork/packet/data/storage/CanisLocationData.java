package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumGender;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.WorldUtil;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class CanisLocationData implements ICanisData {

    private final CanisLocationStorage storage;
    private final UUID uuid;
    private @Nullable UUID ownerId;

    // Location data
    private @Nullable Vec3 position;
    private @Nullable ResourceKey<Level> dimension;

    // Other saved data
    private @Nullable Component name;
    private @Nullable Component ownerName;
    private @Nullable
    EnumGender gender;
//    private boolean hasRadarCollar;

    // Cached objects
    private CanisEntity canis;
    private LivingEntity owner;

    protected CanisLocationData(CanisLocationStorage storageIn, UUID uuid) {
        this.storage = storageIn;
        this.uuid = uuid;
    }

    @Override
    public UUID getCanisId() {
        return this.uuid;
    }

    @Override
    @Nullable
    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Override
    public String getCanisName() {
        return this.name == null ? "" : this.name.getString();
    }

    @Override
    public String getOwnerName() {
        return this.ownerName == null ? "" : this.ownerName.getString();
    }

    public void populate(CanisEntity canisIn) {this.update(canisIn);}

    public void update(CanisEntity canisIn) {
        this.ownerId = canisIn.getOwnerUUID();
        this.position = canisIn.position;
        this.dimension = canisIn.level.dimension();

        this.name = canisIn.getName();
        this.ownerName = canisIn.getOwnersName().orElse(null);
        this.gender = canisIn.getGender();
//        this.hasRadarCollar = canisIn.getAccoutrement(CanisAccouterments.RADIO_BAND.get()).isPresent();

        this.canis = canisIn;
        this.storage.setDirty();
    }


    public void read(CompoundTag compound) {
        this.ownerId = NBTUtilities.getUniqueId(compound, "ownerId");
        this.position = NBTUtilities.getVector3d(compound);
        this.dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, NBTUtilities.getResourceLocation(compound, "dimension"));
        this.name = NBTUtilities.getTextComponent(compound, "name_text_component");
        if (compound.contains("gender", Tag.TAG_STRING)) {
            this.gender = EnumGender.bySaveName(compound.getString("gender"));
        }
//        this.hasRadarCollar = compound.getBoolean("collar");
    }

    public CompoundTag write(CompoundTag compound) {
        NBTUtilities.putUniqueId(compound, "ownerId", this.ownerId);
        NBTUtilities.putVector3d(compound, this.position);
        NBTUtilities.putResourceLocation(compound, "dimension", this.dimension.location());
        NBTUtilities.putTextComponent(compound, "name_text_component", this.name);
        if (this.gender != null) {
            compound.putString("gender", this.gender.getSaveName());
        }
//        compound.putBoolean("collar", this.hasRadarCollar);
        return compound;
    }

    public static CanisLocationData from(CanisLocationStorage storageIn, CanisEntity canisIn) {
        CanisLocationData locationData = new CanisLocationData(storageIn, canisIn.getUUID());
        locationData.populate(canisIn);
        return locationData;
    }

    @Nullable
    public Optional<LivingEntity> getOwner(@Nullable Level worldIn) {
        if (worldIn == null) {
            return Optional.ofNullable(this.owner);
        }
        MinecraftServer server = worldIn.getServer();
        if (server == null) {
            throw new IllegalArgumentException("worldIn must be of ServerLevel");
        }
        for (ServerLevel world : server.getAllLevels()) {
            LivingEntity possibleOwner = WorldUtil.getCachedEntity(world, LivingEntity.class, this.owner, this.uuid);
            if (possibleOwner != null) {
                this.owner = possibleOwner;
                return Optional.of(this.owner);
            }
        }
        this.owner = null;
        return Optional.empty();
    }

    @Nullable
    public Optional<CanisEntity> getCanis(@Nullable Level worldIn) {
        if (worldIn == null) {
            return Optional.ofNullable(this.canis);
        }
        MinecraftServer server = worldIn.getServer();
        if (server == null) {
            throw new IllegalArgumentException("worldIn must be of ServerLevel");
        }
        for (ServerLevel world : server.getAllLevels()) {
            CanisEntity possibleCanis = WorldUtil.getCachedEntity(world, CanisEntity.class, this.canis, this.uuid);
            if (possibleCanis != null) {
                this.canis = possibleCanis;
                return Optional.of(this.canis);
            }
        }
        this.canis = null;
        return Optional.empty();
    }
    public boolean shouldDisplay(Level worldIn, Player playerIn, InteractionHand handIn) {return /*this.hasRadarCollar || */playerIn.isCreative() /*|| playerIn.getItemInHand(handIn).getItem() == CanisItems.CREATIVE_RADAR.get()*/;}
    @Nullable public Component getName(@Nullable Level worldIn) {return this.getCanis(worldIn).map(CanisEntity::getDisplayName).orElse(this.name);}
    @Nullable public Vec3 getPos(@Nullable ServerLevel worldIn) {return this.getCanis(worldIn).map(CanisEntity::position).orElse(this.position);}
    @Nullable public Vec3 getPos() {return this.position;}
    @Nullable public ResourceKey<Level> getDimension() {return this.dimension;}
    @Override public String toString() {return "CanisLocationData [uuid=" + uuid + ", owner=" + ownerId + ", position=" + position + ", dimension=" + dimension + ", name=" + name + ", gender=" + gender + /*", hasRadarCollar=" + hasRadarCollar + */"]";}
}