package com.platinumg17.rigoranthusemortisreborn.canis.common.lib;

import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import net.minecraft.resources.ResourceLocation;

public class Resources {

    //_____________________________________  G U I   T E X T U R E S  _____________________________________//

    public static final ResourceLocation CANIS_INVENTORY = getGui("canis_inventory");
    public static final ResourceLocation CANIS_INFO_SCREEN = getGui("canis_interface");
    public static final ResourceLocation GUI_FOOD_BOWL = getGui("food_bowl");
    public static final ResourceLocation GUI_WAYWARD_TRAVELLER = getGui("wayward_traveller");
    public static final ResourceLocation GUI_TREAT_BAG = getGui("treat_bag");
    public static final ResourceLocation GUI_SMELTERY = getGui("smeltery");

    public static final ResourceLocation GUI_NAME = getGui("spell_name_paper");
    public static final ResourceLocation GUI_CLEAR = getGui("clear_paper");
    public static final ResourceLocation GUI_CREATE = getGui("create_paper");
    public static final ResourceLocation CLEAR_ICON = getGui("clear_icon");
    public static final ResourceLocation CREATE_ICON = getGui("create_icon");

    public static final ResourceLocation SMALL_WIDGETS = getGui("small_widgets");
    public static final ResourceLocation PAGE_SELECTION = getGui("page_selection");
    public static final ResourceLocation SKILL_BUTTON = getGui("skill_button");
    public static final ResourceLocation MODE_BUTTON = getGui("mode_button");
    public static final ResourceLocation BOOLEAN_BUTTON = getGui("boolean_button");

    public static final ResourceLocation JEI_AMALGAMATOR = getGui("gui_amalgamator");
    public static final ResourceLocation JEI_EMORTIC_PRESS = getGui("gui_emortic_crafting_press");


    //_____________________________________  C A N I S   T E X T U R E S  _____________________________________//

    public static final ResourceLocation SADDLE_TEXTURE = getCanisAccessory("saddle");
    public static final ResourceLocation SATCHEL_TEXTURE = getCanisAccessory("satchel");
    public static final ResourceLocation SHADES_TEXTURE = getCanisAccessory("shades");

    public static final ResourceLocation COLLAR_WHITE = getCanisCollar("white");
    public static final ResourceLocation COLLAR_LIGHT_GRAY = getCanisCollar("light_gray");
    public static final ResourceLocation COLLAR_GRAY = getCanisCollar("gray");
    public static final ResourceLocation COLLAR_BLACK = getCanisCollar("black");
    public static final ResourceLocation COLLAR_BROWN = getCanisCollar("brown");
    public static final ResourceLocation COLLAR_RED = getCanisCollar("red");
    public static final ResourceLocation COLLAR_ORANGE = getCanisCollar("orange");
    public static final ResourceLocation COLLAR_YELLOW = getCanisCollar("yellow");
    public static final ResourceLocation COLLAR_LIME = getCanisCollar("lime");
    public static final ResourceLocation COLLAR_GREEN = getCanisCollar("green");
    public static final ResourceLocation COLLAR_CYAN = getCanisCollar("cyan");
    public static final ResourceLocation COLLAR_LIGHT_BLUE = getCanisCollar("light_blue");
    public static final ResourceLocation COLLAR_BLUE = getCanisCollar("blue");
    public static final ResourceLocation COLLAR_PURPLE = getCanisCollar("purple");
    public static final ResourceLocation COLLAR_MAGENTA = getCanisCollar("magenta");
    public static final ResourceLocation COLLAR_PINK = getCanisCollar("pink");

    public static final ResourceLocation CLOTH_WHITE = getClothColor("white");
    public static final ResourceLocation CLOTH_LIGHT_GRAY = getClothColor("light_gray");
    public static final ResourceLocation CLOTH_GRAY = getClothColor("gray");
    public static final ResourceLocation CLOTH_BLACK = getClothColor("black");
    public static final ResourceLocation CLOTH_BROWN = getClothColor("brown");
    public static final ResourceLocation CLOTH_RED = getClothColor("red");
    public static final ResourceLocation CLOTH_ORANGE = getClothColor("orange");
    public static final ResourceLocation CLOTH_YELLOW = getClothColor("yellow");
    public static final ResourceLocation CLOTH_LIME = getClothColor("lime");
    public static final ResourceLocation CLOTH_GREEN = getClothColor("green");
    public static final ResourceLocation CLOTH_CYAN = getClothColor("cyan");
    public static final ResourceLocation CLOTH_LIGHT_BLUE = getClothColor("light_blue");
    public static final ResourceLocation CLOTH_BLUE = getClothColor("blue");
    public static final ResourceLocation CLOTH_PURPLE = getClothColor("purple");
    public static final ResourceLocation CLOTH_MAGENTA = getClothColor("magenta");
    public static final ResourceLocation CLOTH_PINK = getClothColor("pink");

    public static final ResourceLocation SHADES_BLACK = getShadesColor("black");
    public static final ResourceLocation SHADES_BLK_TRANSPARENT = getShadesColor("black_transparent");
    public static final ResourceLocation SHADES_BLUE = getShadesColor("blue");
    public static final ResourceLocation SHADES_CYAN = getShadesColor("cyan");
    public static final ResourceLocation SHADES_GLASS = getShadesColor("glass");
    public static final ResourceLocation SHADES_GOLDEN = getShadesColor("golden");
    public static final ResourceLocation SHADES_GRAY = getShadesColor("gray");
    public static final ResourceLocation SHADES_GREEN = getShadesColor("green");
    public static final ResourceLocation SHADES_ORANGE = getShadesColor("orange");
    public static final ResourceLocation SHADES_PURPLE = getShadesColor("purple");
    public static final ResourceLocation SHADES_RED = getShadesColor("red");
    public static final ResourceLocation SHADES_YELLOW = getShadesColor("yellow");


    //_____________________________________  M O B   T E X T U R E S  _____________________________________//

    public static final ResourceLocation TAME_CANIS_TEXTURE = getCanisTexture("tame_canis_chordata");
    public static final ResourceLocation FERAL_CANIS_TEXTURE = getCanisTexture("feral_canis_chordata");
    public static final ResourceLocation KYPHOS_TEXTURE = getCanisTexture("canis_kyphos");
    public static final ResourceLocation CAVALIER_TEXTURE = getCanisTexture("canis_cavalier");
    public static final ResourceLocation HOMINI_TEXTURE = getCanisTexture("canis_homini");

    public static final ResourceLocation CADAVER_TEXTURE = getMobTexture("sundered_cadaver");
    public static final ResourceLocation NECRAW_TEXTURE = getMobTexture("necraw_fascii");
    public static final ResourceLocation DWELLER_TEXTURE = getMobTexture("languid_dweller");

    //_____________________________________  M O B   M O D E L S  _____________________________________//

    public static final ResourceLocation TAME_CANIS_MODEL = getGeoModel("canis/tame_canis_chordata");
    public static final ResourceLocation FERAL_CANIS_MODEL = getGeoModel("canis/feral_canis_chordata");
    public static final ResourceLocation CANIS_ARMOR_MODEL = getGeoModel("canis/canis_armor");
    public static final ResourceLocation SADDLE_MODEL = getGeoModel("canis/saddle");
    public static final ResourceLocation SATCHEL_MODEL = getGeoModel("canis/satchel");
    public static final ResourceLocation SHADES_MODEL = getGeoModel("canis/shades");
    public static final ResourceLocation BEGGING_MODEL = getGeoModel("canis/tame_canis_begging");
    public static final ResourceLocation LYING_MODEL = getGeoModel("canis/tame_canis_lying");
    public static final ResourceLocation SITTING_MODEL = getGeoModel("canis/tame_canis_sitting");

    public static final ResourceLocation CADAVER_MODEL = getGeoModel("sundered_cadaver");
    public static final ResourceLocation NECRAW_MODEL = getGeoModel("necraw_fascii");
    public static final ResourceLocation DWELLER_MODEL = getGeoModel("languid_dweller");

    //_____________________________________  M O B   A N I M A T I O N S  _____________________________________//

    public static final ResourceLocation TAME_CANIS_ANIMATION = getAnimation("canis/tame_canis_chordata");
    public static final ResourceLocation FERAL_CANIS_ANIMATION = getAnimation("canis/feral_canis_chordata");

    public static final ResourceLocation CADAVER_ANIMATION = getAnimation("sundered_cadaver");
    public static final ResourceLocation NECRAW_ANIMATION = getAnimation("necraw_fascii");
    public static final ResourceLocation DWELLER_ANIMATION = getAnimation("languid_dweller");

    //_____________________________________  O L D  _____________________________________//

    public static final ResourceLocation COLLAR_DEFAULT = getEntity("canis", "canis_collar");
    public static final ResourceLocation COLLAR_GOLDEN = getEntity("canis", "canis_collar_gold");
    public static final ResourceLocation GLASSES_SUNGLASSES = getEntity("canis", "cool_guy_sunglasses");
    public static final ResourceLocation BOW_TIE = getEntity("canis", "canis_bowtie");
    public static final ResourceLocation CAPE = getEntity("canis", "canis_cape");
    public static final ResourceLocation SKILL_SAVIOR = getEntity("canis/skills", "savior");
    public static final ResourceLocation SKILL_CHEST = getEntity("canis", "canis_satchel");

    public static final ResourceLocation IRON_ARMOR = getCanisArmor("iron");
    public static final ResourceLocation DIAMOND_ARMOR = getCanisArmor("diamond");
    public static final ResourceLocation GOLD_ARMOR = getCanisArmor("gold");
    public static final ResourceLocation CHAINMAIL_ARMOR = getCanisArmor("chainmail");
    public static final ResourceLocation LEATHER_ARMOR = getCanisArmor("leather");
    public static final ResourceLocation TURTLE_ARMOR = getCanisArmor("turtle");
    public static final ResourceLocation NETHERITE_ARMOR = getCanisArmor("netherite");

    public static final ResourceLocation IRON_HELMET = getCanisArmor("iron_helmet");
    public static final ResourceLocation DIAMOND_HELMET = getCanisArmor("diamond_helmet");
    public static final ResourceLocation GOLDEN_HELMET = getCanisArmor("golden_helmet");
    public static final ResourceLocation CHAINMAIL_HELMET = getCanisArmor("chainmail_helmet");
    public static final ResourceLocation LEATHER_HELMET = getCanisArmor("leather_helmet");
    public static final ResourceLocation TURTLE_HELMET = getCanisArmor("turtle_helmet");
    public static final ResourceLocation NETHERITE_HELMET = getCanisArmor("netherite_helmet");

    public static final ResourceLocation IRON_BOOTS = getCanisArmor("iron_boots");
    public static final ResourceLocation DIAMOND_BOOTS = getCanisArmor("diamond_boots");
    public static final ResourceLocation GOLDEN_BOOTS = getCanisArmor("golden_boots");
    public static final ResourceLocation CHAINMAIL_BOOTS = getCanisArmor("chainmail_boots");
    public static final ResourceLocation LEATHER_BOOTS = getCanisArmor("leather_boots");
    public static final ResourceLocation NETHERITE_BOOTS = getCanisArmor("netherite_boots");

    public static final ResourceLocation IRON_BODY_PIECE = getCanisArmor("iron_body");
    public static final ResourceLocation DIAMOND_BODY_PIECE = getCanisArmor("diamond_body");
    public static final ResourceLocation GOLDEN_BODY_PIECE = getCanisArmor("golden_body");
    public static final ResourceLocation CHAINMAIL_BODY_PIECE = getCanisArmor("chainmail_body");
    public static final ResourceLocation LEATHER_BODY_PIECE = getCanisArmor("leather_body");
    public static final ResourceLocation NETHERITE_BODY_PIECE = getCanisArmor("netherite_body");

    public static ResourceLocation getAnimation(String animationFileName) {
        return REUtil.getResource("animations/" + animationFileName + ".json");
    }

    public static ResourceLocation getGeoModel(String modelFileName) {
        return REUtil.getResource("geo/" + modelFileName + ".geo.json");
    }

    public static ResourceLocation getEntity(String type, String textureFileName) {
        return REUtil.getResource("textures/entity/" + type + "/" + textureFileName + ".png");
    }

    public static ResourceLocation getMobTexture(String textureFileName) {
        return REUtil.getResource("textures/entity/" + textureFileName + ".png");
    }

    public static ResourceLocation getCanisTexture(String textureFileName) {
        return REUtil.getResource("textures/entity/canis/" + textureFileName + ".png");
    }

    public static ResourceLocation getCanisCollar(String color) {
        return REUtil.getResource("textures/entity/canis/collar_colors/canis_" + color + ".png");
    }

    public static ResourceLocation getClothColor(String color) {
        return REUtil.getResource("textures/entity/canis/accessories/cloth_colors/cloth_" + color + ".png");
    }

    public static ResourceLocation getShadesColor(String color) {
        return REUtil.getResource("textures/entity/canis/accessories/shades_colors/shades_" + color + ".png");
    }

    public static ResourceLocation getCanisArmor(String textureFileName) {
        return REUtil.getResource("textures/entity/canis/armor/" + textureFileName + ".png");
    }

    public static ResourceLocation getCanisAccessory(String textureFileName) {
        return REUtil.getResource("textures/entity/canis/accessories/" + textureFileName + ".png");
    }

    public static ResourceLocation getGui(String textureFileName) {
        return REUtil.getResource("textures/gui/" + textureFileName + ".png");
    }
}