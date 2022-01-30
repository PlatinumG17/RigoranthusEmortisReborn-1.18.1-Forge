package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import static com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen {

    @SubscribeEvent
    public static void datagen(GatherDataEvent event){
        System.out.println("calling datagen");
        BlockTagsProvider blocktagsprovider = new BlockTagsProvider(event.getGenerator(), MOD_ID, event.getExistingFileHelper());
        event.getGenerator().addProvider(new LootTables(event.getGenerator()));
        event.getGenerator().addProvider(new DefaultTableProvider(event.getGenerator()));
        event.getGenerator().addProvider(new CraftingPressRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(new Recipes(event.getGenerator()));
        event.getGenerator().addProvider(new BlockStatesDatagen(event.getGenerator(), MOD_ID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new PsyglyphicRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(new RigBlockTagProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new RigItemTagProvider(event.getGenerator(), blocktagsprovider, MOD_ID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new EntityTagProvider(event.getGenerator(), MOD_ID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new DungeonLootGenerator(event.getGenerator(), MOD_ID));
    }
}


//        event.getGenerator().addProvider(new SpellDocProvider(event.getGenerator()));
//        event.getGenerator().addProvider(new PatchouliProvider(event.getGenerator()));