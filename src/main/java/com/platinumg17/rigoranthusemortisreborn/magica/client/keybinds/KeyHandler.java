package com.platinumg17.rigoranthusemortisreborn.magica.client.keybinds;

import com.platinumg17.rigoranthusemortisreborn.api.apimagic.util.StackUtil;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.CanisInventoriesScreen;
import com.platinumg17.rigoranthusemortisreborn.canis.common.items.CommandWritItem;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.GuiRadialMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EmortisConstants.MOD_ID)
public class KeyHandler {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();
    public static void checkKeysPressed(int key){
//        ItemStack stack = StackUtil.getHeldSpellbook(MINECRAFT.player);
        ItemStack whistle = StackUtil.getHeldWhistle(MINECRAFT.player);

//        if(key == REKeyBindings.NEXT_SLOT.getKey().getValue()  && stack.getItem() instanceof SpellBook){
//            if(!stack.hasTag())
//                return;
//            CompoundTag tag = stack.getTag();
//            int newMode = SpellBook.getMode(tag) + 1;
//            if(newMode > 10)
//                newMode = 0;
//            sendUpdatePacket(tag, newMode);
//            return;
//        }
//        if(key == REKeyBindings.PREVIOUS__SLOT.getKey().getValue()  && stack.getItem() instanceof SpellBook){
//            if(!stack.hasTag())
//                return;
//            CompoundTag tag = stack.getTag();
//            int newMode = SpellBook.getMode(tag) - 1;
//            if(newMode < 0)
//                newMode = 10;
//            sendUpdatePacket(tag, newMode);
//            return;
//        }
        if(key == REKeyBindings.NEXT_COMMAND.getKey().getValue()  && whistle.getItem() instanceof CommandWritItem){
            if(!whistle.hasTag())
                return;
            CompoundTag tag = whistle.getTag();
            int newMode = CommandWritItem.getMode(tag) + 1;
            if(newMode > 5)
                newMode = 0;

            sendUpdatePacket(tag, newMode);
            return;
        }

        if(key == REKeyBindings.PREVIOUS__COMMAND.getKey().getValue()  && whistle.getItem() instanceof CommandWritItem){
            if(!whistle.hasTag())
                return;
            CompoundTag tag = whistle.getTag();
            int newMode = CommandWritItem.getMode(tag) - 1;
            if(newMode < 0)
                newMode = 5;

            sendUpdatePacket(tag, newMode);
            return;
        }

        if(key == REKeyBindings.OPEN_COMMAND_SELECTION.getKey().getValue()){
            if(MINECRAFT.screen instanceof GuiRadialMenu) {
                MINECRAFT.player.closeContainer();
                return;
            }
            if(whistle.getItem() instanceof CommandWritItem && whistle.hasTag() && MINECRAFT.screen == null){
                MINECRAFT.setScreen(new GuiRadialMenu(whistle.getTag()));
            }
        }

        if(key == REKeyBindings.OPEN_CANIS_INV.getKey().getValue()){
            if(MINECRAFT.screen instanceof CanisInventoriesScreen) {
                MINECRAFT.player.closeContainer();
                return;
            }
//            List<CanisEntity> cani;
//            Minecraft mc = Minecraft.getInstance();
//            cani = mc.level.getEntitiesOfClass(CanisEntity.class, mc.player.getBoundingBox().inflate(12D, 12D, 12D),
//                    (canis) -> canis.canInteract(mc.player) && WaywardTravellerSkill.hasInventory(canis)
//            );
//            if (!cani.isEmpty() && MINECRAFT.screen == null) {
//                IntArray array = new IntArray(cani.size());
//                for (int i = 0; i < array.getCount(); i++) {
//                    array.set(i, cani.get(i).getId());
//                }
//                Optional<CanisEntity> target = cani.stream().findFirst();
//                MINECRAFT.setScreen(new CanisInventoriesScreen(new CanisInventoriesContainer(1, mc.player.inventory, array), mc.player.inventory, target.get().getDisplayName()));
//            }
//            if(stack.getItem() instanceof SpellBook && stack.hasTag() && MINECRAFT.screen == null){
//                GuiSpellBook.open(RigoranthusEmortisRebornAPI.getInstance(), stack.getTag(), ((SpellBook) stack.getItem()).getTier().ordinal(), SpellBook.getUnlockedSpellString(stack.getTag()));
//            }
        }
    }
    @SubscribeEvent
    public static void mouseEvent(final InputEvent.MouseInputEvent event) {

        if(MINECRAFT.player == null || MINECRAFT.screen != null || event.getAction() != 1)
            return;
        checkKeysPressed(event.getButton());
    }
    @SubscribeEvent
    public static void keyEvent(final InputEvent.KeyInputEvent event) {
        if(MINECRAFT.player == null || MINECRAFT.screen != null || event.getAction() != 1)
            return;
        checkKeysPressed(event.getKey());
    }

    public static void sendUpdatePacket(CompoundTag tag, int newMode){
        String name = CommandWritItem.getModeName(tag, newMode);
//        String recipe = SpellBook.getRecipeString(tag, newMode);
//        Networking.INSTANCE.sendToServer(new PacketUpdateSpellbook(recipe, newMode, name));
    }
}