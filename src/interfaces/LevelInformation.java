package interfaces;
import collisiondata.Velocity;
import drawable.Block;
import java.util.List;

/**
 * This class features a level information, that holds all the information required to start a game.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04-02
 */
public interface LevelInformation {

    /**
     * Number of balls.
     * @return Number of balls.
     */
    Integer numberOfBalls();

    /**
     * The initial velocity of each ball.
     * @return list of ball velocities.
     */
    List<Velocity> getBallVelocities();

    /**
     * Return the paddle speed.
     * @return paddle's speed.
     */
    Integer paddleSpeed();

    /**
     * Return the paddle width.
     * @return paddle's width.
     */
    Integer paddleWidth();

    /**
     * Return the name of the level.
     * @return the name of the level.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     * @return background of the level.
     */
    Sprite getBackground();

    /**
     * Return a list of blocks that the level use.
     * @return list of blocks.
     */
    List<Block> blocks();

    /**
     * Number of blocks that should be removed during the level.
     * @return Number of blocks that should be removed.
     */
    Integer numberOfBlocksToRemove();
}