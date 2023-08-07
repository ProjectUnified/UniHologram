package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.display.DisplayHologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class DisplayHologramCommand extends HologramCommand {
    protected DisplayHologramCommand(UniHologramTest plugin, @NotNull String name, @NotNull String description, @NotNull String argsUsage, boolean consoleAllowed) {
        super(plugin, name, description, argsUsage, consoleAllowed);
    }

    protected abstract void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args);

    @Override
    protected void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args) {
        if (hologram instanceof DisplayHologram) {
            onHologramCommand(sender, (DisplayHologram<Location>) hologram, args);
        } else {
            sender.sendMessage("Hologram is not a display hologram");
        }
    }
}
