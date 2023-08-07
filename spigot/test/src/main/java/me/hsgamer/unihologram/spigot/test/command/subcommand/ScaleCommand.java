package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.display.DisplayHologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class ScaleCommand extends DisplayHologramCommand {
    public ScaleCommand(UniHologramTest plugin) {
        super(plugin, "scale", "Set the text scale of the hologram", "<scale>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        float scale = Float.parseFloat(args[0]);
        hologram.setScale(scale);
        sender.sendMessage("Set the scale to " + scale);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
