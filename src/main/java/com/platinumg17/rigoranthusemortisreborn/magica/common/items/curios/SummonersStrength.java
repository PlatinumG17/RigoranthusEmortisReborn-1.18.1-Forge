package com.platinumg17.rigoranthusemortisreborn.magica.common.items.curios;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.ISummon;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.SpellCastEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.SummonEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.item.ISpellModifierItem;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.CuriosUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.ModItem;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.LibItemNames;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.method.MethodOrbit;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.method.MethodSelf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID)
public class SummonersStrength extends ModItem implements ISpellModifierItem {

    public SummonersStrength(Properties properties) {
        super(properties);
        setRegistryName(LibItemNames.SUMMONERS_STRENGTH);
    }

    public static List<AbstractCastMethod> sympatheticMethods = new ArrayList<>();

    static{
        sympatheticMethods.add(MethodSelf.INSTANCE);
        sympatheticMethods.add(MethodOrbit.INSTANCE);
    }

    @Override
    public SpellStats.Builder applyItemModifiers(ItemStack stack, SpellStats.Builder builder, AbstractSpellPart spellPart, HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellContext spellContext) {
        builder.addDamageModifier(1.0f);
        return builder;
    }

    public SpellStats.Builder getSimpleStats(SpellStats.Builder builder){
        builder.addDamageModifier(1.0f);
        return builder;
    }

    public static boolean containsThis(Level world, Entity entity){
        if(!world.isClientSide && entity instanceof Player) {
            IItemHandlerModifiable items = CuriosUtil.getAllWornItems((LivingEntity) entity).orElse(null);
            if(items != null){
                for(int i = 0; i < items.getSlots(); i++){
                    Item item = items.getStackInSlot(i).getItem();
                    if(item instanceof SummonersStrength){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void summonedEvent(SummonEvent event){
        if(!event.world.isClientSide && SummonersStrength.containsThis(event.world, event.summon.getOwner((ServerLevel) event.world))){
            event.summon.setTicksLeft(event.summon.getTicksLeft() * 2);
            if (event.summon.getLivingEntity() != null) {
                event.summon.getLivingEntity().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 500, 2));
                event.summon.getLivingEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 1));
            }
        }
    }
    @SubscribeEvent
    public static void castSpell(SpellCastEvent event){
        if(!event.getWorld().isClientSide && event.getEntity() instanceof Player &&  SummonersStrength.containsThis(event.getWorld(), event.getEntityLiving())){
            if(event.spell.getCastMethod() != null && sympatheticMethods.contains(event.spell.getCastMethod())){
                for(LivingEntity i : event.getWorld().getEntitiesOfClass(LivingEntity.class, new AABB(event.getEntityLiving().blockPosition()).inflate(30), (l) -> l instanceof ISummon)){
                    if(event.getEntityLiving().equals(((ISummon) i).getOwner((ServerLevel) event.getWorld()))){
                        EntitySpellResolver spellResolver = new EntitySpellResolver(new SpellContext(event.spell, i).withColors(event.context.colors));
                        spellResolver.onCast(ItemStack.EMPTY, i, i.level);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void summonDeathEvent(SummonEvent.Death event){
        if(!event.world.isClientSide && SummonersStrength.containsThis(event.world, event.summon.getOwner((ServerLevel) event.world))){
            DamageSource source = event.source;
            if(source != null && source.getEntity() != null){
                source.getEntity().hurt(DamageSource.thorns(source.getEntity()).bypassArmor(), 5.0f);
            }
        }
    }
}