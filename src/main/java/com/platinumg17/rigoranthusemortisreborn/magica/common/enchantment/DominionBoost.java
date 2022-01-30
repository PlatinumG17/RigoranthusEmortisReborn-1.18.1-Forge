package com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;

public class DominionBoost extends Enchantment {
    protected DominionBoost() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS});
        setRegistryName(EmortisConstants.MOD_ID, "dominion_boost");
    }
    @Override
    public int getMinCost(int enchantmentLevel) {
        return 1+11*(enchantmentLevel-1);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
