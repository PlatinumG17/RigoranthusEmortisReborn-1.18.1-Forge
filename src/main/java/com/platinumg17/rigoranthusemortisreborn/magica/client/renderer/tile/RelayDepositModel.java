package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.RelayDepositTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RelayDepositModel extends AnimatedGeoModel<RelayDepositTile> {

    @Override
    public ResourceLocation getModelLocation(RelayDepositTile relayDepositTile) {
        return RigoranthusEmortisReborn.rl("geo/relays/relay_deposit.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RelayDepositTile relayDepositTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/relay_deposit.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RelayDepositTile relayDepositTile) {
        return RigoranthusEmortisReborn.rl("animations/relays/relay_deposit_animations.json");
    }
}