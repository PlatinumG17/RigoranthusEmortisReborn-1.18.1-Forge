package com.platinumg17.rigoranthusemortisreborn.magica.common.capability;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;

import java.util.*;
import java.util.stream.Collectors;

public class REPlayerDataCap implements IPlayerCap {

    public Set<AbstractSpellPart> glyphs = new HashSet<>();

    public Set<FamiliarData> familiars = new HashSet<>();

    public REPlayerDataCap(){}

    @Override
    public Collection<AbstractSpellPart> getKnownGlyphs() {
        return glyphs;
    }

    @Override
    public boolean unlockGlyph(AbstractSpellPart spellPart) {
        return glyphs.add(spellPart);
    }

    @Override
    public boolean knowsGlyph(AbstractSpellPart spellPart) {
        return glyphs.contains(spellPart);
    }

    @Override
    public boolean unlockFamiliar(AbstractFamiliarHolder holderID) {
        return familiars.add(new FamiliarData(holderID.id));
    }

    @Override
    public boolean ownsFamiliar(AbstractFamiliarHolder holderID) {
        return familiars.stream().anyMatch(f -> f.familiarHolder.getId().equals(holderID.id));
    }

    @Override
    public Collection<FamiliarData> getUnlockedFamiliars() {
        return familiars;
    }

    @Override
    public FamiliarData getFamiliarData(String id) {
        return this.familiars.stream().filter(f -> f.familiarHolder.id.equals(id)).findFirst().orElse(null);
    }

    @Override
    public void setUnlockedFamiliars(Collection<FamiliarData> familiars) {
        this.familiars = new HashSet<>(familiars);
    }

    @Override
    public boolean removeFamiliar(AbstractFamiliarHolder holderID) {
        return this.familiars.remove(holderID);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        NBTUtil.writeStrings(tag, "glyph_", glyphs.stream().map(s -> s.getId()).collect(Collectors.toList()));
        for(FamiliarData f : familiars){
            tag.put("familiar_" + f.familiarHolder.id, f.toTag());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        RigoranthusEmortisRebornAPI api = RigoranthusEmortisRebornAPI.getInstance();
        for(String s : NBTUtil.readStrings(nbt, "glyph_")){
            if(api.getSpellpartMap().containsKey(s)){
                glyphs.add(api.getSpellpartMap().get(s));
            }
        }

        for(String s : nbt.getAllKeys()){
            if(s.contains("familiar_")){
                familiars.add(new FamiliarData(nbt.getCompound(s)));
            }
        }
    }

    public static REPlayerDataCap deserialize(CompoundTag tag){
        REPlayerDataCap cap = new REPlayerDataCap();
        cap.deserializeNBT(tag);
        return cap;
    }
}