package collisiondata;
import geometryprimitives.Point;

/**
 * This class features a velocity of an object using dx and dy.
 * Including methods to create the velocity from angle and speed and also dx and dy.
 *
 * @author Raz Shenkman
 * @version 2.0
 * @since 2017-04-30
 */
public class Velocity {
    /**
     * The distance in x pivot made in one move.
     */
    private Double dx;
    /**
     * The distance in y pivot made in one move.
     */
    private Double dy;

    /**
     * This constructor creates a velocity object from dx and dy.
     * @param dx distance in x pivot made in one move.
     * @param dy distance in y pivot made in one move.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * This method returns the dx of the velocity.
     * @return dx.
     */
    public Double getDX() {
        return this.dx;
    }

    /**
     * This method returns the dy of the velocity.
     * @return dy.
     */
    public Double getDY() {
        return this.dy;
    }

    /**
     * This method creates velocity from angle and speed, using Math class to calculate dx using sinus and dy using
     * cosinus.
     * @param angle to calculate.
     * @param speed to calculate.
     * @return the original velocity to constructor to create an object from the dx and dy we just got (modified for
     * supporting drawable.Paddle.hit method.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        while (angle > 360) { //if the angle is above 360.
            angle = angle - 360;
        }
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = speed * Math.cos(angle);
        return new Velocity(dx, -dy);
    }

    /**
     * Return new velocity by the inputted dt and the current velocity values.
     * @param dt is the amount of seconds passed since the last call.
     * @return new velocity by the inputted dt and the current velocity values.
     */
    public Velocity getVeloByDt(double dt) {
        return new Velocity(this.dx * dt, this.dy * dt);
    }

    /**
     * This method returns the speed of the ball.
     * @return ball's speed.
     */
    public double getSpeed() {
        return (Math.sqrt((this.getDX() * this.getDX()) + (this.getDY() * this.getDY())));
    }

    /**
     * This method changes a point according to the velocity (adding dx to x, adding dy to y).
     * @param p the point.
     * @return a new point with the velocity values and the inputted point values.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
}
