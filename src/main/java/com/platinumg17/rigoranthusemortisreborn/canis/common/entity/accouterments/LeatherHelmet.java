package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import net.minecraft.world.level.ItemLike;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;

import java.util.function.Supplier;

public class LeatherHelmet extends DyeableAccoutrement {
    public LeatherHelmet(Supplier<? extends ItemLike> itemIn) {
        super(CanisAccoutrementTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccoutrementInstance.RENDER_TOP;
    }
}
