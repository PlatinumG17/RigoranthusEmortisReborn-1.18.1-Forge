package com.platinumg17.rigoranthusemortisreborn.api;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.*;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.entity.familiar.AbstractFamiliarHolder;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.IPsyglyphicRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.IIchoricRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.PotionIngredient;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.VanillaPotionRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.ritual.AbstractRitual;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.ritual.RitualContext;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.interfaces.ISpellValidator;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisItems;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.ItemInit;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.RitualTile;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.FamiliarScript;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.Glyph;
import com.platinumg17.rigoranthusemortisreborn.magica.common.items.RitualOffering;
import com.platinumg17.rigoranthusemortisreborn.magica.common.spell.validation.StandardSpellValidator;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.RecipeRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class of the Rigoranthus Emortis Reborn API.
 *
 * Obtain an instance with {@link RigoranthusEmortisRebornAPI#getInstance()}.
 */
public class RigoranthusEmortisRebornAPI {

    /**
     * The version of the api classes - may not always match the mod's version
     */
    public static final String API_VERSION = "2.0.2";
    public static final String RIGORANTHUS_MODID = "rigoranthusemortisreborn";

    public static IForgeRegistry<Skill> SKILLS;
    public static IForgeRegistry<Accoutrement> ACCOUTERMENTS;
    public static IForgeRegistry<AccoutrementType> ACCOUTREMENT_TYPE;
    public static IForgeRegistry<IBeddingMaterial> BEDDING_MATERIAL;
    public static IForgeRegistry<ICasingMaterial> CASING_MATERIAL;


    /**
     * Map of all Items with Ichor Values
     *
     * key: Item
     * value: Associated Ichor Value
     */
    private Hashtable<ItemStack, Integer> ichorValueMap;

    public Hashtable<ItemStack, Integer> ichorValueMap() {
        new Hashtable<ItemStack, Integer>();
            ichorValueMap.put(Items.BONE_MEAL.getDefaultInstance(), 5);
            ichorValueMap.put(ItemInit.BONE_FRAGMENT.get().getDefaultInstance(), 5);
            ichorValueMap.put(CanisItems.TINY_BONE.get().getDefaultInstance(), 10);
            ichorValueMap.put(Items.BONE.getDefaultInstance(), 15);
            ichorValueMap.put(Items.ROTTEN_FLESH.getDefaultInstance(), 15);
            ichorValueMap.put(Items.BONE_BLOCK.getDefaultInstance(), 45);
            ichorValueMap.put(CanisItems.BIG_BONE.get().getDefaultInstance(), 75);
            ichorValueMap.put(Items.SPIDER_EYE.getDefaultInstance(), 75);
            ichorValueMap.put(Items.FERMENTED_SPIDER_EYE.getDefaultInstance(), 100);

            ichorValueMap.put(MagicItemsRegistry.BOTTLE_OF_ICHOR.getDefaultInstance(), 100);
            ichorValueMap.put(ItemInit.BUCKET_OF_CADAVEROUS_ICHOR.get().getDefaultInstance(), 400);
            ichorValueMap.put(MagicItemsRegistry.DWELLER_FLESH.getStack(), 500);
        return ichorValueMap;
    }

//    public enum PatchouliCategories { mobs, spells, machines, equipment, resources, getting_started }

    /**
     * Map of all spells to be registered in the spell system
     *
     * key: Unique spell ID. Please make this snake_case!
     * value: Associated glyph
     */
    private HashMap<String, AbstractSpellPart> spellpartMap = new HashMap<>();

    private HashMap<String, AbstractRitual> ritualMap = new HashMap<>();

    private HashMap<String, AbstractFamiliarHolder> familiarHolderMap = new HashMap<>();

    private HashMap<String, Glyph> glyphItemMap = new HashMap<>();

    private HashMap<String, FamiliarScript> familiarScriptMap = new HashMap<>();

    /**
     * Contains the list of parchment item instances created during registration
     */
    private HashMap<String, RitualOffering> ritualParchmentMap = new HashMap<>();

    /** Validator to use when crafting a spell in the spell book. */
    private ISpellValidator craftingSpellValidator;
    /** Validator to use when casting a spell. */
    private ISpellValidator castingSpellValidator;

    public List<VanillaPotionRecipe> vanillaPotionRecipes = new ArrayList<>();

    private List<BrewingRecipe> brewingRecipes = new ArrayList<>();

    private List<IPsyglyphicRecipe> psyglyphicAmalgamatorRecipes = new ArrayList<>();

    private List<IIchoricRecipe> craftingPressRecipes = new ArrayList<>();
    /**
     * Spells that all spellbooks contain
     */
    private List<AbstractSpellPart> startingSpells = new ArrayList<>();

    public List<AbstractSpellPart> getDefaultStartingSpells(){
        return spellpartMap.values().stream().filter(Config::isStarterEnabled).collect(Collectors.toList());
    }

    public boolean addStartingSpell(String tag){
        if(RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap().containsKey(tag)){
            return startingSpells.add(RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap().get(tag));
        }else{
            throw new IllegalStateException("Attempted to add a starting spell for an unregistered spell. Spells must be added to the Spell Map first!");
        }
    }

    public List<BrewingRecipe> getAllPotionRecipes(){
        if(brewingRecipes.isEmpty()){
            BrewingRecipeRegistry.getRecipes().forEach(ib ->{
                if(ib instanceof BrewingRecipe)
                    brewingRecipes.add((BrewingRecipe) ib);
            });
            vanillaPotionRecipes.forEach(vanillaPotionRecipe -> {
                BrewingRecipe recipe = new BrewingRecipe(
                        PotionIngredient.fromPotion(vanillaPotionRecipe.potionIn),
                        Ingredient.of(vanillaPotionRecipe.reagent),
                        PotionIngredient.fromPotion(vanillaPotionRecipe.potionOut).getStack()
                );
                brewingRecipes.add(recipe);
            });
        }
        return brewingRecipes;
    }

    public Item getGlyphItem(String glyphName){
        for(Item i : MagicItemsRegistry.RegistrationHandler.ITEMS){
            if(i.getRegistryName().equals(RigoranthusEmortisReborn.rl(getSpellRegistryName(glyphName)))){
                return i;
            }
        }
        return null;
    }

    public Item getGlyphItem(AbstractSpellPart spell){
        return getGlyphItem(spell.getId());
    }

    public Item getFamiliarItem(String id){
        return familiarScriptMap.get(id);
    }

    public AbstractSpellPart registerSpell(String id, AbstractSpellPart part){
        glyphItemMap.put(id, new Glyph(getSpellRegistryName(id), part));
        return spellpartMap.put(id, part);
    }

    public AbstractSpellPart registerSpell(AbstractSpellPart part){
        return registerSpell(part.getId(), part);
    }

    /**
     * A registration helper for addons. Adds dominion costs into the fallback cost map.
     */
    public AbstractSpellPart registerSpell(String id, AbstractSpellPart part, int dominionCost){
        Config.addonSpellCosts.put(id, dominionCost);
        return registerSpell(id, part);
    }

    public AbstractRitual registerRitual(String id, AbstractRitual ritual){
        ritualParchmentMap.put(id, new RitualOffering(getRitualRegistryName(id), ritual));
        return ritualMap.put(id, ritual);
    }

    public AbstractFamiliarHolder registerFamiliar(AbstractFamiliarHolder familiar){
        this.familiarScriptMap.put(familiar.id, new FamiliarScript(familiar));
        return familiarHolderMap.put(familiar.id, familiar);
    }

    public @Nullable
    AbstractRitual getRitual(String id){
        if(!ritualMap.containsKey(id))
            return null;
        try{
            return ritualMap.get(id).getClass().newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public @Nullable
    AbstractRitual getRitual(String id, RitualTile tile, RitualContext context){
        AbstractRitual ritual = getRitual(id);
        if(ritual != null){
            ritual.tile = tile;
            ritual.setContext(context);
        }
        return ritual;
    }

    public String getSpellRegistryName(String id){
        return "glyph_"+ id.toLowerCase();
    }

    public String getRitualRegistryName(String id){
        return "ritual_"+ id.toLowerCase();
    }

    public Map<String, AbstractSpellPart> getSpellpartMap() {
        return spellpartMap;
    }

    public Map<String, Glyph> getGlyphItemMap(){
        return glyphItemMap;
    }

    public Map<String, AbstractRitual> getRitualMap(){
        return ritualMap;
    }

    public Map<String, RitualOffering> getRitualItemMap(){
        return ritualParchmentMap;
    }

    public List<IPsyglyphicRecipe> getPsyglyphicAmalgamatorRecipes() {
        return psyglyphicAmalgamatorRecipes;
    }

    public List<IIchoricRecipe> getCraftingPressRecipes() {
        return craftingPressRecipes;
    }

    public Map<String, AbstractFamiliarHolder> getFamiliarHolderMap(){
        return this.familiarHolderMap;
    }

    public Map<String, FamiliarScript> getFamiliarScriptMap(){
        return this.familiarScriptMap;
    }

    public List<IPsyglyphicRecipe> getPsyglyphicAmalgamatorRecipes(Level world) {
        return world.getRecipeManager().getAllRecipesFor(RecipeRegistry.PSYGLYPHIC_TYPE);
    }

    public List<IIchoricRecipe> getCraftingPressRecipes(Level world) {
        return world.getRecipeManager().getAllRecipesFor(RecipeRegistry.CRAFTING_PRESS_TYPE);
    }

    /**
     * Returns the {@link ISpellValidator} that enforces the standard rules for spell crafting.
     * This validator relaxes the rule about starting with a cast method, to allow for spells that will be imprinted
     * onto caster items, which generally have a built-in cast method.
     */
    public ISpellValidator getSpellCraftingSpellValidator() {
        if(craftingSpellValidator == null)
            craftingSpellValidator = new StandardSpellValidator(false);
        return craftingSpellValidator;
    }

    /**
     * Returns the {@link ISpellValidator} that enforces the standard rules for spells at cast time.
     * This validator enforces all rules, asserting that a spell can be cast.
     */
    public ISpellValidator getSpellCastingSpellValidator() {
        if(castingSpellValidator == null) // Lazy init this because we need configs to load.
            castingSpellValidator = new StandardSpellValidator(true);
        return castingSpellValidator;
    }

    private RigoranthusEmortisRebornAPI(){ }
//    private RigoranthusEmortisRebornAPI(){
//        spell_map = new HashMap<>();
//        glyphMap = new HashMap<>();
//        startingSpells = new ArrayList<>();
//        psyglyphicAmalgamatorRecipes = new ArrayList<>();
//        craftingPressRecipes = new ArrayList<>();
//        ritualMap = new HashMap<>();
//        ritualParchmentMap = new HashMap<>();
//        craftingSpellValidator = new StandardSpellValidator(false);
//        castingSpellValidator = new StandardSpellValidator(true);
//        familiarHolderMap = new HashMap<>();
//        familiarScriptMap = new HashMap<>();
//        ichorValueMap = new Hashtable<>();
//    }
    /** Retrieves a handle to the singleton instance. */
    public static RigoranthusEmortisRebornAPI getInstance() {
        return RigoranthusEmortisRebornAPI;
    }
    private static final RigoranthusEmortisRebornAPI RigoranthusEmortisRebornAPI = new RigoranthusEmortisRebornAPI();

//    public static final Logger LOGGER = LogManager.getLogger("rigoranthusemortisreborn");
    public static final Logger LOGGER = LogManager.getLogger(RIGORANTHUS_MODID + "_api");
}
