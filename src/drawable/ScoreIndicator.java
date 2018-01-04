package drawable;
import biuoop.DrawSurface;
import collisiondata.Counter;
import interfaces.Sprite;
import java.awt.Color;

/**
 * This Class features a ScoreIndicator object, which is a sprite and can be draw and present the score.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-20
 */
public class ScoreIndicator implements Sprite {
    /**
     * The counter of the user's current score.
     */
    private Counter scoreToPrint;

    /**
     * This constructor creates a ScoreIndicator object from a counter of the user's current score.
     * @param score user's current score.
     */
    public ScoreIndicator(Counter score) {
        this.scoreToPrint = score;
    }

    /**
     * This method draws the ScoreIndicator of a draw surface.
     * @param d the draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        // Draw the rectangle (filled with color).
        d.fillRectangle(0, 0,  800, 20);
        d.setColor(Color.BLACK);
        d.drawText(350,  18, "Score: " + this.scoreToPrint.getValue(), 15);
    }

    /**
     * Irrelevant here.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
