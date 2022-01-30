package com.platinumg17.rigoranthusemortisreborn.canis.client.block.model;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.IBeddingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;
import com.platinumg17.rigoranthusemortisreborn.canis.common.block.CanisBedBlock;
import com.platinumg17.rigoranthusemortisreborn.canis.common.block.tileentity.CanisBedTileEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.registries.IRegistryDelegate;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author ProPerciliv
 */
@OnlyIn(Dist.CLIENT)
public class CanisBedModel implements BakedModel {
    public static CanisBedItemOverride ITEM_OVERIDE = new CanisBedItemOverride();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");
    private ForgeModelBakery modelLoader;
    private BlockModel model;
    private BakedModel bakedModel;
    private final Map<Triple<IRegistryDelegate<ICasingMaterial>, IRegistryDelegate<IBeddingMaterial>, Direction>, BakedModel> cache = Maps.newHashMap();
    public CanisBedModel(ForgeModelBakery modelLoader, BlockModel model, BakedModel bakedModel) {
        this.modelLoader = modelLoader;
        this.model = model;
        this.bakedModel = bakedModel;
    }
    public BakedModel getModelVariant(@Nonnull IModelData data) {return this.getModelVariant(data.getData(CanisBedTileEntity.CASING), data.getData(CanisBedTileEntity.BEDDING), data.getData(CanisBedTileEntity.FACING));}
    public BakedModel getModelVariant(ICasingMaterial casing, IBeddingMaterial bedding, Direction facing) {
        Triple<IRegistryDelegate<ICasingMaterial>, IRegistryDelegate<IBeddingMaterial>, Direction> key =
                ImmutableTriple.of(casing != null ? casing.delegate : null, bedding != null ? bedding.delegate : null, facing != null ? facing : Direction.NORTH);
        return this.cache.computeIfAbsent(key, (k) -> bakeModelVariant(k.getLeft(), k.getMiddle(), k.getRight()));
    }
    @Override public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {return this.getModelVariant(null, null, Direction.NORTH).getQuads(state, side, rand, EmptyModelData.INSTANCE);}
    @Override public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {return this.getModelVariant(data).getQuads(state, side, rand, data);}
    @Override public TextureAtlasSprite getParticleIcon(@Nonnull IModelData data) {return this.getModelVariant(data).getParticleIcon(data);}

    @Override
    public IModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        ICasingMaterial casing = null;
        IBeddingMaterial bedding = null;
        Direction facing = Direction.NORTH;
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof CanisBedTileEntity) {
            casing = ((CanisBedTileEntity) tile).getCasing();
            bedding = ((CanisBedTileEntity) tile).getBedding();
        }
        if (state.hasProperty(CanisBedBlock.FACING)) {
            facing = state.getValue(CanisBedBlock.FACING);
        }
        tileData.setData(CanisBedTileEntity.CASING, casing);
        tileData.setData(CanisBedTileEntity.BEDDING, bedding);
        tileData.setData(CanisBedTileEntity.FACING, facing);
        return tileData;
    }

    public BakedModel bakeModelVariant(@Nullable IRegistryDelegate<ICasingMaterial> casingResource, @Nullable IRegistryDelegate<IBeddingMaterial> beddingResource, @Nonnull Direction facing) {
        List<BlockElement> parts = this.model.getElements();
        List<BlockElement> elements = new ArrayList<>(parts.size());
        for (BlockElement part : parts) {
            elements.add(new BlockElement(part.from, part.to, Maps.newHashMap(part.faces), part.rotation, part.shade));
        }
        BlockModel newModel = new BlockModel(this.model.getParentLocation(), elements,
                Maps.newHashMap(this.model.textureMap), this.model.hasAmbientOcclusion(), this.model.getGuiLight(),
                this.model.getTransforms(), new ArrayList<>(this.model.getOverrides()));
        newModel.name = this.model.name;
        newModel.parent = this.model.parent;
        Either<Material, String> casingTexture = findCasingTexture(casingResource);
        newModel.textureMap.put("bedding", findBeddingTexture(beddingResource));
        newModel.textureMap.put("casing", casingTexture);
        newModel.textureMap.put("particle", casingTexture);
        return newModel.bake(this.modelLoader, newModel, ForgeModelBakery.defaultTextureGetter(), getModelRotation(facing), createResourceVariant(casingResource, beddingResource, facing), true);
    }

    private ResourceLocation createResourceVariant(@Nonnull IRegistryDelegate<ICasingMaterial> casingResource, @Nonnull IRegistryDelegate<IBeddingMaterial> beddingResource, @Nonnull Direction facing) {
        String beddingKey = beddingResource != null
                ? beddingResource.name().toString().replace(':', '.')
                : "rigoranthusemortisreborn.canisbed.bedding.missing";
        String casingKey = beddingResource != null
                ? casingResource.name().toString().replace(':', '.')
                : "rigoranthusemortisreborn.canisbed.casing.missing";
        return new ModelResourceLocation(EmortisConstants.MOD_ID, "blocks/canis_bed#bedding=" + beddingKey + ",casing=" + casingKey + ",facing=" + facing.getName());
    }
    private Either<Material, String> findCasingTexture(@Nullable IRegistryDelegate<ICasingMaterial> resource) {return findTexture(resource != null ? resource.get().getTexture() : null);}
    private Either<Material, String> findBeddingTexture(@Nullable IRegistryDelegate<IBeddingMaterial> resource) {return findTexture(resource != null ? resource.get().getTexture() : null);}
    private Either<Material, String> findTexture(@Nullable ResourceLocation resource) {
        if (resource == null) {resource = MISSING_TEXTURE;}
        return Either.left(new Material(InventoryMenu.BLOCK_ATLAS, resource));
    }

    private BlockModelRotation getModelRotation(@Nonnull Direction dir) {
        switch (dir) {
            default:    return BlockModelRotation.X0_Y0; // North
            case EAST:  return BlockModelRotation.X0_Y90;
            case SOUTH: return BlockModelRotation.X0_Y180;
            case WEST:  return BlockModelRotation.X0_Y270;
        }
    }
    @Override public boolean useAmbientOcclusion() {
        return this.bakedModel.useAmbientOcclusion();
    }
    @Override public boolean isGui3d() {
        return this.bakedModel.isGui3d();
    }
    @Override public boolean usesBlockLight() {
        return this.bakedModel.usesBlockLight();
    }
    @Override public boolean isCustomRenderer() {
        return this.bakedModel.isCustomRenderer();
    }
    @Override public TextureAtlasSprite getParticleIcon() {
        return this.bakedModel.getParticleIcon();
    }
    @Override public ItemTransforms getTransforms() {
        return this.bakedModel.getTransforms();
    }
    @Override public ItemOverrides getOverrides() {
        return ITEM_OVERIDE;
    }
}