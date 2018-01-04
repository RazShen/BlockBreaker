package rungame;
import java.awt.Color;
import biuoop.DrawSurface;
import drawable.SpriteCollection;
import interfaces.Animation;

/**
 * This class features a count down animation object.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-24
 */
public class CountdownAnimation implements Animation {
    private boolean running;
    private long numOfMillis;
    private int countFrom;
    private int startFromNumber;
    private long startTime;
    private SpriteCollection gameSprites;
    /**
     * Constructor for countdown animation using given time, number to start counting from and background.
     * @param numOfSeconds given showing time.
     * @param countFrom the number to count down from.
     * @param gameScreen the background.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.running = true;
        this.numOfMillis = (long) (numOfSeconds * 1000); // from sec to MilliSecond.
        this.countFrom = countFrom;
        this.startTime = System.currentTimeMillis(); // current time.
        this.startFromNumber = countFrom;
        this.gameSprites = gameScreen;
    }

    /**
     * Drawing the countdown animation by measuring the passed time and checking if it's bigger than the given time
     * for each number to draw.
     * @param surface the DrawSurface to draw on.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface surface, double dt) {
        surface.setColor(Color.BLUE);
        surface.fillRectangle(20, 40, 760, 560);
        this.gameSprites.drawAllOn(surface);
        surface.setColor(Color.RED);
        surface.drawText(380, 450, Integer.toString(this.countFrom), 70);
        surface.setColor(Color.BLACK);
        surface.drawText(378, 448, Integer.toString(this.countFrom), 70);
        if (System.currentTimeMillis() - this.startTime > this.numOfMillis / this.startFromNumber) {
            this.countFrom--;
            this.startTime = System.currentTimeMillis();
        }
        if (this.countFrom == 0) {
            this.running = false;
        }
    }

    /**
     * Return if this method should stop drawing.
     * @return true- should stop drawing, false otherwise.
     */
    public boolean shouldStop() {
        return !this.running;
    }
}

