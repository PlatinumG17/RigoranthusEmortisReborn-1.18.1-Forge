package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import java.util.function.Supplier;

import net.minecraft.world.level.ItemLike;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Accoutrement;

public class Garments extends Accoutrement {
    public Garments(Supplier<? extends ItemLike> itemIn) {
        super(CanisAccoutrementTypes.GARMENTS, itemIn);
    }
}
