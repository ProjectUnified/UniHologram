package me.hsgamer.unihologram.common.hologram;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SimpleHologram implements Hologram {
    private final List<HologramLine<?>> lines;

    protected SimpleHologram() {
        this.lines = new ArrayList<>();
    }

    @Override
    public @NotNull List<HologramLine<?>> getLines() {
        return Collections.unmodifiableList(lines);
    }

    @Override
    public void addLine(@NotNull HologramLine<?> line) {
        lines.add(line);
        setLines(lines);
    }

    @Override
    public void setLine(int index, @NotNull HologramLine<?> line) {
        lines.set(index, line);
        setLines(lines);
    }

    @Override
    public void removeLine(int index) {
        lines.remove(index);
        setLines(lines);
    }
}
