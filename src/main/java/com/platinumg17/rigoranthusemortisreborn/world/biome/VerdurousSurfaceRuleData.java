package com.platinumg17.rigoranthusemortisreborn.world.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;


//public class VerdurousSurfaceRuleData {
//        private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
//        private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
//        private static final SurfaceRules.RuleSource RED_TERRACOTTA = makeStateRule(Blocks.RED_TERRACOTTA);
//        private static final SurfaceRules.RuleSource BLUE_TERRACOTTA = makeStateRule(Blocks.BLUE_TERRACOTTA);
//
//        protected static SurfaceRules.RuleSource makeRules()
//        {
//            SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
//            SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
//
//            return SurfaceRules.sequence(
//                    SurfaceRules.ifTrue(SurfaceRules.isBiome(VerdurousBiomes.VERDUROUS_FIELDS), GRASS_BLOCK),
//                    SurfaceRules.ifTrue(SurfaceRules.isBiome(VerdurousBiomes.VERDUROUS_WOODLANDS), GRASS_BLOCK),
//
//                    // Default to a grass and dirt surface
//                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
//            );
//        }
//
//        private static SurfaceRules.RuleSource makeStateRule(Block block) {
//            return SurfaceRules.state(block.defaultBlockState());
//        }
//}