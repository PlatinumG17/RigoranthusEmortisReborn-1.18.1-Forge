package com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;

public class CanisSkillData extends CanisData {
    public final Skill skill;

    public CanisSkillData(int entityId, Skill skill) {
        super(entityId);
        this.skill = skill;
    }
}
