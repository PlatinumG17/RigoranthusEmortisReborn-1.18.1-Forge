package com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisFoodHandler;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface ICanis {

    public AbstractCanisEntity getCanis();
    public void untame();
    public boolean canInteract(LivingEntity playerIn);
    public EnumMode getMode();
    public EnumClothColor getClothColor();
    public EnumShadesColor getShadesColor();
    public CanisLevel getCanisLevel();
//    public CanisStage getStage();
    public void increaseLevel(CanisLevel.Type typeIn);

    /**
     * Convenience method to get the level of a skill
     * @param skillGetter A getter function, typically a {@link RegistryObject <com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill>} would be provided
     * @return The level of the skill
     */
    default int getCanisLevel(Supplier<? extends Skill> skillGetter) {
        return this.getCanisLevel(skillGetter.get());
    }

    /**
     * Returns the level of the given skill
     * @param skillIn The {@link Skill}
     * @return The level of the skill
     */
    public int getCanisLevel(Skill skillIn);

    default Optional<SkillInstance> getSkill(Supplier<? extends Skill> skillGetter) {
        return this.getSkill(skillGetter.get());
    }

    public Optional<SkillInstance> getSkill(Skill skillIn);

//    public int getCanisSize();
//    public void setCanisSize(int size);

    public float getMaxHunger();
    public float getCanisHunger();
    public void addHunger(float add);
    public void setCanisHunger(float hunger);

    public boolean addAccoutrement(AccoutrementInstance inst);
    public List<AccoutrementInstance> getAccouterments();
    public List<AccoutrementInstance> removeAccouterments();
    public void markAccoutermentsDirty();

    public float getWagAngle(float limbSwing, float limbSwingAmount, float partialTickTime);
    public float getShakeAngle(float partialTicks, float offset);
    public float getInterestedAngle(float partialTicks);

    public boolean isLying();

    public List<ICanisFoodHandler> getFoodHandlers();

    @Deprecated
    public <T> void setData(DataKey<T> key, T value);
    /**
     * Tries to put the object in the map, does nothing if the key already exists
     */
    @Deprecated
    public <T> void setDataIfEmpty(DataKey<T> key, T value);
    @Deprecated
    public <T> T getData(DataKey<T> key);
    @Deprecated
    public <T> T getDataOrGet(DataKey<T> key, Supplier<T> other);
    @Deprecated
    public <T> T getDataOrDefault(DataKey<T> key, T other);
    @Deprecated
    public <T> boolean hasData(DataKey<T> key);
}