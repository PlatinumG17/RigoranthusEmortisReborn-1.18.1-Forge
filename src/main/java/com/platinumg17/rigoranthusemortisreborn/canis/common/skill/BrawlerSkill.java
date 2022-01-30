package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import java.util.UUID;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisAttributes;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BrawlerSkill extends SkillInstance {

    private static final UUID BRAWLER_DAMAGE_ID = UUID.fromString("9abeafa9-3913-4b4c-b46e-0f1548fb19b3");
    private static final UUID BRAWLER_CRIT_CHANCE_ID = UUID.fromString("f07b5d39-a8cc-4d32-b458-6efdf1dc6836");
    private static final UUID BRAWLER_CRIT_BONUS_ID = UUID.fromString("e19e0d42-6ee3-4ee1-af1c-7519af4354cd");

    public BrawlerSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public void init(AbstractCanisEntity canisIn) {
        canisIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, BRAWLER_DAMAGE_ID, this::createBrawlerModifier);
        canisIn.setAttributeModifier(CanisAttributes.CRIT_CHANCE.get(), BRAWLER_CRIT_CHANCE_ID, this::createBrawlerCritChance);
        canisIn.setAttributeModifier(CanisAttributes.CRIT_BONUS.get(), BRAWLER_CRIT_BONUS_ID, this::createBrawlerCritBonus);
    }

    @Override
    public void set(AbstractCanisEntity canisIn, int levelBefore) {
        canisIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, BRAWLER_DAMAGE_ID, this::createBrawlerModifier);
        canisIn.setAttributeModifier(CanisAttributes.CRIT_CHANCE.get(), BRAWLER_CRIT_CHANCE_ID, this::createBrawlerCritChance);
        canisIn.setAttributeModifier(CanisAttributes.CRIT_BONUS.get(), BRAWLER_CRIT_BONUS_ID, this::createBrawlerCritBonus);
    }

    public AttributeModifier createBrawlerModifier(AbstractCanisEntity canisIn, UUID uuidIn) {
        if (this.level() > 0) {
            double damageBonus = this.level();
            if (this.level() >= 5) {
                damageBonus += 2;
            }
            return new AttributeModifier(uuidIn, "Brawler", damageBonus, AttributeModifier.Operation.ADDITION);
        }
        return null;
    }

    public AttributeModifier createBrawlerCritChance(AbstractCanisEntity canisIn, UUID uuidIn) {
        if (this.level() <= 0) {
            return null;
        }
        double damageBonus = 0.15D * this.level();
        if (this.level() >= 5) {
            damageBonus = 1D;
        }
        return new AttributeModifier(uuidIn, "Brawler Crit Chance", damageBonus, AttributeModifier.Operation.ADDITION);
    }

    public AttributeModifier createBrawlerCritBonus(AbstractCanisEntity canisIn, UUID uuidIn) {
        if (this.level() <= 0) {
            return null;
        }
        return new AttributeModifier(uuidIn, "Brawler Crit Bonus", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}