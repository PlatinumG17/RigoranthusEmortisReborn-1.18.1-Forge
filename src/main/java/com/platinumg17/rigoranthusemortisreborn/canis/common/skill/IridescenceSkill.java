package com.platinumg17.rigoranthusemortisreborn.canis.common.skill;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisSkills;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.WaywardTravellerItemHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.InventoryUtil;
import org.apache.commons.lang3.tuple.Pair;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;

public class IridescenceSkill extends SkillInstance {
    public IridescenceSkill(Skill SkillIn, int levelIn) {
        super(SkillIn, levelIn);
    }

    @Override
    public void tick(AbstractCanisEntity canisIn) {
        if (canisIn.tickCount % 10 == 0 && canisIn.isTame()) {
            BlockPos pos = canisIn.blockPosition();
            BlockState torchState = Blocks.TORCH.defaultBlockState();

            if (canisIn.level.getMaxLocalRawBrightness(canisIn.blockPosition()) < 8 && canisIn.level.isEmptyBlock(pos) && torchState.canSurvive(canisIn.level, pos)) {
                WaywardTravellerItemHandler inventory = canisIn.getSkill(CanisSkills.WAYWARD_TRAVELLER)
                        .map((inst) -> inst.cast(WaywardTravellerSkill.class).inventory()).orElse(null);

                // If null might be because no occurance of wayward traveller is found
                if (this.level() >= 5) {
                    canisIn.level.setBlockAndUpdate(pos, torchState);
                }
                else if (inventory != null) { // If null might be because no occurance of wayward traveller is found
                    Pair<ItemStack, Integer> foundDetails = InventoryUtil.findStack(inventory, (stack) -> stack.getItem() == Items.TORCH);
                    if (foundDetails != null && !foundDetails.getLeft().isEmpty()) {
                        ItemStack torchStack = foundDetails.getLeft();
                        canisIn.consumeItemFromStack(canisIn, torchStack);
                        inventory.setStackInSlot(foundDetails.getRight(), torchStack);
                        canisIn.level.setBlockAndUpdate(pos, torchState);
                    }
                }
            }
        }
    }
}