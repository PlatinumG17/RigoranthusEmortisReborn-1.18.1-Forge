package com.platinumg17.rigoranthusemortisreborn.api.apimagic.loot;

import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.common.datagen.DungeonLootGenerator;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class DungeonLootTables {

    public static List<Supplier<ItemStack>> BASIC_LOOT = new ArrayList<>();
    public static List<Supplier<ItemStack>> UNCOMMON_LOOT = new ArrayList<>();
    public static List<Supplier<ItemStack>> RARE_LOOT = new ArrayList<>();
    //set block ~ ~ ~ minecraft:chest{LootTable:"minecraft:chests/simple_dungeon"}
    public static Random r = new Random();
    static {

        BASIC_LOOT.add(() -> new ItemStack(ItemInit.BONE_FRAGMENT.get(),1 + r.nextInt(3)));
        BASIC_LOOT.add(() -> new ItemStack(BlockRegistry.DOMINION_BERRY_BUSH, 1 + r.nextInt(3)));
        BASIC_LOOT.add(() -> new ItemStack(BlockRegistry.SPECTABILIS_BUSH, 1 + r.nextInt(3)));
        BASIC_LOOT.add(() ->{
            ItemStack stack = new ItemStack(Items.POTION);
            PotionUtils.setPotion(stack, ModPotions.LONG_DOMINION_REGEN_POTION);
            return stack;
        });
        BASIC_LOOT.add(() ->{
            ItemStack stack = new ItemStack(Items.POTION);
            PotionUtils.setPotion(stack, ModPotions.STRONG_DOMINION_REGEN_POTION);
            return stack;
        });
        BASIC_LOOT.add(() ->{
            ItemStack stack = new ItemStack(Items.POTION);
            PotionUtils.setPotion(stack, ModPotions.DOMINION_REGEN_POTION);
            return stack;
        });
        BASIC_LOOT.add(() -> new ItemStack(MagicItemsRegistry.BONE_ARROW, 16 + r.nextInt(16)));
        BASIC_LOOT.add(() -> new ItemStack(ItemInit.RAZORTOOTH_KUNAI.get(), 16 + r.nextInt(16)));
        BASIC_LOOT.add(() -> new ItemStack(ItemInit.THROWING_KNIFE.get(), 16 + r.nextInt(16)));
        BASIC_LOOT.add(() -> new ItemStack(ItemInit.RICOCHET_ROUND.get(), 16 + r.nextInt(16)));

        UNCOMMON_LOOT.add(() -> new ItemStack(ItemInit.BILI_BOMB.get(),1 + r.nextInt(2)));
        UNCOMMON_LOOT.add(() -> new ItemStack(ItemInit.RAZORTOOTH_FRISBEE.get(),1));
        UNCOMMON_LOOT.add(() -> new ItemStack(ItemInit.MORRAI.get(),1));
        UNCOMMON_LOOT.add(() -> new ItemStack(ItemInit.ANDURIL.get(),1));

        UNCOMMON_LOOT.add(() -> new ItemStack(MagicItemsRegistry.dominionGem,1 + r.nextInt(3)));
        UNCOMMON_LOOT.add(() -> new ItemStack(Registration.SOUL_COAL,1 + r.nextInt(3)));
        UNCOMMON_LOOT.add(() -> new ItemStack(MagicItemsRegistry.unadornedAmulet,1));
        UNCOMMON_LOOT.add(() -> new ItemStack(MagicItemsRegistry.unadornedRing,1));

        UNCOMMON_LOOT.add(() -> new ItemStack(ItemInit.PSYGLYPHIC_SCRIPT.get(),1));

        RARE_LOOT.add(() -> new ItemStack(ItemInit.MUSIC_DISK_KICKSTART.get(), 1));
        RARE_LOOT.add(() -> new ItemStack(ItemInit.MUSIC_DISK_NEON_LIGHTS.get(), 1));
    }

    public static ItemStack getRandomItem(List<Supplier<ItemStack>> pool){
        return pool.isEmpty() ? ItemStack.EMPTY : pool.get(r.nextInt(pool.size())).get();
    }

    public static List<ItemStack> getRandomRoll(DungeonLootGenerator.DungeonLootEnhancerModifier modifier){
        List<ItemStack> stacks = new ArrayList<>();

        for(int i = 0; i < modifier.commonRolls; i++){
            if(r.nextDouble() <= modifier.commonChance)
                stacks.add(getRandomItem(BASIC_LOOT));
        }

        for(int i = 0; i < modifier.uncommonRolls; i++){
            if(r.nextDouble() <= modifier.uncommonChance)
                stacks.add(getRandomItem(UNCOMMON_LOOT));
        }
        for(int i = 0; i < modifier.rareRolls; i++){
            if(r.nextDouble() <= modifier.rareChance)
                stacks.add(getRandomItem(RARE_LOOT));
        }
        return stacks;
    }

//    public static ItemStack makeTome(String name, Spell spell){
//        ItemStack stack = new ItemStack(MagicItemsRegistry.LOST_TOME);
//        ISpellCaster spellCaster = CasterUtil.getCaster(stack);
//        spellCaster.setSpell(spell);
//        stack.setHoverName(new TextComponent(name).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withItalic(true)));
//        return stack;
//    }
//
//    public static ItemStack makeTome(String name, Spell spell, String flavorText){
//        ItemStack stack = makeTome(name, spell);
//        ISpellCaster spellCaster =  CasterUtil.getCaster(stack);
//        spellCaster.setFlavorText(flavorText);
//        return stack;
//    }
}