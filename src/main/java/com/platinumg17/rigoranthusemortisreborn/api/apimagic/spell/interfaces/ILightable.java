package com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellContext;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;

public interface ILightable {
    /**
     * Called when a light spell is cast on this block or entity.
     */
    void onLight(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats augments, SpellContext spellContext);
}