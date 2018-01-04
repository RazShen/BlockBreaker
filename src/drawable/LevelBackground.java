package drawable;
import biuoop.DrawSurface;
import interfaces.Sprite;
import java.awt.Color;
import java.awt.Image;

/**
 * This class features a game level background.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-19
 */
public class LevelBackground implements Sprite {
    /**
     * Background backgroundImage.
     */
    private Image backgroundImage;
    /**
     * Background color.
     */
    private Color backgroundColor;
    /**
     * If the background is an image.
     */
    private boolean isImage;

     /**
     * This constructor creates background from image.
     * @param image the given background Image.
     */
    public LevelBackground(Image image) {
        this.backgroundImage = image;
        this.isImage = true;
    }

    /**
     * This constructor creates background from color.
     * @param color the given background color.
     */
    public LevelBackground(Color color) {
        this.backgroundColor = color;
        this.isImage = false;
    }

    /**
     * this method draws the background.
     * @param d draw surface.
     */
    public void drawOn(DrawSurface d) {
        if (this.isImage) {
            d.drawImage(0, 0, this.backgroundImage);
        } else {
            d.setColor(this.backgroundColor);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }
    }

    /**
     * Irrelevant here.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }

}

