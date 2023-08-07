package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.display.DisplayHologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoveOriginCommand extends DisplayHologramCommand {
    public MoveOriginCommand(UniHologramTest plugin) {
        super(plugin, "moveorigin", "Move the origin of the hologram", "", false);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        Player player = (Player) sender;
        hologram.setOriginLocation(player.getEyeLocation());
        sender.sendMessage("Moved the origin to your eye location");
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 0;
    }
}
