package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateBookGUI {

    public CompoundTag tag;
    //Decoder
    public PacketUpdateBookGUI(FriendlyByteBuf buf){
        tag = buf.readNbt();
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(tag);
    }

    public PacketUpdateBookGUI(CompoundTag tag){
        this.tag = tag;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
//            if(RigoranthusEmortisReborn.proxy.getMinecraft().screen instanceof GuiSpellBook)
//                ((GuiSpellBook) RigoranthusEmortisReborn.proxy.getMinecraft().screen).spell_book_tag = tag;
        } );
        ctx.get().setPacketHandled(true);
    }
}