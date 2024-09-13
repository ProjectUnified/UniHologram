package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.api.display.DisplayHologram;
import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class ShadowStrengthCommand extends DisplayHologramCommand {
    public ShadowStrengthCommand(UniHologramTest plugin) {
        super(plugin, "shadowstrength", "Set the shadow strength of the hologram", "<strength>", true);
    }

    @Override
    protected void onHologramCommand(CommandSender sender, DisplayHologram<Location> hologram, String[] args) {
        float strength = Float.parseFloat(args[0]);
        hologram.setShadowStrength(strength);
        sender.sendMessage("Set the shadow strength to " + strength);
    }

    @Override
    protected int getMinimumArgumentLength() {
        return 1;
    }
}
