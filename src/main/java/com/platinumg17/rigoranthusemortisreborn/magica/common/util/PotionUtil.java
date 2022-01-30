package com.platinumg17.rigoranthusemortisreborn.magica.common.util;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.resources.ResourceLocation;

public class PotionUtil {
    public static void addPotionToTag(Potion potionIn, CompoundTag tag){
        ResourceLocation resourcelocation = Registry.POTION.getKey(potionIn);
        if (potionIn == Potions.EMPTY) {
            if(tag.contains("Potion")) {
                tag.remove("Potion");
            }
        } else {
            tag.putString("Potion", resourcelocation.toString());
        }
    }
}