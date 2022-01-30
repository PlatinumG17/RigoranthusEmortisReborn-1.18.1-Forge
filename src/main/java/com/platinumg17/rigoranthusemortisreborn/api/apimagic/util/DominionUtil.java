package com.platinumg17.rigoranthusemortisreborn.api.apimagic.util;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionCap;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionEquipment;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.DominionRegenCalcEvent;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.event.MaxDominionCalcEvent;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.magica.common.armor.MagicArmor;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.DominionJarTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment.EnchantmentRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.EntityFollowProjectile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DominionUtil {

    public static int getPlayerDiscounts(LivingEntity e){
        AtomicInteger discounts = new AtomicInteger();
        CuriosUtil.getAllWornItems(e).ifPresent(items ->{
            for(int i = 0; i < items.getSlots(); i++){
                Item item = items.getStackInSlot(i).getItem();
                if(item instanceof IDominionEquipment)
                    discounts.addAndGet(((IDominionEquipment) item).getDominionDiscount(items.getStackInSlot(i)));
            }
        });
        return discounts.get();
    }

    public static int getMaxDominion(Player e){
        IDominionCap dominion = CapabilityRegistry.getDominion(e).orElse(null);
        if(dominion == null)
            return 0;
        int max = Config.INIT_MAX_DOMINION.get();
        for(ItemStack i : e.getAllSlots()){
            if(i.getItem() instanceof IDominionEquipment){
                max += (((IDominionEquipment) i.getItem()).getMaxDominionBoost(i));
            }
            max += (Config.DOMINION_BOOST_BONUS.get() * EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.DOMINION_BOOST_ENCHANTMENT, i));
        }
        IItemHandlerModifiable items = CuriosUtil.getAllWornItems(e).orElse(null);
        if(items != null){
            for(int i = 0; i < items.getSlots(); i++){
                Item item = items.getStackInSlot(i).getItem();
                if(item instanceof IDominionEquipment)
                    max += (((IDominionEquipment) item).getMaxDominionBoost(items.getStackInSlot(i)));
            }
        }
        int tier = dominion.getBookTier();
        int numGlyphs = dominion.getGlyphBonus() > 5 ? dominion.getGlyphBonus() - 5 : 0;
        max += numGlyphs * 1.1;//Config.GLYPH_MAX_BONUS.get();
        max += tier * 1.1;//Config.TIER_MAX_BONUS.get();

        MaxDominionCalcEvent event = new MaxDominionCalcEvent(e, max);
        MinecraftForge.EVENT_BUS.post(event);
        max = event.getMax();
        return max;
    }

    public static double getDominionRegen(Player e) {
        IDominionCap dominion = CapabilityRegistry.getDominion(e).orElse(null);
        if(dominion == null)
            return 0;
        double regen = Config.INIT_DOMINION_REGEN.get();
        for(ItemStack i : e.getAllSlots()){
            if(i.getItem() instanceof MagicArmor){
                MagicArmor armor = ((MagicArmor) i.getItem());
                regen += armor.getDominionRegenBonus(i);
            }
            regen += Config.DOMINION_REGEN_ENCHANT_BONUS.get() * EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.DOMINION_REGEN_ENCHANTMENT, i);
        }
        IItemHandlerModifiable items = CuriosUtil.getAllWornItems(e).orElse(null);
        if(items != null){
            for(int i = 0; i < items.getSlots(); i++){
                Item item = items.getStackInSlot(i).getItem();
                if(item instanceof IDominionEquipment)
                    regen += ((IDominionEquipment) item).getDominionRegenBonus(items.getStackInSlot(i));
            }
        }
        int tier = dominion.getBookTier();
        double numGlyphs = dominion.getGlyphBonus() > 5 ? dominion.getGlyphBonus() - 5 : 0;
        regen += numGlyphs * 1;// Config.GLYPH_REGEN_BONUS.get();
        regen += tier;
        if(e.getEffect(ModPotions.DOMINION_REGEN_EFFECT) != null)
            regen += Config.DOMINION_REGEN_POTION.get() * (1 + e.getEffect(ModPotions.DOMINION_REGEN_EFFECT).getAmplifier());
        DominionRegenCalcEvent event = new DominionRegenCalcEvent(e, regen);
        MinecraftForge.EVENT_BUS.post(event);
        regen = event.getRegen();
        return regen;
    }

    /**
     * Searches for nearby dominion jars that have enough dominion.
     * Returns the position where the dominion was taken, or null if none were found.
     */
    @Nullable
    public static BlockPos takeDominionNearby(BlockPos pos, Level world, int range, int dominion){
        Optional<BlockPos> loc = BlockPos.findClosestMatch(pos, range, range, (b) -> world.getBlockEntity(b) instanceof DominionJarTile && ((DominionJarTile) world.getBlockEntity(b)).getCurrentDominion() >= dominion);
        if(!loc.isPresent())
            return null;
        DominionJarTile tile = (DominionJarTile) world.getBlockEntity(loc.get());
        tile.removeDominion(dominion);
        return loc.get();
    }

    public static @Nullable BlockPos takeDominionNearbyWithParticles(BlockPos pos, Level world, int range, int dominion){
        BlockPos result = takeDominionNearby(pos,world,range,dominion);
        if(result != null){
            EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(world, result, pos);
            world.addFreshEntity(aoeProjectile);
        }
        return result;
    }

    /**
     * Searches for nearby dominion jars that have enough dominion.
     * Returns the position where the dominion was taken, or null if none were found.
     */
    public static boolean hasDominionNearby(BlockPos pos, Level world, int range, int dominion){
        Optional<BlockPos> loc = BlockPos.findClosestMatch(pos, range, range, (b) -> world.getBlockEntity(b) instanceof DominionJarTile && ((DominionJarTile) world.getBlockEntity(b)).getCurrentDominion() >= dominion);
        return loc.isPresent();
    }

    @Nullable
    public static BlockPos canGiveDominionClosest(BlockPos pos, Level world, int range){
        Optional<BlockPos> loc = BlockPos.findClosestMatch(pos, range, range, (b) ->  world.getBlockEntity(b) instanceof DominionJarTile && ((DominionJarTile) world.getBlockEntity(b)).canAcceptDominion());
        return loc.orElse(null);
    }

    public static List<BlockPos> canGiveDominionAny(BlockPos pos, Level world, int range){
        List<BlockPos> posList = new ArrayList<>();
        BlockPos.withinManhattanStream(pos, range, range, range).forEach(b ->{
            if(world.getBlockEntity(b) instanceof DominionJarTile && ((DominionJarTile) world.getBlockEntity(b)).canAcceptDominion())
                posList.add(b.immutable());
        });
        return posList;
    }
}