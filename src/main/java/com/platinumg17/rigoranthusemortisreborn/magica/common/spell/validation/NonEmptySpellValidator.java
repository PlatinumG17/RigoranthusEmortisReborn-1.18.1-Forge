package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;

import java.util.List;

/** Spell validator that asserts that the spell is non-empty. */
public class NonEmptySpellValidator extends AbstractSpellValidator {
    @Override
    protected void validateImpl(List<AbstractSpellPart> spellRecipe, List<SpellValidationError> validationErrors) {
        if (spellRecipe == null || spellRecipe.isEmpty()) {
            validationErrors.add(new NonEmptySpellValidationError());
        }
    }

    private static class NonEmptySpellValidationError extends BaseSpellValidationError {
        public NonEmptySpellValidationError() {
            super(-1, null, "non_empty_spell");
        }
    }
}