package com.platinumg17.rigoranthusemortisreborn.canis.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.interfaces.ICanisItem;

public class SizeBoneItem extends Item implements ICanisItem {

    private final SizeBoneItem.Type type;

    public static enum Type {
        TINY("tiny_bone"),
        BIG("big_bone");

        String n;

        Type(String n) {
            this.n = n;
        }

        public String getName() {
            return this.n;
        }
    }

    public SizeBoneItem(SizeBoneItem.Type typeIn, Properties properties) {
        super(properties);
        this.type = typeIn;
    }

    @Override
    public InteractionResult processInteract(AbstractCanisEntity canisIn, Level worldIn, Player playerIn, InteractionHand handIn) {
//        if (canisIn.getAge() < 0) {
//            if (!playerIn.level.isClientSide){
//                playerIn.sendMessage(new TranslatableComponent("treat."+this.type.getName()+".too_young"), canisIn.getUUID());
//            }
//            return InteractionResult.FAIL;
//        }
//        else {
            if (!playerIn.abilities.instabuild) {
                playerIn.getItemInHand(handIn).shrink(1);
            }
//            if (!playerIn.level.isClientSide) {
//                canisIn.setCanisSize(canisIn.getCanisSize() + (this.type == Type.BIG ? 1 : -1));
//                canisIn.setCanisSize(canisIn.getCanisSize() + (this.type == Type.BIG ? 1 : -1));
//            }
            return InteractionResult.SUCCESS;
//        }
    }
}