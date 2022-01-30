package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.magica.common.util.PortUtil;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.DataKey;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisItem;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;

import java.util.List;

public class CanisShearsItem extends Item implements ICanisItem {

    private static DataKey<Integer> COOLDOWN = DataKey.make();

    public CanisShearsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (canisIn.isOwnedBy(playerIn)) {
            List<AccoutrementInstance> accouterments = canisIn.getAccouterments();
            if (accouterments.isEmpty()) {
                if (!canisIn.isTame()) {
                    return InteractionResult.CONSUME;
                }
                if (!worldIn.isClientSide) {
                    int cooldownLeft = canisIn.getDataOrDefault(COOLDOWN, canisIn.tickCount) - canisIn.tickCount;
                    if (cooldownLeft <= 0) {
                        worldIn.broadcastEntityEvent(canisIn, EmortisConstants.EntityState.CANIS_SMOKE);
                        if (playerIn.getOffhandItem().getItem() == ItemInit.PACT_OF_SERVITUDE.get()) {
                            canisIn.untame();
                        }
                    }
                }
                return InteractionResult.SUCCESS;
            }
            if (!worldIn.isClientSide) {
                for (AccoutrementInstance inst : accouterments) {
                    ItemStack returnItem = inst.getReturnItem();
                    canisIn.spawnAtLocation(returnItem, 1);
                }
                canisIn.removeAccouterments();
                canisIn.setData(COOLDOWN, canisIn.tickCount + 20);
                PortUtil.sendMessage(playerIn, new TranslatableComponent("entity.rigoranthusemortisreborn.canis.beware"));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}