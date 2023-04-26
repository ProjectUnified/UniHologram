package me.hsgamer.unihologram.spigot.cmi.provider;

import com.Zrips.CMI.CMI;
import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramProvider;
import me.hsgamer.unihologram.spigot.cmi.hologram.CMIHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The hologram provider for CMI
 */
public class CMIHologramProvider implements HologramProvider<Location> {
    /**
     * Check if CMI is available
     *
     * @return true if available
     */
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("CMI");
    }

    @Override
    public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
        return new CMIHologram(name, location);
    }

    @Override
    public Optional<Hologram<Location>> getHologram(@NotNull String name) {
        return Optional.ofNullable(CMI.getInstance().getHologramManager().getByName(name)).map(CMIHologram::new);
    }

    @Override
    public Collection<Hologram<Location>> getAllHolograms() {
        return CMI.getInstance().getHologramManager().getHolograms().values().stream().map(CMIHologram::new).collect(Collectors.toList());
    }

    @Override
    public boolean isLocal() {
        return false;
    }
}
