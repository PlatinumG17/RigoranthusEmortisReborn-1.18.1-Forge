package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.IColoredObject;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementType;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.IDyeableArmorItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.ColorCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class LeatherArmorAccoutrement extends ArmorAccoutrement {
    public LeatherArmorAccoutrement(Supplier<? extends AccoutrementType> typeIn, Supplier<? extends ItemLike> itemIn) {
        super(typeIn, itemIn);
    }

    @Override
    public AccoutrementInstance create(ItemStack armorStack) {
        if (armorStack.isEmpty()) {
            if (this.of(CanisAccouterments.LEATHER_HELMET)) {
                armorStack = new ItemStack(Items.LEATHER_HELMET);
            } else {
                throw new IllegalArgumentException();
            }
        }
        return new LeatherArmorAccoutrement.Instance(armorStack.copy());
    }

    @Override
    public AccoutrementInstance read(CompoundTag compound) {
        AccoutrementInstance inst = super.read(compound);
        if (this.of(CanisAccouterments.LEATHER_HELMET)) {
            // Backwards compatibility
            if (compound.contains("color", Tag.TAG_ANY_NUMERIC)) {
                int color = compound.getInt("color");
                Instance def = inst.cast(Instance.class);
                if (def.armorStack.getItem() instanceof IDyeableArmorItem) {
                    ((IDyeableArmorItem) def.armorStack.getItem()).setColor(def.armorStack, color);
                }
                def.color = ColorCache.make(color);
            }
        }
        return inst;
    }

    public class Instance extends ArmorAccoutrement.Instance implements IColoredObject {

        private ColorCache color = ColorCache.WHITE;
        public Instance(ItemStack armorStack) {
            super(armorStack);
            if (armorStack.getItem() instanceof IDyeableArmorItem) {
                this.color = ColorCache.make(((IDyeableArmorItem) armorStack.getItem()).getColor(armorStack));
            }
        }

        @Override
        public AccoutrementInstance copy() {
            return new LeatherArmorAccoutrement.Instance(this.armorStack.copy());
        }

        @Override
        public float[] getColor() {
            return this.color.getFloatArray();
        }
    }
}