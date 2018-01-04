package task;
import drawable.HighScoresAnimation;
import highscores.HighScoresTable;
import interfaces.Task;
import rungame.AnimationRunner;
import rungame.KeyPressStoppableAnimation;

/**
 * This method features a show high scores task, shows the high score table by using high score animation class.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private HighScoresAnimation highScoresAnimation;
    private String stopKey;

    /**
     * Constructor for show high score task, from animation runner and high score table.
     * @param runner animation runner.
     * @param highScoresTable high score table.
     */
    public ShowHiScoresTask(AnimationRunner runner, HighScoresTable highScoresTable) {
        this.runner = runner;
        this.highScoresAnimation = new HighScoresAnimation(highScoresTable);
        this.stopKey = "space";
    }

    /**
     * This method runs the high score tasks (using the animation runner and creating a new high scores animation).
     * @return Void.
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(runner.getGui().getKeyboardSensor(), this.stopKey,
                this.highScoresAnimation));
        return null;
    }
}
