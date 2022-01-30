package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

//import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibItemNames;
//import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.core.Registry;
//import net.minecraft.world.level.Level;
//import vazkii.patchouli.api.PatchouliAPI;
//
//import javax.annotation.Nonnull;
//
//public class EmorticOrigins extends ModItem{
//
//    public EmorticOrigins() {
//        super(MagicItemsRegistry.defaultItemProperties().stacksTo(1), LibItemNames.EMORTIC_ORIGINS);
//    }
//
//    @Nonnull
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
//        ItemStack stack = playerIn.getItemInHand(handIn);
//
//        if(playerIn instanceof ServerPlayer) {
//            ServerPlayer player=  (ServerPlayer) playerIn;
//            PatchouliAPI.instance.openBookGUI((ServerPlayer) playerIn, Registry.ITEM.getKey(this));
//        }
//
//        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
//    }
//}