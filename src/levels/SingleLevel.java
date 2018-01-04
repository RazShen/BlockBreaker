package levels;
import drawable.Block;
import java.util.ArrayList;
import java.util.List;
import interfaces.LevelInformation;
import interfaces.Sprite;
import collisiondata.Velocity;

/**
 * This class features a single level object.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */

public class SingleLevel implements LevelInformation {
    private String name;
    private Sprite background;
    private Integer numberOfBalls;
    private Integer numberOfBlocksToRemove;
    private List<Block> blocks;
    private List<Velocity> ballsVelocities;
    private Integer paddleSpeed;
    private Integer paddleWidth;
    private Integer blocksStartX;
    private Integer blocksStartY;
    private Integer rowHeight;

    /**
     * This constructor creates a default single level.
     */
    public SingleLevel() {
        this.name = null;
        this.background = null;
        this.numberOfBalls = null;
        this.numberOfBlocksToRemove = null;
        this.blocks = new ArrayList<Block>();
        this.ballsVelocities = new ArrayList<Velocity>();
        this.paddleSpeed = null;
        this.paddleWidth = null;
        this.blocksStartX = null;
        this.blocksStartY = null;
        this.rowHeight = null;
    }
    /**
     * Get level's name.
     * @return the level name.
     */
    public String levelName() {
        return this.name;
    }

    /**
     * Set level's name.
     * @param inputName the inputted name.
     */
    public void setName(String inputName) {
        this.name = inputName;
    }

    /**
     * Get the level's background.
     * @return level's background.
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Set the level's background.
     * @param s the given sprite.
     */
    public void setBackground(Sprite s) {
        this.background = s;
    }

    /**
     * returns the number of balls.
     * @return the number of balls.
     */
    public Integer numberOfBalls() {
        return this.numberOfBalls;
    }

    /**
     * Get the block list of the level.
     * @return the block list of the level.
     */
    public List<Block> getBlockList() {
        return this.blocks;
    }

    /**
     * Sets the level's number of balls.
     * @param numOfBalls the number of balls.
     */
    public void setNumberOfBalls(int numOfBalls) {
        this.numberOfBalls = numOfBalls;
    }

    /**
     * Get the number of blocks to remove in order to finish.
     * @return the number of blocks to remove in order to finish.
     */
    public Integer numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }

    /**
     * Sets the level's number of blocks to remove in order to finish.
     * @param numOfBlocks number of blocks to remove in order to finish.
     */
    public void setNumberOfBlocksToRemove(int numOfBlocks) {
        this.numberOfBlocksToRemove = numOfBlocks;
    }

    /**
     * Get the block list of the level.
     * @return the block list of the level.
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Get the level's block list.
     * @param blocksList the inputted level's block list.
     */
    public void setBlocks(List<Block> blocksList) {
        this.blocks = blocksList;
    }

    /**
     * This method adds blocks to the level's block list.
     * @param block to add.
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    /**
     * Get the balls velocities list.
     * @return the balls velocities list.
     */
    public List<Velocity> getBallVelocities() {
        return this.ballsVelocities;
    }

    /**
     * This method sets the balls velocities list.
     * @param velocities the balls velocities list.
     */
    public void setBallsVelocities(List<Velocity> velocities) {
        this.ballsVelocities = velocities;
    }

    /**
     * This method adds a velocity to the balls velocities list.
     * @param velocity inputted velocity.
     */
    public void addBallVelocity(Velocity velocity) {
        this.ballsVelocities.add(velocity);
    }

    /**
     * Get paddle's speed.
     * @return the paddle's speed.
     */
    public Integer paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Set paddle's speed.
     * @param speed inputted speed.
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * Get paddle's width.
     * @return paddle's width.
     */
    public Integer paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Set paddle's width.
     * @param width paddle's width.
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }

    /**
     * Get the block's start Y position.
     * @return block's start Y position.
     */
    public Integer blocksStartY() {
        return this.blocksStartY;
    }

    /**
     *  Set the block's start Y position.
     * @param y block's start Y position.
     */
    public void setBlocksStartY(int y) {
        this.blocksStartY = y;
    }

    /**
     * Get the block's start X position.
     * @return block's start X position.
     */
    public Integer blocksXPos() {
        return this.blocksStartX;
    }

    /**
     * Set the block's start X position.
     * @param x the x coordinate.
     */
    public void setBlocksXPos(int x) {
        this.blocksStartX = x;
    }

    /**
     * Get the level's row height.
     * @return level's row height.
     */
    public Integer rowHeight() {
        return this.rowHeight;
    }

    /**
     * Set the level's row height.
     * @param height level's row height.
     */
    public void setRowHeight(int height) {
        this.rowHeight = height;
    }

    /**
     * This method check if all the level's values aren't null.
     * @return true if all the level's values aren't null, false otherwise.
     */
    public boolean checkLevelValues() {
        if (this.levelName() == null || this.numberOfBalls() == null || this.getBackground() == null
                || this.numberOfBlocksToRemove() == null || this.paddleSpeed() == null || this.paddleWidth() == null
                || this.blocksXPos() == null || this.blocksStartY() == null || this.rowHeight() == null) {
            return false;
        }
        return true;
    }
}
