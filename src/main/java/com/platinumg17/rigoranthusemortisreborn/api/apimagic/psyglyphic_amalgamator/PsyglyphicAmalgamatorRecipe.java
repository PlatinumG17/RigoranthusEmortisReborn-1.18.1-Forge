package com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicAmalgamatorTile;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.RecipeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PsyglyphicAmalgamatorRecipe implements IPsyglyphicRecipe {

    public Ingredient reagent; // Used in the Amalgamator
    public ItemStack result; // Result item
    public List<Ingredient> pedestalItems; // Items placed on splintered pedestals as part of the recipe
    public String category;
    public ResourceLocation id;
    public int dominionCost;
    public int expGain;

    public PsyglyphicAmalgamatorRecipe(ItemStack result, Ingredient reagent, List<Ingredient> pedestalItems, String category){
        this.reagent = reagent;
        this.pedestalItems = pedestalItems;
        this.result = result;
        this.category = category;
        expGain = 0;
        dominionCost = 0;
        this.id = RigoranthusEmortisReborn.rl(result.getItem().getRegistryName().getPath());
    }

    public PsyglyphicAmalgamatorRecipe(ResourceLocation id, List<Ingredient> pedestalItems, Ingredient reagent, ItemStack result) {
        this(id, pedestalItems, reagent, result, 0, 0);
    }

    public PsyglyphicAmalgamatorRecipe(ResourceLocation id, List<Ingredient> pedestalItems, Ingredient reagent, ItemStack result, int cost, int exp){
        this.reagent = reagent;
        this.pedestalItems = pedestalItems;
        this.result = result;
        this.category = "";
        expGain = exp;
        dominionCost = cost;
        this.id = id;
    }

    public PsyglyphicAmalgamatorRecipe() {
        reagent = Ingredient.EMPTY;
        result = ItemStack.EMPTY;
        pedestalItems = new ArrayList<>();
        expGain = 0;
        dominionCost = 0;
        this.id = RigoranthusEmortisReborn.rl("empty");
    }

    public PsyglyphicAmalgamatorRecipe(Item result, Item reagent, Item[] pedestalItems, String category) {
        ArrayList<Ingredient> stacks = new ArrayList<>();
        for(Item i : pedestalItems){
            stacks.add(Ingredient.of(i));
        }
        this.reagent = Ingredient.of(reagent);
        this.result = new ItemStack(result);
        this.pedestalItems = stacks;
        this.category = category;
        expGain = 5;
        dominionCost = 200;
        this.id = RigoranthusEmortisReborn.rl(result.getRegistryName().getPath());
    }

    @Override
    public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile, @Nullable Player player) {
        pedestalItems = pedestalItems.stream().filter(itemStack -> !itemStack.isEmpty()).collect(Collectors.toList());
        return doesReagentMatch(reagent) && this.pedestalItems.size() == pedestalItems.size() && doItemsMatch(pedestalItems, this.pedestalItems);
    }

    public boolean doesReagentMatch(ItemStack reag){
        return this.reagent.test(reag);
    }

    @Override
    public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile) {
        return result.copy();
    }

    // Function to check if both arrays are same
    static boolean doItemsMatch(List<ItemStack> inputs, List<Ingredient> recipeItems) {
        StackedContents recipeitemhelper = new StackedContents();
        for(ItemStack i : inputs)
            recipeitemhelper.accountStack(i, 1);

        return inputs.size() == recipeItems.size() && (net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  recipeItems) != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PsyglyphicAmalgamatorRecipe that = (PsyglyphicAmalgamatorRecipe) o;
        return Objects.equals(reagent, that.reagent) &&
                Objects.equals(pedestalItems, that.pedestalItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reagent, pedestalItems);
    }

    @Override
    public String toString() {
        return "PsyglyphicAmalgamatorRecipe{" +
                "catalyst = " + reagent +
                ", result = " + result +
//                ", expGain = " + expGain +
                ", pedestalItems = " + pedestalItems +
                '}';
    }

    public JsonElement asRecipe(){
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("type", "rigoranthusemortisreborn:amalgamator_recipe");
//        int counter = 1;
        JsonArray pedArray = new JsonArray();
        for(Ingredient i : this.pedestalItems){
            JsonObject object = new JsonObject();
            object.add("item", i.toJson());
            pedArray.add(object);
        }
//            JsonArray item = new JsonArray();
//            item.add(i.toJson());
//            jsonobject.add("item_" + counter, item);
//            counter++;
        JsonArray reagent =  new JsonArray();
        reagent.add(this.reagent.toJson());
        jsonobject.add("reagent", reagent);

        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("item", this.result.getItem().getRegistryName().toString());
        int count = this.result.getCount();
        if (count > 1) {
            resultObj.addProperty("count", count);
        }
        jsonobject.add("pedestalItems", pedArray);
        jsonobject.add("output", resultObj);
        jsonobject.addProperty("expGain", expGain);
        jsonobject.addProperty("dominionCost", dominionCost);
        return jsonobject;
    }

    /**
     * Converts to a patchouli documentation page
     */
    public JsonElement serialize() {
        JsonObject jsonobject = new JsonObject();

        jsonobject.addProperty("name", this.result.getItem().getDescriptionId());
        jsonobject.addProperty("icon",  this.result.getItem().getRegistryName().toString());
        jsonobject.addProperty("category", this.category);
        JsonArray jsonArray = new JsonArray();
        JsonObject descPage = new JsonObject();
        descPage.addProperty("type", "text");
        descPage.addProperty("text",EmortisConstants.MOD_ID + ".page." + this.result.getItem().getRegistryName().toString().replace(EmortisConstants.MOD_ID + ":", ""));
        JsonObject infoPage = new JsonObject();
        infoPage.addProperty("type", "amalgamator_recipe");
        infoPage.addProperty("recipe", this.result.getItem().getRegistryName().toString());

        jsonArray.add(descPage);
        jsonArray.add(infoPage);
        jsonobject.add("pages", jsonArray);
        return jsonobject;
    }

    @Override
    public boolean consumesDominion() {
        return dominionCost() > 0;
    }

    @Override
    public int dominionCost() {
        return dominionCost;
    }

    @Override
    public int expGain() {
        return expGain;
    }

    @Override
    public boolean matches(PsyglyphicAmalgamatorTile tile, Level worldIn) {
        return isMatch(tile.getPedestalItems(), tile.catalystItem, tile, null);
    }

    public boolean matches(PsyglyphicAmalgamatorTile tile, Level worldIn, @Nullable Player playerEntity) {
        return isMatch(tile.getPedestalItems(), tile.catalystItem, tile, playerEntity);
    }

    @Override
    public ItemStack assemble(PsyglyphicAmalgamatorTile inv) {
        return this.result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.PSYGLYPHIC_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return Registry.RECIPE_TYPE.get(RigoranthusEmortisReborn.rl("amalgamator_recipe"));
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<PsyglyphicAmalgamatorRecipe> {

        @Override
        public PsyglyphicAmalgamatorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient reagent = Ingredient.fromJson(GsonHelper.getAsJsonArray(json, "reagent"));
            ItemStack output = new ItemStack(GsonHelper.getAsItem(json, "output"));
            JsonArray pedestalItems = GsonHelper.getAsJsonArray(json,"pedestalItems");
            int cost = json.has("dominionCost") ? GsonHelper.getAsInt(json, "dominionCost") : 0;
            int exp = json.has("expGain") ? GsonHelper.getAsInt(json, "expGain") : 0;
            List<Ingredient> stacks = new ArrayList<>();

            for(JsonElement e : pedestalItems){
                JsonObject obj = e.getAsJsonObject();
                Ingredient input;
                if(GsonHelper.isArrayNode(obj, "item")){
                    input = Ingredient.fromJson(GsonHelper.getAsJsonArray(obj, "item"));
                }else{
                    input = Ingredient.fromJson(GsonHelper.getAsJsonObject(obj, "item"));
                }
                stacks.add(input);
            }
//            for(int i = 1; i < 9; i++){
//                if(json.has("item_"+i))
//                    stacks.add(Ingredient.fromJson(GsonHelper.getAsJsonArray(json, "item_" + i)));
//            }
            return new PsyglyphicAmalgamatorRecipe(recipeId, stacks, reagent, output, cost, exp);
        }
        @Nullable
        @Override
        public PsyglyphicAmalgamatorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int length = buffer.readInt();
            Ingredient reagent = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            List<Ingredient> stacks = new ArrayList<>();
            for(int i = 0; i < length; i++){
                try{ stacks.add(Ingredient.fromNetwork(buffer)); }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
            int cost = buffer.readInt();
            int exp = buffer.readInt();
            return new PsyglyphicAmalgamatorRecipe(recipeId, stacks, reagent, output, cost, exp);
        }
        @Override
        public void toNetwork(FriendlyByteBuf buf, PsyglyphicAmalgamatorRecipe recipe) {
            buf.writeInt(recipe.pedestalItems.size());
            recipe.reagent.toNetwork(buf);
            buf.writeItem(recipe.result);
            for(Ingredient i : recipe.pedestalItems){
                i.toNetwork(buf);
            }
            buf.writeInt(recipe.dominionCost);
        }
    }
}