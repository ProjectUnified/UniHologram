package io.github.projectunified.unihologram.spigot.folia;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.local.LocalHologramProvider;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The hologram provider for Folia
 */
public class FoliaHologramProvider extends LocalHologramProvider<Location> {
    private final Plugin plugin;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public FoliaHologramProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Check if Folia is available
     *
     * @return true if it is
     */
    public static boolean isAvailable() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    @Override
    protected @NotNull Hologram<Location> newHologram(@NotNull String name, @NotNull Location location, @NotNull Consumer<Hologram<Location>> onCreate, @NotNull Runnable onDestroy) {
        return new FoliaHologram(plugin, name, location, onCreate, onDestroy);
    }
}
