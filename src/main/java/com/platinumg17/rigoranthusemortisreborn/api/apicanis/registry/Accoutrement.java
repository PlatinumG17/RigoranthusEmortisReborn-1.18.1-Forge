package com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import javax.annotation.Nullable;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ItemLike;
import net.minecraft.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IRegistryDelegate;

public class Accoutrement extends ForgeRegistryEntry<Accoutrement> {

    @Nullable
    private String translationKey;
    @Deprecated // Do not call directly use Accoutrement#getReturnItem
    private final Supplier<ItemStack> stack;

    @Deprecated // Do not call directly use Accoutrement#getType
    private final Supplier<? extends AccoutrementType> type;

    public Accoutrement(Supplier<? extends AccoutrementType> typeIn, Supplier<ItemStack> stackIn, int x) {
        this.type = typeIn;
        this.stack = stackIn;
    }
    public Accoutrement(Supplier<? extends AccoutrementType> typeIn, Supplier<? extends ItemLike> itemIn) {this(typeIn, () -> new ItemStack(itemIn.get()), 0);}
    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("accoutrement", RigoranthusEmortisRebornAPI.ACCOUTERMENTS.getKey(this));
        }
        return this.translationKey;
    }
    public byte getRenderLayer() {return AccoutrementInstance.RENDER_DEFAULT;}
    public AccoutrementInstance getDefault() {return new AccoutrementInstance(this);}
    public AccoutrementInstance createInstance(FriendlyByteBuf buf) {return this.getDefault();}
    public AccoutrementInstance read(CompoundTag compound) {return this.getDefault();}
    public void write(AccoutrementInstance instance, FriendlyByteBuf buf) {}
    public void write(AccoutrementInstance instance, CompoundTag compound) {}
    public AccoutrementInstance createFromStack(ItemStack stackIn) {return this.getDefault();}
    public ItemStack getReturnItem(AccoutrementInstance instance) {return this.stack.get();}
    public final AccoutrementType getType() {return this.type.get();}
    public <T extends Accoutrement> boolean of(Supplier<T> accoutrementIn) {return this.of(accoutrementIn.get());}
    public <T extends Accoutrement> boolean of(T accoutrementIn) {return this.of(accoutrementIn.delegate);}
    public <T extends Accoutrement> boolean of(IRegistryDelegate<T> accoutrementDelegateIn) {return accoutrementDelegateIn.equals(this.delegate);}
}