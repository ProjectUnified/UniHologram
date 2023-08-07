package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.unihologram.display.DisplayBillboard;
import me.hsgamer.unihologram.display.DisplayHologram;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class BillboardCommand extends DisplayHologramCommand {
    public BillboardCommand(UniHologramTest plugin) {
        super(plugin, "billboard", "Set the billboard of the hologram", "<billboard>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        DisplayBillboard billboard = DisplayBillboard.valueOf(args[0].toUpperCase());
        hologram.setBillboard(billboard);
        sender.sendMessage("Set the billboard to " + billboard);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
