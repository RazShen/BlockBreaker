package geometryprimitives;
import java.util.ArrayList;

/**
 * This class features a line from 2 points, including the method to compare between lines and check the if lines are
 * equal, and a method to get the intersection point between lines.
 * also including a method to get the closest collision point to a rectangle from the start of the line.
 *
 * @author Raz Shenkman
 * @version 2.0
 * @since 2017-04- 28
 */
public class Line {
    /**
     * First point of the line.
     */
    private Point p1;
    /**
     * Second point of the line.
     */
    private Point p2;
    /**
     * The incline of the Line.
     */
    private Double slope;
    /**
     * The difference in the x pivot.
     */
    private double xDif;
    /**
     * The difference in the y pivot.
     */
    private double yDif;
    /**
     * This constructor creates a line from 2 points.
     *
     * @param start (first point).
     * @param end   (second point).
     */
    public Line(Point start, Point end) {
        this.p1 = start;
        this.p2 = end;
        // calculate the slope from 2 points
        this.xDif = this.p1.getX() - this.p2.getX();
        this.yDif = this.p1.getY() - this.p2.getY();
        if (this.p1.getX() == this.p2.getX()) { //if the slope is infinity
            this.slope = null;
        } else { // if the slope isn't infinity
            this.slope = this.yDif / this.xDif;
        }

    }

    /**
     * This constructor creates a line from 4 coordinates.
     *
     * @param x1 for the x value of the first point.
     * @param y1 for the y value of the first point.
     * @param x2 for the x value of the second point.
     * @param y2 for the y value of the second point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);
        // Calculate the slope from 2 points
        if (this.p1.getX() == this.p2.getX()) { //if the slope is infinity
            this.slope = null;
        } else { // if the slope isn't infinity
            this.slope = (this.p2.getY() - this.p1.getY()) / (this.p2.getX() - this.p1.getX());
        }
        this.xDif = this.p1.getX() - this.p2.getX();
        this.yDif = this.p1.getY() - this.p2.getY();
    }

    /**
     * This method returns the length of the line.
     *
     * @return the absolute value of the length between the points.
     */
    public double length() {
        return Math.abs(this.p1.distance(this.p2));
    }

    /**
     * This method return the middle point of the line.
     *
     * @return the middle point of the line.
     */
    public Point middle() {
        // Return the average point between the two points of the line
        return new Point((this.p1.getX() + this.p2.getX()) / 2, (this.p1.getY() + this.p2.getY()) / 2);
    }

    /**
     * This method returns the start point of the line.
     *
     * @return the start point of the line.
     */
    public Point start() {
        return this.p1;
    }

    /**
     * This method returns the end point of the line.
     *
     * @return the end point of the line.
     */
    public Point end() {
        return this.p2;
    }

    /**
     * This method checks if the lines are intersecting by checking their slopes, and their intersection points.
     *
     * @param other line
     * @return true/false
     */
    public boolean isIntersecting(Line other) {
        if (this.xDif == 0 && other.xDif != 0 || this.xDif != 0 && other.xDif == 0) {
            return (intersectionWith(other) != null);
        } else if (this.xDif == 0 && other.xDif == 0) {
            return false;
        } else {
            if (this.slope == other.slope) {
                return false;
            } else {
                //check if intersection point is in range of the lines
                return  (intersectionWith(other) != null);
            }
        }
    }


    /**
     * This method check if one of the lines is parallel to Y pivot, if it is- it checks if the intersection point is
     * on both lines, if both lines have defined slopes, it finds their intersection point and check if it's on both
     * lines.
     *
     * @param other line
     * @return intersection point or null.
     */
    public Point intersectionWith(Line other) {
        double yColiThis;
        double yColiOther;
        double yInter;
        double xInter;
        Point intersectionPoint;
        // If one of the slopes is infinity (parallel to the Y pivot).
        if (this.slope == null) {
            yColiOther = ((-(other.slope)) * other.p1.getX()) + other.p1.getY(); //intersection with Y pivot
            yInter = other.slope * this.p1.getX() + yColiOther; //intersection of lines Y coordinate
            intersectionPoint = new Point(this.p1.getX(), yInter); //creates the new intersection point
            if (this.checkPoint(intersectionPoint, other) != null) {
                return intersectionPoint;
            } else {
                return null;
            }
            // If one of the slopes is infinity (parallel to the Y pivot).
        } else if (other.slope == null) {
            yColiThis = ((-(this.slope)) * this.p1.getX()) + this.p1.getY(); //intersection with Y pivot
            yInter = this.slope * other.p1.getX() + yColiThis;  //intersection of lines Y coordinate
            intersectionPoint = new Point(other.p1.getX(), yInter); //creates the new intersection point
            if (this.checkPoint(intersectionPoint, other) != null) {
                return intersectionPoint;
            } else {
                return null;
            }
        } else {
            yColiOther = ((-(other.slope)) * other.p1.getX()) + other.p1.getY(); //intersection with Y pivot
            yColiThis = ((-(this.slope)) * this.p1.getX()) + this.p1.getY(); //intersection with Y pivot
            xInter = (yColiOther - yColiThis) / (this.slope - other.slope); //intersection of lines X point
            yInter = this.slope * xInter + yColiThis; //intersection of lines Y coordinate
            intersectionPoint = new Point(xInter, yInter);
            if (this.checkPoint(intersectionPoint, other) != null) { // if intersection point on both lines
                return intersectionPoint;
            } else {
                return null;
            }
        }
    }

    /**
     * This method check if one of the lines is parallel to Y pivot, if it is- it checks if the intersection point is
     * on both lines, if both lines have defined slopes, it finds their intersection point and check if it's on both
     * lines.
     *
     * @param other  line
     * @param cPoint the point to check
     * @return intersection point or null.
     */
    public Point checkPoint(Point cPoint, Line other) {
        double p1X = this.p1.getX();
        double p2X = this.p2.getX();
        double p1Y = this.p1.getY();
        double p2Y = this.p2.getY();
        double oP1X = other.p1.getX();
        double oP2X = other.p2.getX();
        double oP1Y = other.p1.getY();
        double oP2Y = other.p2.getY();
        double maxXL1 = Math.max(p1X, p2X);
        double minXL1 = Math.min(p1X, p2X);
        double maxYL1 = Math.max(p1Y, p2Y);
        double minYL1 = Math.min(p1Y, p2Y);
        double maxXL2 = Math.max(oP1X, oP2X);
        double minXL2 = Math.min(oP1X, oP2X);
        double maxYL2 = Math.max(oP1Y, oP2Y);
        double minYL2 = Math.min(oP1Y, oP2Y);
        // Checks if the intersection point is between the max x and y and min x and y of both lines
        if (cPoint.getX() <= maxXL1 && cPoint.getX() >= minXL1 && cPoint.getY() <= maxYL1 && cPoint.getY() >= minYL1
                && cPoint.getX() <= maxXL2 && cPoint.getX() >= minXL2
                && cPoint.getY() <= maxYL2 && cPoint.getY() >= minYL2) {
            return cPoint;
        }
        return null;
    }

    /**
     * This method checks if the both line's points are equal.
     *
     * @param other line
     * @return true or false
     */
    public boolean equals(Line other) {
        //If the 1's point of the 1's line equals to the 1's point of the other line.
        if (other.p1.equals(this.p1) && other.p2.equals(this.p2)) {
            return true;
            //If the 1's point of the 1's line equals to the 2's point of the other line.
        } else if (other.p1.equals(this.p2) && other.p2.equals(this.p1)) {
            return true;
        }
        return false;
    }

    /**
     * This method returns the closest point of collision into a specific rectangle to the start point of the line.
     * @param rect an collidable rectangle.
     * @return closest point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        ArrayList<Point> intersectionPoints = rect.intersectionPoints(this);
        Point closestIntersectionPoint;
        // If there are no intersection points.
        if (intersectionPoints.isEmpty()) {
            return null;
        } else {
            // Find the closest intersection point to the start of the line.
            closestIntersectionPoint = intersectionPoints.get(0);
            for (int i = 1; i < intersectionPoints.size(); i++) {
                if (this.start().distance(intersectionPoints.get(i))
                        < this.start().distance(closestIntersectionPoint)) {
                    closestIntersectionPoint = intersectionPoints.get(i);
                }
            }
        }
        return closestIntersectionPoint;
    }
}