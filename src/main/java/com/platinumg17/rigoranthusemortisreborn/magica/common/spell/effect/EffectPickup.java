package com.platinumg17.rigoranthusemortisreborn.magica.common.spell.effect;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.lib.GlyphLib;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.augment.AugmentAOE;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.IPickupResponder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class EffectPickup extends AbstractEffect {
    public static EffectPickup INSTANCE = new EffectPickup();

    private EffectPickup() {
        super(GlyphLib.EffectPickupID, "Item Pickup");
    }

    @Override
    public void onResolve(HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        BlockPos pos = new BlockPos(rayTraceResult.getLocation());
        int expansion = 2 + spellStats.getBuffCount(AugmentAOE.INSTANCE);

        List<ItemEntity> entityList = world.getEntitiesOfClass(ItemEntity.class, new AABB(pos.east(expansion).north(expansion).above(expansion),
                pos.west(expansion).south(expansion).below(expansion)));
        for(ItemEntity i : entityList){

            if(isRealPlayer(shooter) && spellContext.castingTile == null){
                ItemStack stack = i.getItem();
                Player player = (Player) shooter;
//                VoidItem.tryVoiding(player, stack);
                if(!player.addItem(stack)){
                    i.setPos(player.getX(), player.getY(), player.getZ());
                }

            }else if(shooter instanceof IPickupResponder){
                i.setItem(((IPickupResponder) shooter).onPickup(i.getItem()));
            }else if(spellContext.castingTile instanceof IPickupResponder){
                i.setItem(((IPickupResponder) spellContext.castingTile).onPickup(i.getItem()));
            }
        }
    }

    @Override
    public boolean wouldSucceed(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats spellStats, SpellContext spellContext) {
        return true;
    }

    @Nonnull
    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(AugmentAOE.INSTANCE);
    }

    @Override
    public String getBookDescription() {
        return "Picks up nearby items in a medium radius where this spell is activated. The range may be expanded with AOE.";
    }

    @Nullable
    @Override
    public Item getCraftingReagent() {
        return Items.HOPPER;
    }

    @Override
    public int getDefaultDominionCost() {
        return 10;
    }

    @Nonnull
    @Override
    public Set<SpellSchool> getSchools() {
        return setOf(SpellSchools.MANIPULATION);
    }
}