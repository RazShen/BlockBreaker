package drawable;
import biuoop.DrawSurface;
import interfaces.Sprite;
import collisiondata.Counter;
import java.awt.Color;

/**
 * This class features lives indicator object using counter to print lives.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05- 24
 */
public class LivesIndicator implements Sprite {
    private Counter livesCount;

    /**
     * Constructor for lives indicator from a counter.
     * @param gameLives counter of game lives.
     */
    public LivesIndicator(Counter gameLives) {

        this.livesCount = gameLives;
    }

    /**
     * Drawing the lives.
     * @param d the draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(150,  18, "Lives: " + this.livesCount.getValue(), 15);
    }

    /**
     * Irrelevant here.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
