package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.CasterUtil;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellBook;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.StackUtil;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraftforge.network.NetworkEvent;
//import net.minecraftforge.network.PacketDistributor;
//
//import java.util.function.Supplier;
//
//public class PacketUpdateSpellbook{
//
//    String spellRecipe;
//    int cast_slot;
//    String spellName;
//
//    public PacketUpdateSpellbook(){}
//
//    public PacketUpdateSpellbook(String spellRecipe, int cast_slot, String spellName){
//        this.spellRecipe = spellRecipe;
//        this.cast_slot = cast_slot;
//        this.spellName = spellName;
//    }
//
//    //Decoder
//    public PacketUpdateSpellbook(FriendlyByteBuf buf){
//        spellRecipe = buf.readUtf(32767);
//        cast_slot = buf.readInt();
//        spellName = buf.readUtf(32767);
//    }
//
//    //Encoder
//    public void toBytes(FriendlyByteBuf buf){
//        buf.writeUtf(spellRecipe);
//        buf.writeInt(cast_slot);
//        buf.writeUtf(spellName);
//    }
//
//    public void handle(Supplier<NetworkEvent.Context> ctx){
//        ctx.get().enqueueWork(()->{
////            if(ctx.get().getSender() != null){
////                InteractionHand hand = StackUtil.getHeldCasterTool(ctx.get().getSender());
////                if(hand == null)
////                    return;
////                ItemStack stack = ctx.get().getSender().getItemInHand(hand);
////                if(spellRecipe != null){
////                    ISpellCaster caster = CasterUtil.getCaster(stack);
////                    caster.setCurrentSlot(cast_slot);
////                    caster.setSpell(Spell.deserialize(spellRecipe));
////                    caster.setSpellName(spellName);
////                    Networking.INSTANCE.send(PacketDistributor.PLAYER.with(()->ctx.get().getSender()), new PacketUpdateBookGUI(stack));
////                }
////            }
//        });
//        ctx.get().setPacketHandled(true);
//    }
//}