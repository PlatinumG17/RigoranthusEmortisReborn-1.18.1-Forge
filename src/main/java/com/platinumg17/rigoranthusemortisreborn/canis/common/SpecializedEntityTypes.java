package com.platinumg17.rigoranthusemortisreborn.canis.common;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisAttributes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisBeamEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import com.platinumg17.rigoranthusemortisreborn.entity.DelphicBloomEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.item.BiliBombEntitiy;
import com.platinumg17.rigoranthusemortisreborn.entity.item.BouncingProjectileEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.item.ConsumableProjectileEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.item.ReturningProjectileEntity;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class SpecializedEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, EmortisConstants.MOD_ID);

    public static final RegistryObject<EntityType<CanisEntity>> CANIS = register("canis", CanisEntity::new, MobCategory.CREATURE, (b) -> b
            .sized(1.4f, 2f)
            .setUpdateInterval(3).setTrackingRange(16)
            .setShouldReceiveVelocityUpdates(true));

    public static final RegistryObject<EntityType<CanisBeamEntity>> CANIS_BEAM = register("canis_beam", CanisBeamEntity::new, MobCategory.MISC, (b) -> b
            .sized(0.25F, 0.25F)
            .setUpdateInterval(4).setTrackingRange(10)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(CanisBeamEntity::new)
            .noSummon());

    public static final RegistryObject<EntityType<ConsumableProjectileEntity>> CONSUMABLE_PROJECTILE = register("consumable_projectile", ConsumableProjectileEntity::new, MobCategory.MISC, (b) -> b
            .sized(0.25F, 0.25F).setUpdateInterval(10).setTrackingRange(4));

    public static final RegistryObject<EntityType<ReturningProjectileEntity>> RETURNING_PROJECTILE = register("returning_projectile", ReturningProjectileEntity::new, MobCategory.MISC, (b) -> b
            .sized(0.25F, 0.25F).setUpdateInterval(2).setTrackingRange(64));

    public static final RegistryObject<EntityType<BouncingProjectileEntity>> BOUNCING_PROJECTILE = register("bouncing_projectile", BouncingProjectileEntity::new, MobCategory.MISC, (b) -> b
            .sized(0.25F, 0.25F).setUpdateInterval(2).setTrackingRange(10));

    public static final RegistryObject<EntityType<BiliBombEntitiy>> BILI_BOMB = register("bili_bomb", BiliBombEntitiy::new, MobCategory.MISC, (b) -> b
            .sized(0.25F, 0.25F).setUpdateInterval(10).setTrackingRange(4));

    public static final RegistryObject<EntityType<DelphicBloomEntity>> DELPHIC_BLOOM = register("delphic_bloom", DelphicBloomEntity::new, MobCategory.MISC, (b) -> b
            .sized(2F, 2F).setShouldReceiveVelocityUpdates(false).setTrackingRange(10));

    private static <E extends Entity, T extends EntityType<E>> RegistryObject<EntityType<E>> register(final String name, final EntityType.EntityFactory<E> sup, final MobCategory classification, final Function<EntityType.Builder<E>, EntityType.Builder<E>> builder) {
        return register(name, () -> builder.apply(EntityType.Builder.of(sup, classification)).build(REUtil.getResourcePath(name)));
    }
    private static <E extends Entity, T extends EntityType<E>> RegistryObject<T> register(final String name, final Supplier<T> sup) {return ENTITIES.register(name, sup);}
    public static void addEntityAttributes(EntityAttributeCreationEvent e) {
        e.put(DELPHIC_BLOOM.get(),
                LivingEntity.createLivingAttributes().build());
        e.put(CANIS.get(),
            Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.25D) // added
                .add(CanisAttributes.JUMP_POWER.get(), 1.0D) // was 0.42D
                .add(CanisAttributes.CRIT_CHANCE.get(), 0.01D)
                .add(CanisAttributes.CRIT_BONUS.get(), 1D)
                .build()
        );
    }
}