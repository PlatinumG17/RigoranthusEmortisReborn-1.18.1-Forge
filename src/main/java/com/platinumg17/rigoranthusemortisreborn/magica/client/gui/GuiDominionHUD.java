package com.platinumg17.rigoranthusemortisreborn.magica.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.client.renderer.IDisplayDominion;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.dominion.IDominionCap;
import com.platinumg17.rigoranthusemortisreborn.magica.client.ClientInfo;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.item.ItemStack;

public class GuiDominionHUD extends GuiComponent {
    private static final Minecraft minecraft = Minecraft.getInstance();

    public boolean shouldDisplayBar(){
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();
        return (mainHand.getItem() instanceof IDisplayDominion && ((IDisplayDominion) mainHand.getItem()).shouldDisplay(mainHand))
                || (offHand.getItem() instanceof IDisplayDominion && ((IDisplayDominion) offHand.getItem()).shouldDisplay(offHand));
    }

    public void drawHUD(PoseStack ms, float pt) {
        if(!shouldDisplayBar())
            return;
        IDominionCap dominion = CapabilityRegistry.getDominion(minecraft.player).orElse(null);
        if(dominion == null)
            return;
        int offsetLeft = 10;
        int dominionLength = 96;
        dominionLength = (int) ((dominionLength) * ((dominion.getCurrentDominion()) / ((double) dominion.getMaxDominion() - 0.0)));
        int height = minecraft.getWindow().getGuiScaledHeight() - 5;
        RenderSystem.setShaderTexture(0, RigoranthusEmortisReborn.rl("textures/gui/dominionbar_gui_border.png"));
        blit(ms,offsetLeft, height - 18, 0, 0, 108, 18, 256, 256);
        int dominionOffset = (int) (((ClientInfo.ticksInGame + pt) / 3 % (33))) * 6;
        // 96
        RenderSystem.setShaderTexture(0, RigoranthusEmortisReborn.rl("textures/gui/dominionbar_gui_dominion.png"));
        blit(ms,offsetLeft + 9, height - 9, 0, dominionOffset, dominionLength,6, 256, 256);
        RenderSystem.setShaderTexture(0, RigoranthusEmortisReborn.rl("textures/gui/dominionbar_gui_border.png"));
        blit(ms,offsetLeft, height - 17, 0, 18, 108, 20, 256, 256);
    }
}