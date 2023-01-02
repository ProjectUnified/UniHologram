package me.hsgamer.unihologram.common.hologram;

/**
 * A hologram that does nothing. Used as a fallback hologram.
 *
 * @param <T> the type of the location
 */
public class NoneHologram<T> extends SimpleHologram<T> {
    public NoneHologram(String name, T location) {
        super(name, location);
    }

    @Override
    protected void update() {
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
    public boolean isInitialized() {
        return true;
    }
}
