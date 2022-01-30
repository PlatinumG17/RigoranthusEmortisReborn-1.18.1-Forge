package com.platinumg17.rigoranthusemortisreborn.items.armor.bonuses;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class AtrophyingSetBonus extends MobEffect {

    public AtrophyingSetBonus() {
        super(MobEffectCategory.BENEFICIAL, 0X51FFAF);
        this.setRegistryName(EmortisConstants.MOD_ID, "atrophying_set_bonus");
    }

    public void applyEffectTick(LivingEntity player, int amplifier) {
        if (player.hasEffect(MobEffects.WITHER)) {
            player.removeEffect(MobEffects.WITHER);
        }
        if ((player.level.dimension() == (Level.NETHER))) {
            if (player.isOnFire()) {
                player.clearFire();
            }
            this.addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 2.0F, AttributeModifier.Operation.ADDITION);
            this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.0F, AttributeModifier.Operation.ADDITION);
            this.addAttributeModifier(Attributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 2.0F, AttributeModifier.Operation.MULTIPLY_BASE);
            //player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 3));
            //player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2));
        }
    }
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
    public String getName() {
        return "rigoranthusemortisreborn.potion.atrophying_set_bonus";
    }
}