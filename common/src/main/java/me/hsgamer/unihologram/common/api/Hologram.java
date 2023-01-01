package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A hologram
 *
 * @param <T> the type of the location
 */
public interface Hologram<T> {
    /**
     * Get the lines of the hologram
     *
     * @return the lines
     */
    @NotNull List<HologramLine> getLines();

    /**
     * Set the lines of the hologram
     *
     * @param lines the lines
     */
    void setLines(@NotNull List<HologramLine> lines);

    /**
     * Add a line to the hologram
     *
     * @param line the line
     */
    void addLine(@NotNull HologramLine line);

    /**
     * Insert a line to the hologram at the index and move the rest down
     *
     * @param index the index
     * @param line  the line
     */
    void insertLine(int index, @NotNull HologramLine line);

    /**
     * Remove a line at the index from the hologram
     *
     * @param index the index
     */
    void removeLine(int index);

    /**
     * Set the line at the index to the new line
     *
     * @param index the index
     * @param line  the new line
     */
    default void setLine(int index, @NotNull HologramLine line) {
        insertLine(index, line);
        removeLine(index + 1);
    }

    /**
     * Get the line at the index
     *
     * @param index the index
     * @return the line
     */
    default HologramLine getLine(int index) {
        return getLines().get(index);
    }

    /**
     * Get the amount of lines
     *
     * @return the amount
     */
    default int size() {
        return getLines().size();
    }

    /**
     * Get the name of the hologram
     *
     * @return the name
     */
    String getName();

    /**
     * Initialize the hologram.
     * Call this before using the hologram.
     */
    void init();

    /**
     * Clear the hologram
     */
    void clear();

    /**
     * Get the location of the hologram
     *
     * @return the location
     */
    T getLocation();

    /**
     * Set the location of the hologram
     *
     * @param location the location
     */
    void setLocation(T location);
}
