package com.platinumg17.rigoranthusemortisreborn.core.registry.fluid.particles;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EmortisParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EmortisConstants.MOD_ID);
    public static final RegistryObject<SimpleParticleType> ICHOR = PARTICLES.register("cadaverous_ichor_particle", () -> new SimpleParticleType(true));

    @ObjectHolder(EmortisConstants.MOD_ID + ":" + GlowParticleData.NAME) public static ParticleType<ColorParticleTypeData> GLOW_TYPE;
    @ObjectHolder(EmortisConstants.MOD_ID + ":" + ParticleLineData.NAME) public static ParticleType<ColoredDynamicTypeData> LINE_TYPE;
    @ObjectHolder(EmortisConstants.MOD_ID + ":" + ParticleSparkleData.NAME) public static ParticleType<ColoredDynamicTypeData> SPARKLE_TYPE;
    @ObjectHolder(EmortisConstants.MOD_ID + ":" + VortexParticleData.NAME) public static ParticleType<ColorParticleTypeData> VORTEX_TYPE;

    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event) {
        System.out.println("Rendering particles");
        IForgeRegistry<ParticleType<?>> r = event.getRegistry();
        r.register( new GlowParticleType().setRegistryName(GlowParticleData.NAME));
        r.register( new LineParticleType().setRegistryName(ParticleLineData.NAME));
        r.register( new GlowParticleType().setRegistryName(ParticleSparkleData.NAME));
        r.register( new GlowParticleType().setRegistryName(VortexParticleData.NAME));
    }

    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent event) {
        System.out.println("Rendering factories");
        Minecraft.getInstance().particleEngine.register(GLOW_TYPE, GlowParticleData::new);
        Minecraft.getInstance().particleEngine.register(LINE_TYPE, ParticleLineData::new);
        Minecraft.getInstance().particleEngine.register(SPARKLE_TYPE, ParticleSparkleData::new);
        Minecraft.getInstance().particleEngine.register(VORTEX_TYPE, VortexParticleData::new);
    }
}