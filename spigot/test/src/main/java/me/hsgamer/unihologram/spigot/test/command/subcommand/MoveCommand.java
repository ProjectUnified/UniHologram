package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoveCommand extends HologramCommand {
    public MoveCommand(UniHologramTest plugin) {
        super(plugin, "move", "Move the hologram", "", false);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args) {
        Player player = (Player) sender;
        hologram.setLocation(player.getLocation());
        sender.sendMessage("Moved");
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 0;
    }
}
