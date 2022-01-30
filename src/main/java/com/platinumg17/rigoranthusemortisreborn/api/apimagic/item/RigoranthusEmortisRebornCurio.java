package com.platinumg17.rigoranthusemortisreborn.api.apimagic.item;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.ModItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public abstract class RigoranthusEmortisRebornCurio extends ModItem {

    public RigoranthusEmortisRebornCurio() {
        super(new Item.Properties().stacksTo(1).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP));
    }

    public RigoranthusEmortisRebornCurio(Item.Properties properties, String registryName){
        super(properties, registryName);
    }

    public RigoranthusEmortisRebornCurio(String registryName){
        super(new Item.Properties().stacksTo(1).tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP), registryName);
    }
    abstract public void wearableTick(LivingEntity wearer);
}