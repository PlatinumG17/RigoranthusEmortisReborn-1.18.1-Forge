package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

//import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public class PacketOpenSpellBook {
//    public CompoundTag tag;
//    public int tier;
//    public String unlockedSpells;
//
//    //Decoder
//    public PacketOpenSpellBook(FriendlyByteBuf buf){
//        tag = buf.readNbt();
//        tier = buf.readInt();
//        unlockedSpells = buf.readUtf(32767);
//    }
//
//    //Encoder
//    public void toBytes(FriendlyByteBuf buf){
//        buf.writeNbt(tag);
//        buf.writeInt(tier);
//        buf.writeUtf(unlockedSpells);
//    }
//
//    public PacketOpenSpellBook(CompoundTag tag, int tier, String unlockedSpells){
//        this.tag = tag;
//        this.tier = tier;
//        this.unlockedSpells = unlockedSpells;
//    }
//
//    public void handle(Supplier<NetworkEvent.Context> ctx){
////        ctx.get().enqueueWork(()-> GuiSpellBook.open(RigoranthusEmortisRebornAPI.getInstance(), tag, tier, unlockedSpells));
//        ctx.get().setPacketHandled(true);
//    }
//}