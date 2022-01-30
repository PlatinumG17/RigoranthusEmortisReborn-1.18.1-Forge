package com.platinumg17.rigoranthusemortisreborn.canis;

import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.CanisInventoriesContainer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.FoodBowlContainer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.TreatBagContainer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.WaywardTravellerContainer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CanisContainerTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, EmortisConstants.MOD_ID);

    public static final RegistryObject<MenuType<FoodBowlContainer>> FOOD_BOWL = register("food_bowl", (windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new FoodBowlContainer(windowId, inv.player.level, pos, inv, inv.player);
    });
    public static final RegistryObject<MenuType<WaywardTravellerContainer>> WAYWARD_TRAVELLER = register("wayward_traveller", (windowId, inv, data) -> {
        Entity entity = inv.player.level.getEntity(data.readInt());
        return entity instanceof CanisEntity ? new WaywardTravellerContainer(windowId, inv, (CanisEntity) entity) : null;
    });
    public static final RegistryObject<MenuType<TreatBagContainer>> TREAT_BAG = register("treat_bag", (windowId, inv, data) -> {
        int slotId = data.readByte();
        return new TreatBagContainer(windowId, inv, slotId, data.readItem());
    });
    public static final RegistryObject<MenuType<CanisInventoriesContainer>> CANIS_INVENTORIES = register("canis_inventories", (windowId, inv, data) -> {
        int noCani = data.readInt();
        List<CanisEntity> cani = new ArrayList<>(noCani);
        SimpleContainerData array = new SimpleContainerData(noCani);
        for (int i = 0; i < noCani; i++) {
            Entity entity = inv.player.level.getEntity(data.readInt());
            if (entity instanceof CanisEntity) {
                cani.add((CanisEntity) entity);
                array.set(i, entity.getId());
            }
        }
        return !cani.isEmpty() ? new CanisInventoriesContainer(windowId, inv, array) : null;
    });

    private static <X extends AbstractContainerMenu, T extends MenuType<X>> RegistryObject<MenuType<X>> register(final String name, final IContainerFactory<X> factory) {
        return register(name, () -> IForgeMenuType.create(factory));
    }

    private static <T extends MenuType<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return CONTAINERS.register(name, sup);
    }
}