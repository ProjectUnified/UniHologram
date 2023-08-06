package me.hsgamer.unihologram.spigot.display.billboard;

/**
 * The axes/points around which the display can pivot
 *
 * @see <a href="https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/Display.Billboard.html">Display.Billboard</a>
 */
public enum DisplayBillboard {
    /**
     * No rotation
     */
    FIXED,
    /**
     * Rotate around the vertical axis
     */
    VERTICAL,
    /**
     * Rotate around the horizontal axis
     */
    HORIZONTAL,
    /**
     * Rotate around the center point
     */
    CENTER
}
