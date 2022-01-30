package com.platinumg17.rigoranthusemortisreborn.magica.client.gui.buttons;

//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.book.GuiSpellBook;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@OnlyIn(Dist.CLIENT)
//public class GuiImageButton extends Button {
//
//    private ResourceLocation image;
//    public String resourceIcon;
//    int u, v, image_width, image_height;
//    GuiSpellBook parent;
//    TranslatableComponent toolTip;
//
//    public GuiImageButton( int x, int y,int u,int v,int w, int h, int image_width, int image_height, String resource_image, Button.OnPress onPress) {
//        super(x, y, w, h, new TextComponent(""), onPress);
//        this.x = x;
//        this.y = y;
//        this.resourceIcon = resource_image;
//        this.u = u;
//        this.v = v;
//        this.image_height = image_height;
//        this.image_width = image_width;
//        image = RigoranthusEmortisReborn.rl(resource_image);
//    }
//
//    public GuiImageButton withTooltip(GuiSpellBook parent, TranslatableComponent toolTip){
//        this.parent = parent;
//        this.toolTip = toolTip;
//        return this;
//    }
//
//    @Override
//    protected void renderBg(PoseStack matrixStack, Minecraft minecraft, int height, int width) {
//    }
//
//    @Override
//    public void render(PoseStack ms, int parX, int parY, float partialTicks) {
//        if (visible) {
//            if(parent != null && parent.isMouseInRelativeRange(parX, parY, x, y, width, height) && toolTip != null){
//                if(!toolTip.toString().isEmpty()){
//                    List<Component> tip = new ArrayList<>();
//                    tip.add(toolTip);
//                    parent.tooltip = tip;
//                }
//            }
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            GuiSpellBook.drawFromTexture(image, x, y, u, v, width, height, image_width, image_height,ms);
//        }
//    }
//}