package com.platinumg17.rigoranthusemortisreborn.magica.common.potions;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SnareEffect extends MobEffect {

    public SnareEffect() {
        super(MobEffectCategory.HARMFUL, 2039587);
        setRegistryName(EmortisConstants.MOD_ID, "snared");
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "0dee8a21-f182-42c8-8361-1ad6186cac30", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}