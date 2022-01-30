package com.platinumg17.rigoranthusemortisreborn.magica.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Registers this mod's {@link KeyMapping}s.
 *
 * @author Choonster
 */
@Mod.EventBusSubscriber(modid = EmortisConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class REKeyBindings {

    private static final String CATEGORY = "key.category.rigoranthusemortisreborn.general";

    public static final KeyMapping OPEN_CANIS_INV = new KeyMapping("key.rigoranthusemortisreborn.open_inventory", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, CATEGORY);
    public static final KeyMapping OPEN_COMMAND_SELECTION = new KeyMapping("key.rigoranthusemortisreborn.command_selection_hud", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);

    public static final KeyMapping NEXT_COMMAND = new KeyMapping("key.rigoranthusemortisreborn.next_command",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            CATEGORY);
    public static final KeyMapping PREVIOUS__COMMAND = new KeyMapping("key.rigoranthusemortisreborn.previous_command",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            CATEGORY);


    @SubscribeEvent
    public static void registerKeyBindings(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(OPEN_CANIS_INV);
        ClientRegistry.registerKeyBinding(OPEN_COMMAND_SELECTION);
//        ClientRegistry.registerKeyBinding(PREVIOUS__COMMAND);
//        ClientRegistry.registerKeyBinding(NEXT_COMMAND);
    }
}

//        ClientRegistry.registerKeyBinding(OPEN_BOOK);
//        ClientRegistry.registerKeyBinding(OPEN_SPELL_SELECTION);
//        ClientRegistry.registerKeyBinding(PREVIOUS__SLOT);
//        ClientRegistry.registerKeyBinding(NEXT_SLOT);

//    public static final KeyBinding OPEN_BOOK = new KeyBinding("key.rigoranthusemortisreborn.open_book", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_C, CATEGORY);
//    public static final KeyBinding OPEN_SPELL_SELECTION = new KeyBinding("key.rigoranthusemortisreborn.selection_hud", KeyConflictContext.IN_GAME,
//                      InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);
//    public static final KeyBinding NEXT_SLOT = new KeyBinding("key.rigoranthusemortisreborn.next_slot",
//            KeyConflictContext.IN_GAME,
//            InputMappings.Type.KEYSYM,
//            GLFW.GLFW_KEY_X,
//            CATEGORY);
//    public static final KeyBinding PREVIOUS__SLOT = new KeyBinding("key.rigoranthusemortisreborn.previous_slot",
//            KeyConflictContext.IN_GAME,
//            InputMappings.Type.KEYSYM,
//            GLFW.GLFW_KEY_Z,
//            CATEGORY);