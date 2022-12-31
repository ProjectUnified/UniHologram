package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

public interface HologramProvider<T> {
    @NotNull Hologram createHologram(@NotNull String name, @NotNull T location);
}
