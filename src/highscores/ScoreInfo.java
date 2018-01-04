package highscores;
import java.io.Serializable;

/**
 * This class stores information about an high score.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */

public class ScoreInfo implements Serializable {
    /**
     * Name of player.
     */
    private String nameOfPlayer;
    /**
     * Score of player.
     */
    private int scoreOfPlayer;

    /**
     * This constructor creates ScoreInfo from name and score.
     * @param name player's name.
     * @param score player's score.
     */
    public ScoreInfo(String name, int score) {
        this.nameOfPlayer = name;
        this.scoreOfPlayer = score;
    }

    /**
     * This method returns the name of the player.
     * @return player's name.
     */
    public String getName() {
        return this.nameOfPlayer;
    }

    /**
     * This method returns the score of the player.
     * @return player's score.
     */
    public int getScore() {
        return this.scoreOfPlayer;
    }


}
