package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.FamiliarCap;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.IFamiliarCap;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public class PacketSyncFamiliars {
//    CompoundTag tag;
//    //Decoder
//    public PacketSyncFamiliars(FriendlyByteBuf buf){
//        tag = buf.readNbt();
//    }
//
//    //Encoder
//    public void toBytes(FriendlyByteBuf buf){
//        buf.writeNbt(tag);
//    }
//
//    public PacketSyncFamiliars(CompoundTag famCaps){
//        this.tag = famCaps;
//    }
//
//    public void handle(Supplier<NetworkEvent.Context> ctx){
//        ctx.get().enqueueWork(()->{
//            Player playerEntity = RigoranthusEmortisReborn.proxy.getPlayer();
//            IFamiliarCap cap = FamiliarCap.getFamiliarCap(playerEntity).orElse(null);
//            if(cap != null){
//                cap.setUnlockedFamiliars(FamiliarCap.deserializeFamiliars(tag));
//            }
//        });
//        ctx.get().setPacketHandled(true);
//    }
//}