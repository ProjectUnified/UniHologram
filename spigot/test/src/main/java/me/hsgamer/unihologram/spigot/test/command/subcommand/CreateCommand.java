package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.unihologram.spigot.test.UniHologramPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateCommand extends SubCommand {
    private final UniHologramPlugin plugin;

    public CreateCommand(UniHologramPlugin plugin) {
        super("create", "Create a new hologram", "/unihologram create <name>", "unihologram.create", false);
        this.plugin = plugin;
    }

    @Override
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        Player player = (Player) sender;
        String name = args[0];
        if (plugin.getHologramManager().getHologram(name) != null) {
            sender.sendMessage("The hologram already exists");
            return;
        }
        plugin.getHologramManager().createHologram(name, player.getLocation());
        sender.sendMessage("Created");
    }

    @Override
    public boolean isProperUsage(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return args.length > 0;
    }
}
