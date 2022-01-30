package com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import net.minecraft.resources.ResourceLocation;

public class SpellTier {
    public static SpellTier ONE = new SpellTier(RigoranthusEmortisReborn.rl("one"), 1);
    public static SpellTier TWO = new SpellTier(RigoranthusEmortisReborn.rl("two"), 2);
    public static SpellTier THREE = new SpellTier(RigoranthusEmortisReborn.rl("three"), 3);

    public int value;
    public ResourceLocation id;

    public SpellTier(ResourceLocation id, int value){
        this.value = value;
        this.id = id;
    }
}