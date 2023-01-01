package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

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

    /**
     * Get a hologram by its name
     *
     * @param name the name of the hologram
     * @return the hologram
     */
    Optional<Hologram<T>> getHologram(@NotNull String name);

    /**
     * Get all holograms
     *
     * @return the collection of holograms
     */
    Collection<Hologram<T>> getAllHolograms();

    /**
     * Check if the provider is local.
     * The provider is local if it can only get the holograms it created.
     *
     * @return true if it is local
     */
    boolean isLocal();
}
