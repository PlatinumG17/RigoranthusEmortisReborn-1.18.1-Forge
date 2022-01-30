package com.platinumg17.rigoranthusemortisreborn.magica.common.capability;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collection;

public interface IPlayerCap extends INBTSerializable<CompoundTag> {
    /*Glyph data*/

    /**
     * Returns the list of known glyphs
     */
    Collection<AbstractSpellPart> getKnownGlyphs();

    /**
     * Adds the glyph to the player data.
     * @return true if the glyph was unlocked, false if they already know it.
     */
    boolean unlockGlyph(AbstractSpellPart spellPart);

    /**
     * @return true if they already know this glyph.
     */
    boolean knowsGlyph(AbstractSpellPart spellPart);

    /*Familiar data*/


    boolean unlockFamiliar(AbstractFamiliarHolder holderID);

    boolean ownsFamiliar(AbstractFamiliarHolder holderID);

    Collection<FamiliarData> getUnlockedFamiliars();

    FamiliarData getFamiliarData(String id);

    void setUnlockedFamiliars(Collection<FamiliarData> familiars);

    boolean removeFamiliar(AbstractFamiliarHolder holderID);
}
