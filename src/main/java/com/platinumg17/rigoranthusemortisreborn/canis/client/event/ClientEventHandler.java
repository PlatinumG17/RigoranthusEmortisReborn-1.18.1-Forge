package com.platinumg17.rigoranthusemortisreborn.canis.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.client.block.model.CanisBedModel;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.widget.CanisInventoryButton;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.CanisPacketHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.OpenCanisScreenData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class ClientEventHandler {

    public static void onModelBakeEvent(final ModelBakeEvent event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModelRegistry();
        try {
            ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(CanisBlocks.CANIS_BED.get());
            ResourceLocation unbakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());
            BlockModel model = (BlockModel) event.getModelLoader().getModel(unbakedModelLoc);
            BakedModel customModel = new CanisBedModel(event.getModelLoader(), model, model.bake(event.getModelLoader(), model, ForgeModelBakery.defaultTextureGetter(), BlockModelRotation.X180_Y180, unbakedModelLoc, true));
            CanisBlocks.CANIS_BED.get().getStateDefinition().getPossibleStates().forEach(state -> {modelRegistry.put(BlockModelShaper.stateToModelLocation(state), customModel);});
            modelRegistry.put(new ModelResourceLocation(resourceLocation, "inventory"), customModel);
        }
        catch(Exception e) {
            RigoranthusEmortisReborn.LOGGER.warn("Could not get base Canis Bed model. Reverting to default textures...");
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onInputEvent(final MovementInputUpdateEvent event) {
        if (event.getInput().jumping) {
            Entity entity = event.getPlayer().getVehicle();
            if (event.getPlayer().isPassenger() && entity instanceof CanisEntity) {
                CanisEntity canis = (CanisEntity) entity;
                if (canis.canJump()) {
                    canis.setJumpPower(100);
                }
            }
        }
    }

    @SubscribeEvent
    public void onScreenInit(final ScreenEvent.InitScreenEvent.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            boolean creative = screen instanceof CreativeModeInventoryScreen;
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            int sizeX = creative ? 195 : 176;
            int sizeY = creative ? 136 : 166;
            int guiLeft = (width - sizeX) / 2;
            int guiTop = (height - sizeY) / 2;
            int x = guiLeft + (creative ? 34 : sizeX / 2 - 10);
            int y = guiTop + (creative ? 39 : 17); // was   creative ? 7 : 48
            event.addListener(new CanisInventoryButton(x, y, screen, (btn) -> {
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenCanisScreenData());
                btn.active = false;
            }));
        }
    }

    @SubscribeEvent
    public void onScreenDrawForeground(final ScreenEvent.DrawScreenEvent event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            boolean creative = screen instanceof CreativeModeInventoryScreen;
            CanisInventoryButton btn = null;
            //TODO just create a static variable in this class
            for (Widget widget : screen.renderables) {
                if (widget instanceof CanisInventoryButton) {
                    btn = (CanisInventoryButton) widget;
                    break;
                }
            }
            if (btn.visible && btn.isHoveredOrFocused()) {
                Minecraft mc = Minecraft.getInstance();
                int width = mc.getWindow().getGuiScaledWidth();
                int height = mc.getWindow().getGuiScaledHeight();
                int sizeX = creative ? 195 : 176;
                int sizeY = creative ? 136 : 166;
                int guiLeft = (width - sizeX) / 2;
                int guiTop = (height - sizeY) / 2;
                if (!creative) {
                    RecipeBookComponent recipeBook = ((InventoryScreen) screen).getRecipeBookComponent();
                    if (recipeBook.isVisible()) {
                        guiLeft += 78;
                    }
                }
//                event.getPoseStack().translate(-guiLeft, -guiTop, 0);
                btn.renderToolTip(event.getPoseStack(), event.getMouseX(), event.getMouseY());
//                event.getPoseStack().translate(guiLeft, guiTop, 0);
            }
        }
    }

    public void drawSelectionBox(PoseStack matrixStackIn, Player player, float particleTicks, AABB boundingBox) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.7F);
        //TODO Used when drawing outline of bounding box
        RenderSystem.lineWidth(2.0F);


        RenderSystem.disableTexture();
        Vec3 vec3d = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double d0 = vec3d.x();
        double d1 = vec3d.y();
        double d2 = vec3d.z();

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        LevelRenderer.renderLineBox(matrixStackIn, bufferbuilder, boundingBox.move(-d0, -d1, -d2), 1F, 1F, 0F, 0.8F);
        Tesselator.getInstance().end();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.3F);
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        //RenderSystem.enableAlphaTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

//    @SubscribeEvent
//    public void onPreRenderGameOverlay(final RenderGameOverlayEvent.Post event) {
//        label: if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) {
//            Minecraft mc = Minecraft.getInstance();
//            if (mc.player == null || !(mc.player.getVehicle() instanceof CanisEntity)) {break label;}
//            PoseStack stack = event.getPoseStack();
//
//            CanisEntity canis = (CanisEntity) mc.player.getVehicle();
//            int width = mc.getWindow().getGuiScaledWidth();
//            int height = mc.getWindow().getGuiScaledHeight();
//            RenderSystem.pushPose();
//            mc.getTextureManager().bind(Screen.GUI_ICONS_LOCATION);
//            RenderSystem.enableBlend();
//            int left = width / 2 + 91;
//            int top = height - ForgeIngameGui.right_height;
//            ForgeIngameGui.right_height += 10;
//            int level = Mth.ceil((canis.getCanisHunger() / canis.getMaxHunger()) * 20.0D);
//            for (int i = 0; i < 10; ++i) {
//                int idx = i * 2 + 1;
//                int x = left - i * 8 - 9;
//                int y = top;
//                int icon = 16;
//                byte backgound = 12;
//                mc.gui.blit(stack, x, y, 16 + backgound * 9, 27, 9, 9);
//                if (idx < level) {
//                    mc.gui.blit(stack, x, y, icon + 36, 27, 9, 9);
//                } else if (idx == level) {
//                    mc.gui.blit(stack, x, y, icon + 45, 27, 9, 9);
//                }
//            }
//            RenderSystem.disableBlend();
//            RenderSystem.enableBlend();
//            left = width / 2 + 91;
//            top = height - ForgeIngameGui.right_height;
//            RenderSystem.setShaderColor(1.0F, 1.0F, 0.0F, 1.0F);
//            int l6 = canis.getAirSupply();
//            int j7 = canis.getMaxAirSupply();
//            if (canis.isEyeInFluid(FluidTags.WATER) || l6 < j7) {
//                int air = canis.getAirSupply();
//                int full = Mth.ceil((air - 2) * 10.0D / 300.0D);
//                int partial = Mth.ceil(air * 10.0D / 300.0D) - full;
//                for (int i = 0; i < full + partial; ++i) {
//                    mc.gui.blit(stack, left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
//                }
//                ForgeIngameGui.right_height += 10;
//            }
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.disableBlend();
//            RenderSystem.popMatrix();
//        }
//    }







//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void onPreRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
//        LivingEntity entity = event.getEntity();
//
//        if(entity instanceof IClimberEntity) {
//            IClimberEntity climber = (IClimberEntity) entity;
//
//            float partialTicks = event.getPartialRenderTick();
//            PoseStack matrixStack = event.getPoseStack();
//
//            Orientation orientation = climber.getOrientation();
//            Orientation renderOrientation = climber.calculateOrientation(partialTicks);
//            climber.setRenderOrientation(renderOrientation);
//
//            float verticalOffset = climber.getVerticalOffset(partialTicks);
//
//            float x = climber.getAttachmentOffset(Direction.Axis.X, partialTicks) - (float) renderOrientation.normal.x * verticalOffset;
//            float y = climber.getAttachmentOffset(Direction.Axis.Y, partialTicks) - (float) renderOrientation.normal.y * verticalOffset;
//            float z = climber.getAttachmentOffset(Direction.Axis.Z, partialTicks) - (float) renderOrientation.normal.z * verticalOffset;
//
//            matrixStack.translate(x, y, z);
//
//            matrixStack.rotate(Vector3f.YP.rotationDegrees(renderOrientation.yaw));
//            matrixStack.rotate(Vector3f.XP.rotationDegrees(renderOrientation.pitch));
//            matrixStack.rotate(Vector3f.YP.rotationDegrees((float) Math.signum(0.5f - orientation.componentY - orientation.componentZ - orientation.componentX) * renderOrientation.yaw));
//        }
//    }
//
//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onPostRenderLiving(RenderLivingEvent.Post<?, ?> event) {
//        LivingEntity entity = event.getEntity();
//
//        if(entity instanceof IClimberEntity) {
//            IClimberEntity climber = (IClimberEntity) entity;
//
//            float partialTicks = event.getPartialRenderTick();
//            PoseStack matrixStack = event.getPoseStack();
//            MultiBufferSource bufferIn = event.getBuffers();
//
//            Orientation orientation = climber.getOrientation();
//            Orientation renderOrientation = climber.getRenderOrientation();
//
//            if(renderOrientation != null) {
//                float verticalOffset = climber.getVerticalOffset(partialTicks);
//
//                float x = climber.getAttachmentOffset(Direction.Axis.X, partialTicks) - (float) renderOrientation.normal.x * verticalOffset;
//                float y = climber.getAttachmentOffset(Direction.Axis.Y, partialTicks) - (float) renderOrientation.normal.y * verticalOffset;
//                float z = climber.getAttachmentOffset(Direction.Axis.Z, partialTicks) - (float) renderOrientation.normal.z * verticalOffset;
//
//                matrixStack.translate(Vector3f.YP.rotationDegrees(-(float) Math.signum(0.5f - orientation.componentY - orientation.componentZ - orientation.componentX) * renderOrientation.yaw));
//                matrixStack.translate(Vector3f.XP.rotationDegrees(-renderOrientation.pitch));
//                matrixStack.translate(Vector3f.YP.rotationDegrees(-renderOrientation.yaw));
//
//                if(Minecraft.getInstance().getRenderManager().isDebugBoundingBox()) {
//                    WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(0, 0, 0, 0, 0, 0).grow(0.2f), 1.0f, 1.0f, 1.0f, 1.0f);
//
//                    double rx = entity.prevPosX + (entity.getPosX() - entity.prevPosX) * partialTicks;
//                    double ry = entity.prevPosY + (entity.getPosY() - entity.prevPosY) * partialTicks;
//                    double rz = entity.prevPosZ + (entity.getPosZ() - entity.prevPosZ) * partialTicks;
//
//                    Vector3d movementTarget = climber.getTrackedMovementTarget();
//
//                    if(movementTarget != null) {
//                        WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(movementTarget.getX() - 0.25f, movementTarget.getY() - 0.25f, movementTarget.getZ() - 0.25f, movementTarget.getX() + 0.25f, movementTarget.getY() + 0.25f, movementTarget.getZ() + 0.25f).offset(-rx - x, -ry - y, -rz - z), 0.0f, 1.0f, 1.0f, 1.0f);
//                    }
//
//                    List<PathingTarget> pathingTargets = climber.getTrackedPathingTargets();
//
//                    if(pathingTargets != null) {
//                        int i = 0;
//
//                        for(PathingTarget pathingTarget : pathingTargets) {
//                            BlockPos pos = pathingTarget.pos;
//
//                            WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(pos).offset(-rx - x, -ry - y, -rz - z), 1.0f, i / (float) (pathingTargets.size() - 1), 0.0f, 0.15f);
//
//                            matrixStack.push();
//                            matrixStack.translate(pos.getX() + 0.5D - rx - x, pos.getY() + 0.5D - ry - y, pos.getZ() + 0.5D - rz - z);
//
//                            matrixStack.rotate(pathingTarget.side.getOpposite().getRotation());
//
//                            WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(-0.501D, -0.501D, -0.501D, 0.501D, -0.45D, 0.501D), 1.0f, i / (float) (pathingTargets.size() - 1), 0.0f, 1.0f);
//
//                            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
//                            VertexConsumer builder = bufferIn.getBuffer(RenderType.LINES);
//
//                            builder.pos(matrix4f, -0.501f, -0.45f, -0.501f).color(1.0f, i / (float) (pathingTargets.size() - 1), 0.0f, 1.0f).endVertex();
//                            builder.pos(matrix4f, 0.501f, -0.45f, 0.501f).color(1.0f, i / (float) (pathingTargets.size() - 1), 0.0f, 1.0f).endVertex();
//                            builder.pos(matrix4f, -0.501f, -0.45f, 0.501f).color(1.0f, i / (float) (pathingTargets.size() - 1), 0.0f, 1.0f).endVertex();
//                            builder.pos(matrix4f, 0.501f, -0.45f, -0.501f).color(1.0f, i / (float) (pathingTargets.size() - 1), 0.0f, 1.0f).endVertex();
//
//                            matrixStack.pop();
//
//                            i++;
//                        }
//                    }
//
//                    Matrix4f matrix4f = matrixStack.getLast().getMatrix();
//                    VertexConsumer builder = bufferIn.getBuffer(RenderType.LINES);
//
//                    builder.pos(matrix4f, 0, 0, 0).color(0, 1, 1, 1).endVertex();
//                    builder.pos(matrix4f, (float) orientation.normal.x * 2, (float) orientation.normal.y * 2, (float) orientation.normal.z * 2).color(1.0f, 0.0f, 1.0f, 1.0f).endVertex();
//
//                    WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(0, 0, 0, 0, 0, 0).offset((float) orientation.normal.x * 2, (float) orientation.normal.y * 2, (float) orientation.normal.z * 2).grow(0.025f), 1.0f, 0.0f, 1.0f, 1.0f);
//
//                    matrixStack.push();
//
//                    matrixStack.translate(-x, -y, -z);
//
//                    matrix4f = matrixStack.getLast().getMatrix();
//
//                    builder.pos(matrix4f, 0, entity.getHeight() * 0.5f, 0).color(0, 1, 1, 1).endVertex();
//                    builder.pos(matrix4f, (float) orientation.localX.x, entity.getHeight() * 0.5f + (float) orientation.localX.y, (float) orientation.localX.z).color(1.0f, 0.0f, 0.0f, 1.0f).endVertex();
//
//                    WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(0, 0, 0, 0, 0, 0).offset((float) orientation.localX.x, entity.getHeight() * 0.5f + (float) orientation.localX.y, (float) orientation.localX.z).grow(0.025f), 1.0f, 0.0f, 0.0f, 1.0f);
//
//                    builder.pos(matrix4f, 0, entity.getHeight() * 0.5f, 0).color(0, 1, 1, 1).endVertex();
//                    builder.pos(matrix4f, (float) orientation.localY.x, entity.getHeight() * 0.5f + (float) orientation.localY.y, (float) orientation.localY.z).color(0.0f, 1.0f, 0.0f, 1.0f).endVertex();
//
//                    WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(0, 0, 0, 0, 0, 0).offset((float) orientation.localY.x, entity.getHeight() * 0.5f + (float) orientation.localY.y, (float) orientation.localY.z).grow(0.025f), 0.0f, 1.0f, 0.0f, 1.0f);
//
//                    builder.pos(matrix4f, 0, entity.getHeight() * 0.5f, 0).color(0, 1, 1, 1).endVertex();
//                    builder.pos(matrix4f, (float) orientation.localZ.x, entity.getHeight() * 0.5f + (float) orientation.localZ.y, (float) orientation.localZ.z).color(0.0f, 0.0f, 1.0f, 1.0f).endVertex();
//
//                    WorldRenderer.drawBoundingBox(matrixStack, bufferIn.getBuffer(RenderType.LINES), new AABB(0, 0, 0, 0, 0, 0).offset((float) orientation.localZ.x, entity.getHeight() * 0.5f + (float) orientation.localZ.y, (float) orientation.localZ.z).grow(0.025f), 0.0f, 0.0f, 1.0f, 1.0f);
//
//                    matrixStack.pop();
//                }
//
//                matrixStack.translate(-x, -y, -z);
//            }
//        }
//    }
}