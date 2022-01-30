package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import java.util.UUID;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.ForgeMod;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;

public class CallousedSolesSkill extends SkillInstance {

    private static final UUID CALLOUSED_SOLES_BOOST_ID = UUID.fromString("1f002df0-9d35-49c6-a863-b8945caa4af4");

    public CallousedSolesSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public void init(AbstractCanisEntity canisIn) {
        canisIn.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), CALLOUSED_SOLES_BOOST_ID, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractCanisEntity canisIn, int level) {
        canisIn.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), CALLOUSED_SOLES_BOOST_ID, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(AbstractCanisEntity canisIn, UUID uuidIn) {
        if (this.level() >= 5) {
            return new AttributeModifier(uuidIn, "Calloused Soles", -0.065D, AttributeModifier.Operation.ADDITION);
        }
        return null;
    }

    @Override
    public InteractionResult canTrample(AbstractCanisEntity canisIn, BlockState state, BlockPos pos, float fallDistance) {
        return this.level() >= 5 ? InteractionResult.FAIL : InteractionResult.PASS;
    }

    @Override
    public InteractionResult onLivingFall(AbstractCanisEntity canisIn, float distance, float damageMultiplier) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractCanisEntity canisIn, float distance) {
        if (this.level() > 0) {
            return InteractionResultHolder.success(distance - this.level() * 3);
        }
        return InteractionResultHolder.pass(0F);
    }
}