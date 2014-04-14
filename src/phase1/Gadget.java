package phase1;
//import physics.*;

/**
 * @author Harlin
 * An abstract data type to represent gadgets in a pingball board.
 */
public interface Gadget {
    /**
     * Add trigger-action hook to a gadget.
     * @param gadget the gadget whose action will be hooked
     */
    public void addTrigger(Gadget gadget);
    
    /**
     * Checks whether the ball will collide and updates ball's velocity accordingly if there is a collision.
     * @param ball the ball which might collide with the gadget
     */
    public void collide(Ball ball);
    
    /**
     * The gadget's action that can be triggered. It's specific to the type of each gadget.
     */
    public void action();
}
