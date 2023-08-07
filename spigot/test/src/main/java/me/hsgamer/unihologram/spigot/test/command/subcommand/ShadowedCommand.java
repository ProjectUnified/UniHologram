package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.display.DisplayHologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class ShadowedCommand extends DisplayHologramCommand {
    public ShadowedCommand(UniHologramTest plugin) {
        super(plugin, "shadowed", "Set the shadowed of the hologram", "<true|false>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        boolean shadowed = Boolean.parseBoolean(args[0]);
        hologram.setShadowed(shadowed);
        sender.sendMessage("Set the shadowed to " + shadowed);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
