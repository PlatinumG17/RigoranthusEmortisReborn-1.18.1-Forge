package com.platinumg17.rigoranthusemortisreborn.items.armor.bonuses;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class InfernalSetBonus extends MobEffect {

    public InfernalSetBonus() {
        super(MobEffectCategory.BENEFICIAL, 0X51FFAF);
        this.setRegistryName(EmortisConstants.MOD_ID, "infernal_set_bonus");

    }
    private static int healTimer = 80;
    private static int damageTimer = 10;
    private static int effectTimer = 400;
    public void applyEffectTick(LivingEntity player, int amplifier) {
        if (player.isOnFire() || player.isInLava()) {
            player.clearFire();
            this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
            healTimer--;
            if (healTimer < 0) {
                player.heal(1f);
                healTimer = 40;
            }
        }
        if (player.isInWaterRainOrBubble()) {
            this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890",-0.3F, AttributeModifier.Operation.MULTIPLY_BASE);
            damageTimer--;
            if (damageTimer < 0) {
                player.hurt(DamageSource.GENERIC,  1f);
                damageTimer = 20;
            }
        }
        if ((player.level.dimension() == (Level.NETHER))) {
            this.addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 2.0F, AttributeModifier.Operation.ADDITION);
            this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.0F, AttributeModifier.Operation.ADDITION);
            this.addAttributeModifier(Attributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 2.0F, AttributeModifier.Operation.MULTIPLY_BASE);
            effectTimer--;
            if (effectTimer < 0) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 400, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2));
                // List as many other effects as you want here. This is for effects that do not have Conditions that need to be met before the effect is applied.
                effectTimer = 400;
            }
        }
    }
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
    public String getName() {
        return "rigoranthusemortisreborn.potion.infernal_set_bonus";
    }
}