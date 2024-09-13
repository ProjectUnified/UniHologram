package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class InsertLineCommand extends HologramCommand {
    public InsertLineCommand(UniHologramTest plugin) {
        super(plugin, "insertline", "Insert a line", "<line> <content>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args) {
        int line;
        try {
            line = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid line");
            return;
        }
        String content = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        hologram.insertLine(line, toLine(content));
        sender.sendMessage("Inserted line " + line + " with " + content);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 2;
    }
}
