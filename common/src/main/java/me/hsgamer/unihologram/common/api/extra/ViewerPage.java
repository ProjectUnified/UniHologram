package me.hsgamer.unihologram.common.api.extra;

/**
 * An extra interface for {@link me.hsgamer.unihologram.common.api.PagedHologram} to change the page for the viewer
 *
 * @param <T> the type of the viewer
 */
public interface ViewerPage<T> {
    /**
     * Change the page for the viewer.
     * Implementations should check if the page is valid, and if not, change it to the closest valid page, or limit the page range from 0 to the maximum page (inclusive).
     *
     * @param viewer the viewer
     * @param page   the page
     */
    void setPage(T viewer, int page);

    /**
     * Get the current page of the viewer
     *
     * @param viewer the viewer
     * @return the current page
     */
    int getPage(T viewer);

    /**
     * Go to the next page
     *
     * @param viewer the viewer
     */
    default void nextPage(T viewer) {
        setPage(viewer, getPage(viewer) + 1);
    }

    /**
     * Go to the previous page
     *
     * @param viewer the viewer
     */
    default void previousPage(T viewer) {
        setPage(viewer, getPage(viewer) - 1);
    }
}
