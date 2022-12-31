package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

public interface HologramProvider<T> {
    @NotNull Hologram<T> createHologram(@NotNull String name, @NotNull T location);
}
