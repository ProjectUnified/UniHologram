package me.hsgamer.unihologram.common.api.extra;

/**
 * An extra interface for {@link me.hsgamer.unihologram.common.api.PagedHologram} to change the page for the viewer
 *
 * @param <T> the type of the viewer
 */
public interface ViewerPage<T> {
    /**
     * Change the page for the viewer
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
}
