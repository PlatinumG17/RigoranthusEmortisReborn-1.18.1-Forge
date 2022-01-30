package com.platinumg17.rigoranthusemortisreborn.world.trees;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class SupplierBlockStateProvider extends AbstractSupplierBlockStateProvider {
    public SupplierBlockStateProvider(String path) {
        super(EmortisConstants.MOD_ID, path);
    }

    public static final Codec<SupplierBlockStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(d -> d.key.getPath()))
            .apply(instance, SupplierBlockStateProvider::new));

    @Override
    protected BlockStateProviderType<?> type() {
        return BlockRegistry.stateProviderType;
    }
}
