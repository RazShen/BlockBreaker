package collisiondata;
import drawable.Ball;
import drawable.Block;
import interfaces.HitListener;
import rungame.GameLevel;

/**
 * This Class features a BallRemover object, which is a listener that decides when to remove a ball.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-20
 */
public class BallRemover implements HitListener {
    /**
     * The game member.
     */
    private GameLevel game;
    /**
     * Holds a counter of the game balls.
     */
    private Counter decreasingBallsCounter;

    /**
     * This constructor creates a new BallRemover from game and counter of game balls.
     * @param game game.
     * @param balls balls counter.
     */
    public BallRemover(GameLevel game, Counter balls) {
        this.game = game;
        this.decreasingBallsCounter = balls;
    }

    /**
     * This method removes balls that hit a ballDestroyer block.
     * @param beingHit the object that is being hit.
     * @param hitter the ball that is hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.isBallsDestroyer()) {
            this.decreasingBallsCounter.decrease(1);
            hitter.removeFromGame(game);
            hitter.removeHitListener(this);
        }
    }

}
