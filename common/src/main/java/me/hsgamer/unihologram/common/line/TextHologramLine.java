package me.hsgamer.unihologram.common.line;

import java.util.Map;

/**
 * A line with text
 */
public class TextHologramLine extends AbstractHologramLine<String> {
    /**
     * Create a new line
     *
     * @param content  the content
     * @param settings the settings
     */
    public TextHologramLine(String content, Map<String, Object> settings) {
        super(content, settings);
    }

    /**
     * Create a new line
     *
     * @param content the content
     */
    public TextHologramLine(String content) {
        super(content);
    }
}
