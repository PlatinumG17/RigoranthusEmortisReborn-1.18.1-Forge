package com.platinumg17.rigoranthusemortisreborn.canis.client.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.WaywardTravellerSkill;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CanisInventoryButton extends Button {
    private Screen parent;
    private int baseX;

    public CanisInventoryButton(int x, int y, Screen parentIn, OnPress onPress) {
        super(x, y, 17, 11, new TextComponent(""), onPress);
        this.baseX = x;
        this.parent = parentIn;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.parent instanceof CreativeModeInventoryScreen) {
            int tabIndex = ((CreativeModeInventoryScreen) this.parent).getSelectedTab();
            this.visible = tabIndex == CreativeModeTab.TAB_INVENTORY.getId();
            this.active = this.visible;
        }
        if (this.parent instanceof InventoryScreen) {
            RecipeBookComponent recipeBook = ((InventoryScreen) this.parent).getRecipeBookComponent();
            if (recipeBook.isVisible()) {
                this.x = this.baseX + 78;
            } else {
                this.x = this.baseX;
            }
        }
        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            List<CanisEntity> cani = mc.level.getEntitiesOfClass(CanisEntity.class, mc.player.getBoundingBox().inflate(12D, 12D, 12D),
                    (canis) -> canis.canInteract(mc.player) && WaywardTravellerSkill.hasInventory(canis)
            );
            this.active = !cani.isEmpty();
        }
        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
//        Font font = mc.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.setShaderTexture(0, Resources.SMALL_WIDGETS);
        int i = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.blit(stack, this.x, this.y, 0, 42 + i * 11, this.width, this.height);
        this.renderBg(stack, mc, mouseX, mouseY);
//        int j = getFGColor();
//        this.drawCenteredString(stack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
        if (this.active) {
            Component msg = new TranslatableComponent("container.rigoranthusemortisreborn.canis_inventories.link");
            this.parent.renderTooltip(stack, msg, mouseX, mouseY);
        } else {
            Component msg = new TranslatableComponent("container.rigoranthusemortisreborn.canis_inventories.link").withStyle(ChatFormatting.RED);
            this.parent.renderTooltip(stack, msg, mouseX, mouseY);
        }
    }
}