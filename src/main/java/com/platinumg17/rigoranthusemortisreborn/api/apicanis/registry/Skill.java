package com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry;

import net.minecraft.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

/**
 * @author ProPercivalalb
 */
public class Skill extends ForgeRegistryEntry<Skill> {

    @Nullable private String translationKey, translationInfoKey;
    @Nullable private final BiFunction<Skill, Integer, SkillInstance> create;
    /**
     * @param sup
     */
    public Skill(BiFunction<Skill, Integer, SkillInstance> sup) {this.create = sup;}
    public int getMaxLevel() {return 5;}
    public int getLevelCost(int toGoToLevel) {return toGoToLevel;}
    public int getCummulativeCost(int level) {return level * (level + 1) / 2;}

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("skill", RigoranthusEmortisRebornAPI.SKILLS.getKey(this));
        }
        return this.translationKey;
    }

    public String getInfoTranslationKey() {
        if (this.translationInfoKey == null) {
            this.translationInfoKey = this.getTranslationKey() + ".description";
        }
        return this.translationInfoKey;
    }

    public SkillInstance getDefault(int level) {
        if (this.create == null) {
            return new SkillInstance(this, level);
        }
        return this.create.apply(this, level);
    }
    public SkillInstance getDefault() {return this.getDefault(1);}
    public boolean hasRenderer() {return false;}
}