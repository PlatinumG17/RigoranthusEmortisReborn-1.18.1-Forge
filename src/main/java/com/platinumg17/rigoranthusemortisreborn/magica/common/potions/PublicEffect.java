package com.platinumg17.rigoranthusemortisreborn.magica.common.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import java.util.List;

/**
 *  GOD HAVE MERCY ON OUR SOULS
 */
public class PublicEffect extends MobEffect {

    List<ItemStack> curativeItems;
    public PublicEffect(MobEffectCategory p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    public PublicEffect(MobEffectCategory type, int color, List<ItemStack> curativeItems){
        this(type,color);
        this.curativeItems = curativeItems;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return this.curativeItems != null ? curativeItems : super.getCurativeItems();
    }
}