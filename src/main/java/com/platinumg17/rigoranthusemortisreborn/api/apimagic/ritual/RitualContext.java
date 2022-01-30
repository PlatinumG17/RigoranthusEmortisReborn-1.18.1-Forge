package com.platinumg17.rigoranthusemortisreborn.api.apimagic.ritual;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.NBTUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class RitualContext {
    public int progress;
    public boolean isDone;
    public boolean isStarted;
    public boolean needsDominionToRun; // Marks the last time dominion was consumed or added.
    public List<ItemStack> consumedItems;

    public RitualContext(){
        progress = 0;
        isDone = false;
        this.isStarted = false;
        consumedItems = new ArrayList<>();
        needsDominionToRun = false;
    }

    public void write(CompoundTag tag){
        tag.putInt("progress", progress);
        tag.putBoolean("complete", isDone);
        tag.putBoolean("started", isStarted);
        tag.putBoolean("needsDominion", needsDominionToRun);
        NBTUtil.writeItems(tag,"item_", consumedItems);
    }

    public static RitualContext read(CompoundTag tag){
        RitualContext context = new RitualContext();
        context.progress = tag.getInt("progress");
        context.isDone = tag.getBoolean("complete");
        context.isStarted = tag.getBoolean("started");
        context.consumedItems = NBTUtil.readItems(tag, "item_");
        context.needsDominionToRun = tag.getBoolean("needsDominion");
        return context;
    }
}