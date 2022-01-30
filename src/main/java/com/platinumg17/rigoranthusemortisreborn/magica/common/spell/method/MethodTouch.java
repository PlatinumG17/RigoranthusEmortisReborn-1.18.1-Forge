package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.method;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketREEffect;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentSensitive;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class MethodTouch extends AbstractCastMethod {
    public static MethodTouch INSTANCE = new MethodTouch();

    private MethodTouch() {
        super(GlyphLib.MethodTouchID, "Touch");
    }

    @Override
    public int getDefaultDominionCost() {
        return 5;
    }

    @Override
    public void onCast(ItemStack stack, LivingEntity caster, Level world, SpellStats spellStats, SpellContext context, SpellResolver resolver) { }

    @Override
    public void onCastOnBlock(UseOnContext context, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        Level world = context.getLevel();
        BlockHitResult res = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), false);
        resolver.onResolveEffect(world, context.getPlayer(), res);
        resolver.expendDominion(context.getPlayer());
        Networking.sendToNearby(context.getLevel(), context.getPlayer(),
                new PacketREEffect(PacketREEffect.EffectType.BURST, res.getBlockPos(), spellContext.colors));
    }

    @Override
    public void onCastOnBlock(BlockHitResult res, LivingEntity caster, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        resolver.onResolveEffect(caster.getCommandSenderWorld(),caster, res);
        resolver.expendDominion(caster);
        Networking.sendToNearby(caster.level, caster, new PacketREEffect(PacketREEffect.EffectType.BURST, res.getBlockPos(), spellContext.colors));
    }

    @Override
    public void onCastOnEntity(ItemStack stack, LivingEntity caster, Entity target, InteractionHand hand, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        resolver.onResolveEffect(caster.getCommandSenderWorld(), caster, new EntityHitResult(target));
        if(spellContext.getType() != SpellContext.CasterType.RUNE)
            resolver.expendDominion(caster);
        Networking.sendToNearby(caster.level, caster, new PacketREEffect(PacketREEffect.EffectType.BURST, target.blockPosition(), spellContext.colors));
    }

    @Override
    public boolean wouldCastSuccessfully(@Nullable ItemStack stack, LivingEntity playerEntity, Level world, SpellStats spellStats, SpellResolver resolver) {
        return false;
    }

    @Override
    public boolean wouldCastOnBlockSuccessfully(UseOnContext context, SpellStats spellStats, SpellResolver resolver) {
        Level world = context.getLevel();
        BlockHitResult res = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), false);
        return resolver.wouldAllEffectsDoWork(res, world, context.getPlayer(), spellStats);
    }

    @Override
    public boolean wouldCastOnBlockSuccessfully(BlockHitResult blockRayTraceResult, LivingEntity caster, SpellStats spellStats, SpellResolver resolver) {
        return resolver.wouldAllEffectsDoWork(blockRayTraceResult, caster.getCommandSenderWorld(), caster, spellStats);
    }

    @Override
    public boolean wouldCastOnEntitySuccessfully(@Nullable ItemStack stack, LivingEntity caster, Entity target, InteractionHand hand, SpellStats spellStats, SpellResolver resolver) {
        return resolver.wouldAllEffectsDoWork(new EntityHitResult(target), caster.getCommandSenderWorld(), caster, spellStats);
    }

    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(AugmentSensitive.INSTANCE);
    }

    @Override
    public String getBookDescription() {
        return "Applies spells at the block or entity that is targeted.";
    }


    @Override
    public Item getCraftingReagent() {
        return Items.STONE_BUTTON;
    }

    @Override
    public boolean defaultedStarterGlyph() {
        return true;
    }
}