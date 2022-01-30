package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisFoodHandler;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisFoodPredicate;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.CanisTags;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChungusPupperSkill extends SkillInstance implements ICanisFoodHandler {

    public static final ICanisFoodPredicate INNER_DYN_PRED = (stackIn) -> {
        Item item = stackIn.getItem();
        return item == Items.ROTTEN_FLESH || (item.isEdible() && ItemTags.FISHES.contains(item)) || ItemTags.FOX_FOOD.contains(item) || CanisTags.BONES.contains(item) || item == MagicItemsRegistry.BOTTLE_OF_ICHOR;
    };

    public ChungusPupperSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public InteractionResultHolder<Float> setCanisHunger(AbstractCanisEntity canisIn, float hunger, float diff) {
        hunger += diff / 10 * this.level();
        return InteractionResultHolder.success(hunger);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return ChungusPupperSkill.INNER_DYN_PRED.isFood(stackIn);
    }

    @Override
    public boolean canConsume(AbstractCanisEntity canisIn, ItemStack stackIn, Entity entityIn) {
        if (this.level() >= 1) {
            Item item = stackIn.getItem();
            if(item == MagicItemsRegistry.BOTTLE_OF_ICHOR)
                return true;
            if (this.level() >= 2) {
                if (item == Items.ROTTEN_FLESH)
                    return true;
                if (this.level() >= 3) {
                    if (item.isEdible() && ItemTags.FISHES.contains(item))
                        return true;
                    if (this.level() >= 4) {
                        if (CanisTags.BONES.contains(item))
                            return true;
                        if (this.level() >= 5) {
                            return ItemTags.FOX_FOOD.contains(item);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public InteractionResult consume(AbstractCanisEntity canisIn, ItemStack stackIn, Entity entityIn) {
        if(this.level() >= 1) {
            Item item = stackIn.getItem();
            if(item == MagicItemsRegistry.BOTTLE_OF_ICHOR) {
                canisIn.addEffect(new MobEffectInstance(MobEffects.SATURATION, 200, 1));
                canisIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
                canisIn.addHunger(40);
                canisIn.consumeItemFromStack(entityIn, stackIn);
            }
            if (this.level() >= 2) {
                if (item == Items.ROTTEN_FLESH) {
                    canisIn.addHunger(30);
                    canisIn.consumeItemFromStack(entityIn, stackIn);
                    return InteractionResult.SUCCESS;
                }
                if (this.level() >= 3) {
                    if (item.isEdible() && ItemTags.FISHES.contains(item)) {
                        canisIn.addHunger(item.getFoodProperties().getNutrition() * 5);
                        canisIn.consumeItemFromStack(entityIn, stackIn);
                        return InteractionResult.SUCCESS;
                    }
                    if (this.level() >= 4) {
                        if (CanisTags.BONES.contains(item)) {
                            canisIn.addHunger(20);
                            canisIn.consumeItemFromStack(entityIn, stackIn);
                            return InteractionResult.SUCCESS;
                        }
                        if (this.level() >= 5 && ItemTags.FOX_FOOD.contains(item)) {
                            canisIn.addHunger(25);
                            canisIn.consumeItemFromStack(entityIn, stackIn);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }
}