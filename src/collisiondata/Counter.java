package collisiondata;

/**
 * This Class features a counter object, which can be increased, decreased and has a getter for it's value.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-20
 */
public class Counter {
    /**
     * The current number the counter holds.
     */
    private int count;

    /**
     * Add a number to current count.
     * @param number added number.
     */
    public void increase(int number) {
        this.count = this.count + number;
    }

    /**
     * Subtract number from current count.
     * @param number to be decreased from count.
     */
    public void decrease(int number) {
        this.count = this.count - number;
    }

    /**
     * Returns the current value the count holds.
     * @return the current value the count holds.
     */
    public int getValue() {
        return this.count;
    }
}
