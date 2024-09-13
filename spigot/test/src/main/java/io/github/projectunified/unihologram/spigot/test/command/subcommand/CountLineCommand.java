package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class CountLineCommand extends HologramCommand {
    public CountLineCommand(UniHologramTest plugin) {
        super(plugin, "countline", "Count lines of a hologram", "<hologram>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args) {
        sender.sendMessage("Lines: " + hologram.size());
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 0;
    }
}
