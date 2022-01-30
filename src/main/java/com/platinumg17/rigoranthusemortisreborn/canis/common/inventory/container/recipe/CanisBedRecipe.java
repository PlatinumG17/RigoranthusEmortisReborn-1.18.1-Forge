package com.platinumg17.rigoranthusemortisreborn.canis.common.inventory.container.recipe;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.IBeddingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.CanisBedUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.CanisRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class CanisBedRecipe extends CustomRecipe implements IShapedRecipe<CraftingContainer> {

    public CanisBedRecipe(ResourceLocation resource) {
        super(resource);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        IBeddingMaterial beddingId = null;
        ICasingMaterial casingId = null;
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if (col == 1 && row < 2) {
                    IBeddingMaterial id = CanisBedUtil.getBeddingFromStack(RigoranthusEmortisRebornAPI.BEDDING_MATERIAL, inv.getItem(row * inv.getWidth() + col));
                    if (id == null) {return false;}
                    if (beddingId == null) {beddingId = id;}
                    else if (beddingId != id) {return false;}
                }
                else {
                    ICasingMaterial id = CanisBedUtil.getCasingFromStack(RigoranthusEmortisRebornAPI.CASING_MATERIAL, inv.getItem(row * inv.getWidth() + col));
                    if (id == null) {return false;}
                    if (casingId == null) {casingId = id;}
                    else if (casingId != id) {return false;}
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        IBeddingMaterial beddingId = CanisBedUtil.getBeddingFromStack(RigoranthusEmortisRebornAPI.BEDDING_MATERIAL, inv.getItem(1));
        ICasingMaterial casingId = CanisBedUtil.getCasingFromStack(RigoranthusEmortisRebornAPI.CASING_MATERIAL, inv.getItem(0));
        return CanisBedUtil.createItemStack(casingId, beddingId);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }
        return nonnulllist;
    }
    //Is on a 3x3 grid or bigger
    @Override public boolean canCraftInDimensions(int width, int height) {return width >= 3 && height >= 3;}
    @Override public RecipeSerializer<?> getSerializer() {return CanisRecipeSerializers.CANIS_BED.get();}
    @Override public int getRecipeWidth() {return 3;}
    @Override public int getRecipeHeight() {return 3;}
}