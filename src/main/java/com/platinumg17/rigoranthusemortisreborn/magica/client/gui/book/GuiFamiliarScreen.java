package com.platinumg17.rigoranthusemortisreborn.magica.client.gui.book;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.buttons.FamiliarButton;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.buttons.GuiImageButton;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
//import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketSummonFamiliar;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.network.chat.TranslatableComponent;
//
//import java.util.List;
//
//public class GuiFamiliarScreen extends BaseBook {
//    public RigoranthusEmortisRebornAPI api;
//    public List<AbstractFamiliarHolder> familiars;
//    public GuiFamiliarScreen(RigoranthusEmortisRebornAPI api, List<AbstractFamiliarHolder> familiars){
//        this.api = api;
//        this.familiars = familiars;
//    }
//
//    @Override
//    public void init() {
//        super.init();
//        layoutParts();
//    }
//
//    public void layoutParts() {
//        int xStart = bookLeft + 20;
//        int yStart = bookTop + 34;
//        final int PER_ROW = 6;
//        int toLayout = Math.min(familiars.size(), PER_ROW * 5);
//        for (int i = 0; i < toLayout; i++) {
//            AbstractFamiliarHolder part = familiars.get(i);
//            int xOffset = 20 * (i % PER_ROW);
//            int yOffset = (i / PER_ROW) * 18;
//            FamiliarButton cell = new FamiliarButton(this, xStart + xOffset, yStart + yOffset, false, part);
//            addRenderableWidget(cell);
//        }
//        addRenderableWidget(new GuiImageButton(bookRight - 71, bookBottom - 13, 0,0,41, 12, 41, 12, "textures/gui/clear_icon.png", (e) -> {Minecraft.getInstance().setScreen(null);}));
//    }
//
//    @Override
//    public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
//        super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
//        drawFromTexture(RigoranthusEmortisReborn.rl("textures/gui/create_paper.png"), 216, 179, 0, 0, 56, 15,56,15, stack);
//        minecraft.font.draw(stack,new TranslatableComponent("rigoranthusemortisreborn.spell_book_gui.familiar").getString(), 20, 24, -8355712);
//        minecraft.font.draw(stack, new TranslatableComponent("rigoranthusemortisreborn.spell_book_gui.close"), 238, 183, -8355712);
//    }
//
//    public void onGlyphClick(Button button){
//        FamiliarButton button1 = (FamiliarButton) button;
//        Networking.INSTANCE.sendToServer(new PacketSummonFamiliar(button1.familiarHolder.id, Minecraft.getInstance().player.getId()));
//        Minecraft.getInstance().setScreen(null);
//    }
//}