package com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IRegistryDelegate;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

public class AccoutrementInstance {

    public static final byte RENDER_TOP = 1;
    public static final byte RENDER_BOTTOM = -1;
    public static final byte RENDER_DEFAULT = 0;
    public static final Comparator<AccoutrementInstance> RENDER_SORTER = Comparator.comparing(AccoutrementInstance::getRenderIndex);

    @Deprecated // Do not call directly use AccoutrementInstance#getAccoutrement
    private final Accoutrement accoutrement;

    public AccoutrementInstance(Accoutrement typeIn) {
        this.accoutrement = typeIn;
    }

    public Accoutrement getAccoutrement() {
        return this.accoutrement;
    }

    public <T extends Accoutrement> boolean of(Supplier<T> accoutrementIn) {
        return this.accoutrement.of(accoutrementIn);
    }

    public <T extends Accoutrement> boolean of(T accoutrementIn) {
        return this.accoutrement.of(accoutrementIn);
    }

    public <T extends Accoutrement> boolean of(IRegistryDelegate<T> accoutrementDelegateIn) {
        return this.accoutrement.of(accoutrementDelegateIn);
    }

    public <T extends AccoutrementType> boolean ofType(Supplier<T> accoutrementTypeIn) {
        return this.ofType(accoutrementTypeIn.get());
    }

    public <T extends AccoutrementType> boolean ofType(T accoutrementTypeIn) {
        return this.ofType(accoutrementTypeIn.delegate);
    }

    public <T extends AccoutrementType> boolean ofType(IRegistryDelegate<T> accoutrementTypeDelegateIn) {
        return accoutrementTypeDelegateIn.equals(this.accoutrement.getType().delegate);
    }

    public AccoutrementInstance copy() {
        return new AccoutrementInstance(this.accoutrement);
    }

    public ItemStack getReturnItem() {
        return this.getAccoutrement().getReturnItem(this);
    }

    public byte getRenderIndex() {
        return this.getAccoutrement().getRenderLayer();
    }

    public final void writeInstance(CompoundTag compound) {
        ResourceLocation rl = this.getAccoutrement().getRegistryName();
        if (rl != null) {
            compound.putString("type", rl.toString());
        }
        this.getAccoutrement().write(this, compound);
    }

    /**
     * Reads the accoutrement from the given NBT data. If the accoutrement id is not
     * valid or an exception is thrown during loading then an empty optional
     * is returned.
     */
    public static Optional<AccoutrementInstance> readInstance(CompoundTag compound) {
        ResourceLocation rl = null;
        try {
            rl = ResourceLocation.tryParse(compound.getString("type"));
            if (RigoranthusEmortisRebornAPI.ACCOUTERMENTS.containsKey(rl)) {
                Accoutrement type = RigoranthusEmortisRebornAPI.ACCOUTERMENTS.getValue(rl);
                return Optional.of(type.read(compound));
            } else {
                RigoranthusEmortisRebornAPI.LOGGER.warn("Failed to load accoutrement {}", compound);
            }
        } catch (Exception e) {
            RigoranthusEmortisRebornAPI.LOGGER.warn("Failed to load accoutrement {}", rl);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T extends AccoutrementInstance> T cast(Class<T> type) {
        if (type.isAssignableFrom(this.getClass())) {
            return (T) this;
        } else {
            throw new RuntimeException("Could not cast " + this.getClass().getName() + " to " + type.getName());
        }
    }
}