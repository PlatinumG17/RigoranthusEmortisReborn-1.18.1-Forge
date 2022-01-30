package com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell;

import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleColor;
import com.platinumg17.rigoranthusemortisreborn.magica.client.particle.ParticleUtil;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellCaster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class SpellCaster implements ISpellCaster {
    private Map<Integer, Spell> spells = new HashMap<>();

    private Map<Integer, String> spellNames = new HashMap<>();

    private Map<Integer, ParticleColor.IntWrapper> spellColors = new HashMap<>();

    private int slot;
    ItemStack stack = ItemStack.EMPTY;
    String flavorText = "";

    public SpellCaster(ItemStack stack){
        this(stack.getOrCreateTag());
        this.stack = stack;
    }

    @Nonnull
    @Override
    public Spell getSpell() {
        return spells.getOrDefault(getCurrentSlot(), Spell.EMPTY);
    }

    @Override
    public @Nonnull Spell getSpell(int slot) {
        return spells.getOrDefault(slot, Spell.EMPTY);
    }

    @Override
    public int getMaxSlots() {
        return 1;
    }

    @Override
    public int getCurrentSlot() {
        return slot;
    }

    @Override
    public void setCurrentSlot(int slot) {
        this.slot = slot;
        writeItem(stack);
    }

    @Override
    public void setSpell(Spell spell, int slot) {
        this.spells.put(slot, spell);
        writeItem(stack);
    }

    @Override
    public void setSpell(Spell spell) {
        this.spells.put(getCurrentSlot(), spell);
        writeItem(stack);
    }

    @NotNull
    @Override
    public ParticleColor.IntWrapper getColor(int slot) {
        return this.spellColors.getOrDefault(slot, ParticleUtil.defaultParticleColorWrapper());
    }

    @Override
    public void setFlavorText(String str) {
        this.flavorText = str;
        writeItem(stack);
    }

    @Override
    public String getSpellName(int slot) {
        return this.spellNames.getOrDefault(slot, "");
    }

    @Override
    public String getSpellName() {
        return this.spellNames.getOrDefault(getCurrentSlot(), "");
    }

    @Override
    public void setSpellName(String name) {
        this.spellNames.put(getCurrentSlot(), name);
        writeItem(stack);
    }

    @Override
    public void setSpellName(String name, int slot) {
        this.spellNames.put(slot, name);
        writeItem(stack);
    }

    @Override
    public String getFlavorText() {
        return flavorText == null ? "" : flavorText;
    }

    @Override
    public void setColor(ParticleColor.IntWrapper color) {
        this.spellColors.put(getCurrentSlot(), color);
        writeItem(stack);
    }

    @Override
    public void setColor(ParticleColor.IntWrapper color, int slot) {
        this.spellColors.put(slot, color);
        writeItem(stack);
    }

    @Nonnull
    @Override
    public ParticleColor.IntWrapper getColor() {
        return this.spellColors.getOrDefault(getCurrentSlot(), ParticleUtil.defaultParticleColorWrapper());
    }

    @Override
    public Map<Integer, Spell> getSpells() {
        return spells;
    }

    @Override
    public Map<Integer, String> getSpellNames() {
        return this.spellNames;
    }

    @Override
    public Map<Integer, ParticleColor.IntWrapper> getColors() {
        return this.spellColors;
    }

    public CompoundTag writeTag(CompoundTag tag){
        tag.putInt("current_slot", getCurrentSlot());
        tag.putString("flavor", getFlavorText());

        for(int i = 0; i < getMaxSlots() + 1; i++){
            tag.putString("spell_" + i, getSpell(i).serialize());
            tag.putString("spell_name_" + i, getSpellName(i));
            tag.putString("spell_color_" + i, getColor(i).serialize());
        }
        return tag;
    }

    public SpellCaster(CompoundTag itemTag){
        CompoundTag tag = itemTag.getCompound(getTagID());

        this.slot = tag.contains("current_slot") ? tag.getInt("current_slot") : 1;
        this.flavorText = tag.getString("flavor");
        for(int i = 0; i < this.getMaxSlots() + 1; i++){
            if(tag.contains("spell_" + i)){
                this.setSpell(Spell.deserialize(tag.getString("spell_" + i)), i);
            }
            if(tag.contains("spell_name_" + i)){
                this.setSpellName(tag.getString("spell_name_" + i), i);
            }

            if(tag.contains("spell_color_" + i)){
                this.setColor(ParticleColor.IntWrapper.deserialize(tag.getString("spell_color_" + i)), i);
            }
        }
    }

    public void writeItem(ItemStack stack){
        if(stack == null || stack.isEmpty()){
            return;
        }
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag casterTag = new CompoundTag(); // Nest our tags so we dont cause conflicts
        writeTag(casterTag);
        tag.put(getTagID(), casterTag);
        stack.setTag(tag);
    }

    @Override
    public String getTagID() {
        return "rigoranthusemortisreborn_spellCaster";
    }
}