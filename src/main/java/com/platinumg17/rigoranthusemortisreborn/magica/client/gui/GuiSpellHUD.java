package com.platinumg17.rigoranthusemortisreborn.magica.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class GuiSpellHUD extends AbstractContainerEventHandler implements GuiEventListener {
    private static final Minecraft minecraft = Minecraft.getInstance();

    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }

    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        return false;
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return false;
    }

    public void drawHUD(PoseStack ms) {
        ItemStack stack = StackUtil.getHeldSpellbook(minecraft.player);
//        if(stack != ItemStack.EMPTY && stack.getItem() instanceof SpellBook && stack.getTag() != null){
//            int offsetLeft = 10;
//            CompoundTag tag = stack.getTag();
//            int mode = tag.getInt(SpellBook.BOOK_MODE_TAG);
//            String renderString = "";
//            if(mode != 0){
//                renderString = mode + " " + SpellBook.getSpellName(stack.getTag());
//            }else{
//                renderString = new TranslatableComponent("rigoranthusemortisreborn.spell_hud.crafting_mode").getString();
//            }
//            minecraft.font.drawShadow(ms,renderString, offsetLeft, minecraft.getWindow().getGuiScaledHeight() - 30 , 0xFFFFFF);
//        }
    }
}