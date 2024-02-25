package me.hsgamer.unihologram.spigot.fancyholograms.provider;

import de.oliver.fancyholograms.FancyHolograms;
import de.oliver.fancyholograms.HologramManagerImpl;
import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramProvider;
import me.hsgamer.unihologram.spigot.fancyholograms.hologram.FHHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The hologram provider for FancyHolograms
 */
public class FHHologramProvider implements HologramProvider<Location> {
    /**
     * Check if FancyHolograms is available
     *
     * @return true if available
     */
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("FancyHolograms");
    }

    @Override
    public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
        return new FHHologram(name, location);
    }

    @Override
    public Optional<Hologram<Location>> getHologram(@NotNull String name) {
        return FancyHolograms.get().getHologramManager().getHologram(name).map(FHHologram::new);
    }

    @Override
    public Collection<Hologram<Location>> getAllHolograms() {
        return ((HologramManagerImpl) FancyHolograms.get().getHologramManager()).getHolograms().stream().map(FHHologram::new).collect(Collectors.toSet());
    }

    @Override
    public boolean isLocal() {
        return false;
    }
}
