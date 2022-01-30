package com.platinumg17.rigoranthusemortisreborn.core.registry.effects;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.registry.RigoranthusDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.core.particles.ParticleTypes;

public class EffectNecrotizingFasciitis extends MobEffect {

    public EffectNecrotizingFasciitis() {
        super(MobEffectCategory.HARMFUL, 0XED5151);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890",-0.3F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC",-3.0F, AttributeModifier.Operation.ADDITION);
        this.setRegistryName(EmortisConstants.MOD_ID, "necrotizing_fasciitis");
    }
    private static int damageTimer = 100;
    public void applyEffectTick(LivingEntity player, int amplifier) {
        for(int i = 0; i < 2; i++){
            player.level.addParticle(ParticleTypes.FALLING_NECTAR, player.getRandomX(1.0), player.getRandomY(), player.getRandomZ(1.0), 0, 0, 0);
        }
        if(player.getDeltaMovement().y > 0 && !player.isInWaterOrBubble()) {
            player.setDeltaMovement(player.getDeltaMovement().multiply(0.7, 1, 0.7));
        }
        damageTimer--;
        if (damageTimer < 0) {
            player.hurt(RigoranthusDamageSources.NECROTIZING_FASCIITIS,  0.2f);
            damageTimer = 100;
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getName() {
        return "rigoranthusemortisreborn.potion.necrotizing_fasciitis";
    }
}