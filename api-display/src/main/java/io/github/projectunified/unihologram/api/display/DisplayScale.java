package io.github.projectunified.unihologram.api.display;

/**
 * The scale of the hologram
 */
public class DisplayScale {
    /**
     * The scale in the x-axis
     */
    public final float x;
    /**
     * The scale in the y-axis
     */
    public final float y;
    /**
     * The scale in the z-axis
     */
    public final float z;

    /**
     * Create a new scale
     *
     * @param x the scale in the x-axis
     * @param y the scale in the y-axis
     * @param z the scale in the z-axis
     */
    public DisplayScale(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
