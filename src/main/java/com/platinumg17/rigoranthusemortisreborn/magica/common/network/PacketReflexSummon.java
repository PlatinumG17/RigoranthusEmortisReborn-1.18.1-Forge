package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import com.platinumg17.rigoranthusemortisreborn.magica.common.event.ReflexSummonEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketReflexSummon {

    public PacketReflexSummon(){}

    //Decoder
    public PacketReflexSummon(FriendlyByteBuf buf){
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf){}

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer serverPlayer = ctx.get().getSender();
            if(serverPlayer!= null){
                ItemStack stack = serverPlayer.getMainHandItem();
                ReflexSummonEvents.castSummon(ctx.get().getSender(), stack);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}