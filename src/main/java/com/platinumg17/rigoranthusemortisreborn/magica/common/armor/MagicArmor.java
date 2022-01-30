package com.platinumg17.rigoranthusemortisreborn.magica.common.armor;

import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.DominionCapability;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionEquipment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class MagicArmor extends ArmorItem implements IDominionEquipment {

    public MagicArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if(world.isClientSide() || world.getGameTime() % 200 !=  0 || stack.getDamageValue() == 0)
            return;

        CapabilityRegistry.getDominion(player).ifPresent(mana -> {
            if(mana.getCurrentDominion() > 20){
                mana.removeDominion(20);
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        });
    }
}