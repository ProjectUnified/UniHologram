package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * The line of a hologram
 */
public interface HologramLine {
    /**
     * Get the content of the line
     *
     * @return the content
     */
    @NotNull Object getContent();

    /**
     * Get the content of the line as a string
     *
     * @return the content as a string
     */
    default @NotNull String getRawContent() {
        return Objects.toString(getContent());
    }

    /**
     * Get the extra settings of the line
     *
     * @return the extra settings
     */
    @NotNull
    Map<String, Object> getSettings();
}
