package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.DyeableAccoutrement;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;

import java.util.function.Supplier;

public class DyeableAccoutrementItem extends AccoutrementItem implements IDyeableArmorItem {

    private Supplier<? extends DyeableAccoutrement> accoutrement;

    public DyeableAccoutrementItem(Supplier<? extends DyeableAccoutrement> accoutrementIn, Properties properties) {
        super(accoutrementIn, properties);
        this.accoutrement = accoutrementIn;
    }

    @Override
    public AccoutrementInstance createInstance(AbstractCanisEntity canisIn, ItemStack stack, Player playerIn) {
        return this.accoutrement.get().create(this.getColor(stack));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            ItemStack stack = new ItemStack(this);
            this.setColor(stack, this.getDefaultColor(stack));
            items.add(stack);
        }
    }
}
