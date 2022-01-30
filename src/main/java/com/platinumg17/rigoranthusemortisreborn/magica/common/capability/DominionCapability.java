package com.platinumg17.rigoranthusemortisreborn.magica.common.capability;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionCap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class DominionCapability implements IDominionCap {

    private final LivingEntity livingEntity;

    private double dominion;

    private int maxDominion;

    private int glyphBonus;

    private int bookTier;


    public DominionCapability(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public double getCurrentDominion() {
        return dominion;
    }

    @Override
    public int getMaxDominion() {
        return maxDominion;
    }

    @Override
    public void setMaxDominion(int maxDominion) {
        this.maxDominion = maxDominion;
    }

    @Override
    public double setDominion(double dominion) {
        if(dominion > getMaxDominion()){
            this.dominion = getMaxDominion();
        }else if(dominion < 0){
            this.dominion = 0;
        }else {
            this.dominion = dominion;
        }
        return this.getCurrentDominion();
    }

    @Override
    public double addDominion(double dominionToAdd) {
        this.setDominion(this.getCurrentDominion() + dominionToAdd);
        return this.getCurrentDominion();
    }

    @Override
    public double removeDominion(double dominionToRemove) {
        if(dominionToRemove < 0)
            dominionToRemove = 0;
        this.setDominion(this.getCurrentDominion() - dominionToRemove);
        return this.getCurrentDominion();
    }

    @Override
    public int getGlyphBonus(){
        return glyphBonus;
    }

    @Override
    public void setGlyphBonus(int glyphBonus){
        this.glyphBonus = glyphBonus;
    }

    @Override
    public int getBookTier(){
        return bookTier;
    }

    @Override
    public void setBookTier(int bookTier){
        this.bookTier = bookTier;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("current", getCurrentDominion());
        tag.putInt("max", getMaxDominion());
        tag.putInt("glyph", getGlyphBonus());
        tag.putInt("book_tier", getBookTier());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setMaxDominion(tag.getInt("max"));
        setDominion(tag.getDouble("current"));
        setBookTier(tag.getInt("book_tier"));
        setGlyphBonus(tag.getInt("glyph"));
    }
}