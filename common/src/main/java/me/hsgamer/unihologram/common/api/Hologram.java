package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Hologram {
    @NotNull List<HologramLine<?>> getLines();

    void setLines(@NotNull List<HologramLine<?>> lines);

    void addLine(@NotNull HologramLine<?> line);

    void setLine(int index, @NotNull HologramLine<?> line);

    void removeLine(int index);

    String getName();

    void init();

    void clear();
}
