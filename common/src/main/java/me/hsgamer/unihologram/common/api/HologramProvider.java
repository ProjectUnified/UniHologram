package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

/**
 * The provider to create {@link Hologram}
 *
 * @param <T> the type of the location
 */
public interface HologramProvider<T> {
    /**
     * Create a new hologram
     *
     * @param name     the name of the hologram
     * @param location the location of the hologram
     * @return the hologram
     */
    @NotNull Hologram<T> createHologram(@NotNull String name, @NotNull T location);
}
