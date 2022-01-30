package com.platinumg17.rigoranthusemortisreborn.api.apimagic.event;

import com.platinumg17.rigoranthusemortisreborn.magica.ITimedEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellContext;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellResolver;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class DelayedSpellEvent implements ITimedEvent {
    private int duration;
    private final Spell spell;
    private final SpellContext context;
    private final HitResult result;
    private final Level world;
    private final @Nullable LivingEntity shooter;

    public DelayedSpellEvent(int delay, Spell spell, HitResult result, Level world, @Nullable LivingEntity shooter, SpellContext context){
        this.duration = delay;
        this.spell = spell;
        this.result = result;
        this.world = world;
        this.shooter = shooter;
        this.context = context;
    }

    @Override
    public void tick(boolean serverSide) {
        duration--;
        if(duration <= 0 && serverSide){
            resolveSpell();
        }else if(!serverSide && result != null){
            BlockPos hitVec = result instanceof EntityHitResult ? ((EntityHitResult) result).getEntity().blockPosition() : new BlockPos(result.getLocation());
            ParticleUtil.spawnTouch((ClientLevel) world, hitVec, context.colors.toParticleColor());
        }
    }

    public void resolveSpell(){
        if(world == null)
            return;
        SpellResolver.resolveEffects(world, shooter, result, spell, context);
    }
    @Override public boolean isExpired() {
        return duration <= 0 || world == null;
    }
}