package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation;


import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractAugment;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;

import java.util.Collection;
import java.util.List;

/**
 * Spell validation that checks a cast method or effect's augment compatibility.
 *
 * @see AbstractSpellPart#getCompatibleAugments()
 */
public class AugmentCompatibilityValidator extends SpellPhraseValidator {
    private static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(EmortisConstants.MOD_ID + ".AugmentCompatibilityValidator");

    @Override
    protected void validatePhrase(SpellPhrase phrase, List<SpellValidationError> validationErrors) {
        AbstractSpellPart action = phrase.getAction();

        if (action != null) {
            phrase.getAugmentPositionMap().values().stream()
                    .flatMap(Collection::stream)
                    .forEach(aug -> {
                        if (!action.getCompatibleAugments().contains(aug.spellPart)) {
                            validationErrors.add(new AugmentCompatibilitySpellValidationError(aug.position, action, aug.spellPart));
                        }
                    });
        }
    }

    private static class AugmentCompatibilitySpellValidationError extends BaseSpellValidationError {
        public AugmentCompatibilitySpellValidationError(int position, AbstractSpellPart spellPart, AbstractAugment augment) {
            super(position, spellPart, "augment_compatibility", spellPart, augment);
        }
    }
}