package com.platinumg17.rigoranthusemortisreborn.api.apimagic.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

public class FamiliarSummonEvent extends EntityEvent {
    public Entity owner;

    public FamiliarSummonEvent(Entity entity, Entity owner) {
        super(entity);
        this.owner = owner;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}