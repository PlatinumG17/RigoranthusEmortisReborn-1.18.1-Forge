package com.platinumg17.rigoranthusemortisreborn.magica.common.potions;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class SummoningCooldownEffect extends MobEffect {
    protected SummoningCooldownEffect() {
        super(MobEffectCategory.HARMFUL, 2039587);
        setRegistryName(EmortisConstants.MOD_ID, "summoned_servant");
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}