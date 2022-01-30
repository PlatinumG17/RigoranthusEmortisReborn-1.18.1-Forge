package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage;

import com.google.common.collect.Maps;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class CanisRespawnStorage extends SavedData {

    private Map<UUID, CanisRespawnData> respawnDataMap = Maps.newConcurrentMap();

    public CanisRespawnStorage() { }

    public static CanisRespawnStorage get(Level world) {
        if (!(world instanceof ServerLevel)) {throw new RuntimeException("Tried to access canis respawn data from the client. This should not happen... please contact your local Used-Car Salesman to get it taken care of immediately");}

        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);

        DimensionDataStorage storage = overworld.getDataStorage();
        return storage.computeIfAbsent(CanisRespawnStorage::load, CanisRespawnStorage::new, EmortisConstants.STORAGE_CANIS_RESPAWN);
    }
    public Stream<CanisRespawnData> getCani(@Nonnull UUID ownerId) {return this.respawnDataMap.values().stream().filter(data -> ownerId.equals(data.getOwnerId()));}
    public Stream<CanisRespawnData> getCani(@Nonnull String ownerName) {return this.respawnDataMap.values().stream().filter(data -> ownerName.equals(data.getOwnerName()));}

    @Nullable
    public CanisRespawnData getData(UUID uuid) {
        if (this.respawnDataMap.containsKey(uuid)) {
            return this.respawnDataMap.get(uuid);
        }
        return null;
    }

    @Nullable
    public CanisRespawnData remove(UUID uuid) {
        if (this.respawnDataMap.containsKey(uuid)) {
            CanisRespawnData storage = this.respawnDataMap.remove(uuid);
            // Mark dirty so changes are saved
            this.setDirty();
            return storage;
        }
        return null;
    }

    @Nullable
    public CanisRespawnData putData(CanisEntity canisIn) {
        UUID uuid = canisIn.getUUID();
        CanisRespawnData storage = new CanisRespawnData(this, uuid);
        storage.populate(canisIn);
        this.respawnDataMap.put(uuid, storage);
        // Mark dirty so changes are saved
        this.setDirty();
        return storage;
    }
    public Set<UUID> getAllUUID() {return Collections.unmodifiableSet(this.respawnDataMap.keySet());}
    public Collection<CanisRespawnData> getAll() {return Collections.unmodifiableCollection(this.respawnDataMap.values());}

    public static CanisRespawnStorage load(CompoundTag nbt) {
        CanisRespawnStorage store = new CanisRespawnStorage();
        store.respawnDataMap.clear();

        ListTag list = nbt.getList("respawnData", Tag.TAG_COMPOUND);

        for (int i = 0; i < list.size(); ++i) {
            CompoundTag respawnCompound = list.getCompound(i);

            UUID uuid = NBTUtilities.getUniqueId(respawnCompound, "uuid");
            CanisRespawnData respawnData = new CanisRespawnData(store, uuid);
            respawnData.read(respawnCompound);

            if (uuid == null) {
                RigoranthusEmortisReborn.LOGGER.info("Failed to load canis respawn data. Please report to your local Used-Car Salesman... Or, you know.. the Mod Author...");
                RigoranthusEmortisReborn.LOGGER.info(respawnData);
                continue;
            }
            store.respawnDataMap.put(uuid, respawnData);
        }
        return store;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, CanisRespawnData> entry : this.respawnDataMap.entrySet()) {
            CompoundTag respawnCompound = new CompoundTag();
            CanisRespawnData respawnData = entry.getValue();
            NBTUtilities.putUniqueId(respawnCompound, "uuid", entry.getKey());
            respawnData.write(respawnCompound);
            list.add(respawnCompound);
        }
        compound.put("respawnData", list);
        return compound;
    }
}