package com.platinumg17.rigoranthusemortisreborn.magica.common.event;

//import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.FamiliarSummonEvent;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.MaxDominionCalcEvent;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.SpellModifierEvent;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar.FamiliarEntity;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.familiar.ISpellCastListener;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Predicate;
//
//@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
//public class FamiliarEvents {
//
//    public static List<FamiliarEntity> getFamiliars(Predicate<FamiliarEntity> predicate){
//        List<FamiliarEntity> stale = new ArrayList<>();
//        List<FamiliarEntity> matching = new ArrayList<>();
//        for(FamiliarEntity familiarEntity : FamiliarEntity.FAMILIAR_SET){
//            if(familiarEntity.isRemoved() || familiarEntity.terminatedFamiliar || familiarEntity.getOwner() == null) {
//                stale.add(familiarEntity);
//            }else if(predicate.test(familiarEntity)){
//                matching.add(familiarEntity);
//            }
//        }
//        FamiliarEntity.FAMILIAR_SET.removeAll(stale);
//        return matching;
//    }
//
//    @SubscribeEvent
//    public static void summonEvent(FamiliarSummonEvent event) {
//        for(FamiliarEntity entity : getFamiliars((f) -> true)){
//            if(entity.getOwner() != null && entity.getOwner().equals(event.owner)){
//                entity.onFamiliarSpawned(event);
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void maxDominionCalc(MaxDominionCalcEvent event) {
//        for(FamiliarEntity entity : getFamiliars(familiarEntity -> true)){
//            if(!entity.isAlive())
//                return;
//            if(entity.getOwner() != null && entity.getOwner().equals(event.getEntity())){
//                event.setMax((int) (event.getMax() -  event.getMax() * entity.getDominionReserveModifier()));
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void spellResolveEvent(SpellModifierEvent event) {
//        for(FamiliarEntity entity : getFamiliars((familiarEntity -> familiarEntity instanceof ISpellCastListener))){
//            if(entity instanceof ISpellCastListener){
//                ((ISpellCastListener) entity).onModifier(event);
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void modifierEvent(SpellModifierEvent event) {
//        for(FamiliarEntity entity :getFamiliars((familiarEntity -> familiarEntity instanceof ISpellCastListener))){
//            if(entity instanceof ISpellCastListener){
//                ((ISpellCastListener) entity).onModifier(event);
//            }
//        }
//    }
//}