package com.platinumg17.rigoranthusemortisreborn.magica.common.enchantment;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.platinumg17.rigoranthusemortisreborn.magica.setup.InjectionUtil.Null;

@ObjectHolder(EmortisConstants.MOD_ID)
public class EnchantmentRegistry {

    public static DominionRegenEnchantment DOMINION_REGEN_ENCHANTMENT = Null();
    public static DominionBoost DOMINION_BOOST_ENCHANTMENT = Null();
    public static ReflexSummonEnchantment REFLEX_SUMMON_ENCHANTMENT = Null();
    @Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerEnchants(final RegistryEvent.Register<Enchantment> event) {
            DOMINION_REGEN_ENCHANTMENT = new DominionRegenEnchantment();
            DOMINION_BOOST_ENCHANTMENT = new DominionBoost();
            REFLEX_SUMMON_ENCHANTMENT = new ReflexSummonEnchantment();
            final IForgeRegistry<Enchantment> registry = event.getRegistry();
            registry.register(DOMINION_REGEN_ENCHANTMENT);
            registry.register(DOMINION_BOOST_ENCHANTMENT);
            registry.register(REFLEX_SUMMON_ENCHANTMENT);
        }
    }
}