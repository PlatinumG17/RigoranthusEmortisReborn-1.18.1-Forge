package com.platinumg17.rigoranthusemortisreborn.util;

import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the array with colors that the player picks from, and provides utility function to handle colors.
 */
@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ColorHandler {
    public static final int DEFAULT_COLOR = 0xA0DCFF;
    private static final List<Pair<Integer, String>> colors;

    static
    {
        colors = new ArrayList<>();

        colors.add(Pair.of(0x0715cd, "blue"));
        colors.add(Pair.of(0xb536da, "orchid"));
        colors.add(Pair.of(0xe00707, "red"));
        colors.add(Pair.of(0x4ac925, "green"));

        colors.add(Pair.of(0x00d5f2, "cyan"));
        colors.add(Pair.of(0xff6ff2, "pink"));
        colors.add(Pair.of(0xf2a400, "orange"));
        colors.add(Pair.of(0x1f9400, "emerald"));

        colors.add(Pair.of(0xa10000, "rust"));
        colors.add(Pair.of(0xa15000, "bronze"));
        colors.add(Pair.of(0xa1a100, "gold"));
        colors.add(Pair.of(0x626262, "iron"));
        colors.add(Pair.of(0x416600, "olive"));
        colors.add(Pair.of(0x008141, "jade"));
        colors.add(Pair.of(0x008282, "teal"));
        colors.add(Pair.of(0x005682, "cobalt"));
        colors.add(Pair.of(0x000056, "indigo"));
        colors.add(Pair.of(0x2b0057, "purple"));
        colors.add(Pair.of(0x6a006a, "violet"));
        colors.add(Pair.of(0x77003c, "fuchsia"));
    }

    public static int getColor(int index) {
        if(index < 0 || index >= colors.size())
            return DEFAULT_COLOR;
        return colors.get(index).getLeft();
    }

    public static Component getName(int index) {
        if(index < 0 || index >= colors.size())
            return new TextComponent("INVALID");
        return new TranslatableComponent("rigoranthusemortisreborn.color." + colors.get(index).getRight());
    }
    public static int getColorSize() {return colors.size();}
    public static ItemStack setDefaultColor(ItemStack stack) {return setColor(stack, DEFAULT_COLOR);}
    public static ItemStack setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("color", color);
        return stack;
    }

    public static int getColorFromStack(ItemStack stack) {
        if(stack.hasTag() && stack.getTag().contains("color", Tag.TAG_ANY_NUMERIC))
            return stack.getTag().getInt("color");
        else return DEFAULT_COLOR;
    }

//    public static int getColorForDimension(ServerLevel world) {
//        ModDimentionConnection c = dimension_HANDLER.getConnectionForDimension(world);
//        return c == null ? ColorHandler.DEFAULT_COLOR : getColorForPlayer(c.getClientIdentifier(), world);
//    }

//    public static int getColorForPlayer(ServerPlayer player) {return getColorForPlayer(IdentifierHandler.encode(player), player.level);}
//    public static int getColorForPlayer(PlayerIdentifier identifier, Level world) {return PlayerSavedData.getData(identifier, world).getColor();}
}
