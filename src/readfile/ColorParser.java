package readfile;
import java.awt.Color;

/**
 * This class features a color parser which gets a string and returns a color according to it's values.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */
public class ColorParser {
    /**
     * This static method returns a color from the string s.
     * @param string inputted string.
     * @return color.
     */
    public static Color colorFromString(String string) {
        if (string.startsWith("color(")) {
            string = string.substring("color(".length());
            if (string.startsWith("RGB(")) {
                string = string.substring("RGB(".length());
                string = string.replace("))", "");
                String[] parts = string.split(",");
                if (!(parts.length == 3)) {
                    System.out.println("Error in RGB (must contain 3 parameters) Check the text file");
                    System.exit(0);
                }
                int x = Integer.valueOf(parts[0]);
                int y = Integer.valueOf(parts[1]);
                int z = Integer.valueOf(parts[2]);
                return new Color(x, y, z);
            } else {
                // Color isn't RGB type.
                string = string.replace(")", "");
                Color inputColor = getColor(string);
                if (inputColor == null) {
                    System.out.println("Not legitimate color, check the text file");
                    System.exit(0);
                } else {
                    return inputColor;
                }
            }
        }
        return null;
    }

    /**
     * This method get a string and checks if it's a color's name, if so it returns a new color by the inputted string.
     * @param colorString inputted string.
     * @return color.
     */
    private static Color getColor(String colorString) {
        if (colorString.equals("black")) {
            return Color.BLACK;
        } else if (colorString.equals("blue")) {
            return Color.BLUE;
        } else if (colorString.equals("cyan")) {
            return Color.CYAN;
        } else if (colorString.equals("gray")) {
            return Color.GRAY;
        } else if (colorString.equals("lightGray")) {
            return Color.LIGHT_GRAY;
        } else if (colorString.equals("green")) {
            return Color.GREEN;
        } else if (colorString.equals("orange")) {
            return Color.ORANGE;
        } else if (colorString.equals("pink")) {
            return Color.PINK;
        } else if (colorString.equals("red")) {
            return Color.RED;
        } else if (colorString.equals("white")) {
            return Color.WHITE;
        } else if (colorString.equals("yellow")) {
            return Color.YELLOW;
        }
        return null;
    }
}
