package interfaces;

/**
 * This interface is used for the Listener pattern inform the HitListener object that it there was a hit.
 *
 * @author Raz Shenkman
 * @version 1.0
 * @since 2017-05-19
 */
public interface HitNotifier {

    /**
     * Add HitListener object as a listener to the list of HitListeners.
     * @param hl HitListener.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove HitListener object as a listener from the list of HitListeners.
     * @param hl HitListener.
     */
    void removeHitListener(HitListener hl);
}