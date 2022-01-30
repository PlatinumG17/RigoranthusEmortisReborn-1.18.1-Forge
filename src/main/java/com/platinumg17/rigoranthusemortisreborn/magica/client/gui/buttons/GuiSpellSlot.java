package com.platinumg17.rigoranthusemortisreborn.magica.client.gui.buttons;

//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.book.GuiSpellBook;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.items.SpellBook;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TextComponent;
//import org.lwjgl.opengl.GL11;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Slots for selecting the spell recipes stored in the book.
// */
//public class GuiSpellSlot extends GuiImageButton {
//
//    public int slotNum;
//    public boolean isSelected;
//
//    public GuiSpellSlot(GuiSpellBook parent, int x, int y,  int slotNum) {
//        super(x, y, 0, 0, 18, 13, 18, 13,"textures/gui/spell_tab.png", parent::onSlotChange);
//        this.parent = parent;
//        this.slotNum = slotNum;
//        this.isSelected = false;
//    }
//
//    @Override
//    public void render(PoseStack stack, int parX, int parY, float partialTicks) {
//        if (visible) {
//            if(parent.isMouseInRelativeRange(parX, parY, x, y, width, height)){
//                String name = SpellBook.getSpellName(parent.spell_book_tag, slotNum);
//                if(!name.isEmpty()){
//                    List<Component> tip = new ArrayList<>();
//                    tip.add(new TextComponent(name));
//                    parent.tooltip = tip;
//                }
//            }
//            ResourceLocation image;
//            image = this.isSelected ? RigoranthusEmortisReborn.rl("textures/gui/spell_tab_selected.png") : RigoranthusEmortisReborn.rl("textures/gui/spell_tab.png");
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            GuiSpellBook.drawFromTexture(image, x, y, u, v, width, height, image_width, image_height, stack);
//            drawCenteredString(stack,Minecraft.getInstance().font, String.valueOf(this.slotNum), x + 8, y + 3,  16777215); // White
//        }
//    }
//}