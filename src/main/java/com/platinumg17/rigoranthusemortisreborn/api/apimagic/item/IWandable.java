package com.platinumg17.rigoranthusemortisreborn.api.apimagic.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

public interface IWandable {
    /**
     * When the wand has made 2 connections, block -> block, block -> entity, entity -> block, or entity -> entity.
     * The FIRST IWandable in the chain is called.
     */
    default void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity){}
    /**
     * When the wand has made 2 connections, block -> block, block -> entity, entity -> block, or entity -> entity.
     * The LAST IWandable in the chain is called.
     */
    default void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {}
    /**
     * Called on the time of wanding.
     */
    default void onWanded(Player playerEntity){}
}