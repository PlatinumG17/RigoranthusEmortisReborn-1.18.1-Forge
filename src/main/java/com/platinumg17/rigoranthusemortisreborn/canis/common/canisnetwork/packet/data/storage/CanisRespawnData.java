package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage;

import com.google.common.collect.Lists;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class CanisRespawnData implements ICanisData {

    private final CanisRespawnStorage storage;
    private final UUID uuid;
    private CompoundTag data;

    //TODO Make it list you can only add too
    private static final List<String> TAGS_TO_REMOVE = Lists.newArrayList(
            "Pos", "Health", "Motion", "Rotation", "FallDistance", "Fire", "Air", "OnGround",
            "Dimension", "PortalCooldown", "Passengers", "Leash", "InLove", "Leash", "HurtTime",
            "HurtByTimestamp", "DeathTime", "AbsorptionAmount", "FallFlying", "Brain", "Sitting"); // Remove canis mode

    protected CanisRespawnData(CanisRespawnStorage storageIn, UUID uuid) {
        this.storage = storageIn;
        this.uuid = uuid;
    }

    @Override
    public UUID getCanisId() {
        return this.uuid;
    }

    @Override
    public String getCanisName() {
        Component name = NBTUtilities.getTextComponent(this.data, "CustomName");
        return name == null ? "" : name.getString();
    }

    @Override
    public UUID getOwnerId() {
        String str = data.getString("OwnerUUID");
        return "".equals(str) ? null : UUID.fromString(str);
    }

    @Override
    public String getOwnerName() {
        Component name = NBTUtilities.getTextComponent(this.data, "lastKnownOwnerName");
        return name == null ? "" : name.getString();
    }

    public void populate(CanisEntity canisIn) {
        this.data = new CompoundTag();
        canisIn.saveWithoutId(this.data);

        // Remove tags that don't need to be saved
        for (String tag : TAGS_TO_REMOVE) {
            this.data.remove(tag);
        }
        this.data.remove("UUID");
        this.data.remove("LoveCause");
    }

    @Nullable
    public CanisEntity respawn(ServerLevel worldIn, Player playerIn, BlockPos pos) {
        CanisEntity canis = SpecializedEntityTypes.CANIS.get().spawn(worldIn, null, null, playerIn, pos, MobSpawnType.TRIGGERED, true, false);
        // Failed for some reason
        if (canis == null) {
            return null;
        }
        CompoundTag compoundnbt = canis.saveWithoutId(new CompoundTag());
        UUID uuid = canis.getUUID();
        compoundnbt.merge(this.data);
        canis.setUUID(uuid);
        canis.load(compoundnbt);

        canis.setMode(EnumMode.DOCILE);
        canis.setOrderedToSit(true);

        return canis;
    }

    public void read(CompoundTag compound) {
        this.data = compound.getCompound("data");
    }

    public CompoundTag write(CompoundTag compound) {
        compound.put("data", this.data);
        return compound;
    }
}