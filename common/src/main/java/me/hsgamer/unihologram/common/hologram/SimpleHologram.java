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
    private final List<HologramLine> lines;
    private final String name;

    protected SimpleHologram(String name) {
        this.name = name;
        this.lines = new ArrayList<>();
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        lines.add(line);
        setLines(lines);
    }

    @Override
    public void setLine(int index, @NotNull HologramLine line) {
        lines.set(index, line);
        setLines(lines);
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        lines.add(index, line);
        setLines(lines);
    }

    @Override
    public void removeLine(int index) {
        lines.remove(index);
        setLines(lines);
    }

    @Override
    public String getName() {
        return name;
    }
}
