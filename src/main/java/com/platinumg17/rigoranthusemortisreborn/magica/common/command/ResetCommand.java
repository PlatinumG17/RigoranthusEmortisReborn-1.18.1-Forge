package com.platinumg17.rigoranthusemortisreborn.magica.common.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.platinumg17.rigoranthusemortisreborn.magica.common.capability.CapabilityRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;

public class ResetCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rig-reset").
                requires(sender -> sender.hasPermission(2))
                .executes(context -> resetPlayers(context.getSource(), ImmutableList.of(context.getSource().getEntityOrException())))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .executes(context -> resetPlayers(context.getSource(), EntityArgument.getEntities(context, "targets")))));
    }

    private static int resetPlayers(CommandSourceStack source, Collection<? extends Entity> entities) {
        for(Entity e : entities){
            if(!(e instanceof LivingEntity))
                continue;
            CapabilityRegistry.getDominion((LivingEntity) e).ifPresent(iMana -> {
                iMana.setBookTier(0);
                iMana.setGlyphBonus(0);
            });
            CapabilityRegistry.getPlayerDataCap((LivingEntity) e).ifPresent(ifam -> ifam.setUnlockedFamiliars(new ArrayList<>()));
        }
        source.sendSuccess(new TranslatableComponent("rigoranthusemortisreborn.reset.cleared"), true);
        return 1;
    }
}