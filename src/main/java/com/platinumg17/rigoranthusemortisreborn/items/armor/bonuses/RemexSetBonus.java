package com.platinumg17.rigoranthusemortisreborn.items.armor.bonuses;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;

public class RemexSetBonus extends MobEffect {

    public RemexSetBonus() {
        super(MobEffectCategory.BENEFICIAL, 0X51FFAF);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 1.5F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 2.0F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 2.0F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.setRegistryName(EmortisConstants.MOD_ID, "remex_set_bonus");

    }
    private static int effectTimer = 400;
    public void applyEffectTick(LivingEntity player, int amplifier) {
        effectTimer--;
        if (effectTimer < 0) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 400, 2));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 400, 5));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 5));
        }
        if (player.getMainHandItem().getItem() == Items.FEATHER) {
            player.setNoGravity(true);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getName() {
        return "rigoranthusemortisreborn.potion.remex_set_bonus";
    }
}