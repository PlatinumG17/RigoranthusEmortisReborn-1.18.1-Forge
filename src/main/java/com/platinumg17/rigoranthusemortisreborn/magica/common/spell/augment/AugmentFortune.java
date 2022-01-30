package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractAugment;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellTier;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;

public class AugmentFortune extends AbstractAugment {
    public static AugmentFortune INSTANCE = new AugmentFortune();

    private AugmentFortune() {
        super(GlyphLib.AugmentFortuneID, "Fortune");
    }

    @Override
    public int getDefaultDominionCost() {
        return 80;
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.TWO;
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return Items.RABBIT_FOOT;
    }

    @Override
    public String getBookDescription() {
        return "Increases the drop chance from mobs killed by Damage and blocks that are destroyed by Break. Cannot be combined with Extract";
    }
}