package interfaces;
import drawable.Ball;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import collisiondata.Velocity;

/**
 * This interface is used to describe the basics of collidable object (an object the ball can collide with).
 *
 * @author Raz Shenkman
 * @version 2.0
 * @since 2017-04-30
 */
public interface Collidable extends IsPaddle {
    /**
     * Return the "collision shape" of the object.
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Return the new velocity after hitting the collidable object.
     * @param collisionPoint collision point between the objects.
     * @param currentVelocity the current velocity of the object colliding with the collidable object.
     * @param hitter the hitting ball.
     * @return a new velocity for the colliding object (the ball) after the collision).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}