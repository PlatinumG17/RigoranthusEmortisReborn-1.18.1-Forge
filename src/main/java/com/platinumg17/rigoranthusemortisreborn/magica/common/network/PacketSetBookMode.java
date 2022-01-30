package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.StackUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellBook;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSetBookMode {

    public CompoundTag tag;
    //Decoder
    public PacketSetBookMode(FriendlyByteBuf buf){
        tag = buf.readNbt();
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(tag);
    }

    public PacketSetBookMode(CompoundTag tag){
        this.tag = tag;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ctx.get().enqueueWork(()-> {
                ServerPlayer sender = ctx.get().getSender();
                if (sender == null) return;

                ItemStack stack = StackUtil.getHeldSpellbook(ctx.get().getSender());
                if (stack.getItem() instanceof SpellBook) {
                    stack.setTag(tag);
                }
            });
        } );
        ctx.get().setPacketHandled(true);
    }
}