package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import net.minecraft.world.item.Items;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.WetSource;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;

public class OceanicRaiderSkill extends SkillInstance {
    public OceanicRaiderSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public void onShakingDry(AbstractCanisEntity canisIn, WetSource source) {
        if (canisIn.level.isClientSide) { // On client do nothing
            return;
        }

        if (source.isWaterBlock()) {
            if (canisIn.getRandom().nextInt(30) < this.level() * 2) {
                canisIn.spawnAtLocation(Items.COD, 1 + canisIn.getRandom().nextInt(2));
            }
            else if (canisIn.getRandom().nextInt(30) < this.level() * 2) {
                canisIn.spawnAtLocation(Items.PRISMARINE_SHARD, 1 + canisIn.getRandom().nextInt(30));
            }
            else if (canisIn.getRandom().nextInt(30) < this.level() * 2) {
                canisIn.spawnAtLocation(Items.PRISMARINE_CRYSTALS, 1 + canisIn.getRandom().nextInt(30));
            }
            else if (canisIn.getRandom().nextInt(30) < this.level() * 2) {
                canisIn.spawnAtLocation(Items.FISHING_ROD, 1);
            }
            else if (canisIn.getRandom().nextInt(30) < this.level() * 2) {
                canisIn.spawnAtLocation(Items.SEA_PICKLE, 1);
            }
            else if (canisIn.getRandom().nextInt(30) < this.level() * 2) {
                canisIn.spawnAtLocation(Items.SEA_LANTERN, 1);
            }
            else if (canisIn.getRandom().nextInt(30) < this.level() * 2) {
                canisIn.spawnAtLocation(Items.TRIDENT, 1 + canisIn.getRandom().nextInt(30));
            }
        }
    }
}