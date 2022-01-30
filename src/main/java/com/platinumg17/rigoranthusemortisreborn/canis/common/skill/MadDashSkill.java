package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;

import java.util.UUID;

public class MadDashSkill extends SkillInstance {

    private static final UUID MAD_DASH_BOOST_ID = UUID.fromString("50671e49-1ded-4097-902b-78bb6b178772");

    public MadDashSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public void init(AbstractCanisEntity canisIn) {
        canisIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, MAD_DASH_BOOST_ID, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractCanisEntity canisIn, int level) {
        canisIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, MAD_DASH_BOOST_ID, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(AbstractCanisEntity canisIn, UUID uuidIn) {
        if (this.level() > 0) {
            double speed = 0.03D * this.level();
            if (this.level() >= 5) {
                speed += 0.04D;
            }
            return new AttributeModifier(uuidIn, "Mad Dash", speed, AttributeModifier.Operation.ADDITION);
        }
        return null;
    }
}