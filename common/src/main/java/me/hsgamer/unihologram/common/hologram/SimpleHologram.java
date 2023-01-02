package me.hsgamer.unihologram.common.hologram;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple hologram for features that only support updating the whole hologram
 *
 * @param <T> the type of the location
 */
public abstract class SimpleHologram<T> implements Hologram<T> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    /**
     * The current lines
     */
    protected List<HologramLine> lines;
    /**
     * The name of the hologram
     */
    protected String name;
    /**
     * The location of the hologram
     */
    protected T location;

    protected SimpleHologram(String name, T location) {
        this.name = name;
        this.location = location;
        this.lines = new ArrayList<>();
    }

    private void checkInitialized() {
        if (!initialized.get()) {
            throw new IllegalStateException("Hologram is not initialized");
        }
    }

    /**
     * Update the hologram
     */
    protected abstract void updateHologram();

    /**
     * Initialize the hologram
     */
    protected abstract void initHologram();

    /**
     * Clear the hologram
     */
    protected abstract void clearHologram();

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkInitialized();
        return Collections.unmodifiableList(lines);
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkInitialized();
        this.lines = new ArrayList<>(lines);
        updateHologram();
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        checkInitialized();
        lines.add(line);
        updateHologram();
    }

    @Override
    public void setLine(int index, @NotNull HologramLine line) {
        checkInitialized();
        lines.set(index, line);
        updateHologram();
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        checkInitialized();
        lines.add(index, line);
        updateHologram();
    }

    @Override
    public void removeLine(int index) {
        checkInitialized();
        lines.remove(index);
        updateHologram();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void init() {
        if (initialized.compareAndSet(false, true)) {
            initHologram();
        }
    }

    @Override
    public void clear() {
        if (initialized.compareAndSet(true, false)) {
            clearHologram();
            lines.clear();
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized.get();
    }

    @Override
    public T getLocation() {
        return location;
    }

    @Override
    public void setLocation(T location) {
        checkInitialized();
        this.location = location;
        updateHologram();
    }
}
