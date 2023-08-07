package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class DeleteLineCommand extends HologramCommand {
    public DeleteLineCommand(UniHologramTest plugin) {
        super(plugin, "deleteline", "Delete a line", "<line>", true);
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
        try {
            hologram.removeLine(line);
            sender.sendMessage("Deleted line " + line);
        } catch (Exception e) {
            sender.sendMessage("Invalid line");
        }
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
