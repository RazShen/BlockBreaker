import highscores.HighScoresTable;
import interfaces.Menu;
import interfaces.Task;
import readfile.LevelSetReader;
import rungame.AnimationRunner;
import rungame.MenuAnimation;
import rungame.SubMenu;
import task.ExitGameTask;
import task.ShowHiScoresTask;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * This class operates a menu and adds a sub menu to it, getting the status of the menu and running it's tasks.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04-24
 */

public class Ass6Game {
    /**
     * This method uses level set (default or inputted by user arguments) and sub menu from it adds the sub menu to
     * the menu, then runs the menu.
     * @param args inputted argument (must be valid level set).
     */
    public static void main(String[] args) {
        String levelSetPath;
        if (args.length != 0) {
            levelSetPath = args[0];
        } else {
            levelSetPath = "default_level_sets.txt";
        }
        AnimationRunner animationRunner = new AnimationRunner(60);
        File scoretable = new File("highscores");
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile(scoretable);
        SubMenu subMenu = new SubMenu("Level Sets", animationRunner.getGui().getKeyboardSensor(),
                animationRunner);
        try {
            Reader levelsSetReader = new InputStreamReader(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetPath));
            LevelSetReader.fromReader(levelsSetReader, subMenu, highScoresTable);
        } catch (Exception e) {
            System.out.println("Problem with user arguments");
            System.exit(0);
        }
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", animationRunner.getGui().getKeyboardSensor(),
                animationRunner);
        menu.addSubMenu("s", "Start Game", subMenu);
        menu.addSelection("h", "High scores", new ShowHiScoresTask(animationRunner, highScoresTable));
        menu.addSelection("q", "Quit game", new ExitGameTask(animationRunner.getGui()));
        while (true) {
            animationRunner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
            menu.reset();
        }
    }
}
