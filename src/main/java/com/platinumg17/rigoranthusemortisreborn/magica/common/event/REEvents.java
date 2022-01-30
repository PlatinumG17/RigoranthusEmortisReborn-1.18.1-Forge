package com.platinumg17.rigoranthusemortisreborn.magica.common.event;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.DominionRegenCalcEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.MaxDominionCalcEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.SpellModifierEvent;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class REEvents {

    @SubscribeEvent
    public static void maxCalc(MaxDominionCalcEvent e){ }

    @SubscribeEvent
    public static void regenCalc(DominionRegenCalcEvent e){
        if(e.getEntityLiving() != null && e.getEntityLiving().getEffect(ModPotions.HEX_EFFECT) != null){
            e.setRegen(e.getRegen()/2.0);
        }
    }

    @SubscribeEvent
    public static void spellCalc(SpellModifierEvent e){
        if(e.caster != null && e.caster.hasEffect(ModPotions.SPELL_DAMAGE_EFFECT)){
            e.builder.addDamageModifier(1.5f * (e.caster.getEffect(ModPotions.SPELL_DAMAGE_EFFECT).getAmplifier() + 1));
        }
    }
}