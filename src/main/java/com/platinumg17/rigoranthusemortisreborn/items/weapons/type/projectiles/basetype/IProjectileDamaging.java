package com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles.basetype;

import net.minecraft.world.item.Item;

public interface IProjectileDamaging {
    int getProjectileDamage();
    static int getDamageFromItem(Item item) {
        return item instanceof IProjectileDamaging ? ((IProjectileDamaging) item).getProjectileDamage() : 0;
    }
}
