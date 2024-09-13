package io.github.projectunified.unihologram.spigot.plugin.holographicdisplays;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.local.LocalHologramProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The hologram provider for HolographicDisplays
 */
public class HDHologramProvider extends LocalHologramProvider<Location> {
    private static final boolean IS_NEW;

    static {
        boolean isNew = false;
        try {
            Class.forName("me.filoghost.holographicdisplays.api.HolographicDisplaysAPI");
            isNew = true;
        } catch (ClassNotFoundException ignored) {
            // IGNORED
        }
        IS_NEW = isNew;
    }

    private final Plugin plugin;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public HDHologramProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Check if HolographicDisplays is available
     *
     * @return true if available
     */
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
    }

    @Override
    protected @NotNull Hologram<Location> newHologram(@NotNull String name, @NotNull Location location) {
        return IS_NEW ? new HDHologram(plugin, name, location) : new HDLegacyHologram(plugin, name, location);
    }
}
