package com.platinumg17.rigoranthusemortisreborn.canis.common.util;

import com.platinumg17.rigoranthusemortisreborn.canis.CanisBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.block.tileentity.CanisBedTileEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.IBeddingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CanisBedUtil {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void setBedVariant(CanisBedTileEntity canisBedTileEntity, ItemStack stack) {
        Pair<ICasingMaterial, IBeddingMaterial> materials = CanisBedUtil.getMaterials(stack);

        canisBedTileEntity.setCasing(materials.getLeft());
        canisBedTileEntity.setBedding(materials.getRight());
    }

    public static ItemStack createRandomBed() {
        ICasingMaterial casing = pickRandom(RigoranthusEmortisRebornAPI.CASING_MATERIAL);
        IBeddingMaterial bedding = pickRandom(RigoranthusEmortisRebornAPI.BEDDING_MATERIAL);
        return CanisBedUtil.createItemStack(casing, bedding);
    }

    public static Pair<ICasingMaterial, IBeddingMaterial> getMaterials(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("rigoranthusemortisreborn");
        if (tag != null) {
            ICasingMaterial casingId = NBTUtilities.getRegistryValue(tag, "casingId", RigoranthusEmortisRebornAPI.CASING_MATERIAL);
            IBeddingMaterial beddingId = NBTUtilities.getRegistryValue(tag, "beddingId", RigoranthusEmortisRebornAPI.BEDDING_MATERIAL);

            return Pair.of(casingId, beddingId);
        }
        return Pair.of(null, null);
    }

    public static ItemStack createItemStack(ICasingMaterial casingId, IBeddingMaterial beddingId) {
        ItemStack stack = new ItemStack(CanisBlocks.CANIS_BED.get(), 1);
        CompoundTag tag = stack.getOrCreateTagElement("rigoranthusemortisreborn");
        NBTUtilities.putRegistryValue(tag, "casingId", casingId);
        NBTUtilities.putRegistryValue(tag, "beddingId", beddingId);
        return stack;
    }

    public static ICasingMaterial getCasingFromStack(IForgeRegistry<ICasingMaterial> registry, ItemStack stack) {
        for (ICasingMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }
        return null;
    }

    public static IBeddingMaterial getBeddingFromStack(IForgeRegistry<IBeddingMaterial> registry, ItemStack stack) {
        for (IBeddingMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }
        return null;
    }

    public static <T extends IForgeRegistryEntry<T>> T pickRandom(IForgeRegistry<T> registry) {
        Collection<T> values = registry.getValues();
        List<T> list = values instanceof List ? (List<T>) values : new ArrayList<>(values);
        return list.get(RANDOM.nextInt(list.size()));
    }
}