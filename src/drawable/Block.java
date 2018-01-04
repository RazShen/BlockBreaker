package drawable;
import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import interfaces.Collidable;
import interfaces.HitListener;
import interfaces.HitNotifier;
import interfaces.Sprite;
import collisiondata.Velocity;
import rungame.GameLevel;

/**
 * This class features a Block which holds a rectangle object and a color. also implements collidable
 * and sprite interfaces.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04- 30
 */
public class Block implements Collidable, Sprite, HitNotifier {
    /**
     * A rectangle object which the block represents.
     */
    private Rectangle blockRec;
    /**
     * The point for drawing the text on the block.
     */
    private Point drawPoint;
    /**
     * The number of available hits the block has.
     */
    private int hits;
    /**
     * The ArrayList of HitListeners.
     */
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    /**
     * The boolean if this block is a ball destroyer.
     */
    private boolean ballsDestroyer = false;
    /**
     * The boolean if this block is a boundary (used for printing frame).
     */
    private boolean isBoundary = false;
    /**
     * The boolean for block's border color.
     */
    private Color borderColor;
    /**
     * Boolean if should print border.
     */
    private Boolean hasBorder = false;
    /**
     * Map of hit points and blockColors.
     */
    private Map<Integer, Color> blockColors;
    /**
     * /**
     * Map of hit points and blockImages.
     */
    private Map<Integer, Image> blockImages;
    /**
     * If we can change the block (1 time).
     */
    private Boolean canBeChanged = true;
    /**
     * The original hit points for restarting the game.
     */
    private int originalHitPoints;

    /**
     * This constructor creates a default block.
     */
    public Block() {
        this.blockRec = new Rectangle(new Point(1, 1), 1, 1);
        this.blockColors = new TreeMap<Integer, Color>();
        this.blockImages = new TreeMap<Integer, Image>();
        this.borderColor = Color.BLACK;
        this.hasBorder = false;
        this.hits = 0;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * This constructor creates a default block with a position.
     * @param topLeftX x coordinate.
     * @param topLeftY y coordinate.
     */
    public Block(int topLeftX, int topLeftY) {
        this.blockRec = new Rectangle(new Point(1, 1), 1, 1);
        this.blockColors = new TreeMap<Integer, Color>();
        this.blockImages = new TreeMap<Integer, Image>();
        this.borderColor = Color.BLACK;
        this.hasBorder = false;
        this.hits = 0;
        this.hitListeners = new ArrayList<HitListener>();
        this.blockRec.setUpperLeftP(new Point(topLeftX, topLeftY));
    }

    /**
     * This constructor creates block from a rectangle and a specific color.
     * @param re a rectangle.
     * @param color color for drawing the block.
     */
    public Block(Rectangle re, Color color) {
        this.blockRec = re;
        this.drawPoint = new Point((int) (re.getUpperLeft().getX() + (re.getWidth() / 2)), (int) re.getUpperLeft()
                .getY() + (re.getHeight() / 1.5));
    }

    /**
     * This constructor creates block from a rectangle and a specific color, and a boolean if it's a ballDestroyer.
     * @param re a rectangle.
     * @param color color for drawing the block.
     * @param ballDestroyer boolean if it's a ballDestroyer.
     */
    public Block(Rectangle re, Color color, boolean ballDestroyer) {
        this.blockRec = re;
        this.drawPoint = new Point((int) (re.getUpperLeft().getX() + (re.getWidth() / 2)), (int) re.getUpperLeft()
                .getY() + (re.getHeight() / 1.5));
        this.ballsDestroyer = ballDestroyer;
        this.isBoundary = true;
    }

    /**
     * This constructor creates block from a rectangle and a specific color, and a boolean if it's a ballDestroyer,
     * and a boolean if it's a boundary.
     * @param re a rectangle.
     * @param color color for drawing the block.
     * @param ballDestroyer boolean if it's a ballDestroyer.
     * @param boundary boolean if it's a boundary.
     */
    public Block(Rectangle re, Color color, boolean ballDestroyer, boolean boundary) {
        this.blockRec = re;
        this.drawPoint = new Point((int) (re.getUpperLeft().getX() + (re.getWidth() / 2)), (int) re.getUpperLeft()
                .getY() + (re.getHeight() / 1.5));
        this.isBoundary = boundary;
    }

    /**
     * This constructor creates block from a rectangle and a specific color, also gets the number of hits.
     * @param re a rectangle.
     * @param color color for drawing the block.
     * @param hitNum number of hits.
     * @param hitListenersList list of hitListeners.
     */
    public Block(Rectangle re, Color color, int hitNum, List<HitListener> hitListenersList) {
        this.hits = hitNum;
        this.blockRec = re;
        this.drawPoint = new Point((int) (re.getUpperLeft().getX() + (re.getWidth() / 2)), (int) re.getUpperLeft()
                .getY() + (re.getHeight() / 1.5));
        this.hitListeners.addAll(hitListenersList);
    }

    /**
     * This constructor creates block from a rectangle and a specific color, also gets the number of hits.
     * @param re a rectangle.
     * @param color color for drawing the block.
     * @param hitNum number of hits.
     */
    public Block(Rectangle re, Color color, int hitNum) {
        this.hits = hitNum;
        this.drawPoint = new Point((int) (re.getUpperLeft().getX() + (re.getWidth() / 2)), (int) re.getUpperLeft()
                .getY() + (re.getHeight() / 1.5));
    }


    /**
     * This method is used to set the number of hits written on the block.
     * @param total hits the ball can hit the block until 'X' is printed on the block.
     */
    public void setHits(int total) {
        this.hits = total;
    }

    /**
     * This method returns the rectangle which the block represents.
     * @return the rectangle member of the block.
     */
    public Rectangle getCollisionRectangle() {
        return this.blockRec;
    }

    /**
     * This method "informs" the block object it has been collided with the ball and it returns the new velocity after
     * the collision (by checking which side of the block the ball has collided with).
     * @param collisionPoint the collision point.
     * @param currentVelocity current velocity of the ball.
     * @param hitter the ball that hitted this block.
     * @return new velocity after the collision.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Reduce the number of available hits the block has (which is printed on the block).
        Velocity returnedVelo = currentVelocity;
        // If the ball hits the left side or the right side.
        if (this.isOnLeft(collisionPoint) || this.isOnRight(collisionPoint)) {
            returnedVelo = new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
        }
        // If the ball hits the bottom or the top.
        if (this.isOnBottom(collisionPoint) || this.isOnTop(collisionPoint)) {
            returnedVelo = new Velocity(returnedVelo.getDX(), -returnedVelo.getDY());
        }
        //Notify there was a hit.
        this.notifyHit(hitter);
        this.hits -= 1;
        return returnedVelo;
    }

    /**
     * This method checks if the collision point is on the top of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the top of the rectangle.
     */
    private boolean isOnTop(Point collisionPoint) {
        return (collisionPoint.getY() == this.blockRec.getUpperBound().start().getY());
    }

    /**
     * This method checks if the collision point is on the bottom of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the bottom of the rectangle.
     */
    private boolean isOnBottom(Point collisionPoint) {
        return (collisionPoint.getY() == this.blockRec.getLowerBound().start().getY());
    }

    /**
     * This method checks if the collision point is on the left edge of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the left edge of the rectangle.
     */
    private boolean isOnLeft(Point collisionPoint) {
        return (collisionPoint.getX() == this.blockRec.getLeftBound().start().getX());
    }

    /**
     * This method checks if the collision point is on the right edge of the rectangle.
     * @param collisionPoint of the ball and the rectangle.
     * @return true/false if the collision point is on the right edge of the rectangle.
     */
    private boolean isOnRight(Point collisionPoint) {
        return (collisionPoint.getX() == this.blockRec.getRightBound().start().getX());
    }

    /**
     * This method returns the color of the block.
     * @return block's color.
     */
    public Map<Integer, Color> getColor() {
        return this.blockColors;
    }

    /**
     * This method draws the block (a filled rectangle with a black frame and the number of hits inside).
     * @param surface - the drawing surface.
     */
    public void drawOn(DrawSurface surface) {
        this.drawCheck(surface);
        if (this.isBoundary) {
            surface.setColor(Color.GRAY);
            // Draw the rectangle (filled with color).
            surface.fillRectangle((int) this.blockRec.getUpperLeft().getX(), (int) this.blockRec.getUpperLeft().getY(),
                (int) this.blockRec.getWidth(), (int) this.blockRec.getHeight());
        }
    }

    /**
     * Do nothing in this assignment.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }

    /**
     * This method adds the block to sprite collection and the collidable object of the game environment of the game.
     * @param game the class game (for initializing).
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * This method removes the block from the game sprites and collidable.
     * @param game the game this block is used in.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     * This method adds a HitListener to the hit listeners of the block.
     * @param hl a HitListener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * This method adds a HitListener to the hit listeners of the block.
     * @param hl a HitListener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * This method notifies all the listeners about a hit event.
     * @param hitter the ball that hitted this block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event.
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * This method returns the hit points of the block.
     * @return hit points of the ball.
     */
    public int getHitPoints() {
        return this.hits;
    }

    /**
     * This method returns if this block is a paddle.
     * @return true/false if this block is a paddle (always false).
     */
    public boolean isItPaddle() {
        return false;
    }

    /**
     * This method returns if this block is a ball destroyer.
     * @return true/false if this block is a ball destroyer.
     */
    public boolean isBallsDestroyer() {
        return this.ballsDestroyer;
    }

    /**
     * This method sets the border color.
     * @param color border color.
     */
    public void setBorderColor(Color color) {
        this.borderColor = color;
        this.hasBorder = true;
    }

    /**
     * This method copies other block values to this block values, also set x and y coordinates.
     * @param copyFromBlock block to copy from.
     * @param xVal x value.
     * @param yVal y value.
     */
    public void getProperties(Block copyFromBlock, int xVal, int yVal) {
        this.blockColors = copyFromBlock.blockColors;
        this.setWidth((int) copyFromBlock.getCollisionRectangle().getWidth());
        this.setHeight((int) copyFromBlock.getCollisionRectangle().getHeight());
        this.blockImages = copyFromBlock.blockImages;
        this.borderColor = copyFromBlock.borderColor;
        this.hasBorder = copyFromBlock.hasBorder;
        this.hits = copyFromBlock.hits;
        this.originalHitPoints = this.hits;
    }

    /**
     * This method sets the block's hit points back after restarting a game.
     */
    public void setBackHitPoint() {
        this.hits = this.originalHitPoints;
    }

    /**
     * Set method sets the block's width.
     * @param width block's width.
     */
    public void setWidth(int width) {
        this.blockRec = new Rectangle(this.blockRec.getUpperLeft(), width, this.blockRec.getHeight());
    }

    /**
     * Set method sets the block's height.
     * @param height block's height.
     */
    public void setHeight(int height) {
        this.blockRec = new Rectangle(this.blockRec.getUpperLeft(), this.blockRec.getWidth(), height);
    }

    /**
     * This method sets the block's upper left point.
     * @param p upper left point.
     */
    public void setUpperLeft(Point p) {
        if (this.canBeChanged) {
            this.blockRec.setUpperLeftP(p);
        }
        this.canBeChanged = false;
    }

    /**
     * This method sets the block colors map.
     * @param filling block colors map.
     */
    public void setBlockColors(Map<Integer, Color> filling) {
            this.blockColors = filling;
    }

    /**
     * This method sets the block images map.
     * @param filling block images map.
     */
    public void setBlockImages(Map<Integer, Image> filling) {
        this.blockImages = filling;
    }

    /**
     * This method checks if the block is a boundary, if not it draws it's properties according to it's values.
     * @param surface draw surface.
     */
    public void drawCheck(DrawSurface surface) {
        if (!this.isBoundary) {
            if (this.blockImages.containsKey(this.hits)) {
                surface.drawImage((int) Math.round(this.blockRec.getUpperLeft().getX()), (int) Math.round(this.blockRec
                                .getUpperLeft().getY()),
                        this.blockImages.get(this.hits));
            } else if (this.blockColors.containsKey(this.hits)) {
                surface.setColor(this.blockColors.get(this.hits));
                surface.fillRectangle(
                        (int) Math.round(this.blockRec.getUpperLeft().getX()),
                        (int) Math.round(this.blockRec.getUpperLeft().getY()),
                        (int) Math.round(this.blockRec.getWidth()),
                        (int) Math.round(this.blockRec.getHeight()));
            } else if (this.blockImages.containsKey(0)) {
                surface.drawImage((int) Math.round(this.blockRec.getUpperLeft().getX()),
                        (int) Math.round(this.blockRec.getUpperLeft().getY()),
                        this.blockImages.get(0));
            } else if (this.blockColors.containsKey(0)) {
                surface.setColor(this.blockColors.get(0));
                surface.fillRectangle(
                        (int) Math.round(this.blockRec.getUpperLeft().getX()),
                        (int) Math.round(this.blockRec.getUpperLeft().getY()),
                        (int) Math.round(this.blockRec.getWidth()),
                        (int) Math.round(this.blockRec.getHeight()));
            }
            if (this.hasBorder) {
                surface.setColor(this.borderColor);
                surface.drawRectangle(
                        (int) Math.round(this.blockRec.getUpperLeft().getX()),
                        (int) Math.round(this.blockRec.getUpperLeft().getY()),
                        (int) Math.round(this.blockRec.getWidth()),
                        (int) Math.round(this.blockRec.getHeight()));
            }
        }
    }
}
