package com.platinumg17.rigoranthusemortisreborn.magica.client.gui.book;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.ModdedScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class BaseBook extends ModdedScreen {

    public final int FULL_WIDTH = 290;
    public final int FULL_HEIGHT = 194;
    public static ResourceLocation background = RigoranthusEmortisReborn.rl("textures/gui/spell_book_template.png");
    public int bookLeft;
    public int bookTop;
    public int bookRight;
    public int bookBottom;

    public BaseBook() {
        super(new TextComponent(""));
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        bookLeft = width / 2 - FULL_WIDTH / 2;
        bookTop = height / 2 - FULL_HEIGHT / 2;
        bookRight = width / 2 + FULL_WIDTH / 2;
        bookBottom = height / 2 + FULL_HEIGHT / 2;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        matrixStack.pushPose();
        if(scaleFactor != 1) {
            matrixStack.scale(scaleFactor, scaleFactor, scaleFactor);
            mouseX /= scaleFactor;
            mouseY /= scaleFactor;
        }
        drawScreenAfterScale(matrixStack,mouseX, mouseY, partialTicks);
        matrixStack.popPose();
    }

    public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        drawFromTexture(background,0, 0, 0, 0, FULL_WIDTH, FULL_HEIGHT, FULL_WIDTH, FULL_HEIGHT, stack);
    }

    public static void drawFromTexture(ResourceLocation resourceLocation, int x, int y, int u, int v, int w, int h, int fileWidth, int fileHeight, PoseStack stack) {
        RenderSystem.setShaderTexture(0, resourceLocation);
        blit(stack,x, y, u, v, w, h, fileWidth, fileHeight);
    }

    public void drawForegroundElements(int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void drawScreenAfterScale(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        resetTooltip();
        renderBackground(stack);
        stack.pushPose();
        stack.translate(bookLeft, bookTop, 0);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        drawBackgroundElements(stack,mouseX, mouseY, partialTicks);
        drawForegroundElements(mouseX, mouseY, partialTicks);
        stack.popPose();
        super.render(stack, mouseX, mouseY, partialTicks);
        drawTooltip(stack, mouseX, mouseY);
    }
}