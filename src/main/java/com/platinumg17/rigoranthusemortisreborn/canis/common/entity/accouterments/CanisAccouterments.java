package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Accoutrement;

import java.util.function.Supplier;

public class CanisAccouterments {

    public static final DeferredRegister<Accoutrement> ACCOUTERMENTS = DeferredRegister.create(Accoutrement.class, EmortisConstants.MOD_ID);

    public static final RegistryObject<DyeableAccoutrement> DYEABLE_COLLAR = register("dyeable_collar", () -> new DyeableAccoutrement(CanisAccoutrementTypes.COLLAR, CanisItems.WOOL_COLLAR));
    public static final RegistryObject<Collar> GOLDEN_COLLAR = register("golden_collar", () -> new Collar(CanisItems.CREATIVE_COLLAR));
    public static final RegistryObject<Glasses> SUNGLASSES = register("sunglasses", () -> new Glasses(CanisItems.SUNGLASSES));
//    public static final RegistryObject<Garments> CAPE = register("cape", () -> new Garments(CanisItems.CAPE));
//    public static final RegistryObject<DyeableAccoutrement> DYEABLE_CAPE = register("dyeable_cape", () -> new DyeableAccoutrement(CanisAccoutrementTypes.GARMENTS, CanisItems.CAPE_COLORED));

    public static final RegistryObject<Helmet> IRON_HELMET = registerHelmet("iron_helmet", () -> Items.IRON_HELMET);
    public static final RegistryObject<Helmet> DIAMOND_HELMET = registerHelmet("diamond_helmet", () -> Items.DIAMOND_HELMET);
    public static final RegistryObject<Helmet> GOLDEN_HELMET = registerHelmet("golden_helmet", () -> Items.GOLDEN_HELMET);
    public static final RegistryObject<Helmet> CHAINMAIL_HELMET = registerHelmet("chainmail_helmet", () -> Items.CHAINMAIL_HELMET);
    public static final RegistryObject<Helmet> TURTLE_HELMET = registerHelmet("turtle_helmet", () -> Items.TURTLE_HELMET);
    public static final RegistryObject<Helmet> NETHERITE_HELMET = registerHelmet("netherite_helmet", () -> Items.NETHERITE_HELMET);

    public static final RegistryObject<ArmorAccoutrement> IRON_BODY_PIECE = registerBodyPiece("iron_body_piece", () -> Items.IRON_CHESTPLATE);
    public static final RegistryObject<ArmorAccoutrement> DIAMOND_BODY_PIECE = registerBodyPiece("diamond_body_piece", () -> Items.DIAMOND_CHESTPLATE);
    public static final RegistryObject<ArmorAccoutrement> GOLDEN_BODY_PIECE = registerBodyPiece("golden_body_piece", () -> Items.GOLDEN_CHESTPLATE);
    public static final RegistryObject<ArmorAccoutrement> CHAINMAIL_BODY_PIECE = registerBodyPiece("chainmail_body_piece", () -> Items.CHAINMAIL_CHESTPLATE);
    public static final RegistryObject<ArmorAccoutrement> NETHERITE_BODY_PIECE = registerBodyPiece("netherite_body_piece", () -> Items.NETHERITE_CHESTPLATE);

    public static final RegistryObject<ArmorAccoutrement> IRON_BOOTS = registerBoots("iron_boots", () -> Items.IRON_BOOTS);
    public static final RegistryObject<ArmorAccoutrement> DIAMOND_BOOTS = registerBoots("diamond_boots", () -> Items.DIAMOND_BOOTS);
    public static final RegistryObject<ArmorAccoutrement> GOLDEN_BOOTS = registerBoots("golden_boots", () -> Items.GOLDEN_BOOTS);
    public static final RegistryObject<ArmorAccoutrement> CHAINMAIL_BOOTS = registerBoots("chainmail_boots", () -> Items.CHAINMAIL_BOOTS);
    public static final RegistryObject<ArmorAccoutrement> NETHERITE_BOOTS = registerBoots("netherite_boots", () -> Items.NETHERITE_BOOTS);

    public static final RegistryObject<LeatherArmorAccoutrement> LEATHER_HELMET = register("leather_helmet", () -> new LeatherArmorAccoutrement(CanisAccoutrementTypes.HEAD, Items.LEATHER_HELMET.delegate));
    public static final RegistryObject<LeatherArmorAccoutrement> LEATHER_BODY_PIECE = register("leather_body_piece", () -> new LeatherArmorAccoutrement(CanisAccoutrementTypes.GARMENTS, Items.LEATHER_CHESTPLATE.delegate));
    public static final RegistryObject<LeatherArmorAccoutrement> LEATHER_BOOTS = register("leather_boots", () -> new LeatherArmorAccoutrement(CanisAccoutrementTypes.FEET, Items.LEATHER_BOOTS.delegate));

    private static RegistryObject<Helmet> registerHelmet(final String name, final Supplier<? extends ItemLike> itemIn) {
        return ACCOUTERMENTS.register(name, () -> new Helmet(itemIn));
    }

    private static RegistryObject<ArmorAccoutrement> registerBoots(final String name, final Supplier<? extends ItemLike> itemIn) {
        return ACCOUTERMENTS.register(name, () -> new ArmorAccoutrement(CanisAccoutrementTypes.FEET, itemIn));
    }

    private static RegistryObject<ArmorAccoutrement> registerBodyPiece(final String name, final Supplier<? extends ItemLike> itemIn) {
        return ACCOUTERMENTS.register(name, () -> new ArmorAccoutrement(CanisAccoutrementTypes.GARMENTS, itemIn));
    }

    private static <T extends Accoutrement> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ACCOUTERMENTS.register(name, sup);
    }
}
