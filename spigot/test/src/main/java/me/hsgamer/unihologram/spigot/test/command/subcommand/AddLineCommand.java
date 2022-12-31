package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.test.UniHologramPlugin;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class AddLineCommand extends HologramCommand {
    public AddLineCommand(UniHologramPlugin plugin) {
        super(plugin, "addline", "Add a new line", "/unihologram addline <hologram> <content>", "unihologram.addline", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args) {
        String content = String.join(" ", args);
        hologram.addLine(new TextHologramLine(content));
        sender.sendMessage("Added a new line: " + content);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
