package collisiondata;
import drawable.Ball;
import drawable.Block;
import interfaces.HitListener;

/**
 * A test for the hit listeners.
 */
public class PrintingHitListener implements HitListener {

    /**
     * Print hit event.
     * @param beingHit the object that is being hit.
     * @param hitter the ball that is hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitPoints() + " points was hit.");
    }
}