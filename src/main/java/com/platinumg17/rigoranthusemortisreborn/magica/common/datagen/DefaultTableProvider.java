package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.platinumg17.rigoranthusemortisreborn.magica.common.block.TableBlock;
import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultTableProvider extends LootTableProvider {
    public DefaultTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }
    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    public static class BlockLootTable extends BlockLoot {
        public List<Block> list = new ArrayList<>();
        @Override
        protected void addTables() {
            registerDropSelf(BlockRegistry.DOMINION_GEM_BLOCK);
            registerDropSelf(BlockRegistry.AZULOREAL_LOG);
            registerDropSelf(BlockRegistry.AZULOREAL_WOOD);
            registerDropSelf(BlockRegistry.STRIPPED_AZULOREAL_LOG);
            registerDropSelf(BlockRegistry.STRIPPED_AZULOREAL_WOOD);
            registerDropSelf(BlockRegistry.JESSIC_LOG);
            registerDropSelf(BlockRegistry.JESSIC_WOOD);
            registerDropSelf(BlockRegistry.STRIPPED_JESSIC_LOG);
            registerDropSelf(BlockRegistry.STRIPPED_JESSIC_WOOD);
            registerBedCondition(BlockRegistry.TABLE_BLOCK, TableBlock.PART, BedPart.HEAD);
        }
        protected <T extends Comparable<T> & StringRepresentable> void registerBedCondition(Block block, Property<T> prop, T isValue) {
            list.add(block);
            this.add(block, LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(prop, isValue)))))));
        }
        public void registerLeavesAndSticks(Block leaves, Block sapling){
            list.add(leaves);
            this.add(leaves, l_state -> createLeavesDrops(l_state, sapling, DEFAULT_SAPLING_DROP_RATES));
        }

        public void registerDropDoor(Block block){
            list.add(block);
            this.add(block, BlockLoot::createDoorTable);
        }

        public void registerDropSelf(Block block){
            list.add(block);
            dropSelf(block);
        }

        public void registerDrop(Block input, ItemLike output){
            list.add(input);
            dropOther(input, output);
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return list;
        }

    }

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(
            Pair.of(BlockLootTable::new, LootContextParamSets.BLOCK)
    );

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return tables;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((p_218436_2_, p_218436_3_) -> {
            LootTables.validate(validationtracker, p_218436_2_, p_218436_3_);
        });
    }
}