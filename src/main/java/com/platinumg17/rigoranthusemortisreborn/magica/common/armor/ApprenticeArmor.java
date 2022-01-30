package com.platinumg17.rigoranthusemortisreborn.magica.common.armor;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import com.platinumg17.rigoranthusemortisreborn.items.armor.RigoranthusArmorMaterial;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ApprenticeArmor extends MagicArmor {
    public ApprenticeArmor(EquipmentSlot slot) {
        super(RigoranthusArmorMaterial.apprentice, slot, MagicItemsRegistry.defaultItemProperties());
    }

    @Override
    public int getMaxDominionBoost(ItemStack stack) {
        return 40;
    }

    @Override
    public int getDominionRegenBonus(ItemStack stack) {
        return 4;
    }
}