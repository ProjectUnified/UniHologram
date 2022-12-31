package me.hsgamer.unihologram.common.line;

import java.util.Map;

public class TextHologramLine extends AbstractHologramLine<String> {
    public TextHologramLine(String content, Map<String, Object> settings) {
        super(content, settings);
    }

    public TextHologramLine(String content) {
        super(content);
    }
}
