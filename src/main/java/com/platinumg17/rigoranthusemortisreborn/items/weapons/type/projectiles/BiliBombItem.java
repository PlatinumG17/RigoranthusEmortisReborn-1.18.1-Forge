package com.platinumg17.rigoranthusemortisreborn.items.weapons.type.projectiles;

import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.entity.item.BiliBombEntitiy;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BiliBombItem extends Item {
    public BiliBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack item = playerIn.getItemInHand(handIn);
        if(!playerIn.isCreative()) {
            item.shrink(1);
        }
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.TNT_PRIMED, SoundSource.NEUTRAL, 1.0F, 1.0F);

        if(!worldIn.isClientSide) {
            BiliBombEntitiy bomb = new BiliBombEntitiy(SpecializedEntityTypes.BILI_BOMB.get(), playerIn, worldIn, playerIn.abilities.mayBuild);
            bomb.setItem(item);
            bomb.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.7F, 1.0F);
            worldIn.addFreshEntity(bomb);
        }
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(item);
    }
}
