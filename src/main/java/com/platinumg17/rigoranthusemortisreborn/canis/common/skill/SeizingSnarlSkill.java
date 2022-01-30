package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.DataKey;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;

public class SeizingSnarlSkill extends SkillInstance {
    public static DataKey<Integer> COOLDOWN = DataKey.make();

    public SeizingSnarlSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }
}