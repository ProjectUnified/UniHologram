package me.hsgamer.unihologram.common.line;

import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractHologramLine<T> implements HologramLine<T> {
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

    @Override
    public @NotNull String getRawContent() {
        return Objects.toString(content);
    }

    @NotNull
    @Override
    public Map<String, Object> getSettings() {
        return settings;
    }
}
