package com.platinumg17.rigoranthusemortisreborn.api.apicanis.client;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.platinumg17.rigoranthusemortisreborn.api.apicanis.entity.AbstractCanisEntity;
//import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.entity.layers.LayerRenderer;
//
//public interface ISkillRenderer<T extends AbstractCanisEntity> {
//    default void render(LayerRenderer<T, EntityModel<T>> layer, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T canis, SkillInstance inst, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//    }
//}