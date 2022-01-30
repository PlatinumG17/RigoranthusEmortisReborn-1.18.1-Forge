package com.platinumg17.rigoranthusemortisreborn.canis.common.block.tileentity;

import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage.CanisLocationData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisTileEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage.CanisLocationStorage;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.NBTUtilities;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.WorldUtil;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.IBeddingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import javax.annotation.Nullable;
import java.util.UUID;

public class CanisBedTileEntity extends PlacedTileEntity {

    private ICasingMaterial casingType = null;
    private IBeddingMaterial beddingType = null;

    public static ModelProperty<ICasingMaterial> CASING = new ModelProperty<>();
    public static ModelProperty<IBeddingMaterial> BEDDING = new ModelProperty<>();
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    private @Deprecated @Nullable CanisEntity canis;
    private @Nullable UUID canisUUID;

    private @Nullable Component name;
    private @Nullable Component ownerName;

    public CanisBedTileEntity(BlockPos pos, BlockState state) {
        super(CanisTileEntityTypes.CANIS_BED.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.casingType = NBTUtilities.getRegistryValue(compound, "casingId", RigoranthusEmortisRebornAPI.CASING_MATERIAL);
        this.beddingType = NBTUtilities.getRegistryValue(compound, "beddingId", RigoranthusEmortisRebornAPI.BEDDING_MATERIAL);
        this.canisUUID = NBTUtilities.getUniqueId(compound, "ownerId");
        this.name = NBTUtilities.getTextComponent(compound, "name");
        this.ownerName = NBTUtilities.getTextComponent(compound, "ownerName");
        this.requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        NBTUtilities.putRegistryValue(compound, "casingId", this.casingType);
        NBTUtilities.putRegistryValue(compound, "beddingId", this.beddingType);
        NBTUtilities.putUniqueId(compound, "ownerId", this.canisUUID);
        NBTUtilities.putTextComponent(compound, "name", this.name);
        NBTUtilities.putTextComponent(compound, "ownerName", this.ownerName);
    }

    public void setCasing(ICasingMaterial casingType) {
        this.casingType = casingType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

    public void setBedding(IBeddingMaterial beddingType) {
        this.beddingType = beddingType;
        this.setChanged();
        this.requestModelDataUpdate();
    }
    public ICasingMaterial getCasing() {return this.casingType;}
    public IBeddingMaterial getBedding() {return this.beddingType;}

    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
            .withInitial(CASING, this.casingType)
            .withInitial(BEDDING, this.beddingType)
            .withInitial(FACING, Direction.NORTH)
            .build();
    }

    public void setOwner(@Nullable CanisEntity owner) {
        this.setOwner(owner == null ? null : owner.getUUID());
        this.canis = owner;
    }

    public void setOwner(@Nullable UUID owner) {
        this.canis = null;
        this.canisUUID = owner;
        this.setChanged();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.canisUUID;
    }

    @Nullable
    public CanisEntity getOwner() {
        return WorldUtil.getCachedEntity(this.level, CanisEntity.class, this.canis, this.canisUUID);
    }

    @Nullable
    public Component getBedName() {
        return this.name;
    }

    @Nullable
    public Component getOwnerName() {
        if (this.canisUUID == null || this.level == null) { return null; }
        CanisLocationData locData = CanisLocationStorage
                .get(this.level)
                .getData(this.canisUUID);
        if (locData != null) {
            Component text = locData.getName(this.level);
            if (text != null) {
                this.ownerName = name;
            }
        }
        return this.ownerName;
    }

    public boolean shouldDisplayName(LivingEntity camera) {
        return true;
    }

    public void setBedName(@Nullable Component nameIn) {
        this.name = nameIn;
        this.setChanged();
    }
}