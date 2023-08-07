package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.line.EmptyHologramLine;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import me.hsgamer.unihologram.spigot.test.UniHologramTest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

public abstract class HologramCommand extends SubCommand {
    protected final UniHologramTest plugin;

    protected HologramCommand(UniHologramTest plugin, @NotNull String name, @NotNull String description, @NotNull String argsUsage, boolean consoleAllowed) {
        super(name, description, "/<label> addline <hologram>" + (argsUsage.isEmpty() ? "" : " " + argsUsage), "unihologram." + name, consoleAllowed);
        this.plugin = plugin;
    }

    public static HologramLine toLine(String string) {
        String[] split = string.split(":", 2);
        if (split.length == 0) {
            return new EmptyHologramLine();
        }
        switch (split[0].toLowerCase()) {
            case "text":
                return new TextHologramLine(split.length == 1 ? "" : split[1]);
            case "item":
                return new ItemHologramLine(new ItemStack(Optional.ofNullable(Material.getMaterial(split.length == 1 ? "" : split[1].trim().toUpperCase())).orElse(Material.STONE)));
            case "empty":
                return new EmptyHologramLine();
            default:
                return new TextHologramLine(string);
        }
    }

    protected abstract void onHologramCommand(CommandSender sender, Hologram<Location> hologram, String[] args);

    protected abstract int getMinimumArgumentLength();

    @Override
    public boolean isProperUsage(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return args.length >= getMinimumArgumentLength() + 1;
    }

    @Override
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        Hologram<Location> hologram = plugin.getHologramManager().getHologram(args[0]);
        if (hologram == null || !hologram.isInitialized()) {
            sender.sendMessage("Hologram not found");
            return;
        }
        onHologramCommand(sender, hologram, Arrays.copyOfRange(args, 1, args.length));
    }
}
