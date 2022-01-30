package com.platinumg17.rigoranthusemortisreborn.api.apimagic.util;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ICasterTool;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.CommandWritItem;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellBook;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StackUtil {
    public static @Nonnull ItemStack getHeldSpellbook(Player playerEntity){
        ItemStack book = playerEntity.getMainHandItem().getItem() instanceof SpellBook ? playerEntity.getMainHandItem() : null;
        return book == null ? (playerEntity.getOffhandItem().getItem() instanceof SpellBook ? playerEntity.getOffhandItem() : ItemStack.EMPTY) : book;
    }

    public static @Nullable
    InteractionHand getHeldCasterTool(Player player){
        InteractionHand casterTool = player.getMainHandItem().getItem() instanceof ICasterTool ? InteractionHand.MAIN_HAND: null;
        return casterTool == null ? (player.getOffhandItem().getItem() instanceof ICasterTool ? InteractionHand.OFF_HAND : null) : casterTool;
    }

    public static @Nonnull ItemStack getHeldWhistle(Player playerEntity){
        ItemStack whistle = playerEntity.getMainHandItem().getItem() instanceof CommandWritItem ? playerEntity.getMainHandItem() : null;
        return whistle == null ? (playerEntity.getOffhandItem().getItem() instanceof CommandWritItem ? playerEntity.getOffhandItem() : ItemStack.EMPTY) : whistle;
    }

    public static @Nullable
    InteractionHand getHeldCommandTool(Player player){
        InteractionHand commandTool = player.getMainHandItem().getItem() instanceof CommandWritItem ? InteractionHand.MAIN_HAND: null;
        return commandTool == null ? (player.getOffhandItem().getItem() instanceof CommandWritItem ? InteractionHand.OFF_HAND : null) : commandTool;
    }
}