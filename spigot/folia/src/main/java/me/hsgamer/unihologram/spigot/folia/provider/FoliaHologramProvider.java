package me.hsgamer.unihologram.spigot.folia.provider;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.provider.LocalHologramProvider;
import me.hsgamer.unihologram.spigot.folia.hologram.FoliaHologram;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

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
    protected @NotNull Hologram<Location> newHologram(@NotNull String name, @NotNull Location location) {
        return new FoliaHologram(plugin, name, location);
    }
}
