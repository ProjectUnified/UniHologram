package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class AddLineCommand extends HologramCommand {
    public AddLineCommand(UniHologramTest plugin) {
        super(plugin, "addline", "Add a new line", "<content>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args) {
        String content = String.join(" ", args);
        hologram.addLine(toLine(content));
        sender.sendMessage("Added a new line: " + content);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
