package com.platinumg17.rigoranthusemortisreborn.canis;

import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.bedding.*;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.*;

public class CanisRegistries {

    public static void newRegistry(RegistryEvent.NewRegistry event) {
        RigoranthusEmortisRebornAPI.SKILLS = makeRegistry("skills", Skill.class).create();
        RigoranthusEmortisRebornAPI.ACCOUTERMENTS = makeRegistry("accouterments", Accoutrement.class).create();
        RigoranthusEmortisRebornAPI.ACCOUTREMENT_TYPE = makeRegistry("accoutrement_type", AccoutrementType.class).disableSync().create();
        RigoranthusEmortisRebornAPI.BEDDING_MATERIAL = makeRegistry("bedding", IBeddingMaterial.class).addCallback(BeddingCallbacks.INSTANCE).create();
        RigoranthusEmortisRebornAPI.CASING_MATERIAL = makeRegistry("casing", ICasingMaterial.class).addCallback(CasingCallbacks.INSTANCE).create(); //TODO ADD holder object
    }
    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(final String name, Class<T> type) {
        return new RegistryBuilder<T>().setName(REUtil.getResource(name)).setType(type);
    }
    private static class BeddingCallbacks implements IForgeRegistry.DummyFactory<IBeddingMaterial> {
        static final BeddingCallbacks INSTANCE = new BeddingCallbacks();
        @Override
        public IBeddingMaterial createDummy(ResourceLocation key) {return new MissingBeddingMaterial().setRegistryName(key);}
    }
    private static class CasingCallbacks implements IForgeRegistry.DummyFactory<ICasingMaterial> {
        static final CasingCallbacks INSTANCE = new CasingCallbacks();
        @Override
        public ICasingMaterial createDummy(ResourceLocation key) {return new MissingCasingMaterial().setRegistryName(key);}
    }
}