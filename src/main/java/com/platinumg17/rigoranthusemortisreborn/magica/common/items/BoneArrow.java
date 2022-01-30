package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.platinumg17.rigoranthusemortisreborn.entity.item.BoneArrowEntity;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BoneArrow extends ArrowItem {

    public BoneArrow() {
        super(MagicItemsRegistry.defaultItemProperties());
        setRegistryName("bone_arrow");
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter) {
        BoneArrowEntity boneArrow = new BoneArrowEntity(world, shooter);
        if(!(shooter instanceof Player))
            return super.createArrow(world, stack, shooter);
        return boneArrow;
    }

//    @Override
//    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
//        tooltip.add(new TranslatableComponent("rigoranthusemortisreborn.bone_arrow.desc"));
//    }
}