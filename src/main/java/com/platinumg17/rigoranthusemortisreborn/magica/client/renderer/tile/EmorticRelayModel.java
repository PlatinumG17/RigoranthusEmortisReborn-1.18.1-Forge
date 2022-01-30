package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.EmorticRelayTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EmorticRelayModel extends AnimatedGeoModel<EmorticRelayTile> {

    @Override
    public ResourceLocation getModelLocation(EmorticRelayTile relayTile) {
        return RigoranthusEmortisReborn.rl("geo/relays/emortic_relay.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EmorticRelayTile relayTile) {
        return RigoranthusEmortisReborn.rl("textures/blocks/emortic_relay.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EmorticRelayTile relayTile) {
        return RigoranthusEmortisReborn.rl("animations/relays/emortic_relay_animations.json");
    }
}