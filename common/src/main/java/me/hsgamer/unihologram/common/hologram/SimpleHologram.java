package me.hsgamer.unihologram.common.hologram;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple hologram for features that only support updating the whole hologram
 *
 * @param <T> the type of the location
 */
public abstract class SimpleHologram<T> implements Hologram<T> {
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

    /**
     * Update the hologram
     */
    protected abstract void update();

    @Override
    public @NotNull List<HologramLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        this.lines = new ArrayList<>(lines);
        update();
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        lines.add(line);
        update();
    }

    @Override
    public void setLine(int index, @NotNull HologramLine line) {
        lines.set(index, line);
        update();
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        lines.add(index, line);
        update();
    }

    @Override
    public void removeLine(int index) {
        lines.remove(index);
        update();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getLocation() {
        return location;
    }

    @Override
    public void setLocation(T location) {
        this.location = location;
        update();
    }
}
