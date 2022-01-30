package com.platinumg17.rigoranthusemortisreborn.magica.client;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.DecorativeOrStorageBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.SpecializedEntityTypes;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.entity.render.mobs.*;
import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.entity.*;
import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile.*;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.FireShotEntity;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ClientHandler {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockRegistry.EMORTIC_CRAFTING_PRESS_TILE, EmorticCraftingPressRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.SPLINTERED_PEDESTAL_TILE, SplinteredPedestalRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.PSYGLYPHIC_AMALG_TILE, PsyglyphicAmalgamatorRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.TABLE_TILE, TableRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.LIGHT_TILE, LightRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.PORTAL_TILE_TYPE, PortalTileRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.INTANGIBLE_AIR_TYPE, IntangibleAirRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.RITUAL_TILE, RitualVesselRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.DECAYING_TILE, DecayingBlockRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.DOMINION_CRYSTALLIZER_TILE, DominionCrystallizerRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.PSYGLYPHIC_TILE, CipherRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.ICHOR_EXTRACTOR_TILE, IchorExtractorRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.EMORTIC_CORTEX_TILE, EmorticCortexRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.RELAY_DEPOSIT_TILE, RelayDepositRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.EMORTIC_RELAY_TILE, EmorticRelayRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.RELAY_SPLITTER_TILE, RelaySplitterRenderer::new);
        event.registerBlockEntityRenderer(BlockRegistry.hangingCadaverSkullTile, HangingSkullRenderer::new);

        event.registerEntityRenderer(SpecializedEntityTypes.CONSUMABLE_PROJECTILE.get(), (manager) -> new ThrownItemRenderer<>(manager, 1.0F, true));
        event.registerEntityRenderer(SpecializedEntityTypes.RETURNING_PROJECTILE.get(), (manager) -> new ThrownItemRenderer<>(manager, 1.0F, true));
        event.registerEntityRenderer(SpecializedEntityTypes.BOUNCING_PROJECTILE.get(), (manager) -> new ThrownItemRenderer<>(manager, 1.3F, true));
        event.registerEntityRenderer(SpecializedEntityTypes.BILI_BOMB.get(), (manager) -> new ThrownItemRenderer<>(manager, 1.0F, true));
        event.registerEntityRenderer(ModEntities.FIRE_SHOT_ENTITY, (manager) -> new FireShotRenderer<>(manager, 2.5F, true));

        event.registerEntityRenderer(ModEntities.FAMILIAR_CADAVER, FamiliarCadaverRenderer::new);
        event.registerEntityRenderer(ModEntities.SUNDERED_CADAVER, SunderedCadaverRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMONED_CADAVER, SummonedCadaverRenderer::new);
        event.registerEntityRenderer(ModEntities.FERAL_CANIS, FeralCanisRenderer::new);
        event.registerEntityRenderer(ModEntities.NECRAW_FASCII, NecrawFasciiRenderer::new);
        event.registerEntityRenderer(ModEntities.LANGUID_DWELLER, LanguidDwellerRenderer::new);

        event.registerEntityRenderer(ModEntities.EMINENTIAL_ENTITY, EminentialProjectionRenderer::new);
        event.registerEntityRenderer(ModEntities.ENTITY_SPELL_ARROW, TippableArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.ENTITY_EVOKER_FANGS_TYPE, RenderFangs::new);
        event.registerEntityRenderer(ModEntities.ALLY_VEX, RenderAllyVex::new);
        event.registerEntityRenderer(ModEntities.BONE_ARROW_ENTITY, TippableArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMON_WOLF, WolfRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMON_HORSE, HorseRenderer::new);
        event.registerEntityRenderer(ModEntities.LIGHTNING_ENTITY, LightningBoltRenderer::new);
        event.registerEntityRenderer(ModEntities.ENTITY_FLYING_ITEM, RenderFlyingItem::new);

        event.registerEntityRenderer( ModEntities.SPELL_PROJ,
                renderManager -> new RenderSpell(renderManager, RigoranthusEmortisReborn.rl("textures/entity/spell_proj.png")));
        event.registerEntityRenderer( ModEntities.ENTITY_FOLLOW_PROJ,
                renderManager -> new RenderBlank(renderManager, RigoranthusEmortisReborn.rl("textures/entity/spell_proj.png")));
        event.registerEntityRenderer(ModEntities.ENTITY_RITUAL,
                renderManager -> new RenderRitualProjectile(renderManager, RigoranthusEmortisReborn.rl("textures/entity/spell_proj.png")));
        event.registerEntityRenderer(ModEntities.ENTITY_WARD,
                renderManager -> new RenderRitualProjectile(renderManager, RigoranthusEmortisReborn.rl("textures/entity/spell_proj.png")));
        event.registerEntityRenderer( ModEntities.LINGER_SPELL,
                renderManager -> new RenderBlank(renderManager, RigoranthusEmortisReborn.rl("textures/entity/spell_proj.png")));
    }

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent evt) {
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.JESSIC_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.JESSIC_TRAPDOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.JESSIC_STAIRS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.JESSIC_SLAB, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZULOREAL_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZULOREAL_TRAPDOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZULOREAL_STAIRS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZULOREAL_SLAB, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.hangingCadaverSkull, RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.POTTED_JESSIC_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.POTTED_AZULOREAL_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.JESSIC_LEAVES, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.JESSIC_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZULOREAL_LEAVES, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZULOREAL_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.AZULOREAL_ORCHID, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.IRIDESCENT_SPROUTS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.LISIANTHUS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SPECTABILIS_BUSH, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.EMORTIC_RELAY, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.RELAY_DEPOSIT, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.EMORTIC_CORTEX_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DOMINION_CRYSTALLIZER_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ICHOR_EXTRACTOR_BLOCK, RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CREATIVE_ICHOR_JAR, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ICHOR_JAR, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DOMINION_JAR, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.EMORTIC_CRAFTING_PRESS_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SPLINTERED_PEDESTAL, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.PSYGLYPHIC_AMALG_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.LIGHT_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DECAYING_BLOCK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.TABLE_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DOMINION_BERRY_BUSH, RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.RE_LILLY_PAD, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CREATIVE_DOMINION_JAR, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DOMINION_GEM_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.RITUAL_BLOCK, RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.DWELLER_BRAIN, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CADAVER_SKULL, RenderType.cutout());
        evt.enqueueWork(() -> {
            ItemProperties.register(MagicItemsRegistry.LUSTERIC_SHIELD, RigoranthusEmortisReborn.rl("blocking"), (item, resourceLocation, livingEntity, arg4) -> {
                return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == item ? 1.0F : 0.0F;
            });
//            ItemProperties.register(ItemInit.CRY_OF_DESPERATION, RigoranthusEmortisReborn.rl("uses"), new ClampedItemPropertyFunction() {
//                @Override
//                public float unclampedCall(ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
//                    return switch (pStack.getDamageValue()) {
//                        case 1 -> 0.75f;
//                        case 2 -> 0.50f;
//                        case 3 -> 0.25f;
//                        default -> 1.0f;
//                    };
//                }
//            });
        });
    }
}







//    @SubscribeEvent
//    public static void initColors(final ColorHandlerEvent.Item event) {
//        event.getItemColors().register((stack, color) -> color > 0 ? -1 :
//            (PotionUtils.getPotion(stack) != Potions.EMPTY ? PotionUtils.getColor(stack) : -1), MagicItemsRegistry.);
//
//        event.getItemColors().register((stack, color) -> color > 0 ? -1 :
//                        (PotionUtils.getPotion(stack) != Potions.EMPTY ? PotionUtils.getColor(stack) : -1), MagicItemsRegistry.);
//
//        event.getItemColors().register((stack, color) -> color > 0 ? -1 :
//            (PotionUtils.getPotion(stack) != Potions.EMPTY ? PotionUtils.getColor(stack) : -1), MagicItemsRegistry.);
//
//        event.getBlockColors().register((state, reader, pos, tIndex) ->
//            reader != null && pos != null && reader.getBlockEntity(pos) instanceof JarTile
//                ? ((JarTile) reader.getBlockEntity(pos)).getColor() : -1, BlockRegistry.JAR);
//    }