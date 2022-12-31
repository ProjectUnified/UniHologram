package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public interface HologramLine {
    @NotNull Object getContent();

    default @NotNull String getRawContent() {
        return Objects.toString(getContent());
    }

    @NotNull
    Map<String, Object> getSettings();
}
