package com.platinumg17.rigoranthusemortisreborn.entity.model.projectile;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.renderer.model.ModelRenderer;
//import net.minecraft.world.entity.Entity;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class BoneArrowModel extends EntityModel<Entity> {
//
//    private final ModelRenderer boneArrow;
//
//    public BoneArrowModel() {
//        texWidth = 64;
//        texHeight = 64;
//
//        boneArrow = new ModelRenderer(this);
//        boneArrow.setPos(0.0F, 24.0F, 0.0F);
//        boneArrow.texOffs(0, 7).addBox(-5.6857F, -13.0F, -1.8143F, 10.0F, 0.0F, 7.0F, 0.0F, false);
//        boneArrow.texOffs(0, 0).addBox(-7.0429F, -12.7714F, -4.8143F, 14.0F, 0.0F, 7.0F, 0.0F, false);
//    }
//
//    @Override
//    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        boneArrow.render(matrixStack, buffer, packedLight, packedOverlay);
//    }
//}