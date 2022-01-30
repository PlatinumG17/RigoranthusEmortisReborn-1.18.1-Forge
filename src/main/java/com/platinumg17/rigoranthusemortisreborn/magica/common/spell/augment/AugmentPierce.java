package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractAugment;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellTier;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public class AugmentPierce extends AbstractAugment {
    public static AugmentPierce INSTANCE = new AugmentPierce();

    private AugmentPierce() {
        super(GlyphLib.AugmentPierceID, "Pierce");
    }

    @Override
    public int getDefaultDominionCost() {
        return 40;
    }

    @Override
    public SpellTier getTier() {
        return SpellTier.TWO;
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return ItemInit.RAZORTOOTH_KUNAI.get();
    }

    @Override
    public String getBookDescription() {
        return "When applied to the Projectile spell, projectiles may continue through their path an additional time after hitting a mob or block. Causes certain effects to also target the block behind them, like Break. Combines with AOE to provide depth.";
    }
}