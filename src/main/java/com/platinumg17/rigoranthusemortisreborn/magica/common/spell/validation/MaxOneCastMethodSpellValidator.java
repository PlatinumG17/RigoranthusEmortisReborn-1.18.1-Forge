package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractCastMethod;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;

import java.util.List;

/**
 * Spell validation that requires at most one cast method in a spell.
 */
public class MaxOneCastMethodSpellValidator extends ScanningSpellValidator<MaxOneCastMethodSpellValidator.OneCastContext> {
    @Override
    protected OneCastContext initContext() {
        return new OneCastContext();
    }

    @Override
    protected void digestSpellPart(OneCastContext context, int position, AbstractSpellPart spellPart, List<SpellValidationError> validationErrors) {
        if (spellPart instanceof AbstractCastMethod) {
            context.count++;
            if (context.count > 1) {
                validationErrors.add(new OneCastMethodSpellValidationError(position, (AbstractCastMethod) spellPart));
            }
        }
    }

    @Override
    protected void finish(OneCastContext context, List<SpellValidationError> validationErrors) {}

    /** Context for this validator is a simple counter on AbstractCastMethod parts */
    public static class OneCastContext { int count = 0; }

    private static class OneCastMethodSpellValidationError extends BaseSpellValidationError {
        public OneCastMethodSpellValidationError(int position, AbstractCastMethod method) {
            super(position, method, "max_one_cast_method", method);
        }
    }
}