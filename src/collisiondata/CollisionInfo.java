package collisiondata;
import geometryprimitives.Point;
import interfaces.Collidable;

/**
 * This Class features an Collision info object which hold the collision point and a the collided- to object.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04-28
 */
public class CollisionInfo {
    /**
     * The collision point.
     */
    private Point coliPoint;
    /**
     * The collided- to object.
     */
    private Collidable collisionOb;

    /**
     * This constructor sets a collisionInfo object.
     * @param p the collision point.
     * @param coli the collided- to object.
     */
    public CollisionInfo(Point p, Collidable coli) {
        this.coliPoint = p;
        this.collisionOb = coli;
    }

    /**
     * This method returns the collision point.
     * @return collision point.
     */
    public Point collisionPoint() {
        return this.coliPoint;
    }

    /**
     * This method returns the object which was collided- to.
     * @return collided- to object.
     */
    public Collidable collisionObject() {
        return this.collisionOb;
    }
}