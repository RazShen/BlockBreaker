package rungame;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collisiondata.BallRemover;
import collisiondata.Counter;
import collisiondata.GameEnvironment;
import drawable.LivesIndicator;
import drawable.Paddle;
import drawable.LevelNameDrawer;
import drawable.ScoreIndicator;
import drawable.SpriteCollection;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import interfaces.Animation;
import interfaces.Collidable;
import interfaces.LevelInformation;
import interfaces.Sprite;
import drawable.Block;
import collisiondata.BlockRemover;
import drawable.Ball;
import collisiondata.ScoreTrackingListener;
import java.util.ArrayList;
import java.awt.Color;
import java.util.List;


/**
 * This class features a GameLevel object that initializing all the object in the game (blocks, pedal, draw surface,
 * gui and balls) and after initializing, it draws all of them on the draw surface, and moving the balls constantly.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-24
 */
public class GameLevel implements Animation {
    private SpriteCollection spriteCollection;
    private GameEnvironment environment;
    private Counter decreasingBlockCounter;
    private Counter decreasingBallCounter;
    private Counter score;
    private Counter livesCounter;
    private ScoreIndicator scoreIndicator;
    private Paddle gamePaddle;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation levelInformation;
    private BallRemover ballRemover;
    private LivesIndicator livesIndicator;

    /**
     * This constructor creates a game from level information, keyboard, animation runner, and live and score counters.
     * @param levelInformation level information.
     * @param animationRunner animation runner.
     * @param keyboard keyboard.
     * @param livesCounter counter for lives.
     * @param scoreCounter counter for score.
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor keyboard, AnimationRunner animationRunner,
                     Counter livesCounter, Counter scoreCounter) {
        this.spriteCollection = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.decreasingBlockCounter = new Counter();
        this.decreasingBallCounter = new Counter();
        this.score = scoreCounter;
        this.livesCounter = livesCounter;
        this.scoreIndicator = new ScoreIndicator(score);
        this.levelInformation = levelInformation;
        this.keyboard = keyboard;
        this.runner = animationRunner;
        this.livesIndicator = new LivesIndicator(this.livesCounter);
    }

    /**
     * This method adds a collidable object to the game environment.
     * @param c a collidable object.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * This method adds a sprites to the list of sprites (sprite collection).
     * @param s a sprite to add.
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.addSprite(s);
    }

    /**
     * This method initializes the game objects- it creates the blocks (borders and game blocks) ,balls and paddle.
     */
    public void initialize() {
        this.addSprite(this.levelInformation.getBackground());
        this.decreasingBlockCounter.increase(this.levelInformation.numberOfBlocksToRemove());
        this.gamePaddle = createPaddle();
        gamePaddle.addToGame(this);
        this.addSprite(this.scoreIndicator);
        this.addBoundariesAndBlocks();
        this.addSprite(new LevelNameDrawer(this.levelInformation.levelName()));
        this.addSprite(this.livesIndicator);
    }

    /**
     * This method runs the game by creating a drawing surface and using the sprite method for drawing and notifying
     * the sprite objects (ball, paddle, blocks) the time has passed.
     */
    public void playOneTurn() {
        this.gamePaddle.centerPaddle();
        // Create the balls.
        addBalls();
        if (this.livesCounter.getValue() > 0) {
            this.runner.run(new CountdownAnimation(2, 3, this.spriteCollection));
        }
        this.running = true;
        this.runner.run(this);
    }


    /**
     * This method remove a collidable object from the game environment of this game.
     * @param c collidable object.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeColilidable(c);
    }

    /**
     * This method removes sprite object from the sprite collection of this game.
     * @param s sprite object.
     */
    public void removeSprite(Sprite s) {
        this.spriteCollection.removeSpirte(s);
    }

    /**
     * Boolean if the game should stop.
     * @return true/false.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * This method prints all the sprites and the game and decide if the game should stop.
     * @param d draw surface.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.spriteCollection.drawAllOn(d);
        this.spriteCollection.notifyAllTimePassed(dt);
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.runner.getGui().getKeyboardSensor(),
                    "space", new PauseScreen()));
        }
        if (decreasingBlockCounter.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        } else if (this.livesCounter.getValue() == 0) {
            this.running = false;
        } else if (decreasingBallCounter.getValue() == 0) {
            this.running = false;
            this.livesCounter.decrease(1);
        }
    }

    /**
     * This method creates a paddle.
     * @return new paddle.
     */
    public Paddle createPaddle() {
        return new Paddle(this.levelInformation, this.runner.getGui());
    }

    /**
     * This method creates the boundaries, the death block and adding all the level info blocks to the game.
     */
    public void addBoundariesAndBlocks() {
        BlockRemover blockRemover = new BlockRemover(this, this.decreasingBlockCounter);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        ArrayList<Block> boundaries = new ArrayList<Block>();
        boundaries.add(new Block(new Rectangle(new Point(0, 20), 800, 20), Color.GRAY,
                false, true));
        boundaries.add(new Block(new Rectangle(new Point(0, 40), 25, 560), Color.GRAY,
                false, true));
        Block ballsDeathBlock = new Block(new Rectangle(new Point(20, 600), 760, 20), Color.GRAY,
                true);
        boundaries.add(ballsDeathBlock);
        boundaries.add(new Block(new Rectangle(new Point(775, 40), 25, 560), Color.GRAY,
                false, true));
        // Add the boundaries to the game.
        for (Block boundary: boundaries) {
            boundary.addToGame(this);
        }
        for (Block block: this.levelInformation.blocks()) {
            block.setBackHitPoint();
            block.addToGame(this);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
        }
    }

    /**
     * This method adds balls to the game.
     */
    public void addBalls() {
        this.decreasingBallCounter.increase(this.levelInformation.numberOfBalls());
        this.ballRemover = new BallRemover(this, this.decreasingBallCounter);
        List<Ball> ballsArr = new ArrayList<Ball>(this.decreasingBallCounter.getValue());
        for (int i = 0; i < this.decreasingBallCounter.getValue(); i++) {
            ballsArr.add(new Ball(400, 560, 5, Color.WHITE, this.ballRemover));
        }
        for (int j = 0; j < ballsArr.size(); j++) {
            ballsArr.get(j).setGameEnvironment(this.environment);
            ballsArr.get(j).addToGame(this);
            ballsArr.get(j).setVelocity(this.levelInformation.getBallVelocities().get(j));
        }
    }

    /**
     * This method checks if there are any blocks left.
     * @return true/false.
     */
    public boolean areThereBlocksLeft() {
        if (this.decreasingBlockCounter.getValue() > 0) {
            return true;
        }
        return false;
    }
}
