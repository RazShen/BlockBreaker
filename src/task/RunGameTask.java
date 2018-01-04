package task;
import biuoop.GUI;
import highscores.HighScoresTable;
import interfaces.LevelInformation;
import interfaces.Task;
import rungame.AnimationRunner;
import rungame.GameFlow;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This method features a run game task, which creates game flow and uses it to run the level information list it gets.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-12.
 */
public class RunGameTask implements Task<Void> {
    private GUI gui;
    private AnimationRunner animationRunner;
    private List<LevelInformation> levels;
    private File highScoresFile;
    private HighScoresTable highScoresTable;

    /**
     * Constructor for run game task from gui, animation runner, list of level information and a high score table.
     * @param gui inputted gui.
     * @param animationRunner inputted animation runner.
     * @param levels inputted list of level information.
     * @param highScoresTable high score table.
     */
    public RunGameTask(GUI gui, AnimationRunner animationRunner, List<LevelInformation> levels,
                       HighScoresTable highScoresTable) {
        this.gui = gui;
        this.animationRunner = animationRunner;
        this.levels = levels;
        this.highScoresFile = new File("highscores");
        this.highScoresTable = highScoresTable;
    }

    /**
     * Run a task and get a returned value.
     * @return Void.
     */
    public Void run() {
        GameFlow game = new GameFlow(this.animationRunner, this.gui.getKeyboardSensor(), this.highScoresTable);
        game.runLevels(this.levels);
        try {
            highScoresTable.save(this.highScoresFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't save high score table");
        }
        return null;
    }
}
