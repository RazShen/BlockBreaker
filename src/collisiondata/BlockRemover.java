package collisiondata;
import drawable.Ball;
import drawable.Block;
import interfaces.HitListener;
import rungame.GameLevel;

/**
 * This Class features a BlockRemover object, which is a listener that decides when to remove a block after a hit.
 * Also updates the counter of remaining blocks.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-20
 */
public class BlockRemover implements HitListener {
    /**
     * The game member.
     */
    private GameLevel game;
    /**
     * Holds a counter of the game blocks.
     */
    private Counter decreasingBlocksCounter;

    /**
     * This constructor creates a new BlockRemover from game and counter of game blocks.
     * @param game game.
     * @param blocks counter of blocks.
     */
    public BlockRemover(GameLevel game, Counter blocks) {
        this.game = game;
        this.decreasingBlocksCounter = blocks;
    }

    /**
     * This method removes  blocks that are hit and reach 0 hit-points.
     * @param beingHit the object that is being hit.
     * @param hitter the ball that is hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() <= 1) {
            this.decreasingBlocksCounter.decrease(1);
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(game);
        }
    }
}