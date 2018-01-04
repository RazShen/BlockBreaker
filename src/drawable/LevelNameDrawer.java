package drawable;
import biuoop.DrawSurface;
import interfaces.Sprite;
import java.awt.Color;

/**
 * This class features the drawer for the level name.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05- 24
 */
public class LevelNameDrawer implements Sprite {
    /**
     * The name of the level.
     */
    private String levelName;

    /**
     * Constructor getting a string.
     * @param name level's name.
     */
    public LevelNameDrawer(String name) {
        this.levelName = name;
    }

    /**
     * Drawing the level's name.
     * @param d the draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(500,  18, "Level Name: " + this.levelName, 15);
    }

    /**
     * Irrelevant here.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}

