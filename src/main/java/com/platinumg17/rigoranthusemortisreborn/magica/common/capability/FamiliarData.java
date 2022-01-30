package com.platinumg17.rigoranthusemortisreborn.magica.common.capability;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.IFamiliar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;


public class FamiliarData {

    public AbstractFamiliarHolder familiarHolder;
    CompoundTag entityTag;

    public FamiliarData(String entityID){
        this.familiarHolder = RigoranthusEmortisRebornAPI.getInstance().getFamiliarHolderMap().get(entityID);
        this.entityTag = new CompoundTag();
    }

    public FamiliarData(CompoundTag tag){
        this.entityTag = tag.contains("entityTag") ? tag.getCompound("entityTag") : new CompoundTag();
        this.familiarHolder = RigoranthusEmortisRebornAPI.getInstance().getFamiliarHolderMap().getOrDefault(tag.getString("familiar"),RigoranthusEmortisRebornAPI.getInstance().getFamiliarHolderMap().get("wixie"));
    }

    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putString("familiar", familiarHolder.id);
        tag.put("entityTag", entityTag);
        return tag;
    }

    public IFamiliar getEntity(Level level){
        return familiarHolder.getSummonEntity(level, entityTag);
    }
}