package com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementType;

import java.util.function.Supplier;

public class CanisAccoutrementTypes {

    public static final DeferredRegister<AccoutrementType> ACCOUTREMENT_TYPE = DeferredRegister.create(AccoutrementType.class, EmortisConstants.MOD_ID);

    public static final RegistryObject<AccoutrementType> COLLAR = register("collar");
    public static final RegistryObject<AccoutrementType> GARMENTS = register("garments");
    public static final RegistryObject<AccoutrementType> GLASSES = register("glasses");
    public static final RegistryObject<AccoutrementType> BAND = register("band");
    public static final RegistryObject<AccoutrementType> HEAD = register("head");
    public static final RegistryObject<AccoutrementType> FEET = register("feet");
    public static final RegistryObject<AccoutrementType> TAIL = register("tail");

    private static RegistryObject<AccoutrementType> register(final String name) {
        return register(name, () -> new AccoutrementType());
    }

    private static <T extends AccoutrementType> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ACCOUTREMENT_TYPE.register(name, sup);
    }
}
