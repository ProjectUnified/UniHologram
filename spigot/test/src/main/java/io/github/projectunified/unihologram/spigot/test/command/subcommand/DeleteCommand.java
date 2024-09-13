package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
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
