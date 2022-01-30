package com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IRegistryDelegate;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisTransmogrification;

public class SkillInstance implements ICanisTransmogrification {
    protected final IRegistryDelegate<Skill> skillDelegate;
    protected int level;
    public SkillInstance(Skill SkillIn, int levelIn) {
        this(SkillIn.delegate, levelIn);
    }
    public SkillInstance(Skill SkillIn) {
        this(SkillIn.delegate, 1);
    }
    public SkillInstance(IRegistryDelegate<Skill> skillDelegateIn, int levelIn) {
        this.skillDelegate = skillDelegateIn;
        this.level = levelIn;
    }
    public Skill getSkill() {
        return this.skillDelegate.get();
    }
    public final int level() {
        return this.level;
    }
    public final void setLevel(int levelIn) {
        this.level = levelIn;
    }
    public boolean of(Supplier<Skill> SkillIn) {
        return this.of(SkillIn.get());
    }
    public boolean of(Skill SkillIn) {
        return this.of(SkillIn.delegate);
    }
    public boolean of(IRegistryDelegate<Skill> skillDelegateIn) {
        return skillDelegateIn.equals(this.skillDelegate);
    }
    public SkillInstance copy() {
        return this.skillDelegate.get().getDefault(this.level);
    }
    public void writeToNBT(AbstractCanisEntity canisIn, CompoundTag compound) {compound.putInt("level", this.level());}
    public void readFromNBT(AbstractCanisEntity canisIn, CompoundTag compound) {this.setLevel(compound.getInt("level"));}
    public void writeToBuf(FriendlyByteBuf buf) {
        buf.writeInt(this.level());
    }
    public void readFromBuf(FriendlyByteBuf buf) {
        this.setLevel(buf.readInt());
    }
    public final void writeInstance(AbstractCanisEntity canisIn, CompoundTag compound) {
        ResourceLocation rl = this.skillDelegate.name();
        if (rl != null) {
            compound.putString("type", rl.toString());
        }
        this.writeToNBT(canisIn, compound);
    }

    public static Optional<SkillInstance> readInstance(AbstractCanisEntity canisIn, CompoundTag compound) {
        ResourceLocation rl = ResourceLocation.tryParse(compound.getString("type"));
        if (RigoranthusEmortisRebornAPI.SKILLS.containsKey(rl)) {
            SkillInstance inst = RigoranthusEmortisRebornAPI.SKILLS.getValue(rl).getDefault();
            inst.readFromNBT(canisIn, compound);
            return Optional.of(inst);
        } else {
            RigoranthusEmortisRebornAPI.LOGGER.warn("Failed to load Skill {}", rl);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends SkillInstance> T cast(Class<T> type) {
        if (this.getClass().isAssignableFrom(type)) {
            return (T) this;
        } else {
            throw new RuntimeException("Could not cast " + this.getClass().getName() + " to " + type.getName());
        }
    }
    @Override public String toString() {return String.format("%s [skill: %s, level: %d]", this.getClass().getSimpleName(), skillDelegate.name(), this.level);}

    /**
     * Called when ever this instance is first added to a canis, this is called when
     * the level is first set on the canis or when it is loaded from NBT and when the
     * skills are synced to the client
     *
     * @param canisIn The canis
     */
    public void init(AbstractCanisEntity canisIn) {
    }

    /**
     * Called when the level of the canis changes
     * Is not called when the canis is loaded from NBT
     *
     * @param canisIn The canis
     */
    public void set(AbstractCanisEntity canisIn, int levelBefore) {
    }

    public boolean hasRenderer() {
        return this.getSkill().hasRenderer();
    }
}