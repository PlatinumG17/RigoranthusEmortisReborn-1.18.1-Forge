package com.platinumg17.rigoranthusemortisreborn.world.gen.feature;

import com.platinumg17.rigoranthusemortisreborn.entity.mobs.LanguidDwellerEntity;
import com.platinumg17.rigoranthusemortisreborn.entity.mobs.SunderedCadaverEntity;
import com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class OreBlockEmortis extends OreBlock {

    private final UniformInt oreXpDrop = UniformInt.of(8, 15);

    public OreBlockEmortis() {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(10f, 12f).lightLevel(value -> 3).requiresCorrectToolForDrops().sound(SoundType.STONE), UniformInt.of(8, 15));
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.oreXpDrop.sample(RANDOM) : 0;
    }

    /**
     * Perform side-effects from block dropping, such as creating silverfish
     */
    public void spawnAfterBreak(BlockState pState, ServerLevel pLevel, BlockPos pPos, ItemStack pStack) {
        UniformInt spookChance = UniformInt.of(0, 20);
        UniformInt nightmareChance = UniformInt.of(0, 50);
        SunderedCadaverEntity sunderedCadaver = ModEntities.SUNDERED_CADAVER.create(pLevel);
        LanguidDwellerEntity languidDweller = ModEntities.LANGUID_DWELLER.create(pLevel);

        if (spookChance.sample(RANDOM) == 10) {
            sunderedCadaver.absMoveTo(pPos.getX(), pPos.getY(), pPos.getZ());
            sunderedCadaver.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(pPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            pLevel.addFreshEntity(sunderedCadaver);
            sunderedCadaver.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.covetous_fiend").withStyle(Style.EMPTY.withItalic(true)));
            sunderedCadaver.setCustomNameVisible(false);
        }
        if (nightmareChance.sample(RANDOM) == 25) {
            languidDweller.absMoveTo(pPos.getX(), pPos.getY(), pPos.getZ());
            languidDweller.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(pPos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            pLevel.addFreshEntity(languidDweller);
            languidDweller.setCustomName(new TranslatableComponent("entity.rigoranthusemortisreborn.covetous_fiend").withStyle(Style.EMPTY.withItalic(true)));
            languidDweller.setCustomNameVisible(false);
        }
        super.spawnAfterBreak(pState, pLevel, pPos, pStack);
    }
}