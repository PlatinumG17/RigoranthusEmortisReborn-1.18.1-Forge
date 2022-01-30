package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.smeltery;

import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.RESignEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RigoranthusTileEntities {
	public static DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, EmortisConstants.MOD_ID);

	public static RegistryObject<BlockEntityType<MasterfulSmelteryTile>> MASTERFUL_SMELTERY_TILE =
			TILE_ENTITIES.register("masterful_smeltery_tile", () -> BlockEntityType.Builder.of(
					MasterfulSmelteryTile::new, Registration.MASTERFUL_SMELTERY.get()).build(null));

	public static RegistryObject<BlockEntityType<RESignEntity>> RE_SIGN_ENTITIES =
			TILE_ENTITIES.register("re_sign_entity", () -> BlockEntityType.Builder.of(
					RESignEntity::new, Registration.AZULOREAL_STANDING.get(), Registration.AZULOREAL_WALL.get(), Registration.JESSIC_STANDING.get(), Registration.JESSIC_WALL.get()).build(null));

	public static void register(IEventBus modEventBus) {
		TILE_ENTITIES.register(modEventBus);
	}
}