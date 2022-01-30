package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractAugment;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;

public class FormSpellArrow extends SpellArrow {
    public FormSpellArrow(String registryName, AbstractAugment augment, int numParts) {
        super(registryName, augment, numParts);
    }

    @Override
    public void modifySpell(Spell spell) {
        for(int i = 0; i < numParts; i++) {
            spell.recipe.add(1,this.part);
        }
    }
}
