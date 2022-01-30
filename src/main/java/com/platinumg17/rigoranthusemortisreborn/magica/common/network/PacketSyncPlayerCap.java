package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.IPlayerCap;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.REPlayerDataCap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncPlayerCap {
    CompoundTag tag;
    //Decoder
    public PacketSyncPlayerCap(FriendlyByteBuf buf){
        tag = buf.readNbt();
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(tag);
    }

    public PacketSyncPlayerCap(CompoundTag famCaps){
        this.tag = famCaps;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            Player playerEntity = RigoranthusEmortisReborn.proxy.getPlayer();
            IPlayerCap cap = CapabilityRegistry.getPlayerDataCap(playerEntity).orElse(new REPlayerDataCap());

            if(cap != null){
                cap.deserializeNBT(tag);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}