package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisTransmogrification;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Accoutrement;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementType;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.IDyeableArmorItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.ColorCache;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DyeableAccoutrement extends Accoutrement {

    public DyeableAccoutrement(Supplier<? extends AccoutrementType> typeIn, Supplier<? extends ItemLike> itemIn) {
        super(typeIn, itemIn);
    }

    @Override
    public AccoutrementInstance createInstance(FriendlyByteBuf buf) {
        return this.create(buf.readInt());
    }

    @Override
    public void write(AccoutrementInstance instance, FriendlyByteBuf buf) {
        DyeableAccoutrementInstance exact = instance.cast(DyeableAccoutrementInstance.class);
        buf.writeInt(exact.getColor());
    }

    @Override
    public void write(AccoutrementInstance instance, CompoundTag compound) {
        DyeableAccoutrementInstance exact = instance.cast(DyeableAccoutrementInstance.class);
        compound.putInt("color", exact.getColor());
    }
    @Override public AccoutrementInstance read(CompoundTag compound) {return this.create(compound.getInt("color"));}

    @Override
    public AccoutrementInstance getDefault() {return this.create(0);}

    @Override
    public ItemStack getReturnItem(AccoutrementInstance instance) {
        DyeableAccoutrementInstance exact = instance.cast(DyeableAccoutrementInstance.class);
        ItemStack returnStack = super.getReturnItem(instance);
        if (returnStack.getItem() instanceof IDyeableArmorItem) {
            ((IDyeableArmorItem) returnStack.getItem()).setColor(returnStack, exact.getColor());
        } else {
            RigoranthusEmortisReborn.LOGGER.info("Unable to set set dyeable accoutrement color.");
        }
        return returnStack;
    }
    public AccoutrementInstance create(int color) {return new DyeableAccoutrementInstance(color);}

    @Override
    public AccoutrementInstance createFromStack(ItemStack stackIn) {
        Item item = stackIn.getItem();
        if (item instanceof IDyeableArmorItem) {
            return this.create(((IDyeableArmorItem) item).getColor(stackIn));
        }
        return this.getDefault();
    }

    public class DyeableAccoutrementInstance extends AccoutrementInstance implements ICanisTransmogrification {
        private ColorCache color;
        public DyeableAccoutrementInstance(int colorIn) {this(ColorCache.make(colorIn));}
        public DyeableAccoutrementInstance(ColorCache colorIn) {
            super(DyeableAccoutrement.this);
            this.color = colorIn;
        }
        public int getColor() {return this.color.get();}
        public float[] getFloatArray() {return this.color.getFloatArray();}
        @Override public AccoutrementInstance copy() {return new DyeableAccoutrementInstance(this.color);}
        @Override
        public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
            ItemStack stack = playerIn.getItemInHand(handIn);
            DyeColor dyeColor = DyeColor.getColor(stack);
            if (dyeColor != null) {
                int colorNew = REUtil.colorDye(this.color.get(), dyeColor);
                // No change
                if (this.color.is(colorNew)) {
                    return InteractionResult.FAIL;
                }
                this.color = ColorCache.make(colorNew);
                canisIn.consumeItemFromStack(playerIn, stack);
                // Make sure to sync change with client
                canisIn.markAccoutermentsDirty();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
    }
}