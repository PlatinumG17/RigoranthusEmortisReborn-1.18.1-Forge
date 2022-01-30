package com.platinumg17.rigoranthusemortisreborn.util;

import com.google.common.collect.Lists;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

import java.util.List;

public class StringHelper {

    public static List<Component> getShiftInfoGui()
    {
        List<Component> list = Lists.newArrayList();
        list.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.gui_close"));
        Component tooltip1 = new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_hold_shift");
        Component shift = new TextComponent("[Shift]");
        Component tooltip2 = new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_shift_more_options");
        tooltip1.copy().withStyle(ChatFormatting.GRAY);
        shift.copy().withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC);
        tooltip2.copy().withStyle(ChatFormatting.GRAY);
        list.add(tooltip1.copy().append(shift).append(tooltip2));
        return list;
    }

    public static Component getShiftInfoText()
    {
        Component tooltip1 = new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold");
        Component shift = new TextComponent("[Shift]");
        Component tooltip2 = new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".for_details");
        tooltip1.copy().withStyle(ChatFormatting.GRAY);
        shift.copy().withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC);
        tooltip2.copy().withStyle(ChatFormatting.GRAY);
        return tooltip1.copy().append(shift).append(tooltip2);
    }
}