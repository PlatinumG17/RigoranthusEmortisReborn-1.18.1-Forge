package com.platinumg17.rigoranthusemortisreborn.items.armor.bonuses;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IncorporealSetBonus extends MobEffect {

    public IncorporealSetBonus() {
        super(MobEffectCategory.BENEFICIAL, 0X51FFAF);
        this.setRegistryName(EmortisConstants.MOD_ID, "incorporeal_set_bonus");
    }
    private static int healTimer = 80;
    private static int damageTimer = 10;
    public void applyEffectTick(LivingEntity player, int amplifier) {
        if ((player.level.getMaxLocalRawBrightness(player.getOnPos()) < 5) || player.level.isNight()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 1));
            if (player.level.isNight()) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 400, 1));
                if (player.isCrouching()) {
                    healTimer--;
                    if (healTimer < 0) {
                        player.heal(1f);
                        healTimer = 40;
                    }
                }
            }
        }
        if (player.isInWaterRainOrBubble()) {
            if(player.level.isClientSide) {
                LocalPlayer playerClient = Minecraft.getInstance().player;
                if (playerClient != null && playerClient.input.jumping) {
                    player.setDeltaMovement(player.getDeltaMovement().multiply(0.8, 0.8, 0.8));
                } else player.setDeltaMovement(player.getDeltaMovement().multiply(0.8, 2.2, 0.8));
            }
            damageTimer--;
            if (damageTimer < 0) {
                player.hurt(DamageSource.GENERIC,  1f);
                damageTimer = 20;
            }
        }
    }
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
    public String getName() {
        return "rigoranthusemortisreborn.potion.incorporeal_set_bonus";
    }
}