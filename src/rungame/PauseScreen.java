package rungame;
import biuoop.DrawSurface;
import interfaces.Animation;
import java.awt.Color;


/**
 * This class features a pause screen animation object.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-24
 */
public class PauseScreen implements Animation {

    /**
     * Prints the pause screen, until user press 'space'.
     * @param d draw surface.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.drawText(143, 501, "Paused -- Press space to continue", 35);
        d.drawCircle(400, 300, 100);
        d.setColor(Color.cyan);
        d.fillCircle(400, 300, 93);
        d.setColor(Color.black);
        d.fillCircle(400, 300, 90);
        d.setColor(Color.blue);
        d.fillRectangle(362, 250, 35, 100);
        d.fillRectangle(405, 250, 35, 100);
    }

    /**
     * Boolean for stop printing the pause screen.
     * @return false.
     */
    public boolean shouldStop() { return false; }

}
