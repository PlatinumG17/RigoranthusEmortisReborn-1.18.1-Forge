package com.platinumg17.rigoranthusemortisreborn.magica.common.items;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.SpellSchool;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.IPlayerCap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Glyph extends ModItem {
    public AbstractSpellPart spellPart;
    public Glyph(String registryName, AbstractSpellPart part) {
        super(registryName);
        this.spellPart = part;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if(worldIn.isClientSide)
            return super.use(worldIn, playerIn, handIn);

        if(!Config.isSpellEnabled(this.spellPart.getId())){
            playerIn.sendMessage(new TranslatableComponent("rigoranthusemortisreborn.spell.disabled"), Util.NIL_UUID);
            return super.use(worldIn, playerIn, handIn);
        }
        IPlayerCap playerDataCap = CapabilityRegistry.getPlayerDataCap(playerIn).orElse(null);
        if(playerDataCap != null){
            if(playerDataCap.knowsGlyph(spellPart) || RigoranthusEmortisRebornAPI.getInstance().getDefaultStartingSpells().contains(spellPart)){
                playerIn.sendMessage(new TextComponent("You already know this spell!"),  Util.NIL_UUID);
                return super.use(worldIn, playerIn, handIn);
            }else if(playerDataCap.unlockGlyph(spellPart)){
                CapabilityRegistry.EventHandler.syncPlayerCap(playerIn);
                playerIn.getItemInHand(handIn).shrink(1);
                playerIn.sendMessage(new TextComponent("Unlocked " + this.spellPart.getName()), Util.NIL_UUID);
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
        if(spellPart != null){
            if(!Config.isSpellEnabled(this.spellPart.getId())){
                tooltip2.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.glyph_disabled"));
            }else if(spellPart != null){
                tooltip2.add(new TranslatableComponent("tooltip.rigoranthusemortisreborn.glyph_level", spellPart.getTier().value).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE)));
                tooltip2.add(new TranslatableComponent("rigoranthusemortisreborn.schools"));
                for(SpellSchool s : spellPart.getSchools()){
                    tooltip2.add(s.getTextComponent());
                }
            }
        }
    }

    public JsonElement asRecipe(){
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("type", "rigoranthusemortisreborn:glyph_recipe");
        jsonobject.addProperty("tier", this.spellPart.getTier().toString());
        if(this.spellPart.getCraftingReagent() != null)
            jsonobject.addProperty("input", this.spellPart.getCraftingReagent().getRegistryName().toString());
        jsonobject.addProperty("output", this.getRegistryName().toString());
        return jsonobject;
    }
}