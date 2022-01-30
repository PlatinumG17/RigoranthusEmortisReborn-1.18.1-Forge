package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisFoodHandler;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisFoodPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FoodHandler {

    private static final List<ICanisFoodHandler> HANDLERS = Collections.synchronizedList(new ArrayList<>(4));
    private static final List<ICanisFoodPredicate> DYN_PREDICATES = Collections.synchronizedList(new ArrayList<>(2));

    public static void registerHandler(ICanisFoodHandler handler) {
        HANDLERS.add(handler);
    }
    public static void registerDynPredicate(ICanisFoodPredicate handler) {
        DYN_PREDICATES.add(handler);
    }

    public static Optional<ICanisFoodPredicate> isFood(ItemStack stackIn) {
        for (ICanisFoodPredicate predicate : DYN_PREDICATES) {
            if (predicate.isFood(stackIn)) {
                return Optional.of(predicate);
            }
        }
        if (stackIn.getItem() instanceof ICanisFoodHandler) {
            if (((ICanisFoodHandler) stackIn.getItem()).isFood(stackIn)) {
                return Optional.of((ICanisFoodHandler) stackIn.getItem());
            }
        }
        for (ICanisFoodHandler handler : HANDLERS) {
            if (handler.isFood(stackIn)) {
                return Optional.of(handler);
            }
        }
        return Optional.empty();
    }

    public static Optional<ICanisFoodHandler> getMatch(@Nullable AbstractCanisEntity canisIn, ItemStack stackIn, @Nullable Entity entityIn) {
        for (ICanisFoodHandler handler : canisIn.getFoodHandlers()) {
            if (handler.canConsume(canisIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }
        if (stackIn.getItem() instanceof ICanisFoodHandler) {
            if (((ICanisFoodHandler) stackIn.getItem()).canConsume(canisIn, stackIn, entityIn)) {
                return Optional.of((ICanisFoodHandler) stackIn.getItem());
            }
        }
        for (ICanisFoodHandler handler : HANDLERS) {
            if (handler.canConsume(canisIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }
        return Optional.empty();
    }
}