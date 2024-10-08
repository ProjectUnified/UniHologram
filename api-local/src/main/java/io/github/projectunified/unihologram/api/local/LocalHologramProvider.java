package io.github.projectunified.unihologram.api.local;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.HologramProvider;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A hologram provider that stores created holograms
 *
 * @param <T> the type of the location
 */
public abstract class LocalHologramProvider<T> implements HologramProvider<T> {
    private final Map<String, Hologram<T>> createdHolograms = new HashMap<>();

    /**
     * Make a new hologram
     *
     * @param name     the name of the hologram
     * @param location the location of the hologram
     * @return the hologram
     */
    protected abstract @NotNull Hologram<T> newHologram(@NotNull String name, @NotNull T location);

    /**
     * Create a new hologram
     *
     * @param name     the name of the hologram
     * @param location the location of the hologram
     * @return the hologram
     */
    @Override
    public final @NotNull Hologram<T> createHologram(@NotNull String name, @NotNull T location) {
        if (createdHolograms.containsKey(name)) {
            throw new IllegalArgumentException("Hologram " + name + " already exists");
        }
        Hologram<T> hologram = newHologram(name, location);
        createdHolograms.put(name, hologram);
        return hologram;
    }

    /**
     * Get a hologram by its name
     *
     * @param name the name of the hologram
     * @return the hologram
     */
    @Override
    public Optional<Hologram<T>> getHologram(@NotNull String name) {
        return Optional.ofNullable(createdHolograms.get(name));
    }

    @Override
    public Collection<Hologram<T>> getAllHolograms() {
        return Collections.unmodifiableCollection(createdHolograms.values());
    }
}
