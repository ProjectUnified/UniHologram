package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.display.DisplayHologram;
import io.github.projectunified.unihologram.api.display.DisplayTextAlignment;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class TextAlignmentCommand extends DisplayHologramCommand {
    public TextAlignmentCommand(UniHologramTest plugin) {
        super(plugin, "textalignment", "Set the text alignment of the hologram", "<alignment>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        DisplayTextAlignment alignment = DisplayTextAlignment.valueOf(args[0].toUpperCase());
        hologram.setAlignment(alignment);
        sender.sendMessage("Set the text alignment to " + alignment);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
