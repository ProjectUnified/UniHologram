package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.spigot.test.UniHologramPlugin;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class HologramCommand extends SubCommand {
    protected final UniHologramPlugin plugin;

    protected HologramCommand(UniHologramPlugin plugin, @NotNull String name, @NotNull String description, @NotNull String usage, @Nullable String permission, boolean consoleAllowed) {
        super(name, description, usage, permission, consoleAllowed);
        this.plugin = plugin;
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
        if (hologram == null) {
            sender.sendMessage("Hologram not found");
            return;
        }
        onHologramCommand(sender, hologram, Arrays.copyOfRange(args, 1, args.length));
    }
}
