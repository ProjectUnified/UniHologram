package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.display.DisplayHologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.awt.*;

public class BackgroundColorCommand extends DisplayHologramCommand {
    public BackgroundColorCommand(UniHologramTest plugin) {
        super(plugin, "background", "Set the background color of the hologram", "<r> <g> <b>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        int r = Integer.parseInt(args[0]);
        int g = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);
        if (r < 0 && g < 0 && b < 0) {
            hologram.setBackgroundColor(null);
            sender.sendMessage("Reset the background color");
        } else {
            Color color = new Color(r, g, b);
            hologram.setBackgroundColor(color);
            sender.sendMessage("Set the background color to " + color);
        }
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 3;
    }
}
