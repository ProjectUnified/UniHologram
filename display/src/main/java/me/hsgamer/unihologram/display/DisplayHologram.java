package me.hsgamer.unihologram.display;

import me.hsgamer.unihologram.common.api.Hologram;

import java.awt.*;

/**
 * The {@link me.hsgamer.unihologram.common.api.Hologram} that uses the new Minecraft TextDisplay API
 *
 * @param <T> the type of the location
 */
public interface DisplayHologram<T> extends Hologram<T> {
    /**
     * Get the background color
     *
     * @return the background color
     */
    Color getBackgroundColor();

    /**
     * Set the background color
     *
     * @param color the background color
     */
    void setBackgroundColor(Color color);

    /**
     * Get the scale of the text
     *
     * @return the scale
     */
    DisplayScale getScale();

    /**
     * Set the scale of the text
     *
     * @param scale the scale
     */
    void setScale(DisplayScale scale);

    /**
     * Get the shadow radius
     *
     * @return the shadow radius
     */
    float getShadowRadius();

    /**
     * Set the shadow radius
     *
     * @param radius the shadow radius
     */
    void setShadowRadius(float radius);

    /**
     * Get the shadow strength
     *
     * @return the shadow strength
     */
    float getShadowStrength();

    /**
     * Set the shadow strength
     *
     * @param strength the shadow strength
     */
    void setShadowStrength(float strength);

    /**
     * Get if the text is shadowed
     *
     * @return true if it is
     */
    boolean isShadowed();

    /**
     * Set if the text is shadowed
     *
     * @param shadowed true if it is
     */
    void setShadowed(boolean shadowed);

    /**
     * Get the billboard
     *
     * @return the billboard
     */
    DisplayBillboard getBillboard();

    /**
     * Set the billboard
     *
     * @param billboard the billboard
     */
    void setBillboard(DisplayBillboard billboard);

    /**
     * Get the text alignment
     *
     * @return the text alignment
     */
    DisplayTextAlignment getAlignment();

    /**
     * Set the text alignment
     *
     * @param alignment the text alignment
     */
    void setAlignment(DisplayTextAlignment alignment);

    /**
     * Get the origin location.
     * This is the location of the text display, while {@link Hologram#getLocation()} is the location of the top of the display.
     *
     * @return the origin location
     */
    T getOriginLocation();

    /**
     * Set the origin location
     *
     * @param originLocation the origin location
     * @see #getOriginLocation()
     */
    void setOriginLocation(T originLocation);
}
