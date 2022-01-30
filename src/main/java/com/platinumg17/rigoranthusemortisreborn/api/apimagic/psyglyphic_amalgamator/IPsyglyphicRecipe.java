package com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator;

import com.platinumg17.rigoranthusemortisreborn.magica.common.block.tile.PsyglyphicAmalgamatorTile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import javax.annotation.Nullable;
import java.util.List;

public interface IPsyglyphicRecipe extends Recipe<PsyglyphicAmalgamatorTile> {

    boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile, @Nullable Player player);

    /**
     * Tile sensitive result
     */
    ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, PsyglyphicAmalgamatorTile psyglyphicAmalgamatorTile);

    default boolean consumesDominion(){
        return dominionCost() > 0;
    }
    int dominionCost();
    int expGain();
}