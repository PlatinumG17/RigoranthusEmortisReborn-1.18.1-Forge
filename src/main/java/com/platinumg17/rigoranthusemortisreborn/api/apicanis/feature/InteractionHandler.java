package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InteractionHandler {

    private static final List<ICanisItem> HANDLERS = Collections.synchronizedList(new ArrayList<>(4));

    public static void registerHandler(ICanisItem handler) {HANDLERS.add(handler);}

    public static InteractionResult getMatch(@Nullable AbstractCanisEntity canisIn, ItemStack stackIn, Player playerIn, InteractionHand handIn) {
        if (stackIn.getItem() instanceof ICanisItem) {
            ICanisItem item = (ICanisItem) stackIn.getItem();
            InteractionResult result = item.processInteract(canisIn, canisIn.level, playerIn, handIn);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        for (ICanisItem handler : HANDLERS) {
            InteractionResult result = handler.processInteract(canisIn, canisIn.level, playerIn, handIn);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    }
}