package com.platinumg17.rigoranthusemortisreborn.magica.client;

//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
//import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.block.TableBlock;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment.EnchantmentRegistry;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellBook;
//import net.minecraft.client.Minecraft;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.event.entity.player.ItemTooltipEvent;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EmortisConstants.MOD_ID)
//public class PlayerEvent {
//
//    private static final Minecraft minecraft = Minecraft.getInstance();
//
//    @SubscribeEvent
//    public static void onTick(final TickEvent.RenderTickEvent evt) {
//    }
//
//    @SubscribeEvent
//    public static void onBlock(final PlayerInteractEvent.RightClickBlock event) {
//        Player entity = event.getPlayer();
//        if(!event.getWorld().isClientSide || event.getHand() != InteractionHand.MAIN_HAND || event.getWorld().getBlockState(event.getPos()).getBlock() instanceof TableBlock)
//            return;
//        if(entity.getItemInHand(event.getHand()).getItem() instanceof SpellBook){
//            event.setCanceled(true);
//        }
//    }
//
////    @SubscribeEvent
////    public static void onTooltip(final ItemTooltipEvent event){
////        ItemStack stack = event.getItemStack();
////        int level = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.REFLEX_SUMMON_ENCHANTMENT, stack);
////        if(level > 0 && stack.hasTag() && stack.getTag().contains("spell")){
////            Spell spell = Spell.deserialize(stack.getTag().getString("spell"));
//////            if(level > 0 && new ReactiveCaster(stack).getSpell().isValid()){
//////                Spell spell = new ReactiveCaster(stack).getSpell();
////            event.getToolTip().add(new TextComponent(spell.getDisplayString()));
////        }
////    }
//
//    @SubscribeEvent
//    public static void onItem(final PlayerInteractEvent.RightClickItem event) {
//        Player entity = event.getPlayer();
//        if(!event.getWorld().isClientSide || event.getHand() != InteractionHand.MAIN_HAND)
//            return;
//        if(entity.getItemInHand(event.getHand()).getItem() instanceof SpellBook) {
//            event.setCanceled(true);
//        }
//    }
//}