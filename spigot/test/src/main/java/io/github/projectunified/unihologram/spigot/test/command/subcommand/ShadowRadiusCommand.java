package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.display.DisplayHologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class ShadowRadiusCommand extends DisplayHologramCommand {
    public ShadowRadiusCommand(UniHologramTest plugin) {
        super(plugin, "shadowradius", "Set the shadow radius of the hologram", "<radius>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        float radius = Float.parseFloat(args[0]);
        hologram.setShadowRadius(radius);
        sender.sendMessage("Set the shadow radius to " + radius);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
