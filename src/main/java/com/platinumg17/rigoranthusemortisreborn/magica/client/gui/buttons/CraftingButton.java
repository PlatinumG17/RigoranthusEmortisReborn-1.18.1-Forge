package com.platinumg17.rigoranthusemortisreborn.magica.client.gui.buttons;

//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.SpellValidationError;
//import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.book.GuiSpellBook;
//import net.minecraft.ChatFormatting;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TranslatableComponent;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class CraftingButton extends GuiImageButton {
//    int slotNum;
//    public String spellTag;
//    public String resourceIcon;
//    public List<SpellValidationError> validationErrors;
//
//    public CraftingButton(GuiSpellBook parent, int x, int y, int slotNum, Button.OnPress onPress) {
//        super( x, y, 0, 0, 22, 20, 22, 20, "textures/gui/spell_glyph_slot.png", onPress);
//        this.slotNum = slotNum;
//        this.spellTag = "";
//        this.resourceIcon = "";
//        this.validationErrors = new LinkedList<>();
//        this.parent = parent;
//    }
//
//    public void clear() {
//        this.spellTag = "";
//        this.resourceIcon = "";
//        this.validationErrors.clear();
//    }
//
//    @Override
//    public void render(PoseStack ms, int parX, int parY, float partialTicks) {
//        if (visible) {
//            if (validationErrors.isEmpty()) {
//                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            } else {
//                RenderSystem.setShaderColor(1.0F, 0.7F, 0.7F, 1.0F);
//            }
//            //GuiSpellBook.drawFromTexture(new ResourceLocation(ExampleMod.MODID, this.resourceIcon), x, y, 0, 0, 20, 20, 20, 20);
//            if(!this.resourceIcon.equals("")){
//                GuiSpellBook.drawFromTexture(RigoranthusEmortisReborn.rl("textures/items/" + resourceIcon), x + 3, y + 2, u, v, 16, 16, 16, 16,ms);
//            }
//            if(parent.isMouseInRelativeRange(parX, parY, x, y, width, height)){
//                if(parent.api.getSpellpartMap().containsKey(this.spellTag)) {
//                    List<Component> tooltip = new LinkedList<>();
//                    tooltip.add(new TranslatableComponent(parent.api.getSpellpartMap().get(this.spellTag).getLocalizationKey()));
//                    for (SpellValidationError ve : validationErrors) {
//                        tooltip.add(ve.makeTextComponentExisting().copy().withStyle(ChatFormatting.RED));
//                    }
//                    parent.tooltip = tooltip;
//                }
//            }
//        }
//        super.render(ms, parX, parY, partialTicks);
//    }
//}