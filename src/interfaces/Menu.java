package interfaces;

/**
 * This class features a generic menu, allowing to add tasks and sub menus and get it's current status.
 * @param <T> generic type.
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-04-02
 */
public interface Menu<T> extends Animation  {
    /**
     * Add a selected task to the menu.
     * @param key key for task to start.
     * @param message to print.
     * @param returnVal return value of the task.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Get current status.
     * @return status (generic).
     */
    T getStatus();

    /**
     * This method resets the menu status and stop boolean.
     */
    void reset();

    /**
     * This method adds sub menu to the menu.
     * @param key to press to start running the sub menu.
     * @param message to print for the sub menu.
     * @param subMenu the submenu.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}