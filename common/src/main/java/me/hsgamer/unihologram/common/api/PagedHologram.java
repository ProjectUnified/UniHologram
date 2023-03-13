package me.hsgamer.unihologram.common.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * A {@link Hologram} that supports multiple pages
 *
 * @param <T> the type of the location
 */
public interface PagedHologram<T> extends Hologram<T> {
    /**
     * Get the first page
     *
     * @return the first page
     */
    default int getFirstPage() {
        return 0;
    }

    /**
     * Get the amount of pages
     *
     * @return the amount
     */
    int getPages();

    /**
     * Get the lines of the hologram
     *
     * @param page the page
     * @return the lines
     */
    @NotNull List<HologramLine> getLines(int page);

    /**
     * Set the lines of the hologram
     *
     * @param page  the page
     * @param lines the lines
     */
    void setLines(int page, @NotNull List<HologramLine> lines);

    /**
     * Add a line to the hologram
     *
     * @param page the page
     * @param line the line
     */
    void addLine(int page, @NotNull HologramLine line);

    /**
     * Insert a line to the hologram at the index and move the rest down
     *
     * @param page  the page
     * @param index the index
     * @param line  the line
     */
    void insertLine(int page, int index, @NotNull HologramLine line);

    /**
     * Remove a line at the index from the hologram
     *
     * @param page  the page
     * @param index the index
     */
    void removeLine(int page, int index);

    /**
     * Set the line at the index to the new line
     *
     * @param page  the page
     * @param index the index
     * @param line  the new line
     */
    default void setLine(int page, int index, @NotNull HologramLine line) {
        insertLine(page, index, line);
        removeLine(page, index + 1);
    }

    /**
     * Get the line at the index
     *
     * @param page  the page
     * @param index the index
     * @return the line
     */
    default Optional<HologramLine> getLine(int page, int index) {
        List<HologramLine> lines = getLines(page);
        if (index < 0 || index >= lines.size()) {
            return Optional.empty();
        }
        return Optional.of(lines.get(index));
    }

    /**
     * Get the amount of lines
     *
     * @param page the page
     * @return the amount
     */
    default int size(int page) {
        return getLines(page).size();
    }

    @Override
    default @NotNull List<HologramLine> getLines() {
        return getLines(getFirstPage());
    }

    @Override
    default void setLines(@NotNull List<HologramLine> lines) {
        setLines(getFirstPage(), lines);
    }

    @Override
    default void addLine(@NotNull HologramLine line) {
        addLine(getFirstPage(), line);
    }

    @Override
    default void insertLine(int index, @NotNull HologramLine line) {
        insertLine(getFirstPage(), index, line);
    }

    @Override
    default void removeLine(int index) {
        removeLine(getFirstPage(), index);
    }

    @Override
    default void setLine(int index, @NotNull HologramLine line) {
        setLine(getFirstPage(), index, line);
    }

    @Override
    default Optional<HologramLine> getLine(int index) {
        return getLine(getFirstPage(), index);
    }

    @Override
    default int size() {
        return size(getFirstPage());
    }
}
