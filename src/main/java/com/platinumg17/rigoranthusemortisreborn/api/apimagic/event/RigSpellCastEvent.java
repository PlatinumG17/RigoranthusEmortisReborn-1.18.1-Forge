package com.platinumg17.rigoranthusemortisreborn.api.apimagic.event;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.Spell;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellContext;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentDurationDown;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.effect.EffectGlider;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.effect.EffectLaunch;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.method.MethodSelf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class RigSpellCastEvent extends LivingEvent {

    public Spell spell;
    public SpellContext context;

    public Spell launchSelf(/*Spell spell*/){
        return new Spell().add(MethodSelf.INSTANCE)
                .add(EffectLaunch.INSTANCE, 2)
                .add(EffectGlider.INSTANCE)
                .add(AugmentDurationDown.INSTANCE);
    }

    @Deprecated
    public RigSpellCastEvent(LivingEntity entity, Spell spell){
        super(entity);
        this.spell = new Spell().add(MethodSelf.INSTANCE)
                .add(EffectLaunch.INSTANCE, 2)
                .add(EffectGlider.INSTANCE)
                .add(AugmentDurationDown.INSTANCE);
        context = new SpellContext(spell, entity);
    }

    public RigSpellCastEvent(LivingEntity entity, Spell spell, SpellContext context){
        super(entity);
        this.spell = spell;
        this.context = context;
    }

    public Level getWorld(){
        return this.getEntityLiving().level;
    }
}