package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicCipherTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PsyglyphicCipherModel extends AnimatedGeoModel<PsyglyphicCipherTile> {
    @Override
    public ResourceLocation getModelLocation(PsyglyphicCipherTile cipherTile) {
        return RigoranthusEmortisReborn.rl("geo/psyglyphic_cipher.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PsyglyphicCipherTile cipherTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/psyglyphic_cipher.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PsyglyphicCipherTile cipherTile) {
        return RigoranthusEmortisReborn.rl("animations/psyglyphic_cipher_animation.json");
    }
}