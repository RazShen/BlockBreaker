package readfile;
import drawable.Block;
import java.util.Map;
import java.util.TreeMap;

/**
 * this class features a block factory (specific block for each symbol).
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */
public class BlocksFromSymbolsFactory  {
    private Map<String, Block> blocks;
    private Map<String, Integer> spacerWidths;

    /**
     * This constructor creates a default block factory.
     */
    public BlocksFromSymbolsFactory() {
        this.blocks = new TreeMap<String, Block>();
        this.spacerWidths = new TreeMap<String, Integer>();
    }

    /**
     * This constructor creates a block factory from map of blocks and spacers.
     * @param blocks map.
     * @param spacerWidths map.
     */
    public BlocksFromSymbolsFactory(Map<String, Block> blocks, Map<String, Integer> spacerWidths) {
        this.blocks = blocks;
        this.spacerWidths = spacerWidths;
    }

    /**
     * This method returns the width of a spacer (from the spacer's map).
     * @param s inputted spacer.
     * @return the spacer width.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * This method checks if the spacer is inside the spacer map.
     * @param s inputted spacer..
     * @return true/false.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * This method checks if there is a block with string s as it's key in the blocks map.
     * @param s inputted string.
     * @return true/false.
     */
    public boolean isBlockSymbol(String s) {
        return blocks.containsKey(s);
    }

    /**
     * This method returns a block with the properties of block with key s in the blocks map.
     * @param s inputted string.
     * @param x coordinate.
     * @param y coordinate.
     * @return block with the properties of block with key s in the blocks map.
     */
    public Block getBlock(String s, int x, int y) {
        // Create a new block.
        BasicBlockCreator blockCreator = new BasicBlockCreator();
        Block block = blockCreator.create(x, y);
        // Copy values from block inside the factory with the key s.
        block.getProperties(this.blocks.get(s), x, y);
        return block;
    }

    /**
     * Adds a key (string) and value (block) to the blocks map.
     * @param string inputted string.
     * @param block inputted block.
     */
    public void putBlock(String string, Block block) {
        this.blocks.put(string, block);
    }

    /**
     * Adds a key (string) and value (int) to the spacer's map.
     * @param string inputted string.
     * @param num inputted spacer width.
     */
    public void putSpaceWidth(String string, Integer num) {
        this.spacerWidths.put(string, num);
    }
}
