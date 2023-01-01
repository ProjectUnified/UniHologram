package me.hsgamer.unihologram.common.api.extra;

/**
 * An extra for {@link me.hsgamer.unihologram.common.api.Hologram} to control the visibility of the hologram
 *
 * @param <T> the type of the viewer
 */
public interface Visibility<T> {
    /**
     * Check if the viewer can see the hologram
     *
     * @param viewer the viewer
     * @return true if the viewer can see the hologram
     */
    boolean isVisible(T viewer);

    /**
     * Show the hologram to all viewers
     */
    void showAll();

    /**
     * Hide the hologram from all viewers
     */
    void hideAll();

    /**
     * Show the hologram to the viewer
     *
     * @param viewer the viewer
     */
    void showTo(T viewer);

    /**
     * Hide the hologram from the viewer
     *
     * @param viewer the viewer
     */
    void hideTo(T viewer);
}
