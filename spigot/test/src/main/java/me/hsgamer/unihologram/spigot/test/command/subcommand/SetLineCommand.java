package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.spigot.test.UniHologramPlugin;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SetLineCommand extends HologramCommand {
    public SetLineCommand(UniHologramPlugin plugin) {
        super(plugin, "setline", "Set a line", "/unihologram setline <hologram> <line> <content>", "unihologram.setline", true);
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
