package com.platinumg17.rigoranthusemortisreborn.magica.common.network;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.DelayedSpellEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.EventQueue;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellContext;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class PacketClientDelayEffect {
    public Spell spell;
    public int duration;
    public int shooterID; // -1 is a null entity
    public ParticleColor.IntWrapper color;
    public @Nullable BlockHitResult hitPos;
    public int hitEntityID;

    //Decoder
    public PacketClientDelayEffect(FriendlyByteBuf buf){
        duration = buf.readInt();
        spell = Spell.deserialize(buf.readUtf());
        shooterID = buf.readInt();
        color = ParticleColor.IntWrapper.deserialize(buf.readUtf());
        hitEntityID = buf.readInt();
        if(hitEntityID == -1) {
            hitPos = buf.readBlockHitResult();
        }
    }
    //Encoder
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(duration);
        buf.writeUtf(spell.serialize());
        buf.writeInt(shooterID);
        buf.writeUtf(color.serialize());
        buf.writeInt(hitEntityID);

        if(hitEntityID == -1) {
            buf.writeBlockHitResult(hitPos);
        }
    }

    public PacketClientDelayEffect(int duration, @Nullable LivingEntity shooter, Spell spell, SpellContext context, @Nullable BlockHitResult hitPos, @Nullable Entity hitEntity){
        this.duration = duration;
        this.shooterID = shooter == null ? -1 : shooter.getId();
        this.color = context.colors;
        this.spell = spell;
        this.hitPos = hitPos;
        this.hitEntityID = hitEntity == null ? -1 : hitEntity.getId();
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            Level world = RigoranthusEmortisReborn.proxy.getClientWorld();
            Entity hitEntity = world.getEntity(hitEntityID);
            HitResult result;
            if(hitEntityID == -1){
                result = hitPos;
            }else if(hitEntity == null){
                result = BlockHitResult.miss(new Vec3(0, 0,0), Direction.UP, BlockPos.ZERO);
            }else{
                result = new EntityHitResult(hitEntity);
            }
            EventQueue.getClientQueue().addEvent(new DelayedSpellEvent(duration, spell, result, world, (LivingEntity) world.getEntity(shooterID), new SpellContext(spell, (LivingEntity) world.getEntity(shooterID)).withColors(color)));
        } );
        ctx.get().setPacketHandled(true);
    }
}