package com.platinumg17.rigoranthusemortisreborn.canis;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.DyeableAccoutrement;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.*;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.CanisLevel;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Accoutrement;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.CanisSummoningCharmItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.CadaverBoneItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.SizeBoneItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.ThrowableItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.TreatBagItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.TreatItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class CanisItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EmortisConstants.MOD_ID);

    public static final RegistryObject<Item> THROW_BONE = registerThrowBone("throw_bone");
    public static final RegistryObject<Item> THROW_BONE_WET = registerThrowBoneWet("throw_bone_wet");
    public static final RegistryObject<Item> THROW_STICK = registerThrowStick("throw_stick");
    public static final RegistryObject<Item> THROW_STICK_WET = registerThrowStickWet("throw_stick_wet");
    public static final RegistryObject<Item> TRAINING_TREAT = registerTreat("training_treat", CanisLevel.Type.CHORDATA, 25);
    public static final RegistryObject<Item> REGULAR_TREAT = registerTreat("regular_treat", CanisLevel.Type.CHORDATA, 50);
    public static final RegistryObject<Item> MASTER_TREAT = registerTreat("master_treat", CanisLevel.Type.CHORDATA, 75);
    public static final RegistryObject<Item> HOMINI_TREAT = registerTreat("homini_treat", CanisLevel.Type.CHORDATA, 100);
//    public static final RegistryObject<Item> BREEDING_BONE = register("breeding_bone");
    public static final RegistryObject<Item> COLLAR_SHEARS = registerWith("collar_shears", CanisShearsItem::new, 1);
    public static final RegistryObject<Item> CANIS_SUMMONING_CHARM = registerWith("canis_summoning_charm", CanisSummoningCharmItem::new, 1);
    public static final RegistryObject<Item> CADAVER_SUMMONING_CHARM = registerWith("cadaver_summoning_charm", CadaverSummoningCharmItem::new, 1);

    public static final RegistryObject<DyeableAccoutrementItem> WOOL_COLLAR = registerAccoutrementDyed("wool_collar", CanisAccouterments.DYEABLE_COLLAR);
    public static final RegistryObject<AccoutrementItem> CREATIVE_COLLAR = registerAccoutrement("creative_collar", CanisAccouterments.GOLDEN_COLLAR);
    public static final RegistryObject<Item> WHISTLE = registerWith("whistle", CommandWritItem::new, 1);
    public static final RegistryObject<Item> TREAT_BAG = registerWith("treat_bag", TreatBagItem::new, 1);
    public static final RegistryObject<Item> CHEW_STICK = register("chew_stick", ChewStickItem::new);

    public static final RegistryObject<AccoutrementItem> SUNGLASSES = registerAccoutrement("sunglasses", CanisAccouterments.SUNGLASSES);
    public static final RegistryObject<Item> TINY_BONE = registerSizeBone("tiny_bone", SizeBoneItem.Type.TINY);
    public static final RegistryObject<Item> BIG_BONE = registerSizeBone("big_bone", SizeBoneItem.Type.BIG);
    public static final RegistryObject<Item> MASTER_CHANGE = registerWith("master_change", ChangeMasterItem::new, 1);

    private static Item.Properties createInitialProp() {
        return new Item.Properties().tab(RigoranthusEmortisReborn.RIGORANTHUS_EMORTIS_GROUP);
    }

    private static RegistryObject<Item> registerThrowBone(final String name) {
        return register(name, () -> new ThrowableItem(THROW_BONE_WET, Items.BONE.delegate, createInitialProp().stacksTo(2)));
    }

    private static RegistryObject<Item> registerThrowStick(final String name) {
        return register(name, () -> new ThrowableItem(THROW_STICK_WET, THROW_STICK, createInitialProp().stacksTo(8)));
    }

    private static RegistryObject<Item> registerThrowBoneWet(final String name) {
        return register(name, () -> new CadaverBoneItem(THROW_BONE, createInitialProp().stacksTo(1)));
    }

    private static RegistryObject<Item> registerThrowStickWet(final String name) {
        return register(name, () -> new CadaverBoneItem(THROW_STICK, createInitialProp().stacksTo(1)));
    }

    private static RegistryObject<Item> registerSizeBone(final String name, final SizeBoneItem.Type typeIn) {
        return register(name, () -> new SizeBoneItem(typeIn, createInitialProp()));
    }

    private static RegistryObject<Item> registerTreat(final String name, final CanisLevel.Type typeIn, int maxLevel) {
        return register(name, () -> new TreatItem(maxLevel, typeIn, createInitialProp()));
    }

    private static RegistryObject<DyeableAccoutrementItem> registerAccoutrementDyed(final String name, Supplier<? extends DyeableAccoutrement> type) {
        return register(name, () -> new DyeableAccoutrementItem(type, createInitialProp()));
    }

    private static RegistryObject<AccoutrementItem> registerAccoutrement(final String name, Supplier<? extends Accoutrement> type) {
        return register(name, () -> new AccoutrementItem(type, createInitialProp()));
    }

    private static <T extends Item> RegistryObject<T> registerWith(final String name, Function<Item.Properties, T> itemConstructor, int maxStackSize) {
        return register(name, () -> itemConstructor.apply(createInitialProp().stacksTo(maxStackSize)));
    }

    private static <T extends Item> RegistryObject<T> register(final String name, Function<Item.Properties, T> itemConstructor) {
        return register(name, () -> itemConstructor.apply(createInitialProp()));
    }

    private static RegistryObject<Item> register(final String name) {
        return registerWith(name, (Function<Item.Properties, Item.Properties>) null);
    }

    private static RegistryObject<Item> registerWith(final String name, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        Item.Properties prop = createInitialProp();
        return register(name, () -> new Item(extraPropFunc != null ? extraPropFunc.apply(prop) : prop));
    }

    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ITEMS.register(name, sup);
    }

    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        REUtil.acceptOrElse(CanisItems.WOOL_COLLAR, (item) -> {
            itemColors.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack);
            }, item);
        }, CanisBlocks::logError);
    }
}













//    public static final RegistryObject<AccoutrementItem> CAPE = registerAccoutrement("cape", CanisAccouterments.CAPE);