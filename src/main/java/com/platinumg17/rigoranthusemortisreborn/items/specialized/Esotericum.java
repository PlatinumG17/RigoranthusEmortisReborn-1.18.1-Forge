package com.platinumg17.rigoranthusemortisreborn.items.specialized;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibItemNames;
import net.minecraft.advancements.Advancement;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry.defaultItemProperties;

public class Esotericum extends Item {
    public Esotericum() {
        super(defaultItemProperties());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof Player) {
            if (!world.isClientSide) {
                ServerPlayer player = ((ServerPlayer) entity);
                if (!stack.hasTag()) {
                    if (player.getAdvancements().getOrStartProgress(Advancement.Builder.advancement().
                                build(new ResourceLocation("rigoranthusemortisreborn:adventure/listen_to_a_forgotten_record"))).isDone()) {
                        CompoundTag tag = new CompoundTag();
                        tag.putBoolean(EmortisConstants.MOD_ID + ".hasAdvancement", true);
                        stack.setTag(tag);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        if (stack.hasTag()) {
            if (stack.hasTag() && stack.getTag().getBoolean(EmortisConstants.MOD_ID + ".hasAdvancement")) {
                tooltip.add(new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".esotericum").setStyle(Style.EMPTY));
                tooltip.add(new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".esotericum2").setStyle(Style.EMPTY));
            }
        } else {
            tooltip.add(new TranslatableComponent("tooltip." + EmortisConstants.MOD_ID + ".esotericum_obfuscated").setStyle(Style.EMPTY));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}