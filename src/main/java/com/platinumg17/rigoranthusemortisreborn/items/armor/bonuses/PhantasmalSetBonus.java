package com.platinumg17.rigoranthusemortisreborn.items.armor.bonuses;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class PhantasmalSetBonus extends MobEffect {

    public PhantasmalSetBonus() {
        super(MobEffectCategory.BENEFICIAL, 0X51FFAF);
        this.setRegistryName(EmortisConstants.MOD_ID, "phantasmal_set_bonus");
    }
    private static int effectTimer = 400;
    public void applyEffectTick(LivingEntity player, int amplifier) {
        if ((player.level.getMaxLocalRawBrightness(player.blockPosition()) < 5) || player.level.isNight()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 1));
            if (player.isShiftKeyDown()) {
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 2));
            }
            effectTimer--;
            if (effectTimer < 0) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 400, 1));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 400, 6));
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 400, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 400, 2));
                effectTimer = 400;
            }
        }
        if ((player.level.dimension() == (Level.END))) {
            this.addAttributeModifier(Attributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 2.0F, AttributeModifier.Operation.MULTIPLY_BASE);
            if (player.isShiftKeyDown()) {
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 2));
            }
            effectTimer--;
            if (effectTimer < 0) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 400, 10));
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 400, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2));
                player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 100, 2));
                effectTimer = 400;
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getName() {
        return "rigoranthusemortisreborn.potion.phantasmal_set_bonus";
    }
}