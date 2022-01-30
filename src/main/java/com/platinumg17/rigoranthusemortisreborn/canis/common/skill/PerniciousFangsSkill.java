package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;

public class PerniciousFangsSkill extends SkillInstance {
    public PerniciousFangsSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (canisIn.isTame()) {
            if (this.level() < 5) {
                return InteractionResult.PASS;
            }
            ItemStack stack = playerIn.getItemInHand(handIn);
            if (stack.getItem() == Items.SPIDER_EYE) {
                if (playerIn.getEffect(MobEffects.POISON) == null || canisIn.getCanisHunger() < 30) {
                    return InteractionResult.FAIL;
                }
                if (!worldIn.isClientSide) {
                    playerIn.removeEffect(MobEffects.POISON);
                    canisIn.setCanisHunger(canisIn.getCanisHunger() - 30);
                    playerIn.addEffect(new MobEffectInstance(ModPotions.PERNICIOUS_SET_BONUS, 5000));
                    canisIn.consumeItemFromStack(playerIn, stack);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult isPotionApplicable(AbstractCanisEntity canisIn, MobEffectInstance effectIn) {
        if (this.level() >= 3) {
            if (effectIn.getEffect() == MobEffects.POISON) {
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult attackEntityAsMob(AbstractCanisEntity dog, Entity entity) {
        if (entity instanceof LivingEntity) {
            if (this.level() > 0) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, this.level() * 20, 0));
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.PASS;
    }
}