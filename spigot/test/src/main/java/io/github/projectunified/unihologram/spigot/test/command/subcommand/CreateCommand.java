package io.github.projectunified.unihologram.spigot.test.command.subcommand;

import io.github.projectunified.unihologram.spigot.test.UniHologramTest;
import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateCommand extends SubCommand {
    private final UniHologramTest plugin;

    public CreateCommand(UniHologramTest plugin) {
        super("create", "Create a new hologram", "/<label> create <name>", "unihologram.create", false);
        this.plugin = plugin;
    }

    @Override
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        Player player = (Player) sender;
        String name = args[0];
        if (plugin.getHologramManager().createHologram(name, player.getLocation())) {
            player.sendMessage("Created");
        } else {
            sender.sendMessage("The hologram already exists");
        }
    }

    @Override
    public boolean isProperUsage(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return args.length > 0;
    }
}
