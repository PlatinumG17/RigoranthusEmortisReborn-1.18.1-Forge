package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractAugment;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellTier;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

public class AugmentExtract extends AbstractAugment {
    public static AugmentExtract INSTANCE = new AugmentExtract();

    private AugmentExtract() {
        super(GlyphLib.AugmentExtractID, "Extract");
    }

    @Override
    public int getDefaultDominionCost() {
        return 30;
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return Items.EMERALD_BLOCK;
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.TWO;
    }

    @Override
    public String getBookDescription() {
        return "Applies a silk-touch effect to Break and causes Explosion to not destroy blocks that drop. Cannot be combined with Fortune.";
    }
}