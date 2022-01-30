package com.platinumg17.rigoranthusemortisreborn.magica.common.items.curios;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.RigoranthusEmortisRebornCurio;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionEquipment;
import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractDominionCurio extends RigoranthusEmortisRebornCurio implements IDominionEquipment {
    public AbstractDominionCurio(String reg){
        super(reg);
    }

    @Override
    public void wearableTick(LivingEntity wearer) { }
}