package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class DeleteCommand extends HologramCommand {
    public DeleteCommand(UniHologramTest plugin) {
        super(plugin, "delete", "Delete a hologram", "", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args) {
        hologram.clear();
        sender.sendMessage("Deleted");
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 0;
    }
}
