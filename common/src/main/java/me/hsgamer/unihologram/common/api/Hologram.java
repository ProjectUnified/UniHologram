package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Hologram<T> {
    @NotNull List<HologramLine> getLines();

    void setLines(@NotNull List<HologramLine> lines);

    void addLine(@NotNull HologramLine line);

    void insertLine(int index, @NotNull HologramLine line);

    void removeLine(int index);

    default void setLine(int index, @NotNull HologramLine line) {
        insertLine(index, line);
        removeLine(index + 1);
    }

    String getName();

    void init();

    void clear();

    T getLocation();

    void setLocation(T location);
}
