package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage;

import com.google.common.collect.Maps;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static net.minecraft.nbt.Tag.TAG_COMPOUND;

public class CanisLocationStorage extends SavedData {

    private Map<UUID, CanisLocationData> locationDataMap = Maps.newConcurrentMap();
    public CanisLocationStorage() { }

    public static CanisLocationStorage get(Level world) {
        if (!(world instanceof ServerLevel)) {
            throw new RuntimeException("Tried to access canis location data from the client. This should not happen...");
        }

        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);

        DimensionDataStorage storage = overworld.getDataStorage();
        return storage.computeIfAbsent(CanisLocationStorage::load, CanisLocationStorage::new, EmortisConstants.STORAGE_CANIS_LOCATION);
    }

    public Stream<CanisLocationData> getCani(LivingEntity owner) {
        UUID ownerId = owner.getUUID();

        return this.locationDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()));
    }

    public Stream<CanisLocationData> getCani(LivingEntity owner, ResourceKey<Level> key) {
        UUID ownerId = owner.getUUID();
        return this.locationDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()))
                .filter(data -> key.equals(data.getDimension()));
    }

    @Nullable
    public CanisLocationData getData(CanisEntity canisIn) {
        return getData(canisIn.getUUID());
    }

    @Nullable
    public CanisLocationData getData(UUID uuid) {
        if (this.locationDataMap.containsKey(uuid)) {
            return this.locationDataMap.get(uuid);
        }
        return null;
    }

    @Nullable
    public CanisLocationData remove(CanisEntity canisIn) {
        return remove(canisIn.getUUID());
    }

    @Nullable
    public CanisLocationData getOrCreateData(CanisEntity canisIn) {
        UUID uuid = canisIn.getUUID();
        return this.locationDataMap.computeIfAbsent(uuid, ($) -> {
            this.setDirty();
            return CanisLocationData.from(this, canisIn);
        });
    }

    @Nullable
    public CanisLocationData remove(UUID uuid) {
        if (this.locationDataMap.containsKey(uuid)) {
            CanisLocationData storage = this.locationDataMap.remove(uuid);
            // Mark dirty so changes are saved
            this.setDirty();
            return storage;
        }
        return null;
    }

    @Nullable
    public CanisLocationData putData(CanisEntity canisIn) {
        UUID uuid = canisIn.getUUID();
        CanisLocationData storage = new CanisLocationData(this, uuid);
        this.locationDataMap.put(uuid, storage);
        // Mark dirty so changes are saved
        this.setDirty();
        return storage;
    }
    public Set<UUID> getAllUUID() {return Collections.unmodifiableSet(this.locationDataMap.keySet());}
    public Collection<CanisLocationData> getAll() {return Collections.unmodifiableCollection(this.locationDataMap.values());}

    public static CanisLocationStorage load(CompoundTag nbt) {
        CanisLocationStorage store = new CanisLocationStorage();
        store.locationDataMap.clear();
        ListTag list = nbt.getList("locationData", TAG_COMPOUND);
        // Old style
        if (list.isEmpty()) {
            list = nbt.getList("canis_locations", TAG_COMPOUND);
        }

        for (int i = 0; i < list.size(); ++i) {
            CompoundTag locationCompound = list.getCompound(i);
            UUID uuid = NBTUtilities.getUniqueId(locationCompound, "uuid");

            // Old style
            if (uuid == null) {
                uuid = NBTUtilities.getUniqueId(locationCompound, "entityId");
            }
            CanisLocationData locationData = new CanisLocationData(store, uuid);
            locationData.read(locationCompound);
            if (uuid == null) {
                RigoranthusEmortisReborn.LOGGER.info("Failed to load canis location data. Please report to mod author...");
                RigoranthusEmortisReborn.LOGGER.info(locationData);
                continue;
            }
            store.locationDataMap.put(uuid, locationData);
        }
        return store;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        ListTag list = new ListTag();
        for (Entry<UUID, CanisLocationData> entry : this.locationDataMap.entrySet()) {
            CompoundTag locationCompound = new CompoundTag();
            CanisLocationData locationData = entry.getValue();
            NBTUtilities.putUniqueId(locationCompound, "uuid", entry.getKey());
            locationData.write(locationCompound);
            list.add(locationCompound);
        }
        compound.put("locationData", list);
        return compound;
    }
}