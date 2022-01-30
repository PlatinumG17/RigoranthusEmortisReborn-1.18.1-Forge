package com.platinumg17.rigoranthusemortisreborn.magica.common.datagen;

import com.platinumg17.rigoranthusemortisreborn.magica.setup.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTables extends BaseLootTableProvider {
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        System.out.println(BlockRegistry.DOMINION_JAR);
        blockTables.put(BlockRegistry.DOMINION_JAR, createDominionJarTable("dominion_jar", BlockRegistry.DOMINION_JAR));
        System.out.println(BlockRegistry.ICHOR_JAR);
        blockTables.put(BlockRegistry.ICHOR_JAR, createIchorJarTable("ichor_jar", BlockRegistry.ICHOR_JAR));
    }

    public void putEntityTable(EntityType e, LootTable.Builder table){
        entityTables.put(e.getDefaultLootTable(), table);
    }
}