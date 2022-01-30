package com.platinumg17.rigoranthusemortisreborn.api.apimagic.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * An event that fires after Rigoranthus Emortis has calculated the preliminary Max Dominion provided by gear, glyphs, and book tiers.
 */
public class MaxDominionCalcEvent extends LivingEvent {
    private int max;
    public MaxDominionCalcEvent(LivingEntity entity, int max) {
        super(entity);
        this.max = max;
    }
    public void setMax(int newMax){
        this.max = Math.max(newMax, 0);
    }
    public int getMax(){
        return this.max;
    }
}