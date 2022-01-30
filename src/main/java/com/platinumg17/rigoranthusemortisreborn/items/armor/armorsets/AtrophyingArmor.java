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
import static com.platinumg17.rigoranthusemortisreborn.items.armor.RigoranthusArmorMaterial.ATROPHYING_NETHERITE;

public class AtrophyingArmor extends ArmorItem {
    private boolean previousEquip = false;

    public AtrophyingArmor(EquipmentSlot slot) {
        super(ATROPHYING_NETHERITE, slot, ARMOR_PROP);
    }

    public static Component newTip(String tip) {
        return new TranslatableComponent("tooltip.rigoranthusemortisreborn" + tip).setStyle(Style.EMPTY);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tip, flagIn);
        if (Screen.hasShiftDown()) {
            tip.add(newTip(".atrophying_ingot"));
            tip.add(newTip(".atrophying_ingot2"));
            tip.add(newTip(".atrophying_ingot3"));
            tip.add(newTip(".atrophying_ingot4"));
            tip.add(newTip(".atrophying_ingot5"));
            tip.add(newTip(".atrophying_ingot6"));
            tip.add(newTip(".atrophying_ingot7"));
            tip.add(newTip(".atrophying_ingot8"));
            tip.add(newTip(".atrophying_ingot9"));
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
            if (boots.getItem() == Registration.ATROPHYING_N_BOOTS && legs.getItem() == Registration.ATROPHYING_N_LEGS && chest.getItem() == Registration.ATROPHYING_N_CHEST && helm.getItem() == Registration.ATROPHYING_N_HELMET) {
                player.addEffect(new MobEffectInstance(ModPotions.ATROPHYING_SET_BONUS, 5, 1));
                this.previousEquip = true;
            } else if (this.previousEquip) {
                player.removeEffect(ModPotions.ATROPHYING_SET_BONUS);
                this.previousEquip = false;
            }
        }
    }
}