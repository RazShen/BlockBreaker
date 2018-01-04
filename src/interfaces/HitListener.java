package interfaces;
import drawable.Ball;
import drawable.Block;

/**
 * This interface is used for the Listener pattern to describe an object that gets notified when a hit occur.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-19
 */
public interface HitListener {

    /**
     * his method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit the object that is being hit.
     * @param hitter the ball that is hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
