package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.EventQueue;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketTimedEvent;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.BlockUtil;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.item.ItemUseContext;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.Level;
//
//public class REItemDebug extends ModItem{
//    public REItemDebug() {
//        super(new Item.Properties());
//        setRegistryName("debug");
//    }
//
////    @Override
////    public InteractionResult useOn(UseOnContext context) {
////        if(!context.getLevel().isClientSide){
////
////            for(BlockPos p : BlockPos.betweenClosed(context.getClickedPos().immutable().east(20).north(20), context.getClickedPos().immutable().south(20).west(20))){
////                if(context.getLevel().random.nextFloat() < 0.03) {
////                    double distance = BlockUtil.distanceFrom(p, context.getClickedPos());
////                    int time = (int) (40 + distance * 5 + context.getLevel().random.nextInt(10));
////                    EruptionEvent event = new EruptionEvent(context.getLevel(), p.immutable(), time, (int) (distance*2));
////                    EventQueue.getServerInstance().addEvent(event);
////                    Networking.sendToNearby(context.getLevel(), context.getClickedPos(), new PacketTimedEvent(event.serialize(new CompoundTag())));
////                }
////            }
////        }
////        return super.useOn(context);
////    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level world, Player playerIn, InteractionHand handIn) {
//
//        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
//    }
//}