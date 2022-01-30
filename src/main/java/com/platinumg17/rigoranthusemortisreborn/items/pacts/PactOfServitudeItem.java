package com.platinumg17.rigoranthusemortisreborn.items.pacts;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PactOfServitudeItem extends Item {
    public PactOfServitudeItem(Item.Properties properties) { super(properties); }

    public static Component newTip(String tip) {
        return new TranslatableComponent("tooltip.pact.rigoranthusemortisreborn" + tip).setStyle(Style.EMPTY);
    }
    public static Component holdShift(String tip) {
        return new TranslatableComponent("tooltip.rigoranthusemortisreborn" + tip).setStyle(Style.EMPTY);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tip, flagIn);
        if (Screen.hasShiftDown()) {
            tip.add(newTip(".servitude"));
            tip.add(newTip(".servitude2"));
            tip.add(newTip(".servitude3"));
            tip.add(newTip(".servitude4"));
        } else {
            tip.add(holdShift(".hold_shift"));
        }
    }
}