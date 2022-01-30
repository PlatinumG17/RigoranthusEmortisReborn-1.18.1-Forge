package com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.screens;

import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import com.platinumg17.rigoranthusemortisreborn.canis.common.block.tileentity.FoodBowlTileEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.CanisInventoriesContainer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.TreatBagContainer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.WaywardTravellerContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class CanisScreens {

//    public static class WaywardTravellerContainerProvider implements MenuProvider {
//
//        private AbstractCanisEntity canis;
//
//        public WaywardTravellerContainerProvider(AbstractCanisEntity canisIn) {
//            this.canis = canisIn;
//        }
//
//        @Override
//        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
//            return new WaywardTravellerContainer(windowId, inventory, this.canis);
//        }
//
//        @Override
//        public Component getDisplayName() {
//            return new TranslatableComponent("container.rigoranthusemortisreborn.wayward_traveller");
//        }
//    }

    public static class CanisInventoriesContainerProvider implements MenuProvider {

        private List<CanisEntity> canis;

        public CanisInventoriesContainerProvider(List<CanisEntity> canisIn) {
            this.canis = canisIn;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            SimpleContainerData array = new SimpleContainerData(this.canis.size());
            for (int i = 0; i < array.getCount(); i++) {
                array.set(i, this.canis.get(i).getId());
            }
            return new CanisInventoriesContainer(windowId, inventory, array);
        }

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.rigoranthusemortisreborn.canis_inventories");
        }
    }

    public static class TreatBagContainerProvider implements MenuProvider {

        private ItemStack stack;
        private int slotId;

        public TreatBagContainerProvider(ItemStack stackIn, int slotId) {
            this.stack = stackIn;
            this.slotId = slotId;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new TreatBagContainer(windowId, inventory, this.slotId, this.stack);
        }

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.rigoranthusemortisreborn.treat_bag");
        }
    }

//    public static void openWaywardTravellerScreen(ServerPlayer player, AbstractCanisEntity canisIn) {
//        if (canisIn.isAlive()) {
//            NetworkHooks.openGui(player, new WaywardTravellerContainerProvider(canisIn), (buf) -> {
//                buf.writeInt(canisIn.getId());
//            });
//        }
//    }

    public static void openCanisInventoriesScreen(ServerPlayer player, List<CanisEntity> canisIn) {
        if (!canisIn.isEmpty()) {
            NetworkHooks.openGui(player, new CanisInventoriesContainerProvider(canisIn), (buf) -> {
                buf.writeInt(canisIn.size());
                for (CanisEntity canis : canisIn) {
                    buf.writeInt(canis.getId());
                }
            });
        }
    }

    public static void openFoodBowlScreen(ServerPlayer player, FoodBowlTileEntity foodBowl) {
        NetworkHooks.openGui(player, foodBowl, foodBowl.getBlockPos());
    }

    public static void openTreatBagScreen(ServerPlayer player, ItemStack stackIn, int slotId) {
        if (stackIn.getItem() == CanisItems.TREAT_BAG.get()) {
            NetworkHooks.openGui(player, new TreatBagContainerProvider(stackIn, slotId), buf -> {
                buf.writeVarInt(slotId);
                buf.writeItem(stackIn);
            });
        }
    }
}