package drawable;
import biuoop.DrawSurface;
import highscores.HighScoresTable;
import interfaces.Animation;
import java.awt.Color;

/**
 * This class features a game HighScoresAnimation object. used for running high score table animation.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-19
 */
public class HighScoresAnimation implements Animation {
    /**
     * high score table.
     */
    private HighScoresTable highScoresTable;
    /**
     * Constructor for HighScoresAnimation from high score table.
     * @param scores high score table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.highScoresTable = scores;
    }


    /**
     * Prints the high score table screen.
     * @param d draw surface.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.CYAN);
        d.fillRectangle(0, d.getHeight() / 2 - 220, d.getWidth(), 90);
        d.setColor(Color.BLACK);
        d.fillRectangle(0, d.getHeight() / 2 - 220, d.getWidth(), 3);
        d.drawText(180, d.getHeight() / 2 - 150, "High Scores Table", 50);
        d.fillRectangle(0, d.getHeight() / 2 - 130, d.getWidth(), 3);
        for (int i = 0; i < this.highScoresTable.getHighScores().size(); i++) {
            d.drawText(280, 250 + 40 * i,
                    (i + 1) + ".      " + this.highScoresTable.getHighScores().get(i).getName(), 30);
            d.drawText(470, 250 + 40 * i,
                    Integer.toString(this.highScoresTable.getHighScores().get(i).getScore()), 30);
        }
        d.drawText(250, 550, "Press space to continue", 30);
    }

    /**
     * Boolean if should stop printing the pause screen.
     * @return true/false.
     */
    public boolean shouldStop() { return false; }

}
