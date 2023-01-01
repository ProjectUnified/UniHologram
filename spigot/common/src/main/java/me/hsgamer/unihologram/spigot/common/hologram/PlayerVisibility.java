package me.hsgamer.unihologram.spigot.common.hologram;

import org.bukkit.entity.Player;

/**
 * An extra for {@link me.hsgamer.unihologram.common.api.Hologram} to control the visibility of the hologram
 */
public interface PlayerVisibility {
    /**
     * Check if the player can see the hologram
     *
     * @param player the player
     * @return true if the player can see the hologram
     */
    boolean isVisible(Player player);

    /**
     * Show the hologram to all players
     */
    void showAll();

    /**
     * Hide the hologram from all players
     */
    void hideAll();

    /**
     * Show the hologram to the player
     *
     * @param player the player
     */
    void showTo(Player player);

    /**
     * Hide the hologram from the player
     *
     * @param player the player
     */
    void hideTo(Player player);
}
