package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ISpellHotkeyListener;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.StackUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketHotkeyPressed {

    public enum Key{
        NEXT,
        PREVIOUS
    }
    Key key;

    public PacketHotkeyPressed(Key key){
        this.key = key;
    }

    //Decoder
    public PacketHotkeyPressed(FriendlyByteBuf buf){
        this.key = Key.valueOf(buf.readUtf());
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(key.name());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer player = ctx.get().getSender();
            if(player != null){
                InteractionHand hand = StackUtil.getHeldCommandTool(player);
                if(hand == null)
                    return;
                ItemStack stack = player.getItemInHand(hand);
                if(!(stack.getItem() instanceof ISpellHotkeyListener hotkeyListener)){
                    return;
                }
                if(key == Key.NEXT){
                    hotkeyListener.onNextKeyPressed(stack, player);
                }else if(key == Key.PREVIOUS){
                    hotkeyListener.onPreviousKeyPressed(stack, player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}