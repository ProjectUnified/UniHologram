package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.display.DisplayHologram;
import io.github.projectunified.unihologram.api.display.DisplayScale;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class ScaleCommand extends DisplayHologramCommand {
    public ScaleCommand(UniHologramTest plugin) {
        super(plugin, "scale", "Set the text scale of the hologram", "<x> <y> <z>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        DisplayScale scale;
        if (args.length == 1) {
            float scaleValue = Float.parseFloat(args[0]);
            scale = new DisplayScale(scaleValue, scaleValue, scaleValue);
        } else if (args.length >= 3) {
            float x = Float.parseFloat(args[0]);
            float y = Float.parseFloat(args[1]);
            float z = Float.parseFloat(args[2]);
            scale = new DisplayScale(x, y, z);
        } else {
            sender.sendMessage("Invalid arguments");
            return;
        }
        hologram.setScale(scale);
        sender.sendMessage("Set the scale to " + scale);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
