package io.github.projectunified.unihologram.spigot.plugin.decentholograms;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.HologramProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The hologram provider for DecentHolograms
 */
public class DHHologramProvider implements HologramProvider<Location> {
    /**
     * Check if DecentHolograms is available
     *
     * @return true if available
     */
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("DecentHolograms");
    }

    @Override
    public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
        return new DHHologram(name, location);
    }

    @Override
    public Optional<Hologram<Location>> getHologram(@NotNull String name) {
        return Optional.ofNullable(DHAPI.getHologram(name)).map(DHHologram::new);
    }

    @Override
    public Collection<Hologram<Location>> getAllHolograms() {
        return DecentHologramsAPI.get().getHologramManager().getHolograms()
                .stream()
                .map(DHHologram::new)
                .collect(Collectors.toSet());
    }
}
