package readfile;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import collisiondata.Velocity;
import drawable.Block;
import drawable.LevelBackground;
import interfaces.LevelInformation;
import levels.SingleLevel;

/**
 * This class features a level specification reader, gets a reader and return a list of level information.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-12
 */
public class LevelSpecificationReader {
    private static BlocksFromSymbolsFactory blocksFromSymbolsFactory;

    /**
     * This method gets a reader and return a list of level information objects.
     * @param reader inputted reader.
     * @return level information object list.
     */
    public static List<LevelInformation> fromReader(Reader reader) {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        SingleLevel level = null;
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        int counter = 0;
        String singleLine;
        int numOfLine;
        boolean isInBlockSection = false;
        blocksFromSymbolsFactory = null;
        try {
            while ((singleLine = lineNumberReader.readLine()) != null) {
                numOfLine = lineNumberReader.getLineNumber();
                singleLine = singleLine.trim();
                if (!singleLine.equals("") && !singleLine.startsWith("#")) {
                    if (!isInBlockSection) {
                        if (singleLine.equals("START_LEVEL")) {
                            level = new SingleLevel();
                        } else if (singleLine.equals("END_LEVEL")) {
                            if (!level.checkLevelValues()) {
                                System.out.println("Missing information about the  check the text file");
                                System.exit(0);
                            }
                            levels.add(level);
                            level = null;
                        } else if (singleLine.equals("START_BLOCKS")) {
                            isInBlockSection = true;
                            counter = 0;
                        } else {
                            parseRegularLine(singleLine, numOfLine, level);
                        }
                    } else if (singleLine.equals("END_BLOCKS")) {
                        isInBlockSection = false;
                        blocksFromSymbolsFactory = null;
                    } else {
                        parseBlockSectionLine(singleLine, numOfLine, counter, level);
                        counter++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Can't read Level specification file");
            System.exit(0);
        } finally {
            if (lineNumberReader != null) {
                try {
                    lineNumberReader.close();
                } catch (IOException e) {
                    System.out.println("Can't close Level specification file");
                    System.exit(0);
                }
            }
        }
        return levels;
    }

    /**
     * This method gets a line which is not in the block parsing part (between start_blocks and end_blocks) and adds
     * the values to the level information.
     * @param line current reading line.
     * @param lineNum current reading line number.
     * @param level level information to add to.
     */
    public static void parseRegularLine(String line, int lineNum, SingleLevel level) {
        String[] parts = line.trim().split(":");
        if (!(parts.length == 2)) {
            System.out.println("Illegal input, must have a key before : and value after :,"
                    + " in line: " + lineNum + " Check the text file");
            System.exit(0);
        }
        String key = parts[0];
        String value = parts[1];
        if (key.equals("level_name")) {
            level.setName(value);
        }
        if (key.equals("ball_velocities")) {
            int numberOfBalls = 0;
            String[] velocities = value.split(" ");
            for (String velocity : velocities) {
                String[] velocityComponents = velocity.split(",");
                if (!(velocityComponents.length == 2)) {
                    System.out.println("Illegal velocity input in line: " + lineNum + " Check the text file");
                    System.exit(0);
                }
                String angle = velocityComponents[0];
                String speed = velocityComponents[1];
                try {
                    if (Double.valueOf(speed) <= 0) {
                        throw new NumberFormatException();
                    }
                    level.addBallVelocity(Velocity.fromAngleAndSpeed(Double.valueOf(angle),
                            Double.valueOf(speed)));
                    numberOfBalls++;
                } catch (NumberFormatException e) {
                    System.out.println("Illegal velocity input, in line: " + lineNum + " Check the text file");
                    System.exit(0);
                }
            }
            level.setNumberOfBalls(numberOfBalls);
        }
        if (key.equals("background")) {
            if (value.startsWith("image(")) {
                value = value.substring("image(".length());
                value = value.replace(")", "");
                try {
                    Image image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(value));
                    LevelBackground background = new LevelBackground(image);
                    level.setBackground(background);
                } catch (Exception e) {
                    System.out.println("Can't find the background image, in line: " + lineNum + " Check the text file");
                    System.exit(0);
                }
            } else if (value.startsWith("color(")) {
                Color color = ColorParser.colorFromString(value);
                if (color == null) {
                    System.out.println("Check color input, in line: " + lineNum + " Check the text file");
                    System.exit(0);
                }
                LevelBackground background = new LevelBackground(color);
                level.setBackground(background);
            } else {
                System.out.println("No image or color for background, in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }

        if (key.equals("paddle_width")) {
            try {
                if (Integer.valueOf(value) <= 0) {
                    throw new NumberFormatException();
                }
                level.setPaddleWidth(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                System.out.println("Illegal paddle width in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }

        if (key.equals("paddle_speed")) {
            try {
                if (Integer.valueOf(value) <= 0) {
                    throw new Exception();
                }
                level.setPaddleSpeed(Integer.valueOf(value));
            } catch (Exception e) {
                System.out.println("Illegal paddle speed in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }

        if (key.equals("block_definitions")) {
            try {
                Reader blockReader = new InputStreamReader(ClassLoader.getSystemClassLoader()
                        .getResourceAsStream(value));
                blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(blockReader);
            } catch (Exception e) {
                System.out.println("Illegal block defenitions file check line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }

        if (key.equals("blocks_start_x")) {
            try {
                if (Integer.valueOf(value) < 0) {
                    throw new Exception();
                }
                level.setBlocksXPos(Integer.valueOf(value));
            } catch (Exception e) {
                System.out.println("Illegal X value of printing blocks in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }
        if (key.equals("blocks_start_y")) {
            try {
                if (Integer.valueOf(value) < 0) {
                    throw new Exception();
                }
                level.setBlocksStartY(Integer.valueOf(value));
            } catch (Exception e) {
                System.out.println("Illegal Y value of printing blocks in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }


        if (key.equals("num_blocks")) {
            try {
                if (Integer.valueOf(value) < 0) {
                    throw new Exception();
                }
                level.setNumberOfBlocksToRemove(Integer.valueOf(value));
            } catch (Exception e) {
                System.out.println("Error in the number of blocks in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }

        if (key.equals("row_height")) {
            try {
                if (Integer.valueOf(value) <= 0) {
                    throw new Exception();
                }
                level.setRowHeight(Integer.valueOf(value));
            } catch (Exception e) {
                System.out.println("EIllegal row height in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }
    }

    /**
     * This method add block to the level, by using the block factory.
     * @param line current reading line.
     * @param lineNum current reading line number.
     * @param counter counter of the line in block parsing part.
     * @param level level to add blocks and spacers to.
     */
    private static void parseBlockSectionLine(String line, int lineNum, int counter, SingleLevel level) {
        if (!level.checkLevelValues()) {
            System.out.print("Missing level information, check the text file");
            System.exit(0);
        }
        int currentX = level.blocksXPos();
        int currentY = level.blocksStartY() + counter * level.rowHeight();
        for (int i = 0; i < line.length(); i++) {
            String symbol = String.valueOf(line.charAt(i));
            if (blocksFromSymbolsFactory.isSpaceSymbol(symbol)) {
                currentX += blocksFromSymbolsFactory.getSpaceWidth(symbol);
            } else if (blocksFromSymbolsFactory.isBlockSymbol(symbol)) {
                Block block = blocksFromSymbolsFactory.getBlock(symbol, currentX, currentY);
                level.addBlock(block);
                currentX = (int) (currentX + block.getCollisionRectangle().getWidth());
            } else {
                System.out.println("Error in symbol in line: " + lineNum + " Check the text file");
                System.exit(0);
            }
        }
    }
}
