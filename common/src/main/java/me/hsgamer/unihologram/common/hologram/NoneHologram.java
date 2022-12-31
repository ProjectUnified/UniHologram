package me.hsgamer.unihologram.common.hologram;

import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A hologram that does nothing. Used as a fallback hologram
 *
 * @param <T> the type of the location
 */
public class NoneHologram<T> extends SimpleHologram<T> {
    private T location;

    public NoneHologram(String name, T location) {
        super(name);
        this.location = location;
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        // EMPTY
    }

    @Override
    public void init() {
        // EMPTY
    }

    @Override
    public void clear() {
        // EMPTY
    }

    @Override
    public T getLocation() {
        return location;
    }

    @Override
    public void setLocation(T location) {
        this.location = location;
    }
}
