package com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.SpellCastEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.SpellModifierEvent;

public interface ISpellCastListener {

    default void onCast(SpellCastEvent event){}

    default void onModifier(SpellModifierEvent event){}
}