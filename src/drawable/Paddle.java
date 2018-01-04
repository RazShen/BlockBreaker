package drawable;
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.GUI;
import geometryprimitives.Line;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import interfaces.LevelInformation;
import interfaces.Sprite;
import interfaces.Collidable;
import collisiondata.Velocity;
import rungame.GameLevel;

/**
 * This class features a drawable.Paddle which is a block that has a keyboard sensor and a different hit method.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04- 30
 */
public class Paddle implements Sprite, Collidable {
    /**
     * The paddle's keyboard sensor for controlling it.
     */
    private KeyboardSensor keyboard;
    /**
     * The paddle's block.
     */
    private Block paddleBlock;
    /**
     * Paddle's speed.
     */
    private int speed;
    /**
     * Paddle's width.
     */
    private int width;
    /**
     * Paddle's center point.
     */
    private Point centerP;

    /**
     * This constructor sets a paddle from a gui (needed for the keyboard sensor). also it sets the paddle's block.
     * @param gui the inputted graphic user interface.
     */
    public Paddle(GUI gui) {
        this.keyboard = gui.getKeyboardSensor();
        this.paddleBlock = new Block(new Rectangle(new Point(400, 570), 100, 20), Color.ORANGE);
        this.speed = 10;
        this.width = 50;
    }

    /**
     * This constructor sets a paddle from level information and gui.
     * @param levelInformation the level information.
     * @param gui the inputted graphic user interface.
     */
    public Paddle(LevelInformation levelInformation, GUI gui) {
        this.keyboard = gui.getKeyboardSensor();
        this.speed = levelInformation.paddleSpeed();
        this.width = levelInformation.paddleWidth();
        this.centerP = new Point(400, 570);
        this.paddleBlock = new Block(new Rectangle(new Point(centerP.getX() - (this.width / 2),
                570), this.width, 20), Color.ORANGE);
    }

    /**
     * This method moves the paddle left by changing it's upper left point).
     * @param dt is the amount of seconds passed since the last call.
     */
    public void moveLeft(double dt) {
        int movement = (int) -(this.speed * dt);
        if (this.paddleBlock.getCollisionRectangle().getUpperLeft().getX() + movement >= 20) {
            this.paddleBlock.getCollisionRectangle().setUpperLeftP(movement);
        }
    }

    /**
     * This method moves the paddle right by changing it's upper left point).
     * @param dt is the amount of seconds passed since the last call.
     */
    public void moveRight(double dt) {
        int movement = (int) (this.speed * dt);
        if (this.paddleBlock.getCollisionRectangle().getUpperLeft().getX() + this.width + movement <= 780) {
            this.paddleBlock.getCollisionRectangle().setUpperLeftP(movement);
        }
    }

    /**
     * This method is a part of the sprite interface, every time the game's sprite collection notify all it's objects
     * that time has passed this method checks if the user pressed the left key or the right key on the keyboard.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
    }

    /**
     * This method draws the paddle on the draw surface (a rectangle with a frame).
     * @param d the draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        // Draw the rectangle (filled with color)
        d.setColor(Color.ORANGE);
        d.fillRectangle((int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.paddleBlock.getCollisionRectangle().getWidth(),
                (int) this.paddleBlock.getCollisionRectangle().getHeight());
        d.setColor(Color.BLACK);
        // Draw the rectangle's frame.
        d.drawRectangle((int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.paddleBlock.getCollisionRectangle().getWidth(),
                (int) this.paddleBlock.getCollisionRectangle().getHeight());
    }

    /**
     * This method is part of the collidable interface.
     * @return this paddle's rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.paddleBlock.getCollisionRectangle();
    }

    /**
     * This method returns new velocity after the ball hit the paddle.
     * @param collisionPoint collision point between the objects.
     * @param currentVelocity the current velocity of the object colliding with the paddle.
     * @param hitter hitting ball.
     * @return new velocity of the object after hitting the paddle.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Point upperLeft = this.paddleBlock.getCollisionRectangle().getUpperLeft();
        double upperX = upperLeft.getX();
        double upperY = upperLeft.getY();
        double coliX = collisionPoint.getX();
        if (this.isOnTop(collisionPoint)) {
            int lengthOfTop = (int) this.paddleBlock.getCollisionRectangle().getWidth();
            // If the ball didn't collide with the side of the paddle find out which part of the paddle it did
            // collide with and accordingly return the new velocity.
            Line firstPartPaddle = new Line(upperLeft, new Point(upperX + lengthOfTop / 5, upperY));
            Line secondPartPaddle = new Line(new Point(upperX + lengthOfTop / 5, upperY),
                    new Point(upperX + (2 * lengthOfTop) / 5, upperY));
            Line thirdPartPaddle = new Line(new Point(upperX + (2 * lengthOfTop) / 5, upperY),
                    new Point(upperX + (3 * lengthOfTop) / 5, upperY));
            Line fourthPartPaddle = new Line(new Point(upperX + (3 * lengthOfTop) / 5, upperY),
                    new Point(upperX + (4 * lengthOfTop) / 5, upperY));
            Line fifthPartPaddle = new Line(new Point(upperX + (4 * lengthOfTop) / 5, upperY),
                    new Point(upperX + (5 * lengthOfTop) / 5, upperY));
            // First part of the paddle.
            if (coliX >= firstPartPaddle.start().getX() && coliX < firstPartPaddle.end().getX()) {
                return Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
                // Second part of the paddle.
            } else if (coliX >= secondPartPaddle.start().getX() && coliX < secondPartPaddle.end().getX()) {
                return Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
                // Third (middle) part of the paddle.
            } else if (coliX >= thirdPartPaddle.start().getX() && coliX < thirdPartPaddle.end().getX()) {
                return new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
                // Fourth part of the paddle.
            } else if (coliX >= fourthPartPaddle.start().getX() && coliX < fourthPartPaddle.end().getX()) {
                return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
                // Fifth part of the paddle.
            } else if (coliX >= fifthPartPaddle.start().getX() && coliX <= fifthPartPaddle.end().getX()) {
                return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
                // If it didn't collide the paddle at all then something is wrong with the collision points.
            } else {
                throw new RuntimeException("Something's wrong with the collision points");
            }
        }
        if (this.isOnBottom(collisionPoint)) {
            return new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
        }
        if (this.isOnLeft(collisionPoint) || this.isOnRight(collisionPoint)) {
            return new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
        }
        return null;
    }

    /**
     * This method checks if the collision point is on the top of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the top of the rectangle.
     */
    private boolean isOnTop(Point collisionPoint) {
        return (collisionPoint.getY() == this.paddleBlock.getCollisionRectangle().getUpperBound().start().getY());
    }

    /**
     * This method checks if the collision point is on the bottom of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the bottom of the rectangle.
     */
    private boolean isOnBottom(Point collisionPoint) {
        return (collisionPoint.getY() == this.paddleBlock.getCollisionRectangle().getLowerBound().start().getY());
    }

    /**
     * This method checks if the collision point is on the left edge of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the left edge of the rectangle.
     */
    private boolean isOnLeft(Point collisionPoint) {
        return (collisionPoint.getX() == this.paddleBlock.getCollisionRectangle().getLeftBound().start().getX());
    }

    /**
     * This method checks if the collision point is on the right edge of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the right edge of the rectangle.
     */
    private boolean isOnRight(Point collisionPoint) {
        return (collisionPoint.getX() == this.paddleBlock.getCollisionRectangle().getRightBound().start().getX());
    }

    /**
     * This method adds the paddle to the game's sprites and collidables.
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * This method returns true (used for finding if the ball is inside the paddle).
     * @return true.
     */
    public boolean isItPaddle() {
        return true;
    }

    /**
     * This method centers the paddle by it's center point.
     */
    public void centerPaddle() {
        this.getCollisionRectangle().centerRectangle((int) centerP.getX() - (this.width / 2));
    }
}
