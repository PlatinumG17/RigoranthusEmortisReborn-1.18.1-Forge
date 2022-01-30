package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisAttributes;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import java.util.UUID;

public class  CavalierSkill extends SkillInstance {

    private static final UUID CAVALIER_JUMP = UUID.fromString("7f338124-f223-4630-8515-70ee0bfbc653");

    public CavalierSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public void init(AbstractCanisEntity canisIn) {
        canisIn.setAttributeModifier(CanisAttributes.JUMP_POWER.get(), CAVALIER_JUMP, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractCanisEntity canisIn, int level) {
        canisIn.setAttributeModifier(CanisAttributes.JUMP_POWER.get(), CAVALIER_JUMP, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(AbstractCanisEntity canisIn, UUID uuidIn) {
        if (this.level() > 0) {
            double speed = 0.06D * this.level();

            if (this.level() >= 5) {
                speed += 0.04D;
            }
            return new AttributeModifier(uuidIn, "Cavalier", speed, AttributeModifier.Operation.ADDITION);
        }
        return null;
    }

    @Override
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (stack.isEmpty()) { // Held item
            if (canisIn.canInteract(playerIn) && this.level() > 0) { // Canis
                if (playerIn.getVehicle() == null && !playerIn.isOnGround()) { // Player
                    if (!canisIn.level.isClientSide) {
                        canisIn.setOrderedToSit(false);
                        playerIn.setYRot(canisIn.yRot);
                        playerIn.setXRot(canisIn.xRot);
                        playerIn.startRiding(canisIn);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void livingTick(AbstractCanisEntity canis) {
        if (canis.isVehicle() && canis.getCanisHunger() < 1) {
            canis.getControllingPassenger().sendMessage(new TranslatableComponent("skill.rigoranthusemortisreborn.cavalier.exhausted", canis.getName()), canis.getUUID());
            canis.ejectPassengers();
        }
    }

    @Override
    public InteractionResultHolder<Integer> hungerTick(AbstractCanisEntity canisIn, int hungerTick) {
        if (canisIn.isControlledByLocalInstance()) {
            hungerTick += this.level() < 5 ? 3 : 1;
            return InteractionResultHolder.success(hungerTick);
        }
        return InteractionResultHolder.pass(hungerTick);
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractCanisEntity canisIn, float distance) {
        if (this.level() >= 5) {
            return InteractionResultHolder.success(distance - 1F);
        }
        return InteractionResultHolder.pass(0F);
    }

    @Override
    public InteractionResult hitByEntity(AbstractCanisEntity canisIn, Entity entity) {
        // If the attacking entity is riding block
        return canisIn.isPassengerOfSameVehicle(entity) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}