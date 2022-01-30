package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.CasterUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.IPlayerCap;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellBook;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketUpdateSpellColors {

    String spellRecipe;
    int castSlot;
    String spellName;
    int r;
    int g;
    int b;

    public PacketUpdateSpellColors(){}

    public PacketUpdateSpellColors(int castSlot, int r, int g, int b){
        this.castSlot = castSlot;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    //Decoder
    public PacketUpdateSpellColors(FriendlyByteBuf buf){
        castSlot = buf.readInt();
        r = buf.readInt();
        g = buf.readInt();
        b = buf.readInt();
    }

    public PacketUpdateSpellColors(int slot, double red, double green, double blue) {
        this(slot, (int)red, (int) green, (int) blue);
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(castSlot);
        buf.writeInt(r);
        buf.writeInt(g);
        buf.writeInt(b);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
//            if(ctx.get().getSender() != null){
//                ItemStack stack = StackUtil.getHeldSpellbook(ctx.get().getSender());
//                if(stack != null && stack.getItem() instanceof SpellBook){
//                    CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
//                    ISpellCaster caster =  CasterUtil.getCaster(stack);
//                    caster.setColor(new ParticleColor.IntWrapper(r, g, b), castSlot);
//                    caster.setCurrentSlot(castSlot);
//                    IPlayerCap cap = CapabilityRegistry.getPlayerDataCap(ctx.get().getSender()).orElse(null);
//                    Networking.INSTANCE.send(PacketDistributor.PLAYER.with(()->ctx.get().getSender()), new PacketUpdateBookGUI(stack));
//                    Networking.INSTANCE.send(PacketDistributor.PLAYER.with(()->ctx.get().getSender()),
//                            new PacketOpenSpellBook(stack, ((SpellBook) stack.getItem()).tier.value));
//
//                }
//            }
        });
        ctx.get().setPacketHandled(true);

    }
}