package rungame;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Menu;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 * This class features a menu animation.
 * @param <T> generic type of the menu.
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-06-09
 */
public class MenuAnimation<T> implements Menu<T> {
    private String menuTitle;
    private Map<String, T> menuMap;
    private KeyboardSensor keyboard;
    private boolean stop;
    private ArrayList<String> messages;
    private ArrayList<String> keys;
    private Menu<T> menuSubMenu;
    private AnimationRunner runner;
    private T status;

    /**
     * This constructor creates a menu animation object from name, keyboard sensor and animation runner.
     * @param name inputted name.
     * @param keyboardSensor keyboard.
     * @param animationRunner animation runner.
     */
    public MenuAnimation(String name, KeyboardSensor keyboardSensor, AnimationRunner animationRunner) {
        this.menuTitle = name;
        this.menuMap =  new TreeMap<String, T>();
        this.keyboard = keyboardSensor;
        this.messages = new ArrayList<String>();
        this.keys = new ArrayList<String>();
        this.runner = animationRunner;
    }

    /**
     * This method adds a task to the menu.
     * @param key to press to start the task.
     * @param message for printing.
     * @param returnVal return value of the task.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.menuMap.put(key, returnVal);
        this.messages.add(message);
        this.keys.add(key);

    }

    /**
     * Get current status.
     * @return status (generic).
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * Drawing the animation.
     * @param d draw surface.
     * @param dt is the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.getStatus() != null) {
            this.stop = true;
        }
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, d.getHeight() / 2 - 220, d.getWidth(), 90);
        d.setColor(Color.BLACK);
//        d.fillRectangle(0, d.getHeight() / 2 - 220, d.getWidth(), 3);
        d.drawText(260, d.getHeight() / 2 - 150, this.menuTitle, 70);
//       d.fillRectangle(0, d.getHeight() / 2 - 130, d.getWidth(), 3);
        for (int i = 0; i < this.messages.size(); i++) {
            d.drawText(150, 200 + 60 * (i + 2), "(" + this.keys.get(i) + ") " + this.messages.get(i), 30);
        }
        for (int i = 0; i < this.keys.size(); i++) {
            if (this.keyboard.isPressed(this.keys.get(i))) {
                if (this.keyboard.isPressed("h")) {
                    this.status = this.menuMap.get("h");
                    this.stop = true;
                } else if (this.keyboard.isPressed("q")) {
                    this.status = this.menuMap.get("q");
                    this.stop = true;
                } else if (this.keyboard.isPressed("s")) {
                    this.runner.run(this.menuSubMenu);
                    this.status = this.menuSubMenu.getStatus();
                    this.menuSubMenu.reset();
                    this.stop = true;
                    break;
                }
            }
        }
    }

    /**
     * Boolean if should stop drawing.
     * @return true/false.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * This method resets the menu status and stop boolean.
     */
    public void reset() {
        this.stop = false;
        this.status = null;
    }

    /**
     * This method adds sub menu to the menu.
     * @param key to press to start running the sub menu.
     * @param message to print for the sub menu.
     * @param subMenu the submenu.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.menuSubMenu = subMenu;
        this.keys.add(key);
        this.messages.add(message);
    }
}
