package drawable;
import biuoop.DrawSurface;
import interfaces.Animation;
import java.awt.Color;

/**
 * This class features the end screen of the game.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05- 24
 */
public class EndScreen implements Animation {
    /**
     * Score to print.
     */
    private int score;
    /**
     * Winning or losing (to print).
     */
    private boolean win;

    /**
     * Constructor for end screen from user's score and if he won or lost.
     * @param score user's score.
     * @param winOrLose won or lost the game.
     */
    public EndScreen(int score, boolean winOrLose) {
        this.score = score;
        this.win = winOrLose;
    }

    /**
     * Printing the end screen.
     * @param d draw surface.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.win) {
            d.setColor(Color.BLUE);
            d.drawText(250, 170, "You Win!", 50);
            d.setColor(Color.BLACK);
            d.drawText(180, 300, "Your score is  " + this.score, 50);
            d.drawText(150, 500, "Press space to continue", 50);
        } else {
            d.setColor(Color.RED);
            d.drawText(250, 170, "Game Over.", 50);
            d.setColor(Color.BLACK);
            d.drawText(180, 300, "Your score is  " + this.score, 50);
            d.drawText(150, 500, "Press space to continue", 50);
        }
    }

    /**
     * Boolean for stop printing the end screen.
     * @return false.
     */
    public boolean shouldStop() { return false; }

}
