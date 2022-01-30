package com.platinumg17.rigoranthusemortisreborn.api.apimagic.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nonnull;

public class CuriosUtil {
    public static LazyOptional<IItemHandlerModifiable> getAllWornItems(@Nonnull LivingEntity living) {
        return CuriosApi.getCuriosHelper().getEquippedCurios(living);
    }
}