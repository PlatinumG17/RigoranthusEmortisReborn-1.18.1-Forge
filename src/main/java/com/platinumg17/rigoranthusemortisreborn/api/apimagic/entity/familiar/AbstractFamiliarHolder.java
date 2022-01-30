package com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public abstract class AbstractFamiliarHolder {

    public Predicate<Entity> isEntity;
    public String id;

    public AbstractFamiliarHolder(String id, Predicate<Entity> isConversionEntity){
        this.id = id;
        this.isEntity = isConversionEntity;
    }
    public abstract IFamiliar getSummonEntity(Level world, CompoundTag tag);

    public ItemStack getOutputItem(){
        return new ItemStack(RigoranthusEmortisRebornAPI.getInstance().getFamiliarItem(getId()));
    }

    public String getImagePath(){
        return "familiar_" + id + ".png";
    }

    public String getId(){
        return this.id;
    }

    public TranslatableComponent getLangDescription(){
        return new TranslatableComponent("rigoranthusemortisreborn.familiar_desc." + this.id);
    }

    public TranslatableComponent getLangName(){
        return new TranslatableComponent("rigoranthusemortisreborn.familiar_name." + this.id);
    }

    public String getEntityKey(){
        return this.id;
    }
    public String getBookName(){
        return "";
    }
    public String getBookDescription(){
        return "";
    }
}