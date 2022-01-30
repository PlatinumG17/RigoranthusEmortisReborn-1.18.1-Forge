package com.platinumg17.rigoranthusemortisreborn.magica.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractAugment;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractCastMethod;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractEffect;
import com.platinumg17.rigoranthusemortisreborn.api.apimagic.spell.AbstractSpellPart;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Tuple;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataDumpCommand {
    public static final Path PATH_AUGMENT_COMPATIBILITY = Paths.get("rigoranthusemortisreborn", "augment_compatibility.csv");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rig-data")
                .requires(sender -> sender.hasPermission(2)) // Op required
                .then(Commands.literal("dump")
                        .then(Commands.literal("augment-compatibility-csv")
                                .executes(DataDumpCommand::dumpAugmentCompat)))
        );
    }

    /**
     * Creates a CSV file at {@link DataDumpCommand#PATH_AUGMENT_COMPATIBILITY} all augment compatibility information
     */
    public static int dumpAugmentCompat(CommandContext<CommandSourceStack> context) {
        Map<String, AbstractSpellPart> spells = RigoranthusEmortisRebornAPI.getInstance().getSpellpartMap();

        // Collect the Augments
        List<AbstractAugment> augments = spells.values().stream()
                .filter(p -> p instanceof AbstractAugment)
                .map(p -> (AbstractAugment) p)
                .sorted(Comparator.comparing(a -> a.getId()))
                .collect(Collectors.toList());

        // Collect the augment compatibilities
        List<Tuple<AbstractSpellPart, Set<AbstractAugment>>> augmentCompat = spells.values().stream()
                .filter(part -> part instanceof AbstractCastMethod)
                .map(part -> new Tuple<>(part, part.getCompatibleAugments()))
                .sorted(Comparator.comparing(t -> t.getA().getId()))
                .collect(Collectors.toList());
        // Technically can be done in one sort, but writing a comparator based on type is ugly.
        augmentCompat.addAll(spells.values().stream()
                .filter(part -> part instanceof AbstractEffect)
                .map(part -> new Tuple<>(part, part.getCompatibleAugments()))
                .sorted(Comparator.comparing(t -> t.getA().getId()))
                .collect(Collectors.toList()));

        // Write the file
        File file = PATH_AUGMENT_COMPATIBILITY.toFile();
        try {
            Files.createDirectories(PATH_AUGMENT_COMPATIBILITY.getParent());
            PrintWriter w = new PrintWriter(new FileWriterWithEncoding(file, "UTF-8", false));

            // Header Line
            w.println("glyph, " + augments.stream().map(a -> a.getId()).collect(Collectors.joining(", ")));

            // Rows
            for (Tuple<AbstractSpellPart, Set<AbstractAugment>> row : augmentCompat) {
                AbstractSpellPart part = row.getA();
                Set<AbstractAugment> compatibleAugments = row.getB();

                w.print(part.getId() + ", ");

                // Columns
                w.print(augments.stream()
                        .map(a -> compatibleAugments.contains(a) ? "T" : "F")
                        .collect(Collectors.joining(", ")));
                w.println();
            }
            w.close();
        } catch (IOException ex) {
            LogManager.getLogger(EmortisConstants.MOD_ID).error("Unable to dump augment compatibility chart", ex);
            context.getSource().sendFailure(new TextComponent("Error when trying to produce the data dump.  Check the logs."));

            // This is somewhat expected, just fail the command.  Logging took care of reporting.
            return 0;
        } catch (Exception ex) {
            LogManager.getLogger(EmortisConstants.MOD_ID).error("Exception caught when trying to dump data", ex);
            context.getSource().sendFailure(new TextComponent("Error when trying to produce the data dump.  Check the logs."));

            // We really didn't expect this.  Re-throw.
            throw ex;
        }

        context.getSource().sendSuccess(new TextComponent("Dumped data to " + file), true);
        return 1;
    }
}