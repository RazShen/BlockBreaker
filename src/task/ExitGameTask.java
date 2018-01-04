package task;
import biuoop.GUI;
import interfaces.Task;

/**
 * This method features a exit game task, which closes the gui and shuts down the program.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-12.
 */
public class ExitGameTask implements Task<Void> {
    private GUI gui;

    /**
     * Constructor for exit game task from gui.
     * @param gui inputted gui.
     */
    public ExitGameTask(GUI gui) {
        this.gui = gui;
    }

    /**
     * This method runs the exit game tasks and shuts down the program.
     * @return Void.
     */
    public Void run() {
        this.gui.close();
        System.exit(0);
        return null;
    }
}
