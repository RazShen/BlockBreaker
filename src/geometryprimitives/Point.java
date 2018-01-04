package geometryprimitives;

/**
 * This class features a point from a coordinate, including the method to compare between points and check distance
 * to another point.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04-02
 */
public class Point {
    /**
     * X coordinate of the point.
     */
    private double x;
    /**
     * Y coordinate of the point.
     */
    private double y;

    /**
     * Constructor for a point.
     * @param x coordinate.
     * @param y coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Distance method return the distance from this point to the other point.
     * @param other another point.
     * @return the distance to the other point.
     */
    public double distance(Point other) {
        double distX = this.x - other.x;
        double distY = this.y - other.y;
        return Math.sqrt((distX * distX) + (distY * distY));
    }

    /**
     * Equals method returns true is the points are equal, false otherwise.
     * @param other point.
     * @return true of false.
     */
    public boolean equals(Point other) {
        if (other.x == this.x && other.y == this.y) {
            return true;
        }
        return false;
    }

    /**
     * This method return the x value of this point.
     * @return value of x point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * This method return the y value of this point.
     * @return value of y point.
     */
    public double getY() {
        return this.y;
    }
}
