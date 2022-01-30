package com.platinumg17.rigoranthusemortisreborn.magica.client.gui.buttons;

//import com.mojang.blaze3d.vertex.PoseStack;
//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.book.GuiFamiliarScreen;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.book.GuiSpellBook;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.client.gui.screens.Screen;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FamiliarButton extends Button {
//    public boolean isCraftingSlot;
//    public String resourceIcon;
//    public String tooltip = "tooltip";
//
//    GuiFamiliarScreen parent;
//    public AbstractFamiliarHolder familiarHolder;
//    public FamiliarButton(GuiFamiliarScreen parent, int x, int y, boolean isCraftingSlot, AbstractFamiliarHolder familiar) {
//        super(x, y,  16, 16, Component.nullToEmpty(""), parent::onGlyphClick);
//        this.parent = parent;
//        this.x = x;
//        this.y = y;
//        this.width = 16;
//        this.height = 16;
//        this.isCraftingSlot = isCraftingSlot;
//        this.resourceIcon = familiar.getImagePath();
//        this.familiarHolder = familiar;
//    }
//
//    @Override
//    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
//        if (visible) {
//            if(this.resourceIcon != null && !this.resourceIcon.equals("")) {
//                GuiSpellBook.drawFromTexture(RigoranthusEmortisReborn.rl("textures/items/" + this.resourceIcon), x, y, 0, 0, 16, 16,16,16 , ms);
//            }
//            if(parent.isMouseInRelativeRange(mouseX, mouseY, x, y, width, height)){
//                List<Component> tip = new ArrayList<>();
//                if(Screen.hasShiftDown()){
//                    tip.add(familiarHolder.getLangDescription());
//                }else{
//                    tip.add(familiarHolder.getLangName());
//                    tip.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.hold_shift"));
//                }
//                parent.tooltip = tip;
//            }
//        }
//    }
//}