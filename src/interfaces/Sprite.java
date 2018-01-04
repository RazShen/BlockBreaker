package interfaces;
import biuoop.DrawSurface;

/**
 * This interface is used to describe the basics of a sprite object (an object the game can draw and make changes
 * to it's visibility).
 *
 * @author Raz Shenkman
 * @version 2.0
 * @since 2017-04-30
 */
public interface Sprite {

    /**
     * Draw the sprite object on the screen.
     * @param d the draw surface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite object that time has passed.
     * @param dt is the amount of seconds passed since the last call.
     */
    void timePassed(double dt);
}
