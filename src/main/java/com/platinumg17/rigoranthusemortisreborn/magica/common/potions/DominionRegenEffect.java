package com.platinumg17.rigoranthusemortisreborn.magica.common.potions;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class DominionRegenEffect extends MobEffect {
    protected DominionRegenEffect() {
        super(MobEffectCategory.BENEFICIAL, 8080895);
        setRegistryName(EmortisConstants.MOD_ID, "dominion_regen");
    }
}