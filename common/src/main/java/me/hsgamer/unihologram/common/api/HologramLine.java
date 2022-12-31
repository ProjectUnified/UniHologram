package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface HologramLine<T> {
    @NotNull T getContent();

    @NotNull String getRawContent();

    @NotNull
    Map<String, Object> getSettings();
}
