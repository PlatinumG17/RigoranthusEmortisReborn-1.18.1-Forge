package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.GlowParticleData;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.LightTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Random;

public class LightRenderer implements BlockEntityRenderer<LightTile> {
    public LightRenderer(BlockEntityRendererProvider.Context ctx) { }

    @Override
    public void render(LightTile lightTile, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int i, int i1) {
        Level world = lightTile.getLevel();
        BlockPos pos = lightTile.getBlockPos();
        Random rand = world.random;
        if(Minecraft.getInstance().isPaused())
            return;
        world.addParticle(
                GlowParticleData.createData(new ParticleColor(
                        rand.nextInt(lightTile.red),
                        rand.nextInt(lightTile.green),
                        rand.nextInt(lightTile.blue)
                )),
                pos.getX() +0.5 + ParticleUtil.inRange(-0.1, 0.1)  , pos.getY() +0.5  + ParticleUtil.inRange(-0.1, 0.1) , pos.getZ() +0.5 + ParticleUtil.inRange(-0.1, 0.1),
                0,0,0);
    }
}