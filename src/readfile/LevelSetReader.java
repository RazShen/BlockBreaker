package readfile;
import highscores.HighScoresTable;
import interfaces.LevelInformation;
import rungame.SubMenu;
import task.RunGameTask;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.List;


/**
 * This class features a level set reader file which gets a reader and a subMenu, and set the subMenu values.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-12
 */
public class LevelSetReader  {
    /**
     * This method edits the sub menu and adds new tasks to it.
     * @param reader inputted reader.
     * @param subMenu inputted sub menu.
     * @param highScoresTable inputted high score table.
     */
    public static void fromReader(Reader reader, SubMenu subMenu, HighScoresTable highScoresTable) {
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String singleLine;
        int numOfLine;
        String key = null;
        String message = null;
        try {
            while ((singleLine = lineNumberReader.readLine()) != null) {
                numOfLine = lineNumberReader.getLineNumber();
                singleLine = singleLine.trim();
                if (!singleLine.equals("") && !singleLine.startsWith("#")) {
                    if ((numOfLine % 2) != 0) {
                        String[] partsInPairs = singleLine.split(":");
                        key = partsInPairs[0];
                        message = partsInPairs[1];
                    } else {
                        Reader levelsReader = new InputStreamReader(
                                ClassLoader.getSystemClassLoader().getResourceAsStream(singleLine));
                        List<LevelInformation> levelSet = LevelSpecificationReader.fromReader(levelsReader);
                        RunGameTask runGameTask = new RunGameTask(subMenu.getAnimationRunner()
                                .getGui(), subMenu.getAnimationRunner(), levelSet, highScoresTable);
                        subMenu.addSelection(key, message, runGameTask);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Problem with level set file, check text");
            System.exit(0);
        }
    }
}
