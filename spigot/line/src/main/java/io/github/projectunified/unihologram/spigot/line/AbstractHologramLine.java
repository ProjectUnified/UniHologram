package io.github.projectunified.unihologram.spigot.line;

import io.github.projectunified.unihologram.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

abstract class AbstractHologramLine<T> implements HologramLine {
    private final T content;
    private final Map<String, Object> settings;

    AbstractHologramLine(T content, Map<String, Object> settings) {
        this.content = content;
        this.settings = settings;
    }

    AbstractHologramLine(T content) {
        this(content, Collections.emptyMap());
    }

    @Override
    @NotNull
    public T getContent() {
        return content;
    }

    @Override
    @NotNull
    public Map<String, Object> getSettings() {
        return settings;
    }
}
