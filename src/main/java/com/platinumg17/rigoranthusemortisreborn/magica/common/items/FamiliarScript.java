package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.IPlayerCap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class FamiliarScript extends ModItem {
    public AbstractFamiliarHolder familiar;

    public FamiliarScript(AbstractFamiliarHolder familiar){
        super("familiar_" + familiar.id);
        this.familiar = familiar;
    }
    public FamiliarScript(Properties properties) {
        super(properties);
    }

    public FamiliarScript(Properties properties, String registryName) {
        super(properties, registryName);
    }

    public FamiliarScript(String registryName) {
        super(registryName);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if(worldIn.isClientSide || handIn != InteractionHand.MAIN_HAND)
            return super.use(worldIn, playerIn, handIn);

        IPlayerCap familiarCap = CapabilityRegistry.getPlayerDataCap(playerIn).orElse(null);
        if(familiarCap != null){
            if(familiarCap.ownsFamiliar(familiar)){
                playerIn.sendMessage(new TranslatableComponent("rigoranthusemortisreborn.familiar.owned"), Util.NIL_UUID);
                return super.use(worldIn, playerIn, handIn);
            }
            familiarCap.unlockFamiliar(familiar);
            CapabilityRegistry.EventHandler.syncPlayerCap(playerIn);
            playerIn.sendMessage(new TranslatableComponent("rigoranthusemortisreborn.familiar.unlocked"), Util.NIL_UUID);
            playerIn.getItemInHand(handIn).shrink(1);
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
        tooltip2.add(new TranslatableComponent("rigoranthusemortisreborn.familiar.script"));
    }
}