package com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import java.util.List;

/**
 * A special Spell resolver that ignores player limits such as dominion.
 */
public class EntitySpellResolver extends SpellResolver {

    public EntitySpellResolver(SpellContext context){
        super(context);
    }

    public void onCastOnEntity(LivingEntity target){
        super.onCastOnEntity(ItemStack.EMPTY, spellContext.caster, target, InteractionHand.MAIN_HAND);
    }

    @Override
    boolean enoughDominion(LivingEntity entity) {
        return true;
    }
}