package com.platinumg17.rigoranthusemortisreborn.api.apimagic.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class DominionRegenCalcEvent extends LivingEvent {
    private double regen;
    public DominionRegenCalcEvent(LivingEntity entity, double regen) {
        super(entity);
        this.regen = regen;
    }
    public void setRegen(double newRegen){
        this.regen = Math.max(newRegen, 0);
    }
    public double getRegen(){
        return this.regen;
    }
}