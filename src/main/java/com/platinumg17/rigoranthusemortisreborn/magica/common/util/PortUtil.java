package com.platinumg17.rigoranthusemortisreborn.magica.common.util;

import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketNoSpamChatMessage;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PortUtil {
    public static void sendMessage(Entity playerEntity, Component component){
        playerEntity.sendMessage(component, Util.NIL_UUID);
    }
    public static void sendMessageNoSpam(Entity playerEntity, Component component){
        if (playerEntity instanceof Player) {
            Networking.sendToPlayer(new PacketNoSpamChatMessage(component, 0, false), (Player) playerEntity);
        }
    }
    public static void sendMessageCenterScreen(Entity playerEntity, Component component){
        if (playerEntity instanceof Player) {
            Networking.sendToPlayer(new PacketNoSpamChatMessage(component, 0, true), (Player) playerEntity);
        }
    }
    public static void sendMessage(Entity playerEntity, String message){
        sendMessage(playerEntity, new TextComponent(message));
    }
}