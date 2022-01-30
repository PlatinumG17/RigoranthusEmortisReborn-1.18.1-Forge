package com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ISpellModifier;
import javax.annotation.Nonnull;
import java.util.Set;

public abstract class AbstractAugment extends AbstractSpellPart implements ISpellModifier {

    public AbstractAugment(String tag, String description) {
        super(tag, description);
    }

    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf();
    }

    @Override
    abstract public int getDefaultDominionCost();

    public SpellStats.Builder applyModifiers(SpellStats.Builder builder, AbstractSpellPart spellPart){
        return builder;
    }
}