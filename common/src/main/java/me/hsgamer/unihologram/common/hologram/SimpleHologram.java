package me.hsgamer.unihologram.common.hologram;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    private void update() {
        setLines(lines);
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        return Collections.unmodifiableList(lines);
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
    public Optional<HologramLine> getLine(int index) {
        if (index < 0 || index >= lines.size()) {
            return Optional.empty();
        }
        return Optional.of(lines.get(index));
    }

    @Override
    public String getName() {
        return name;
    }
}
