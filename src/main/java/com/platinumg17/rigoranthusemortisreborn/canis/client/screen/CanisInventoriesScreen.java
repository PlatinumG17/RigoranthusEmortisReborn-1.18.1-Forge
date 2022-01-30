package com.platinumg17.rigoranthusemortisreborn.canis.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.AccoutrementInstance;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.widget.SmallBackButton;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.widget.SmallForwardButton;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.CanisPacketHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.CanisInventoryPageData;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.DyeableAccoutrement.DyeableAccoutrementInstance;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.CanisInventoriesContainer;
import com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.slot.CanisInventorySlot;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources;
import com.platinumg17.rigoranthusemortisreborn.magica.client.keybinds.REKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class CanisInventoriesScreen extends AbstractContainerScreen<CanisInventoriesContainer> {

    private Button left, right;
    private Player player;

    public CanisInventoriesScreen(CanisInventoriesContainer waywardTraveller, Inventory playerInventory, Component displayName) {
        super(waywardTraveller, playerInventory, displayName);
        this.player = playerInventory.player;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    public void init() {
        super.init();
        this.left = new SmallBackButton(this.leftPos + this.imageWidth - 31, this.topPos + 3, Component.nullToEmpty(""), (btn) -> {
            int page = this.getMenu().getPage();
            if (page > 0) {
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new CanisInventoryPageData(--page));
            }
            btn.active = page > 0;
            this.right.active = page < this.getMenu().getTotalNumColumns() - 9;
        });
        this.right = new SmallForwardButton(this.leftPos + this.imageWidth - 26 + 9, this.topPos + 3, Component.nullToEmpty(""), (btn) -> {
            int page = this.getMenu().getPage();

            if (page < this.getMenu().getTotalNumColumns() - 9) {
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new CanisInventoryPageData(++page));
            }
            btn.active = page < this.getMenu().getTotalNumColumns() - 9;
            this.left.active = page > 0;
        });
        if (this.getMenu().getTotalNumColumns() > 9) {
            this.left.active = false;
            this.right.active = true;
        } else {
            this.left.visible = false;
            this.right.visible = false;
        }
        this.addRenderableWidget(this.left);
        this.addRenderableWidget(this.right);
    }

    @Override
    protected void renderLabels(PoseStack stack, int par1, int par2) {

        this.font.draw(stack, this.title.getString(), 8, 6, 0x000000);
        this.font.draw(stack, this.title.getString(), 9, 5, 0xFFFFFF);

        this.font.draw(stack, this.playerInventoryTitle, 8.0F, this.imageHeight - 96 + 3, 0x000000);
        this.font.draw(stack, this.playerInventoryTitle, 9.0F, this.imageHeight - 96 + 2, 0xFFFFFF);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int xMouse, int yMouse) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.CANIS_INVENTORY);
        int l = (this.width - this.imageWidth) / 2;
        int i1 = (this.height - this.imageHeight) / 2;
        this.blit(stack, l, i1, 0, 0, this.imageWidth, this.imageHeight);

        for (CanisInventorySlot slot : this.getMenu().getSlots()) {
            if (!slot.isActive()) {
                continue;
            }
            Optional<AccoutrementInstance> inst = slot.getCanis().getAccoutrement(CanisAccouterments.DYEABLE_COLLAR.get());
            if (inst.isPresent()) {
                float[] color = inst.get().cast(DyeableAccoutrementInstance.class).getFloatArray();
                RenderSystem.setShaderColor(color[0], color[1], color[2], 1.0F);
            } else {
                RenderSystem.setShaderColor(1, 1, 1, 1);
            }
            this.blit(stack, l + slot.x - 1, i1 + slot.y - 1, 197, 2, 18, 18);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            if (this.player.abilities.instabuild) {
                this.minecraft.setScreen(new CreativeModeInventoryScreen(this.player));
            } else {
                this.minecraft.setScreen(new InventoryScreen(this.player));
            }
            return true;
        }
        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), REKeyBindings.OPEN_CANIS_INV.getKey().getValue())) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void renderTooltip(PoseStack stack, int mouseX, int mouseY) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            this.renderTooltip(stack, this.hoveredSlot.getItem(), mouseX, mouseY);
        }
    }
}