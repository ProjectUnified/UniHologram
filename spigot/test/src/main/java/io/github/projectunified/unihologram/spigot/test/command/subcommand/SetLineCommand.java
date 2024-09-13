package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SetLineCommand extends HologramCommand {
    public SetLineCommand(UniHologramTest plugin) {
        super(plugin, "setline", "Set a line", "<line> <content>", true);
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
        hologram.setLine(line, toLine(content));
        sender.sendMessage("Set line " + line + " to " + content);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 2;
    }
}
