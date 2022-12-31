package me.hsgamer.unihologram.spigot.test.command.subcommand;

import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.unihologram.spigot.test.UniHologramPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DeleteCommand extends SubCommand {
    private final UniHologramPlugin plugin;

    public DeleteCommand(UniHologramPlugin plugin) {
        super("delete", "Delete a hologram", "/unihologram delete <name>", "unihologram.delete", true);
        this.plugin = plugin;
    }

    @Override
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        String name = args[0];
        if (plugin.getHologramManager().getHologram(name) == null) {
            sender.sendMessage("The hologram does not exist");
            return;
        }
        plugin.getHologramManager().removeHologram(name);
        sender.sendMessage("Deleted");
    }

    @Override
    public boolean isProperUsage(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return args.length > 0;
    }
}
