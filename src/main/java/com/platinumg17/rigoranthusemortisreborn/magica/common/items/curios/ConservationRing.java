package com.platinumg17.rigoranthusemortisreborn.magica.common.items.curios;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.RigoranthusEmortisRebornCurio;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionEquipment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class ConservationRing extends RigoranthusEmortisRebornCurio implements IDominionEquipment {

    public ConservationRing(String registry) {
        super(registry);
    }

    @Override
    public void wearableTick(LivingEntity wearer) { }

    public abstract int getDominionDiscount();

    @Override
    public int getMaxDominionBoost(ItemStack stack) {
        return 10;
    }

    @Override
    public int getDominionRegenBonus(ItemStack stack) {
        return 1;
    }
}