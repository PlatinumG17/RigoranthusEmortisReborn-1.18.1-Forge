package com.platinumg17.rigoranthusemortisreborn.items.armor.armorsets;

import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.platinumg17.rigoranthusemortisreborn.core.init.Registration.ARMOR_PROP;
import static com.platinumg17.rigoranthusemortisreborn.items.armor.RigoranthusArmorMaterial.PHANTASMAL_NETHERITE;

public class PhantasmalArmor extends ArmorItem {
    private boolean previousEquip = false;

    public PhantasmalArmor(EquipmentSlot slot) {
        super(PHANTASMAL_NETHERITE, slot, ARMOR_PROP);
    }


    public static Component newTip(String tip) {
        return new TranslatableComponent("tooltip.rigoranthusemortisreborn" + tip).setStyle(Style.EMPTY);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tip, flagIn);
        if (Screen.hasShiftDown()) {
            tip.add(newTip(".phantasmal_ingot"));
            tip.add(newTip(".phantasmal_ingot2"));
            tip.add(newTip(".phantasmal_ingot3"));
            tip.add(newTip(".phantasmal_ingot4"));
            tip.add(newTip(".phantasmal_ingot5"));
            tip.add(newTip(".phantasmal_ingot6"));
            tip.add(newTip(".phantasmal_ingot7"));
            tip.add(newTip(".phantasmal_ingot8"));
            tip.add(newTip(".phantasmal_ingot9"));
            tip.add(newTip(".phantasmal_ingot10"));
            tip.add(newTip(".phantasmal_ingot11"));
            tip.add(newTip(".phantasmal_ingot12"));
            tip.add(newTip(".phantasmal_ingot13"));
            tip.add(newTip(".phantasmal_ingot14"));
            tip.add(newTip(".phantasmal_ingot15"));
            tip.add(newTip(".phantasmal_ingot16"));
            tip.add(newTip(".phantasmal_ingot17"));
            tip.add(newTip(".phantasmal_ingot18"));
            tip.add(newTip(".phantasmal_ingot19"));
        } else {
            tip.add(newTip(".hold_shift"));
        }
    }

    public void onArmorTick(ItemStack itemStack, Level world, Player player) {
        if (Config.enableArmorSetBonuses.get()) {
            ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
            ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack helm = player.getItemBySlot(EquipmentSlot.HEAD);
            if (boots.getItem() == Registration.PHANTASMAL_N_BOOTS && legs.getItem() == Registration.PHANTASMAL_N_LEGS && chest.getItem() == Registration.PHANTASMAL_N_CHEST && helm.getItem() == Registration.PHANTASMAL_N_HELMET) {
                player.addEffect(new MobEffectInstance(ModPotions.PHANTASMAL_SET_BONUS, 5, 1));
                this.previousEquip = true;
            } else if (this.previousEquip) {
                player.removeEffect(ModPotions.PHANTASMAL_SET_BONUS);
                this.previousEquip = false;
            }
        }
    }
}