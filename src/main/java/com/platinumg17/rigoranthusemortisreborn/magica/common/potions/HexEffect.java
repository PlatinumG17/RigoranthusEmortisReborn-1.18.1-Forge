package com.platinumg17.rigoranthusemortisreborn.magica.common.potions;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class HexEffect extends MobEffect {
    protected HexEffect() {
        super(MobEffectCategory.HARMFUL, 8080895);
        setRegistryName(EmortisConstants.MOD_ID, "hex");
    }
}
