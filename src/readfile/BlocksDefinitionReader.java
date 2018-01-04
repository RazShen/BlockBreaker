package readfile;
import drawable.Block;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

/**
 * this class features a block definition reader, that creates block factory (specific block for each symbol).
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */
public class BlocksDefinitionReader {
    /**
     * Creating a new BlockFromSymbolsFactory from the reader.
     * @param reader inputted reader.
     * @return a new BlockFromSymbolsFactory.
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader)  {
        String singleLine;
        BlocksFromSymbolsFactory blockFactory = new BlocksFromSymbolsFactory();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        int lineNum = lineNumberReader.getLineNumber();
        Map<String, String> tryMap = new TreeMap<String, String>();
        Map<String, String> defaultValues = new TreeMap<String, String>();
        Map<String, String> valuesOfBlocks = new TreeMap<String, String>();
        // Reading the block definition file.
        try {
            while ((singleLine = lineNumberReader.readLine()) != null) {
                singleLine = singleLine.trim();
                if (!singleLine.equals("") && !singleLine.startsWith("#")) {
                    if (singleLine.startsWith("default")) {
                        singleLine = singleLine.substring("default".length()).trim();
                        defaultValues = getLineValues(singleLine, lineNum);
                    } else if (singleLine.startsWith("bdef")) {
                        tryMap.clear();
                        singleLine = singleLine.substring("bdef".length()).trim();
                        tryMap = getLineValues(singleLine, lineNum);
                        valuesOfBlocks.clear();
                        valuesOfBlocks.putAll(defaultValues);
                        //Overrides the default values.
                        valuesOfBlocks.putAll(tryMap);
                        if (valuesOfBlocks.containsKey("symbol") && valuesOfBlocks.containsKey("width")
                                && valuesOfBlocks.containsKey("height") && valuesOfBlocks.containsKey("hit_points")) {
                            String symbol = valuesOfBlocks.get("symbol");
                            Block block = new Block();
                            try {
                                if (Integer.valueOf(valuesOfBlocks.get("width")) <= 0) {
                                    throw new Exception();
                                } else if (Integer.valueOf(valuesOfBlocks.get("hit_points")) <= 0) {
                                    throw new Exception();
                                } else if (Integer.valueOf(valuesOfBlocks.get("height")) <= 0) {
                                    throw new Exception();
                                }
                                block.setWidth(Integer.valueOf(valuesOfBlocks.get("width")));
                                block.setHeight(Integer.valueOf(valuesOfBlocks.get("height")));
                                block.setHits(Integer.valueOf(valuesOfBlocks.get("hit_points")));
                            } catch (Exception e) {
                                System.out.println("Wrong block information values in line:" + lineNum
                                        + " check text file");
                                System.exit(0);
                            }
                            if (valuesOfBlocks.containsKey("stroke")) {
                                block.setBorderColor(ColorParser.colorFromString(valuesOfBlocks.get("stroke")));
                            }
                            Map<Integer, Color> blockColors = new TreeMap<Integer, Color>();
                            Map<Integer, Image> blockImages = new TreeMap<Integer, Image>();
                            if (valuesOfBlocks.containsKey("fill")) {
                                valuesOfBlocks.put("fill-0", valuesOfBlocks.get("fill"));
                                valuesOfBlocks.remove("fill");
                            }
                            for (int i = 0; i <= Integer.valueOf(valuesOfBlocks.get("hit_points")); i++) {
                                if (valuesOfBlocks.containsKey("fill-" + i)) {
                                    String fill = valuesOfBlocks.get("fill-" + i);
                                    if (fill.startsWith("image(")) {
                                        fill = fill.substring("image(".length());
                                        fill = fill.replace(")", "");
                                        Image image = ImageIO.read(ClassLoader.getSystemClassLoader().
                                                getResourceAsStream(fill));
                                        blockImages.put(i, image);
                                    } else if (fill.startsWith("color(")) {
                                        Color color = ColorParser.colorFromString(fill);
                                        blockColors.put(i, color);
                                    } else {
                                        System.out.println("Wrong block's fill:" + lineNum + " check text file");
                                        System.exit(0);
                                    }
                                }
                            }
                            block.setBlockColors(blockColors);
                            block.setBlockImages(blockImages);
                            blockFactory.putBlock(symbol, block);
                        } else {
                            System.out.println("Missing block information in line:" + lineNum  + " check text file");
                            System.exit(0);
                        }
                    } else if (singleLine.startsWith("sdef")) {
                        singleLine = singleLine.substring("sdef".length()).trim();
                        Map<String, String> spacerMap = getLineValues(singleLine, lineNum);
                        if (spacerMap.containsKey("symbol") && spacerMap.containsKey("width")) {
                            String symbol = spacerMap.get("symbol");
                            Integer width = Integer.valueOf(spacerMap.get("width"));
                            blockFactory.putSpaceWidth(symbol, width);
                        } else {
                            System.out.println("Wrong spacer information values in line:" + lineNum
                                    + " check text file");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("Wrong Values for the block defenitions file");
                        System.exit(0);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("can't read BlocksDefinitionReader file");
            System.exit(0);
        } finally {
            if (lineNumberReader != null) {
                try {
                    lineNumberReader.close();
                } catch (IOException e) {
                    System.out.println("can't close BlocksDefinitionReader file");
                    System.exit(0);
                }
            }
        }
        return blockFactory;
    }
    /**
     * This method parses a line and returns a form of key + : + value map, which is later added to block's definitions.
     * @param line line.
     * @param lineNum line number.
     * @return map of key and value.
     */
    private static Map<String, String> getLineValues(String line, int lineNum) {
        Map<String, String> pairsMap = new TreeMap<String, String>();
        String[] pairs = line.split(" ");
        for (String singlePair : pairs) {
            String[] partsInPairs = singlePair.split(":");
            if (partsInPairs.length != 2) {
                System.out.println("Error in text, must have key before ':' and value after" + lineNum  + "check text");
                System.exit(0);
            }
            pairsMap.put(partsInPairs[0], partsInPairs[1]);
        }
        return pairsMap;
    }
}
