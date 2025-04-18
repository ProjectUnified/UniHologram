package io.github.projectunified.unihologram.spigot.plugin.vanilla;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.local.LocalHologramProvider;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The hologram provider for Vanilla
 */
public class VanillaHologramProvider extends LocalHologramProvider<Location> {
    private final Plugin plugin;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public VanillaHologramProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected @NotNull Hologram<Location> newHologram(@NotNull String name, @NotNull Location location, @NotNull Consumer<Hologram<Location>> onCreate, @NotNull Runnable onDestroy) {
        return new VanillaHologram(plugin, name, location, onCreate, onDestroy);
    }
}
