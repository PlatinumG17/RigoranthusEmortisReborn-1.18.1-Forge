package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisTransmogrification;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Accoutrement;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ArmorAccoutrement extends Accoutrement {

    public ArmorAccoutrement(Supplier<? extends AccoutrementType> typeIn, Supplier<? extends ItemLike> itemIn) {
        super(typeIn, itemIn);
    }

    public AccoutrementInstance create(ItemStack armorStack) {
        if (armorStack.isEmpty()) {
            if (this.of(CanisAccouterments.IRON_HELMET)) {
                armorStack = new ItemStack(Items.IRON_HELMET);
            } else if (this.of(CanisAccouterments.DIAMOND_HELMET)) {
                armorStack = new ItemStack(Items.DIAMOND_HELMET);
            } else if (this.of(CanisAccouterments.GOLDEN_HELMET)) {
                armorStack = new ItemStack(Items.GOLDEN_HELMET);
            } else if (this.of(CanisAccouterments.CHAINMAIL_HELMET)) {
                armorStack = new ItemStack(Items.CHAINMAIL_HELMET);
            } else if (this.of(CanisAccouterments.TURTLE_HELMET)) {
                armorStack = new ItemStack(Items.TURTLE_HELMET);
            } else if (this.of(CanisAccouterments.NETHERITE_HELMET)) {
                armorStack = new ItemStack(Items.NETHERITE_HELMET);
            } else {
                throw new IllegalArgumentException();
            }
        }
        return new ArmorAccoutrement.Instance(armorStack.copy());
    }

    @Override
    public AccoutrementInstance getDefault() {
        return new ArmorAccoutrement.Instance(ItemStack.EMPTY);
    }

    @Override
    public AccoutrementInstance createFromStack(ItemStack stackIn) {
        return this.create(stackIn.copy());
    }

    @Override
    public AccoutrementInstance createInstance(FriendlyByteBuf buf) {
        return this.create(buf.readItem());
    }

    @Override
    public void write(AccoutrementInstance instance, FriendlyByteBuf buf) {
        ArmorAccoutrement.Instance exact = instance.cast(ArmorAccoutrement.Instance.class);
        buf.writeItem(exact.armorStack);
    }

    @Override
    public void write(AccoutrementInstance instance, CompoundTag compound) {
        ArmorAccoutrement.Instance exact = instance.cast(ArmorAccoutrement.Instance.class);
        CompoundTag itemTag = new CompoundTag();
        exact.armorStack.save(itemTag);
        compound.put("item", itemTag);
    }

    @Override
    public AccoutrementInstance read(CompoundTag compound) {
        return this.create(ItemStack.of(compound.getCompound("item")));
    }

    public class Instance extends AccoutrementInstance implements ICanisTransmogrification {

        @Nonnull
        protected final ItemStack armorStack;

        public Instance(ItemStack armorStack) {
            super(ArmorAccoutrement.this);
            this.armorStack = armorStack;
        }

        @Override
        public void init(AbstractCanisEntity canisIn) {
            EquipmentSlot slotType = null;
            if (this.armorStack.getItem() instanceof ArmorItem) {
                slotType = ((ArmorItem) this.armorStack.getItem()).getSlot();
            }
            canisIn.getAttributes().addTransientAttributeModifiers(this.armorStack.getAttributeModifiers(slotType));
        }

        @Override
        public void remove(AbstractCanisEntity canisIn) {
            EquipmentSlot slotType = null;
            if (this.armorStack.getItem() instanceof ArmorItem) {
                slotType = ((ArmorItem) this.armorStack.getItem()).getSlot();
            }
            canisIn.getAttributes().removeAttributeModifiers(this.armorStack.getAttributeModifiers(slotType));
        }

        @Override
        public AccoutrementInstance copy() {
            return new ArmorAccoutrement.Instance(this.armorStack.copy());
        }

        public boolean hasEffect() {
            return this.armorStack.hasFoil();
        }

        @Override
        public ItemStack getReturnItem() {
            return this.armorStack;
        }
    }
}