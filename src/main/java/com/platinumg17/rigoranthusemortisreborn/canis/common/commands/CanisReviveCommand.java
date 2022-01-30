package com.platinumg17.rigoranthusemortisreborn.canis.common.commands;

import com.platinumg17.rigoranthusemortisreborn.canis.common.commands.arguments.UUIDArgument;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.storage.*;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;

import com.google.common.base.Strings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.literal;

public class CanisReviveCommand {

    public static final DynamicCommandExceptionType COLOR_INVALID = new DynamicCommandExceptionType((arg) -> {
        return new TranslatableComponent("command.canisrevive.invalid", arg);
    });
    public static final DynamicCommandExceptionType SPAWN_EXCEPTION = new DynamicCommandExceptionType((arg) -> {
        return new TranslatableComponent("command.canisrevive.exception", arg);
    });
    public static final Dynamic2CommandExceptionType TOO_MANY_OPTIONS = new Dynamic2CommandExceptionType((arg1, arg2) -> {
        return new TranslatableComponent("command.canisrevive.imprecise", arg1, arg2);
    });

    public static void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal("canis")
                        .requires(s -> s.hasPermission(2))
                        .then(Commands.literal("locate")
                                .then(Commands.literal("byuuid")
                                        .then(Commands.argument("canis_owner", UUIDArgument.uuid()).suggests(CanisReviveCommand.getOwnerIdSuggestionsLocate())
                                                .then(Commands.argument("canis_uuid", UUIDArgument.uuid()).suggests(CanisReviveCommand.getCanisIdSuggestionsLocate())
                                                        .executes(c -> locate(c)))))
                                .then(Commands.literal("byname")
                                        .then(Commands.argument("owner_name", StringArgumentType.string()).suggests(CanisReviveCommand.getOwnerNameSuggestionsLocate())
                                                .then(Commands.argument("canis_name", StringArgumentType.string()).suggests(CanisReviveCommand.getCanisNameSuggestionsLocate())
                                                        .executes(c -> locate2(c))))))
                        .then(Commands.literal("revive")
                                .then(Commands.literal("byuuid")
                                        .then(Commands.argument("canis_owner", UUIDArgument.uuid()).suggests(CanisReviveCommand.getOwnerIdSuggestionsRevive())
                                                .then(Commands.argument("canis_uuid", UUIDArgument.uuid()).suggests(CanisReviveCommand.getCanisIdSuggestionsRevive())
                                                        .executes(c -> respawn(c)))))
                                .then(Commands.literal("byname")
                                        .then(Commands.argument("owner_name", StringArgumentType.string()).suggests(CanisReviveCommand.getOwnerNameSuggestionsRevive())
                                                .then(Commands.argument("canis_name", StringArgumentType.string()).suggests(CanisReviveCommand.getCanisNameSuggestionsRevive())
                                                        .executes(c -> respawn2(c))))))
        );
    }

    public static void registerSerializers() {
        ArgumentTypes.register(REUtil.getResourcePath("uuid"), UUIDArgument.class, new EmptyArgumentSerializer<>(UUIDArgument::uuid));
    }
    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerIdSuggestionsLocate() {
        return (context, builder) -> getOwnerIdSuggestions(CanisLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }
    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerIdSuggestionsRevive() {
        return (context, builder) -> getOwnerIdSuggestions(CanisRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getOwnerIdSuggestions(Collection<? extends ICanisData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            return SharedSuggestionProvider.suggest(possibilities.stream()
                    .map(ICanisData::getOwnerId)
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toSet()),
                builder);
        } else if (context.getSource() instanceof SharedSuggestionProvider) {
            return context.getSource().customSuggestion((CommandContext<SharedSuggestionProvider>) context, builder);
        } else {
            return Suggestions.empty();
        }
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getCanisIdSuggestionsLocate() {
        return (context, builder) -> getCanisIdSuggestions(CanisLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }
    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getCanisIdSuggestionsRevive() {
        return (context, builder) -> getCanisIdSuggestions(CanisRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getCanisIdSuggestions(Collection<? extends ICanisData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            UUID ownerId = context.getArgument("canis_owner", UUID.class);
            if (ownerId == null) {
                return Suggestions.empty();
            }
            return SharedSuggestionProvider.suggest(possibilities.stream()
                    .filter(data -> ownerId.equals(data.getOwnerId()))
                    .map(ICanisData::getCanisId)
                    .map(Object::toString)
                    .collect(Collectors.toSet()),
                builder);
        } else if (context.getSource() instanceof SharedSuggestionProvider) {
            return context.getSource().customSuggestion((CommandContext<SharedSuggestionProvider>) context, builder);
        } else {
            return Suggestions.empty();
        }
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerNameSuggestionsLocate() {
        return (context, builder) -> getOwnerNameSuggestions(CanisLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }
    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerNameSuggestionsRevive() {
        return (context, builder) -> getOwnerNameSuggestions(CanisRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    public static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getOwnerNameSuggestions(Collection<? extends ICanisData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            return SharedSuggestionProvider.suggest(possibilities.stream()
                    .map(ICanisData::getOwnerName)
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toSet()),
                builder);

        } else if (context.getSource() instanceof SharedSuggestionProvider) {
            return context.getSource().customSuggestion((CommandContext<SharedSuggestionProvider>) context, builder);
        } else {
            return Suggestions.empty();
        }
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getCanisNameSuggestionsLocate() {
        return (context, builder) -> getCanisNameSuggestions(CanisLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }
    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getCanisNameSuggestionsRevive() {
        return (context, builder) -> getCanisNameSuggestions(CanisRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    public static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getCanisNameSuggestions(Collection<? extends ICanisData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            String ownerName = context.getArgument("owner_name", String.class);

            if (ownerName == null) {
                return Suggestions.empty();
            }
            return SharedSuggestionProvider.suggest(possibilities.stream()
                    .filter(data -> ownerName.equals(data.getOwnerName()))
                    .map(ICanisData::getCanisName)
                    .filter((str) -> !Strings.isNullOrEmpty(str))
                    .collect(Collectors.toList()),
                builder);
        } else if (context.getSource() instanceof SharedSuggestionProvider) {
            return context.getSource().customSuggestion((CommandContext<SharedSuggestionProvider>)context, builder);
        } else {
            return Suggestions.empty();
        }
    }

    private static int respawn(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();

        UUID ownerUuid = ctx.getArgument("canis_owner", UUID.class);
        UUID uuid = ctx.getArgument("canis_uuid", UUID.class);

        CanisRespawnStorage respawnStorage = CanisRespawnStorage.get(world);
        CanisRespawnData respawnData = respawnStorage.getData(uuid);

        if (respawnData == null) {
            throw COLOR_INVALID.create(uuid.toString());
        }
        return respawn(respawnStorage, respawnData, source);
    }

    private static int respawn2(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();

        String ownerName = ctx.getArgument("owner_name", String.class);
        String canisName = ctx.getArgument("canis_name", String.class);

        CanisRespawnStorage respawnStorage = CanisRespawnStorage.get(world);
        List<CanisRespawnData> respawnData = respawnStorage.getCani(ownerName).filter((data) -> data.getCanisName().equalsIgnoreCase(canisName)).collect(Collectors.toList());

        if (respawnData.isEmpty()) {
            throw COLOR_INVALID.create(canisName);
        }
        if (respawnData.size() > 1) {
            StringJoiner joiner = new StringJoiner(", ");
            for (CanisRespawnData data : respawnData) {
                joiner.add(Objects.toString(data.getCanisId()));
            }
            throw TOO_MANY_OPTIONS.create(joiner.toString(), respawnData.size());
        }
        return respawn(respawnStorage, respawnData.get(0), source);
    }

    private static int respawn(CanisRespawnStorage respawnStorage, CanisRespawnData respawnData, final CommandSourceStack source) throws CommandSyntaxException {
        CanisEntity canis = respawnData.respawn(source.getLevel(), source.getPlayerOrException(), source.getPlayerOrException().blockPosition().above());
        if (canis != null) {
            respawnStorage.remove(respawnData.getCanisId());
            source.sendSuccess(new TranslatableComponent("commands.canisrevive.uuid.success", respawnData.getCanisName()), false);
            return 1;
        }
        source.sendSuccess(new TranslatableComponent("commands.canisrevive.uuid.failure", respawnData.getCanisName()), false);
        return 0;
    }

    private static int locate(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();

        UUID ownerUuid = ctx.getArgument("canis_owner", UUID.class);
        UUID uuid = ctx.getArgument("canis_uuid", UUID.class);

        CanisLocationStorage locationStorage = CanisLocationStorage.get(world);
        CanisLocationData locationData = locationStorage.getData(uuid);
        if (locationData == null) {
            throw COLOR_INVALID.create(uuid.toString());
        }
        return locate(locationStorage, locationData, source);
    }

    private static int locate2(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();
        String ownerName = ctx.getArgument("owner_name", String.class);
        String canisName = ctx.getArgument("canis_name", String.class);
        CanisLocationStorage locationStorage = CanisLocationStorage.get(world);
        List<CanisLocationData> locationData = locationStorage.getAll().stream()
                .filter(data -> ownerName.equals(data.getOwnerName())).filter((data) -> data.getCanisName().equalsIgnoreCase(canisName)).collect(Collectors.toList());
        if (locationData.isEmpty()) {
            throw COLOR_INVALID.create(canisName);
        }
        if (locationData.size() > 1) {
            StringJoiner joiner = new StringJoiner(", ");
            for (CanisLocationData data : locationData) {
                joiner.add(Objects.toString(data.getCanisId()));
            }
            throw TOO_MANY_OPTIONS.create(joiner.toString(), locationData.size());
        }
        return locate(locationStorage, locationData.get(0), source);
    }

    private static int locate(CanisLocationStorage respawnStorage, CanisLocationData locationData, final CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayerOrException();
        if (locationData.getDimension().equals(player.level.dimension())) {
//            String translateStr = RadarItem.getDirectionTranslationKey(locationData, player);
            int distance = Mth.ceil(locationData.getPos() != null ? locationData.getPos().distanceTo(player.position) : -1);
//            source.sendSuccess(new TranslatableComponent(translateStr, locationData.getName(player.level), distance), false);
        } else {
            source.sendSuccess(new TranslatableComponent("canisradar.notindim", locationData.getDimension()), false); // TODO change message
        }
        return 1;
    }
}