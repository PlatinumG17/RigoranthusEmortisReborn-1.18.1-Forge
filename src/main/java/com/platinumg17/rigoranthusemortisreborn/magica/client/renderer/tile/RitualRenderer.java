package com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.tile;

//import com.mojang.blaze3d.vertex.PoseStack;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.renderer.item.GenericItemRenderer;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.RitualTile;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.world.entity.item.ItemEntity;
//import net.minecraft.world.item.ItemStack;
//
//public class RitualRenderer implements BlockEntityRenderer<RitualTile> {
//    public RitualRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
////        super(rendererDispatcherIn);
//    }
//
//    public void renderFloatingItem(RitualTile tileEntityIn, ItemEntity entityItem, double x, double y, double z, PoseStack stack, MultiBufferSource iRenderTypeBuffer){
//        stack.pushPose();
//        tileEntityIn.frames += 1.5f * Minecraft.getInstance().getDeltaFrameTime();
//        entityItem.setYHeadRot(tileEntityIn.frames);
//        entityItem.age = (int) tileEntityIn.frames;
//        Minecraft.getInstance().getEntityRenderDispatcher().render(entityItem, 0.5,1,0.5, entityItem.yRot, 2.0f,stack, iRenderTypeBuffer,15728880);
//
//        stack.popPose();
//    }
//
//    @Override
//    public void render(RitualTile tileEntityIn, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int i, int i1) {
//        double x = tileEntityIn.getBlockPos().getX();
//        double y = tileEntityIn.getBlockPos().getY();
//        double z = tileEntityIn.getBlockPos().getZ();
//        if(tileEntityIn.stack == null)
//            return;
//        if (tileEntityIn.entity == null || !ItemStack.matches(tileEntityIn.entity.getItem(), tileEntityIn.stack)) {
//            tileEntityIn.entity = new ItemEntity(tileEntityIn.getLevel(), x, y, z, tileEntityIn.stack);
//        }
//        ItemEntity entityItem = tileEntityIn.entity;
//        x = x + .5;
//        y = y + 0.9;
//        z = z +.5;
//        renderFloatingItem(tileEntityIn, entityItem, x, y , z, matrixStack, iRenderTypeBuffer);
//    }
//
//    public static GenericItemRenderer getISTER(){
//        return new GenericItemRenderer(new RitualVesselModel());
//    }
//}