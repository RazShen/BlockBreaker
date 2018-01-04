package drawable;
import java.util.ArrayList;
import biuoop.DrawSurface;
import interfaces.Sprite;

/**
 * This class features a collection of sprites, arranged in an array list. has a method to let them know the time
 * passed, and a method for drawing all of them.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04-29
 */
public class SpriteCollection {
    /**
     * The array list to store all the sprite objects.
     */
    private ArrayList<Sprite> listOfSprites;

    /**
     * This constructor creates an empty array list of sprite objects type.
     */
    public SpriteCollection() {
        this.listOfSprites = new ArrayList<Sprite>();
    }

    /**
     * This method adds a sprite to the list of sprites.
     * @param s a sprite to add.
     */
    public void addSprite(Sprite s) {
        listOfSprites.add(s);
    }

    /**
     * This method notify all of the sprites time has passed.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {
        ArrayList<Sprite> sprites = new ArrayList<Sprite>(this.listOfSprites);
        for (Sprite entity: sprites) {
            entity.timePassed(dt);
        }
    }

    /**
     * This method draws all the sprites.
     * @param d the given draw surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite entity: this.listOfSprites) {
            entity.drawOn(d);
        }
    }

    /**
     * This method removes sprite object from the sprite collection.
     * @param s sprite object.
     */
    public void removeSpirte(Sprite s) {
        this.listOfSprites.remove(s);
    }
}
