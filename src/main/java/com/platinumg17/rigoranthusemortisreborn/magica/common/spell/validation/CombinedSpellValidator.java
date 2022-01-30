package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellValidator;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Combined spell validator that runs the validations of all its children.
 */
public class CombinedSpellValidator implements ISpellValidator {
    private final List<ISpellValidator> validators;

    /** Create a combined spell validator from the given list of validators. */
    public CombinedSpellValidator(List<ISpellValidator> validators) {
        this.validators = validators;
    }

    /** Create a combined spell validator from the given list of validators. */
    public CombinedSpellValidator(ISpellValidator... validators) {
        this.validators = Arrays.asList(validators);
    }

    @Override
    public List<SpellValidationError> validate(List<AbstractSpellPart> spellRecipe) {
        return validators.stream()
                .flatMap(v -> v.validate(spellRecipe).stream())
                .collect(Collectors.toList());
    }
}