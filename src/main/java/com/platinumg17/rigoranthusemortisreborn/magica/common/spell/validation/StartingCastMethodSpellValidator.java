package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractCastMethod;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;

import java.util.List;

/**
 * Spell validation that requires a spell start with a cast method.
 */
public class StartingCastMethodSpellValidator extends AbstractSpellValidator {
    @Override
    protected void validateImpl(List<AbstractSpellPart> spellRecipe, List<SpellValidationError> errors) {
        if (!(spellRecipe.isEmpty() || spellRecipe.get(0) instanceof AbstractCastMethod)) {
            errors.add(new StartingCastMethodSpellValidationError());
        }
    }

    private static class StartingCastMethodSpellValidationError extends BaseSpellValidationError {
        public StartingCastMethodSpellValidationError() {
            super(-1, null, "starting_cast_method");
        }
    }
}