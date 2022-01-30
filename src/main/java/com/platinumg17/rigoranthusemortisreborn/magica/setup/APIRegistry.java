package com.platinumg17.rigoranthusemortisreborn.magica.setup;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.ritual.AbstractRitual;
import com.platinumg17.rigoranthusemortisreborn.magica.common.ritual.RitualSummonersPact;
import com.platinumg17.rigoranthusemortisreborn.magica.common.ritual.RitualHealing;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.effect.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.method.*;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;

public class APIRegistry {

    public static void registerSpells() {
//        registerSpell(MethodProjectile.INSTANCE);
//        registerSpell(MethodTouch.INSTANCE);
//        registerSpell(MethodSelf.INSTANCE);
//        registerSpell(EffectBreak.INSTANCE);
        registerSpell(EffectHarm.INSTANCE);
        registerSpell(EffectIgnite.INSTANCE);
        registerSpell(EffectPhantomBlock.INSTANCE);
        registerSpell(EffectHeal.INSTANCE);
        registerSpell(EffectKnockback.INSTANCE);
        registerSpell(EffectHaste.INSTANCE);
        registerSpell(EffectLight.INSTANCE);
        registerSpell(EffectDispel.INSTANCE);
        registerSpell(EffectFreeze.INSTANCE);
        registerSpell(EffectLaunch.INSTANCE);
        registerSpell(EffectPull.INSTANCE);
        registerSpell(EffectBlink.INSTANCE);
        registerSpell(EffectExplosion.INSTANCE);
        registerSpell(EffectLightning.INSTANCE);
        registerSpell(EffectSlowfall.INSTANCE);
        registerSpell(EffectShield.INSTANCE);
        registerSpell(EffectAquatic.INSTANCE);
        registerSpell(EffectFangs.INSTANCE);
//        registerSpell(EffectSummonVex.INSTANCE);
        registerSpell(EffectStrength.INSTANCE);
//        registerSpell(AugmentAccelerate.INSTANCE);
//        registerSpell(AugmentSplit.INSTANCE);
//        registerSpell(AugmentAmplify.INSTANCE);
//        registerSpell(AugmentAOE.INSTANCE);
//        registerSpell(AugmentExtendTime.INSTANCE);
//        registerSpell(AugmentPierce.INSTANCE);
//        registerSpell(AugmentDampen.INSTANCE);
//        registerSpell(AugmentExtract.INSTANCE);
//        registerSpell(AugmentFortune.INSTANCE);
//        registerSpell(EffectPickup.INSTANCE);
        registerSpell(EffectSnare.INSTANCE);
        registerSpell(EffectLeap.INSTANCE);
        registerSpell(EffectDelay.INSTANCE);
        registerSpell(EffectIntangible.INSTANCE);
        registerSpell(EffectInvisibility.INSTANCE);
//        registerSpell(AugmentDurationDown.INSTANCE);
        registerSpell(EffectWither.INSTANCE);
//        registerSpell(EffectCraft.INSTANCE);
        registerSpell(EffectFlare.INSTANCE);
        registerSpell(EffectColdSnap.INSTANCE);
        registerSpell(EffectGravity.INSTANCE);
        registerSpell(EffectCut.INSTANCE);
//        registerSpell(EffectCrush.INSTANCE);
//        registerSpell(EffectSummonWolves.INSTANCE);
//        registerSpell(EffectSummonSteed.INSTANCE);
//        registerSpell(EffectSummonDecoy.INSTANCE);
        registerSpell(EffectHex.INSTANCE);
//        registerSpell(MethodUnderfoot.INSTANCE);
        registerSpell(EffectGlider.INSTANCE);
//        registerSpell(MethodOrbit.INSTANCE);
////        registerSpell(EffectRune.INSTANCE);
//        registerRitual(new RitualSummonersPact());
//        registerRitual(new RitualHealing());
////        registerRitual(new RitualCadaverSummoning());
//
        registerSpell(EffectBounce.INSTANCE);
//        registerSpell(AugmentSensitive.INSTANCE);
//        registerSpell(EffectWindshear.INSTANCE);
        registerSpell(EffectLinger.INSTANCE);
    }


    public static void registerFamiliar(AbstractFamiliarHolder familiar){
        RigoranthusEmortisRebornAPI.getInstance().registerFamiliar(familiar);
    }

    public static void registerSpell(AbstractSpellPart spellPart) {
        RigoranthusEmortisRebornAPI.getInstance().registerSpell(spellPart.getId(), spellPart);
    }

    public static void registerRitual(AbstractRitual ritual){
        RigoranthusEmortisRebornAPI.getInstance().registerRitual(ritual.getID(), ritual);
    }

    public static void registerSpell(String id, AbstractSpellPart spellPart) {
        RigoranthusEmortisRebornAPI.getInstance().registerSpell(id, spellPart);
    }

    private APIRegistry() {
    }
}