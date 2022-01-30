package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Helmet extends ArmorAccoutrement {
    public Helmet(Supplier<? extends ItemLike> itemIn) {
        super(CanisAccoutrementTypes.HEAD, itemIn);
    }
}