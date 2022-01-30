package com.platinumg17.rigoranthusemortisreborn.magica.common.armor;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import com.platinumg17.rigoranthusemortisreborn.items.armor.RigoranthusArmorMaterial;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class MasterArmor  extends MagicArmor{
    public MasterArmor(EquipmentSlot slot) {
        super(RigoranthusArmorMaterial.master, slot, MagicItemsRegistry.defaultItemProperties());
    }

    @Override
    public int getMaxDominionBoost(ItemStack stack) {
        return 80;
    }

    @Override
    public int getDominionRegenBonus(ItemStack stack) {
        return 6;
    }
}