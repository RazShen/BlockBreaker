package rungame;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collisiondata.Counter;
import drawable.EndScreen;
import drawable.HighScoresAnimation;
import highscores.HighScoresTable;
import highscores.ScoreInfo;
import interfaces.LevelInformation;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class features a game flow object. used for running multiple levels.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */

public class GameFlow {
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter scoreCounter;
    private Counter livesCounter;
    private HighScoresTable highScoreTable;
    private File f;
    private GUI gui;

    /**
     * Constructor for the game flow, getting a keyboard and an animation runner.
     * @param ar animation runner.
     * @param ks keyboard sensor.
     * @param highScoresTable high score table.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, HighScoresTable highScoresTable) {
        this.keyboardSensor = ks;
        this.animationRunner = ar;
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.livesCounter.increase(7);
        this.highScoreTable = highScoresTable;
        this.f = new File("highscores");
        this.gui = ar.getGui();
    }

    /**
     * Method for running a list of level information (multiple levels).
     * @param levels list of level information.
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,
                    livesCounter, scoreCounter);
            level.initialize();
            while (this.livesCounter.getValue() > 0 && level.areThereBlocksLeft()) {
                level.playOneTurn();
            }
            // Check if the user's lives are 0.
            if (this.livesCounter.getValue() == 0) {
                this.animationRunner.run(new KeyPressStoppableAnimation(this.animationRunner.getGui()
                        .getKeyboardSensor(), "space",
                        new EndScreen(this.scoreCounter.getValue(), false)));
                this.addHighScore();
                this.animationRunner.run(new KeyPressStoppableAnimation(this.animationRunner.getGui()
                        .getKeyboardSensor(), "space", new HighScoresAnimation(this.highScoreTable)));
                break;
            }
            // Check if the user's is in the last level and has won the game.
            if (levelInfo == levels.get(levels.size() - 1)) {
                if (this.livesCounter.getValue() > 0 && !level.areThereBlocksLeft()) {
                    this.animationRunner.run(new KeyPressStoppableAnimation(this.animationRunner.getGui()
                            .getKeyboardSensor(), "space",
                            new EndScreen(this.scoreCounter.getValue(), true)));
                    this.addHighScore();
                    this.animationRunner.run(new KeyPressStoppableAnimation(this.animationRunner.getGui()
                            .getKeyboardSensor(), "space", new HighScoresAnimation(this.highScoreTable)));
                    break;
                }
            }
        }

    }

    /**
     * This method adds a high score to the table if it has a relevant value.
     */
    public void addHighScore() {
        int rank = this.highScoreTable.getRank(scoreCounter.getValue());
        // If the table's size is less than 5.
        if (highScoreTable.size() < 5) {
            DialogManager dialog = this.gui.getDialogManager();
            String name = dialog.showQuestionDialog("Enter Name", "What is your name?", "");
            this.highScoreTable.add(new ScoreInfo(name, scoreCounter.getValue()));
            try {
                highScoreTable.save(this.f);
            } catch (IOException e) {
                int x = 5;
            }
        // If the rank is relevant- less than the size of the table.
        } else if (rank <= highScoreTable.size()) {
            DialogManager dialog = this.gui.getDialogManager();
            String name = dialog.showQuestionDialog("Enter Name", "What is your name?", "");
            this.highScoreTable.add(new ScoreInfo(name, scoreCounter.getValue()));
            try {
                highScoreTable.save(this.f);
            } catch (IOException e) {
                int x = 5;
            }
        }
    }
}
