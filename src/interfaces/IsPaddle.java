package interfaces;

/**
 * This interface is extended by Collidable interface, it's purpose is to know if the object we collided with is paddle.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-19
 */

public interface IsPaddle {
    /**
     * This method returns true/false whether the collidable is a paddle or not.
     * @return true/false whether the collidable is a paddle or not.
     */
    boolean isItPaddle();
}
