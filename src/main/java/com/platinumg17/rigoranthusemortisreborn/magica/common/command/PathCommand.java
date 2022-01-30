package com.platinumg17.rigoranthusemortisreborn.magica.common.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.Networking;
import com.platinumg17.rigoranthusemortisreborn.magica.common.network.PacketTogglePathing;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class PathCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rig-pathing").
                requires(sender -> sender.hasPermission(2))
                .executes(context -> setPathing(context.getSource(), ImmutableList.of(context.getSource().getEntityOrException()))));
    }

    private static int setPathing(CommandSourceStack source, ImmutableList<? extends Entity> of) {
        Networking.INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayer) of.get(0)),
                new PacketTogglePathing());
        return 1;
    }
}