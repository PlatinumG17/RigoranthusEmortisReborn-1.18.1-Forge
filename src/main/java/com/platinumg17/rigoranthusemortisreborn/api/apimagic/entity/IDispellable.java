package com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity;

import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public interface IDispellable {
    /**
     * When dispel hits a target
     * @param caster The entity that cast this spell. This can be NULL in the case of runes/blocks/tile-entities that cast spells.
     * @return Returns true if dispel was successful.
     */
    boolean onDispel(@Nullable LivingEntity caster);

}