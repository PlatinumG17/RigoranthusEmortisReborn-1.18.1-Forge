package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ExperienceGem extends ModItem{

    public ExperienceGem(Properties properties) {
        super(properties);
    }

    public ExperienceGem(Properties properties, String registryName){
        this(properties);
        setRegistryName(EmortisConstants.MOD_ID, registryName);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".exp_gem").setStyle(Style.EMPTY));
            tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".exp_gem2").setStyle(Style.EMPTY));
        } else {
            tooltip.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".hold_shift").setStyle(Style.EMPTY));
        }
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        if(!world.isClientSide) {
            if(playerEntity.isCrouching()){
                playerEntity.giveExperiencePoints(getValue() * playerEntity.getItemInHand(hand).getCount());
                playerEntity.getItemInHand(hand).shrink( playerEntity.getItemInHand(hand).getCount());
            }else{
                playerEntity.giveExperiencePoints(getValue());
                playerEntity.getItemInHand(hand).shrink(1);
            }
        }
        return InteractionResultHolder.pass(playerEntity.getItemInHand(hand));
    }
    public abstract int getValue();
}