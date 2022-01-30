package com.platinumg17.rigoranthusemortisreborn.api.apimagic.event;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

public class SpellResolveEvent extends Event {
    public Level world;
    public @Nullable LivingEntity shooter;
    public HitResult rayTraceResult;
    public Spell spell;
    public SpellContext context;

    public SpellResolveEvent(Level world, LivingEntity shooter, HitResult result, Spell spell, SpellContext spellContext){
        this.world = world;
        this.shooter = shooter;
        this.rayTraceResult = result;
        this.spell = spell;
        this.context = spellContext;
    }

    /**
     * Fired before a spell is resolved. Can be cancelled to stop resolving.
     */
    public static class Pre extends SpellResolveEvent{
        public Pre(Level world, LivingEntity shooter, HitResult result, Spell spell, SpellContext spellContext){
            super(world, shooter, result, spell, spellContext);
        }
        @Override public boolean isCancelable() {
            return true;
        }
    }

    /**
     * Fired after a spell has resolved its effects. Cannot be canceled.
     */
    public static class Post extends SpellResolveEvent{
        public Post(Level world, LivingEntity shooter, HitResult result, Spell spell, SpellContext spellContext) {
            super(world, shooter, result, spell, spellContext);
        }
        @Override public boolean isCancelable() {
            return false;
        }
    }
}