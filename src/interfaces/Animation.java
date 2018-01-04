package interfaces;
import biuoop.DrawSurface;

/**
 * This interface featues an animation which has a do one frame method and should stop boolean.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04-02
 */
public interface Animation {
    /**
     * Drawing the animation.
     * @param d draw surface.
     * @param dt is the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Boolean if should stop drawing.
     * @return true/false.
     */
    boolean shouldStop();
}
