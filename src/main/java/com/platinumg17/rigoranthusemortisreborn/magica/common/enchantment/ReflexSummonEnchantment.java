package com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ReflexSummonEnchantment extends Enchantment {
    protected ReflexSummonEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS});
        setRegistryName(EmortisConstants.MOD_ID, "reflex_summon");
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 1+11*(enchantmentLevel-1);
    }

//    @Override
//    public int getMaxCost(int enchantmentLevel) {
//        return 10;
//    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

//    @Override
//    public boolean canEnchant(ItemStack stack) {
//        return true;
//    }
//
//    @Override
//    public boolean canApplyAtEnchantingTable(ItemStack stack) {
//        return true;
//    }
//
//    @Override
//    public boolean isAllowedOnBooks() {
//        return false;
//    }
}
