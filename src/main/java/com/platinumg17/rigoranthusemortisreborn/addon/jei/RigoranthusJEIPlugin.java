package com.platinumg17.rigoranthusemortisreborn.addon.jei;

import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.IBeddingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.ICasingMaterial;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicAmalgamatorRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.psyglyphic_amalgamator.PsyglyphicEnchantingRecipe;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.recipe.CraftingPressRecipe;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.gui.SmelteryScreenBase;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisBlocks;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.CanisBedUtil;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.core.init.Registration;
import com.platinumg17.rigoranthusemortisreborn.magica.common.potions.ModPotions;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.MagicItemsRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JeiPlugin
public class RigoranthusJEIPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return RigoranthusEmortisReborn.rl("plugin_" + EmortisConstants.MOD_ID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(
				new CraftingPressRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
				new PsyglyphicAmalgamatorRecipeCategory(registry.getJeiHelpers().getGuiHelper())
		);
	}

	@Override
	public void registerAdvanced(IAdvancedRegistration registration) {
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		if (Config.ClientConfig.enableJeiPlugin.get() && Config.ClientConfig.enableJeiCatalysts.get()) {
			registry.addRecipeCatalyst(new ItemStack(BlockRegistry.EMORTIC_CRAFTING_PRESS_BLOCK), CraftingPressRecipeCategory.UID);
			registry.addRecipeCatalyst(new ItemStack(BlockRegistry.PSYGLYPHIC_AMALG_BLOCK), PsyglyphicAmalgamatorRecipeCategory.UID);

			registry.addRecipeCatalyst(new ItemStack(Registration.MASTERFUL_SMELTERY.get()), VanillaRecipeCategoryUid.FURNACE);
			registry.addRecipeCatalyst(new ItemStack(Registration.MASTERFUL_SMELTERY.get()), VanillaRecipeCategoryUid.FUEL);
		}
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		if (Config.ClientConfig.enableJeiPlugin.get() && Config.ClientConfig.enableJeiClickArea.get()) {
			registry.addRecipeClickArea(SmelteryScreenBase.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.FURNACE);
		}
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		if (Config.ClientConfig.enableAllCanisBedRecipes.get()) {
			doRegister(registration);
		}
	}

	public void doRegister(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(CanisBlocks.CANIS_BED.get().asItem(), (stack, ctx) -> {
			Pair<ICasingMaterial, IBeddingMaterial> materials = CanisBedUtil.getMaterials(stack);

			String casingKey = materials.getLeft() != null
					? materials.getLeft().getRegistryName().toString()
					: "rigoranthusemortisreborn:casing_missing";

			String beddingKey = materials.getRight() != null
					? materials.getRight().getRegistryName().toString()
					: "rigoranthusemortisreborn:bedding_missing";

			return casingKey + "+" + beddingKey;
		});
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		List<CraftingPressRecipe> pressRecipes = new ArrayList<>();
		List<PsyglyphicAmalgamatorRecipe> amalgamator = new ArrayList<>();
		RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
		for(Recipe i : manager.getRecipes()) {
			if (i instanceof CraftingPressRecipe) {
				pressRecipes.add((CraftingPressRecipe) i);
			}
			if (i instanceof PsyglyphicAmalgamatorRecipe && !(i instanceof PsyglyphicEnchantingRecipe)) {
				amalgamator.add((PsyglyphicAmalgamatorRecipe) i);
			}
		}
		registration.addRecipes(pressRecipes, CraftingPressRecipeCategory.UID);
		registration.addRecipes(amalgamator, PsyglyphicAmalgamatorRecipeCategory.UID);

		ItemStack dominionPot = PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.DOMINION_REGEN_POTION);
		ItemStack necroPot = PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.NECROTIZING_FASCIITIS);

		IJeiBrewingRecipe dominionPotionRecipe = registration.getVanillaRecipeFactory().createBrewingRecipe(Collections.singletonList(new ItemStack(BlockRegistry.DOMINION_BERRY_BUSH)),
				PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD), dominionPot );
		IJeiBrewingRecipe necroPotionRecipe = registration.getVanillaRecipeFactory().createBrewingRecipe(Collections.singletonList(new ItemStack(MagicItemsRegistry.DWELLER_FLESH)),
				PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD), necroPot );

		registration.addRecipes(Collections.singletonList(dominionPotionRecipe), new ResourceLocation(ModIds.MINECRAFT_ID, "brewing"));
		registration.addRecipes(Collections.singletonList(necroPotionRecipe), new ResourceLocation(ModIds.MINECRAFT_ID, "brewing"));

		registration.addRecipes(CanisBedRecipeMaker.createCanisBedRecipes(), VanillaRecipeCategoryUid.CRAFTING);
	}
}