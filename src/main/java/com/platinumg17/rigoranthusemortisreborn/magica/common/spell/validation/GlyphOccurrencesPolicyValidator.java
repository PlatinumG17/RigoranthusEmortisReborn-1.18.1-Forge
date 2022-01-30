package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spell validation that enforces the glyph limits policy set out in the configuration.
 */
public class GlyphOccurrencesPolicyValidator extends ScanningSpellValidator<Map<String, Integer>> {
    @Override
    protected Map<String, Integer> initContext() {
        return new HashMap<>();
    }

    @Override
    protected void digestSpellPart(Map<String, Integer> partCounts, int position, AbstractSpellPart spellPart, List<SpellValidationError> validationErrors) {
        // Count the glyph
        if (partCounts.containsKey(spellPart.getId())) {
            partCounts.put(spellPart.getId(), partCounts.get(spellPart.getId()) + 1);
        } else {
            partCounts.put(spellPart.getId(), 1);
        }

        // Check if over the limit
        int limit = spellPart.PER_SPELL_LIMIT == null ? Integer.MAX_VALUE : spellPart.PER_SPELL_LIMIT.get();
        if (partCounts.getOrDefault(spellPart.getId(), 0) > limit) {
            validationErrors.add(new GlyphOccurrencesPolicySpellValidationError(position, spellPart, limit));
        }
    }

    @Override
    protected void finish(Map<String, Integer> context, List<SpellValidationError> validationErrors) {}

    private static class GlyphOccurrencesPolicySpellValidationError extends BaseSpellValidationError {
        public GlyphOccurrencesPolicySpellValidationError(int position, AbstractSpellPart part, int limit) {
            super(
                    position,
                    part,
                    "glyph_occurrences_policy",
                    part
            );
        }
    }
}