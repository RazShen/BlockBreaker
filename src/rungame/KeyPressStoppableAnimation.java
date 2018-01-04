package rungame;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * This class features an animation which stops by pressing a specific key.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */

public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private String pauseKey;
    private Animation thisAnimation;
    private boolean isAlreadyPressed;

    /**
     * This constructor creates an animation which stops by pressing a specific key from keyboard, a key for stopping
     * and an animation.
     * @param sensor keyboard.
     * @param key for stopping
     * @param animation animation.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.pauseKey = key;
        this.thisAnimation = animation;
        this.isAlreadyPressed = true;
    }

    /**
     * This doOneFrame method uses the doOneFrame of the animation and also check if the specific key was pressed since
     * this object was created.
     * @param d draw surface.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.thisAnimation.doOneFrame(d, dt);
        if (this.keyboard.isPressed(this.pauseKey) && !this.isAlreadyPressed) {
            this.stop = true;
        }
        if (!this.keyboard.isPressed(this.pauseKey)) {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * Boolean if should stop printing the pause screen.
     * @return true/false.
     */
    public boolean shouldStop() { return this.stop; }
}
