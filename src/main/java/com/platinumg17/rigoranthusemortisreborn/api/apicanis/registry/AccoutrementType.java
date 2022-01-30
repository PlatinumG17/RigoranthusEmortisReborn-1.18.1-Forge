package com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry;

import net.minecraft.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;

import javax.annotation.Nullable;

public class AccoutrementType extends ForgeRegistryEntry<AccoutrementType> {

    @Nullable
    private String translationKey;

    public int numberToPutOn() {
        return 1;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("accoutrement_type", RigoranthusEmortisRebornAPI.ACCOUTREMENT_TYPE.getKey(this));
        }
        return this.translationKey;
    }
}
