package rungame;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import interfaces.Animation;

/**
 * This class features an animation runner object, which run an animation object.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-24
 */
public class AnimationRunner {
    /**
     * Animation runner gui.
     */
    private GUI gui;
    /**
     * Animation runner fps.
     */
    private int framesPerSecond;
    /**
     * Animation runner sleeper.
     */
    private Sleeper sleeper;
    /**
     * DtValue is the amount of seconds passed since the last call.
     */
    private double dtValue;

    /**
     * The constructor for the animation runner gets a number which is fps.
     * @param fps frames per second.
     */
    public AnimationRunner(int fps) {
        this.framesPerSecond = fps;
        this.sleeper = new Sleeper();
        this.gui = new GUI("Arkanoid", 800, 600);
        this.dtValue = 1.0 / this.framesPerSecond;
    }

    /**
     * The running method, runs an animation object by using it's doOneFrame method and should stop boolean.
     * @param animation object.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            animation.doOneFrame(d, this.dtValue);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Return the animation's runner gui.
     * @return the gui.
     */
    public GUI getGui() {
        return this.gui;
    }
}