package interfaces;
import drawable.Block;

/**
 * This interface creates a block from x and y coordinates.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-12
 */
public interface BlockCreator {
    /**
     * This method creates a block at the specified location.
     * @param xpos x coordinate.
     * @param ypos y coordinate.
     * @return new block.
     */
    Block create(int xpos, int ypos);
}