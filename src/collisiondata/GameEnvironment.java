package collisiondata;
import geometryprimitives.Line;
import interfaces.Collidable;
import java.util.ArrayList;

/**
 * This class features a game environment which holds all the collidable objects, so a ball can "find" which object
 * it's going to collide with.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04- 28
 */
public class GameEnvironment {
    /**
     * An array list to hold all the collidable objects in the game.
     */
    private ArrayList<Collidable> collidableObjects;
    /**
     * This constructor creates an array list of collidable objects for the game environment.
     */
    public GameEnvironment() {
        this.collidableObjects = new ArrayList<Collidable>();
    }

    /**
     * This method adds a collidable object to the game environment array list of collidables.
     * @param c a collidable object.
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.collidableObjects.add(c);
        }
    }

    /**
     * This method remove a collidable object from the game environment.
     * @param c collidable object.
     */
    public void removeColilidable(Collidable c) {
        this.collidableObjects.remove(c);
    }

    /**
     * This method returns the closest collision info object (collision point and the collision object) by checking
     * the nearest collision, picking the closest one and create a collision info object which hold the collision point
     * and the object which the ball will collide into.
     * @param trajectory - a line from 2 points: center of the ball and the next position of the ball (according to it's
     * velocity).
     * @return a collision object which hold the closest collision point the next object to be collided with.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Collidable closestOb = null;
        // Check if there is even an object to be collided with in the trajectory.
        for (int i = 0; i < this.collidableObjects.size(); i++) {
            if (trajectory.closestIntersectionToStartOfLine(collidableObjects.get(i).
                    getCollisionRectangle()) != null) {
                closestOb = collidableObjects.get(i);
                break;
            }
        }
        // If there isn't an object to collide with.
        if (closestOb == null) {
            return null;
        } else {
            // Find the closest point of collision from all the collideable objects.
            for (int i = 0; i < this.collidableObjects.size(); i++) {
                if (trajectory.closestIntersectionToStartOfLine(collidableObjects.get(i).getCollisionRectangle())
                        != null) {
                    // If other collision point (of another collideable object) is closer to the start of the line.
                    if (trajectory.start().distance(trajectory.closestIntersectionToStartOfLine(collidableObjects
                            .get(i).getCollisionRectangle())) < trajectory.start()
                            .distance(trajectory.closestIntersectionToStartOfLine(closestOb.
                                    getCollisionRectangle()))) {
                            closestOb = collidableObjects.get(i);
                    }
                }
            }
        }
        // Return a new collision info object using the closest collision point we found and it's related collideable
        // object.
        return new CollisionInfo(trajectory.closestIntersectionToStartOfLine(closestOb.getCollisionRectangle()),
                closestOb);
    }
}