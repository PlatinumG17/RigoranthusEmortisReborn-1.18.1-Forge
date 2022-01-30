package com.platinumg17.rigoranthusemortisreborn.items.specialized;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgottenRecord extends RecordItem {

    private static final Map<SoundEvent, RecordItem> RECORDS = new HashMap<>();
    public ForgottenRecord(int comparatorValueIn, SoundEvent soundIn, Properties builder) {
        super(comparatorValueIn, soundIn, builder);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".forgotten_record").setStyle(Style.EMPTY));
            tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".forgotten_record2").setStyle(Style.EMPTY));
            tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".forgotten_record3").setStyle(Style.EMPTY));
        } else {
            tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY));
        }
    }

    @Nullable
//    @OnlyIn(Dist.CLIENT)
    public static RecordItem getBySound(SoundEvent soundIn) {
        return RECORDS.get(soundIn);
    }
}