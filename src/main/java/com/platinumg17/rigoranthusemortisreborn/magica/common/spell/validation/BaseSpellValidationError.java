package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

/**
 * @author BaileyHoll
 * Base class for common logic when constructing a {@link SpellValidationError}.
 *
 * Provides logic around finding the exact localization keys and for building text components that translate both
 * the core message and the glyphs being passed as arguments.
 *
 * Subclasses of this base class will seek localization keys under
 * <code>"rigoranthusemortisreborn.spell.validation.exists."</code>
 * or <code>"rigoranthusemortisreborn.spell.validation.adding."</code>.
 */
public class BaseSpellValidationError implements SpellValidationError {
    private static final Component[] NO_ARGS = new Component[0];
    private final int position;
    private final AbstractSpellPart spellPart;
    private final String localizationCode;
    private final Object[] translationArguments;

    /**
     * Creates a new Spell Validation Error.
     *
     * @param position the glyph position in the overall spell that was validated.
     * @param spellPart the actual spell part in the offending position
     * @param localizationCode the localization code
     * @param translationArguments arguments to be passed to the i18n system when constructing text components for this
     *                             error.
     * @see TranslatableComponent
     */
    public BaseSpellValidationError(int position,
                                    AbstractSpellPart spellPart,
                                    String localizationCode,
                                    Component... translationArguments) {
        this.position = position;
        this.spellPart = spellPart;
        this.localizationCode = localizationCode;
        this.translationArguments = translationArguments;
    }

    /**
     * Creates a new Spell Validation Error.
     *
     * @param position the glyph position in the overall spell that was validated.
     * @param spellPart the actual spell part in the offending position
     * @param localizationCode the localization code
     */
    public BaseSpellValidationError(int position,
                                    AbstractSpellPart spellPart,
                                    String localizationCode) {
        this(position, spellPart, localizationCode, NO_ARGS);
    }

    /**
     * Creates a new Spell Validation Error.
     *
     * @param position the glyph position in the overall spell that was validated.
     * @param spellPart the actual spell part in the offending position
     * @param localizationCode the localization code
     * @param spellParts arguments to be passed to the i18n system when constructing text components for this error.
     */
    public BaseSpellValidationError(int position,
                                    AbstractSpellPart spellPart,
                                    String localizationCode,
                                    AbstractSpellPart... spellParts) {
        this(position, spellPart, localizationCode, translateGlyphs(spellParts));
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Nullable
    @Override
    public AbstractSpellPart getSpellPart() {
        return spellPart;
    }

    @Override
    public Component makeTextComponentExisting() {
        return makeTextComponent("rigoranthusemortisreborn.spell.validation.exists.");
    }
    @Override
    public Component makeTextComponentAdding() {
        return makeTextComponent("rigoranthusemortisreborn.spell.validation.adding.");
    }

    private Component makeTextComponent(String keyPrefix) {
        return new TranslatableComponent(keyPrefix + localizationCode, translationArguments);
    }

    /**
     * Translates a number of spell parts into an array of Components suitable for use as the arguments
     * parameter of the constructor.
     */
    private static Component[] translateGlyphs(AbstractSpellPart... parts) {
        Component[] textComponents = new Component[parts.length];
        for (int i = 0; i < parts.length; i++) {
            textComponents[i] = new TranslatableComponent(parts[i].getLocalizationKey());
        }
        return textComponents;
    }
}