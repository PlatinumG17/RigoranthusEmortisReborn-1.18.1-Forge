package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Zombie;

import java.util.List;

public class UndertakerSkill extends SkillInstance {
    public UndertakerSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public void livingTick(AbstractCanisEntity canisIn) {
        if (canisIn.level.isClientSide || canisIn.tickCount % 2 == 0) {
            return;
        }
        if (this.level() >= 0) {
            byte damage = 1;

            if (this.level() >= 5) {
                damage = 2;
            }
            List<Zombie> list = canisIn.level.getEntitiesOfClass(
                    Zombie.class, canisIn.getBoundingBox().inflate(this.level() * 3, 4D, this.level() * 3), EntitySelector.ENTITY_STILL_ALIVE
            );
            for (Zombie zombie : list) {
                if (canisIn.getRandom().nextInt(10) == 0) {
                    zombie.hurt(DamageSource.GENERIC, damage);
                    canisIn.level.addParticle(ParticleTypes.ENCHANT, canisIn.getRandomX(1.5D), canisIn.getRandomY() + 0.8D, canisIn.getRandomZ(1.5D), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}