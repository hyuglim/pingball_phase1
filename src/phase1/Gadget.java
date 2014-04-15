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
     * Find out how much time is left until ball-gadget collision.
     * @param ball the ball that may collide
     * @returns how much time is left until collision
     */
    public double timeUntilCollision(Ball ball);
    
    /**
     * Simulates a ball-gadget collision. 
     * Moves the ball, updates the ball's velocity, and moves the ball again for however much time is left in that step.
     * @param ball the ball that will collide with the gadget
     * @param timeToGo how much time is left in this step
     * @param board needed for recursive calling
     */
    public void collide(Ball ball, double timeToGo, Board board);
    
    /**
     * The gadget's action that can be triggered. It's specific to the type of each gadget.
     */
    public void action();

    /**
     * Trigger other gadget's actions.
     */
    public void trigger();
}
