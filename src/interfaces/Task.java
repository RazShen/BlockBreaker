package interfaces;

/**
 * This interface is used to describe a task, that runs.
 * @param <T> generic type.
 * @author Raz Shenkman
 * @version 2.0
 * @since 2017-06-19
 */
public interface Task<T> {
    /**
     * Run a task and get a returned value.
     * @return returned value (generic).
     */
    T run();
}
