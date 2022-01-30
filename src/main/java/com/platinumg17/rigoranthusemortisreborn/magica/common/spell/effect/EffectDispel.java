package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.effect;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.IDispellable;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.DispelEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class EffectDispel extends AbstractEffect {
    public static EffectDispel INSTANCE = new EffectDispel();

    private EffectDispel() {
        super(GlyphLib.EffectDispelID, "Dispel");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        if(rayTraceResult.getEntity() instanceof LivingEntity){
            LivingEntity entity = (LivingEntity) rayTraceResult.getEntity();
            Collection<MobEffectInstance> effects = entity.getActiveEffects();
            MobEffectInstance[] array = effects.toArray(new MobEffectInstance[0]);
            for(MobEffectInstance e : array){
                if(e.isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
                    entity.removeEffect(e.getEffect());
            }
            if(entity instanceof IDispellable && entity.isAlive() && entity.getHealth() > 0 && !entity.isRemoved()){
                ((IDispellable) entity).onDispel(shooter);
            }
            MinecraftForge.EVENT_BUS.post(new DispelEvent(rayTraceResult, world, shooter, spellStats.getAugments(), spellContext));
        }
    }

    @Override
    public void onResolveBlock(BlockHitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        if(world.getBlockState(rayTraceResult.getBlockPos()) instanceof IDispellable){
            ((IDispellable) world.getBlockState(rayTraceResult.getBlockPos())).onDispel(shooter);
        }
    }

    @Override
    public boolean wouldSucceed(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        return rayTraceResult instanceof EntityHitResult || (rayTraceResult instanceof BlockHitResult && world.getBlockState(((BlockHitResult) rayTraceResult).getBlockPos()).getBlock() instanceof IDispellable);
    }

    @Override
    public int getDefaultDominionCost() {
        return 30;
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return Items.MILK_BUCKET;
    }

    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        // Augments were sent with the DispelEvent, but there's no use of its augments field.
        return augmentSetOf();
    }

    @Override
    public String getBookDescription() {
        return "Removes any potion effects on the target. Will also dispel tamed summons.";
    }

    @Nonnull
    @Override
    public Set<SpellSchool> getSchools() {
        return setOf(SpellSchools.ABJURATION);
    }
}