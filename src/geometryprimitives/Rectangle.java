package geometryprimitives;
import java.util.ArrayList;
/**
 * This Class features an Rectangle, getter methods for it's proportions, setting method for it's
 * upper left point, and a method to returns a list of intersection points with a line.
 *
 * @author Raz Shenkman
 * @version 2.0
 * @since 2017-04-30
 */
public class Rectangle {
    /**
     * Upper left point.
     */
    private Point upperLeftP;
    /**
     * Upper right point.
     */
    private Point upperRightP;
    /**
     * Down left point.
     */
    private Point downLeftP;
    /**
     * Down right point.
     */
    private Point downRightP;
    /**
     * Upper line of the rectangle.
     */
    private Line upperBound;
    /**
     * Lower line of the rectangle.
     */
    private Line lowerBound;
    /**
     * Right line of the rectangle.
     */
    private Line rightBound;
    /**
     * Left line of the rectangle.
     */
    private Line leftBound;
    /**
     * Width of the rectangle.
     */
    private double widthRec;
    /**
     * Height of the rectangle.
     */
    private double heightRec;

    /**
     * This is a constructor to set a rectangle from upper- left point, a width and a height.
     * @param upperLeft point.
     * @param width of the rectangle.
     * @param height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeftP = upperLeft;
        // Create the rectangle's boundaries.
        this.upperRightP = new Point(upperLeftP.getX() + width, upperLeftP.getY());
        this.downLeftP = new Point(upperLeftP.getX(), upperLeftP.getY() + height);
        this.downRightP = new Point(upperLeftP.getX() + width, upperLeftP.getY() + height);
        this.upperBound = new Line(upperLeftP, upperRightP);
        this.lowerBound = new Line(downLeftP, downRightP);
        this.rightBound = new Line(upperRightP, downRightP);
        this.leftBound = new Line(upperLeftP, downLeftP);
        // Create the rectangle's width and height.
        this.widthRec = width;
        this.heightRec = height;
    }

    /**
     * This method returns a list of intersection points (possibly empty) with an inputted line by calculating the
     * intersection points with the rectangle boundaries.
     * @param line inputted line.
     * @return list of intersection points.
     */
    public ArrayList<Point> intersectionPoints(Line line) {
        ArrayList<Point> intersectionPoints = new ArrayList<Point>();
        if (this.upperBound.isIntersecting(line)) { //if there is an intersection point with the upper line.
            intersectionPoints.add(this.upperBound.intersectionWith(line));
        }
        if (this.lowerBound.isIntersecting(line)) { //if there is an intersection point with the lower line.
            intersectionPoints.add(this.lowerBound.intersectionWith(line));
        }
        if (this.rightBound.isIntersecting(line)) { //if there is an intersection point with the right line.
            intersectionPoints.add(this.rightBound.intersectionWith(line));
        }
        if (this.leftBound.isIntersecting(line)) { //if there is an intersection point with the left line.
            intersectionPoints.add(this.leftBound.intersectionWith(line));
        }
        return intersectionPoints;
    }

    /**
     * This method returns the width of the rectangle.
     * @return rectangle's width.
     */
    public double getWidth() {
        return this.widthRec;
    }

    /**
     * This method returns the height of the rectangle.
     * @return rectangle's height.
     */
    public double getHeight() {
        return this.heightRec;
    }

    /**
     * This method returns the upper left point of the rectangle.
     * @return rectangle's upper left point.
     */
    public Point getUpperLeft() {
        return this.upperLeftP;
    }

    /**
     * This method returns the upper line of the rectangle.
     * @return rectangle's upper line.
     */
    public Line getUpperBound() {
        return this.upperBound;
    }

    /**
     * This method returns the lower line of the rectangle.
     * @return rectangle's lower line.
     */
    public Line getLowerBound() {
        return this.lowerBound;
    }

    /**
     * This method returns the right line of the rectangle.
     * @return rectangle's right line.
     */
    public Line getRightBound() {
        return this.rightBound;
    }

    /**
     * This method returns the left line of the rectangle.
     * @return rectangle's left line.
     */
    public Line getLeftBound() {
        return this.leftBound;
    }

    /**
     * This method sets the upper left point of the rectangle, it is used for changing the paddle's location.
     * @param distance the movement the paddle makes.
     */
    public void setUpperLeftP(int distance) {
        this.upperLeftP = new Point(this.upperLeftP.getX() + distance, this.upperLeftP.getY());
        this.upperRightP = new Point(upperLeftP.getX() + getWidth(), upperLeftP.getY());
        this.downLeftP = new Point(upperLeftP.getX(), upperLeftP.getY() + getHeight());
        this.downRightP = new Point(upperLeftP.getX() + getWidth(), upperLeftP.getY() + getHeight());
        this.upperBound = new Line(upperLeftP, upperRightP);
        this.lowerBound = new Line(downLeftP, downRightP);
        this.rightBound = new Line(upperRightP, downRightP);
        this.leftBound = new Line(upperLeftP, downLeftP);
    }

    /**
     * This method sets the upper left point of the rectangle.
     * @param newPoint inputted upper left point.
     */
    public void setUpperLeftP(Point newPoint) {
        this.upperLeftP = new Point(newPoint.getX(), newPoint.getY());
        this.upperRightP = new Point(upperLeftP.getX() + getWidth(), upperLeftP.getY());
        this.downLeftP = new Point(upperLeftP.getX(), upperLeftP.getY() + getHeight());
        this.downRightP = new Point(upperLeftP.getX() + getWidth(), upperLeftP.getY() + getHeight());
        this.upperBound = new Line(upperLeftP, upperRightP);
        this.lowerBound = new Line(downLeftP, downRightP);
        this.rightBound = new Line(upperRightP, downRightP);
        this.leftBound = new Line(upperLeftP, downLeftP);
    }

    /**
     * This method moves the rectangle to a center point on the draw surface (used for centering the paddle).
     * @param centerP center point recieved for centering the rectangle.
     */
    public void centerRectangle(int centerP) {
        this.upperLeftP = new Point(centerP, 570);
        this.upperRightP = new Point(upperLeftP.getX() + getWidth(), upperLeftP.getY());
        this.downLeftP = new Point(upperLeftP.getX(), upperLeftP.getY() + getHeight());
        this.downRightP = new Point(upperLeftP.getX() + getWidth(), upperLeftP.getY() + getHeight());
        this.upperBound = new Line(upperLeftP, upperRightP);
        this.lowerBound = new Line(downLeftP, downRightP);
        this.rightBound = new Line(upperRightP, downRightP);
        this.leftBound = new Line(upperLeftP, downLeftP);
    }
}
