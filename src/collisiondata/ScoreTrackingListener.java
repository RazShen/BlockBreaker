package collisiondata;
import drawable.Ball;
import drawable.Block;
import interfaces.HitListener;

/**
 * This Class features a ScoreTrackingListener object, which is a HitListener and keeps track on the user's score.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-20
 */
public class ScoreTrackingListener implements HitListener {
    /**
     * Counter of the user current score.
     */
    private Counter currentScore;

    /**
     * This constructor create a ScoreTrackingListener from a new Counter.
     * @param scoreCounter user current score counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method updates the user score according to the hit.
     * @param beingHit the object that is being hit.
     * @param hitter the ball that is hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
        if (beingHit.getHitPoints() <= 1) {
            this.currentScore.increase(10);
        }
    }
}
