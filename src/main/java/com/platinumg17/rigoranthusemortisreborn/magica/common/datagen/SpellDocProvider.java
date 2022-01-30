package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.IPsyglyphicRecipe;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicAmalgamatorRecipe;
//import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
//import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
//import com.platinumg17.rigoranthusemortisreborn.magica.setup.APIRegistry;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.data.DirectoryCache;
//import net.minecraft.data.DataProvider;
//import net.minecraft.world.item.Items;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SpellDocProvider implements DataProvider {
//    private final DataGenerator generator;
//    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
//    private static final Logger LOGGER = LogManager.getLogger();
//    public SpellDocProvider(DataGenerator generatorIn) {
//        this.generator = generatorIn;
//    }
//
//    @Override
//    public void run(DirectoryCache cache) throws IOException {
////        APIRegistry.registerAmalgamatorRecipes();
//        Path path = this.generator.getOutputFolder();
//        System.out.println("ACTING IN DOC PROVIDER");
//        ArrayList<AbstractSpellPart> spells = new ArrayList<>(RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap().values());
//
//        for(AbstractSpellPart spellPatchouliObj : spells){
//            Path path1 = getSpellPath(path, spellPatchouliObj);
//            try {
//                DataProvider.save(GSON, cache, spellPatchouliObj.serialize(), path1);
//            } catch (IOException ioexception) {
//                LOGGER.error("Couldn't save spell {}", path1, ioexception);
//            }
//        }
//        List<PsyglyphicAmalgamatorRecipe> amalgamatorRecipes = new ArrayList<>();
//        for (IPsyglyphicRecipe iPsyglyphicRecipe : RigoranthusEmortisRebornAPI.getInstance().getPsyglyphicAmalgamatorRecipes()) {
//            if (iPsyglyphicRecipe instanceof PsyglyphicAmalgamatorRecipe) {
//                if(((PsyglyphicAmalgamatorRecipe) iPsyglyphicRecipe).result.getItem() == Items.AIR)
//                    continue;
//                amalgamatorRecipes.add((PsyglyphicAmalgamatorRecipe) iPsyglyphicRecipe);
//            }
//        }
//        System.out.println(amalgamatorRecipes);
//        for(PsyglyphicAmalgamatorRecipe r : amalgamatorRecipes ){
//            Path path1 = getAmalgamatorPath(path, r);
//            try {
//                DataProvider.save(GSON, cache, r.serialize(), path1);
//                System.out.println(r);
//            } catch (IOException ioexception) {
//                LOGGER.error("Couldn't save amalgamator {}", path1, ioexception);
//            }
//        }
//    }
//    private static Path getSpellPath(Path pathIn, AbstractSpellPart spell) {
//        return pathIn.resolve("data/rigoranthusemortisreborn/spells/" + spell.getTag() + ".json");
//    }
//    private static Path getAmalgamatorPath(Path pathIn, PsyglyphicAmalgamatorRecipe e) {
//        System.out.println(e.result.getItem().toString());
//        return pathIn.resolve("data/rigoranthusemortisreborn/amalgamator/" + e.result.getItem().getRegistryName().toString().replace(EmortisConstants.MOD_ID + ":", "") + ".json");
//    }
//    @Override
//    public String getName() {
//        return "Patchouli Documentation Provider";
//    }
//}