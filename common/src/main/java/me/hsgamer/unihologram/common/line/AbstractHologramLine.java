package me.hsgamer.unihologram.common.line;

import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractHologramLine<T> implements HologramLine {
    private final T content;
    private final Map<String, Object> settings;

    protected AbstractHologramLine(T content, Map<String, Object> settings) {
        this.content = content;
        this.settings = settings;
    }

    protected AbstractHologramLine(T content) {
        this(content, Collections.emptyMap());
    }

    @NotNull
    @Override
    public T getContent() {
        return content;
    }

    @NotNull
    @Override
    public Map<String, Object> getSettings() {
        return settings;
    }
}
