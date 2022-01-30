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
import net.minecraft.world.entity.player.Player;

public class AqueousSetBonus extends MobEffect {

    public AqueousSetBonus() {
        super(MobEffectCategory.BENEFICIAL, 0X51FFAF);
        this.setRegistryName(EmortisConstants.MOD_ID, "aqueous_set_bonus");
    }
    private static int healTimer = 80;
    private static int airBubbleTimer = 20;
    public void applyEffectTick(LivingEntity player, int amplifier) {
        player.fireImmune();

        if (player.isInWaterRainOrBubble()) {
            if(player.level.isClientSide) {
                LocalPlayer playerClient = Minecraft.getInstance().player;
                if (playerClient != null && (playerClient.input.up || playerClient.input.down || playerClient.input.left || playerClient.input.right)) {
                    player.setDeltaMovement(player.getDeltaMovement().multiply(1.1, 1.1, 1.1));
                }
                if (playerClient != null) {
                    LivingEntity target = player.getLastHurtMob();
                    if(target != null && !target.isDeadOrDying()) {
                        float health = target.getHealth();
                        if(health - 2f != 0f) {
                            target.hurt(DamageSource.playerAttack((Player) player), 2f);
                        }
                    }
                }
            }
//            this.addAttributeModifier(Attributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 2.0F, AttributeModifier.Operation.MULTIPLY_BASE);
//            this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
//            this.addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 2.0F, AttributeModifier.Operation.MULTIPLY_BASE);
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 3));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 3));
            if(player.isInBubbleColumn()) {
                if(player.level.isClientSide) {
                    LocalPlayer playerClient = Minecraft.getInstance().player;
                    airBubbleTimer--;
                    if (playerClient != null && airBubbleTimer < 0) {
                        player.setAirSupply(player.getAirSupply() + 2);
                        airBubbleTimer = 20;
                    }
                }
            }
//                player.addEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 4));
//                player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 200, 1));
            healTimer--;
            if (healTimer < 0) {
                player.heal(1f);
                healTimer = 40;
            }
        }
    }
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getName() {
        return "rigoranthusemortisreborn.potion.aqueous_set_bonus";
    }
}