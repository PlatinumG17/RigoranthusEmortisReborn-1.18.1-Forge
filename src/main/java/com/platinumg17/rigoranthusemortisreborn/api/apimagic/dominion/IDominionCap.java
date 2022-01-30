package com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IDominionCap extends INBTSerializable<CompoundTag> {

    double getCurrentDominion();

    int getMaxDominion();

    void setMaxDominion(int max);

    double setDominion(final double mana);

    double addDominion(final double manaToAdd);

    double removeDominion(final double manaToRemove);

    default int getGlyphBonus(){
        return 0;
    }

    default int getBookTier(){
        return 0;
    }

    default void setGlyphBonus(int bonus){}

    default void setBookTier(int tier){}
}