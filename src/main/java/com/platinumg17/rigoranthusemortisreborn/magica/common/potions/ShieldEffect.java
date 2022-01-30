package com.platinumg17.rigoranthusemortisreborn.magica.common.potions;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ShieldEffect extends MobEffect {
    public ShieldEffect() {
        super(MobEffectCategory.BENEFICIAL, 2039587);
        setRegistryName(EmortisConstants.MOD_ID, "shield");
    }
}
