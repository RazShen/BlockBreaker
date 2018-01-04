package drawable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Line;
import interfaces.HitListener;
import interfaces.HitNotifier;
import interfaces.Sprite;
import collisiondata.Velocity;
import collisiondata.GameEnvironment;
import rungame.GameLevel;
import geometryprimitives.Rectangle;

/**
 * This class features a ball, with fields of center, color, velocity, and a game environment.
 * the ball has methods to: draw it, set it's velocity, getters and setters for the ball's qualities.
 *
 * @author Raz Shenkman
 * @version 2.0
 * @since 2017-04- 30
 */
public class Ball implements Sprite, HitNotifier {
    /**
     *Center point of the ball.
     */
    private Point centerP;
    /**
     * Radius of the ball.
     */
    private int radius;
    /**
     * Color of the ball.
     */
    private Color bColor;
    /**
     * collisiondata.Velocity of the ball.
     */
    private Velocity bVelocity;
    /**
     * The game environment of the ball.
     */
    private GameEnvironment gameEnvironment;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * This constructor creates a ball from a center point, size and color. set the velocity to 0 (the problem won't
     * crash if the user didn't set the velocity).
     * @param x center point x value.
     * @param y center point x value.
     * @param r radius of the ball.
     * @param color color of the ball.
     */
    public Ball(int x, int y, int r, Color color) {
        if (x < 0 || y < 0 || r < 0) {
            throw new RuntimeException("The ball values are wrong");
        }
        this.centerP = new Point(x, y);
        this.radius = r;
        this.bColor = color;
        this.gameEnvironment = new GameEnvironment();
        this.bVelocity = new Velocity(0, 0);
    }

    /**
     * This constructor creates a new ball from  a center point, size, color and a list of HItListeners, set the
     * velocity to 0 (the problem won't crash if the user didn't set the velocity).
     * @param x center point x value.
     * @param y center point x value.
     * @param r radius of the ball.
     * @param color color of the ball.
     * @param hitlist list of HitListeners.
     */
    public Ball(int x, int y, int r, Color color, HitListener hitlist) {
        if (x < 0 || y < 0 || r < 0) {
            throw new RuntimeException("The ball values are wrong");
        }
        this.centerP = new Point(x, y);
        this.radius = r;
        this.bColor = color;
        this.gameEnvironment = new GameEnvironment();
        this.bVelocity = new Velocity(0, 0);
        this.addHitListener(hitlist);
    }
    /**
     * This method returns x value of the center of the ball.
     * @return x.
     */
    public int getX() {
        return (int) this.centerP.getX();
    }

    /**
     * This method returns y value of the center of the ball.
     * @return y.
     */
    public int getY() {
        return (int) this.centerP.getY();
    }

    /**
     * This method returns the radius of the ball.
     * @return radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * This method returns the color of the ball.
     * @return color of the ball.
     */
    public java.awt.Color getColor() {
        return this.bColor;
    }

    /**
     * This method draws the ball (a filled circle with a frame and a dot inside) on a given surface.
     * @param surface (of type DrawSurface).
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.RED);
        surface.fillCircle(this.getX(), this.getY(), 2);
    }

    /**
     * This method sets the velocity and changes the boolean didSetVelo of the ball class to true.
     * @param v velocity.
     */
    public void setVelocity(Velocity v) {
        this.bVelocity = v;
    }

    /**
     * This method returns the ball velocity.
     * @return velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.bVelocity;
    }

    /**
     * This method sets the specific game environment of the ball.
     * @param gameEnv a game environment.
     */
    public void setGameEnvironment(GameEnvironment gameEnv) {
        this.gameEnvironment = gameEnv;
    }

    /**
     * This method's purpose is moving the ball one step ahead according to the ball's velocity,
     * first, it checks if the ball is inside the paddle (happens if you move the paddle while the block is
     * going to hit it's side), if so- move it away from the rectangle and change it's velocity.
     * second, if the ball isn't inside the paddle colliding with anything move the ball one step and that's it.
     * third, if the line is colliding with anything- we check the ball's movement and move the ball near the collision
     * point.
     * finally, with change the ball velocity (we get the velocity from the object with collided with- the hit method).
     * @param dt is the amount of seconds passed since the last call.
     */
    public void moveOneStep(double dt) {
        int cPX = (int) centerP.getX(); // center point of the ball x value.
        int cPY = (int) centerP.getY(); // center point of the ball y value.
        double veloDX = this.getVelocity().getVeloByDt(dt).getDX();
        double veloDY = this.getVelocity().getVeloByDt(dt).getDY();
        int radiusB = this.getSize();
        double coliPX; // x value of the collision point.
        double coliPY; // y value of the collision point.
        // Calculate the trajectory using the ball's velocity.
        Line trajectory = new Line(this.centerP, new Point(cPX + veloDX, cPY + veloDY));
        // Check if the ball is colliding with anything and if it's inside a rectangle (used speciall for the paddle).
        if (this.gameEnvironment.getClosestCollision(trajectory) != null) {
            if (this.gameEnvironment.getClosestCollision(trajectory).collisionObject().isItPaddle()) {
                if (insideRec(this.gameEnvironment.getClosestCollision(trajectory).collisionObject()
                        .getCollisionRectangle())) {
                    this.centerP = new Point(cPX, cPY - 20); // move the ball away from the paddle.
                    this.setVelocity(this.gameEnvironment.getClosestCollision(trajectory).collisionObject()
                            .hit(this, this.gameEnvironment.getClosestCollision(trajectory).collisionPoint(),
                                    this.getVelocity())); // change the ball velocity.
                    return;
                }
            }
        }

        // If the ball can move without any collisions.
        if (this.gameEnvironment.getClosestCollision(trajectory) == null) {
            this.centerP = new Point(cPX + veloDX, cPY + veloDY);
        // If there is a collision, check the ball's movement in order to move it close to the collision point.
        } else {
            coliPX = this.gameEnvironment.getClosestCollision(trajectory).collisionPoint().getX();
            coliPY = this.gameEnvironment.getClosestCollision(trajectory).collisionPoint().getY();
            if (veloDX > 0 && veloDY < 0) {
                this.centerP = new Point(coliPX - radiusB * 0.1, coliPY + radiusB * 0.1);
            } else if (veloDX > 0 && veloDY > 0) {
                this.centerP = new Point(coliPX - radiusB * 0.1, coliPY - radiusB * 0.1);
            } else if (veloDX < 0 && veloDY < 0) {
                this.centerP = new Point(coliPX + radiusB * 0.1, coliPY + radiusB * 0.1);
            } else if (veloDX < 0 && veloDY > 0) {
                this.centerP = new Point(coliPX + radiusB * 0.1, coliPY - radiusB * 0.1);
            }
            // Set the ball's velocity (get it from the hit method).
            if (!this.gameEnvironment.getClosestCollision(trajectory).collisionObject().isItPaddle()) {
                notifyHit((Block) this.gameEnvironment.getClosestCollision(trajectory).collisionObject());
            }
            this.setVelocity(this.gameEnvironment.getClosestCollision(trajectory).collisionObject()
                    .hit(this, this.gameEnvironment.getClosestCollision(trajectory).collisionPoint(),
                            this.getVelocity()));
        }
    }

    /**
     * This function checks if the center of the ball is inside any rectangle (used specially for the paddle).
     * @return true/false if the ball is inside the rectangle.
     * @param rec the rectangle.
     */
    private boolean insideRec(Rectangle rec) {
        double cPX = centerP.getX();
        double cPY = centerP.getY();
        // Return true if the ball is inside the paddle boundaries, false otherwise.
        return cPX >= rec.getLeftBound().start().getX() && cPX <= rec.getRightBound().start().getX() && cPY
                >= rec.getUpperBound().start().getY() && cPY <= rec.getLowerBound().start().getY();
    }

    /**
     * This method is used to move the ball one step each time the time has passed using move one step.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * This method adds the ball to the sprite collection of the game.
     * @param game the class game (for initializing).
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * This method remove this ball from the game's sprites collection.
     * @param game current game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

    /**
     * This method adds a HitListener to the hit listeners of the ball.
     * @param hl a HitListener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * This method adds a HitListener to the hit listeners of the ba;;.
     * @param hl a HitListener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * This method notifies all the listeners about a hit event.
     * @param hitter the ball that hitted this block.
     */
    private void notifyHit(Block hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event.
        for (HitListener hl : listeners) {
            hl.hitEvent(hitter, this);
        }
    }
}
