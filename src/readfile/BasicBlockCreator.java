package readfile;
import drawable.Block;
import interfaces.BlockCreator;

/**
 * this class features a basic block creator class, which creates a block from x and y coordintes.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */
public class BasicBlockCreator implements BlockCreator {
    /**
     * Empty constructor.
     */
    public BasicBlockCreator() {

    }

    /**
     * This method creates a new block from x and y coordinates.
     * @param xpos x coordinate.
     * @param ypos y coordinate.
     * @return new block.
     */
    public Block create(int xpos, int ypos) {
        return new Block(xpos, ypos);
    }
}
