package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AnimBlockItem extends BlockItem implements IAnimatable {
    AnimationFactory manager = new AnimationFactory(this);

    public AnimBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void registerControllers(AnimationData animationData) { }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }
}