package com.platinumg17.rigoranthusemortisreborn.core.init;

//import com.google.common.collect.Maps;
//import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
//import net.minecraft.Util;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//
//import javax.annotation.Nullable;
//import java.util.Map;
//import java.util.function.Supplier;
//
///**
// * @author SmellyModder (Luke Tonon)
// */
//public final class REBoatRegistry {
//    private static final Map<String, REBoatRegistry.BoatData> BOATS = Util.make(Maps.newHashMap(), (entries) -> {
//        entries.put("minecraft:oak", new REBoatRegistry.BoatData(() -> Items.OAK_BOAT, Blocks.OAK_PLANKS, "minecraft:oak"));
//    });
//
//    public static synchronized void registerBoat(String boatName, Supplier<Item> boat, Block plank) {
//        BOATS.put(boatName, new REBoatRegistry.BoatData(boat, plank, boatName));
//    }
//
//
//    @Nullable
//    public static REBoatRegistry.BoatData getDataForBoat(String boatName) {
//        return BOATS.get(boatName);
//    }
//
//    public static String getNameForData(REBoatRegistry.BoatData data) {
//        for (Map.Entry<String, REBoatRegistry.BoatData> entries : BOATS.entrySet()) {
//            if (entries.getValue().equals(data)) {
//                return entries.getKey();
//            }
//        }
//        return getBaseBoatName();
//    }
//
//    public static String getBaseBoatName() {
//        return BOATS.size() > 1 ? (String) BOATS.keySet().toArray()[1] : "minecraft:oak";
//    }
//
//    public static class BoatData {
//        private final Supplier<Item> boat;
//        private final Block plank;
//        private final ResourceLocation texture;
//
//        public BoatData(Supplier<Item> boat, Block plank, String texture) {
//            this.boat = boat;
//            this.plank = plank;
//            this.texture = this.processTexture(texture);
//        }
//
//        public Item getBoatItem() {
//            return this.boat.get();
//        }
//
//        public Item getPlankItem() {
//            return this.plank.asItem();
//        }
//
//        public ResourceLocation getTexture() {
//            return this.texture;
//        }
//
//        private ResourceLocation processTexture(String texture) {
//            String wood = findWood(texture);
//            return new ResourceLocation(EmortisConstants.MOD_ID, "textures/entity/boat/" + wood + ".png");
//        }
//
//        private String findModId(String parentString) {
//            StringBuilder builder = new StringBuilder();
//            for (char parentChars : parentString.toCharArray()) {
//                if (parentChars == ':') {
//                    break;
//                }
//                builder.append(parentChars);
//            }
//            return builder.toString();
//        }
//
//        private String findWood(String parentString) {
//            StringBuilder builder = new StringBuilder();
//            boolean start = false;
//            for (char parentChars : parentString.toCharArray()) {
//                if (start) {
//                    builder.append(parentChars);
//                }
//
//                if (parentChars == ':') {
//                    start = true;
//                }
//            }
//            return builder.toString();
//        }
//    }
//}