package phase1;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class Wall implements Gadget{
    private final LineSegment wall;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private double countdown;
    private final double reflectCoeff = 1.0;
    public String side;
    
    /**
     * Creates an outer wall.
     * @param name indicates which wall it will be. has to be one of "left", "top", "right", or "bottom"
     */
    public Wall(String name){
        this.side = name;
        if (name.equals("left")){
            wall = new LineSegment(-0.05,20.05,-0.05,-0.05); 
        } else if (name.equals("top")){
            wall = new LineSegment(-0.05,-0.05,20.05,-0.05);
        } else if (name.equals("right")){
            wall = new LineSegment(20.05,-0.05,20.05,20.05);
        } else {
            wall= new LineSegment(20.05,20.05,-0.05,20.05);
        }
    }
    
    /**
     * Add trigger-action hook to a gadget.
     * @param gadget the gadget whose action will be hooked
     */
    public void addTrigger(Gadget gadget){
        gadgetsToBeTriggered.add(gadget);
    }

    /**
     * Find out how much time is left until ball-gadget collision.
     * @param ball the ball that may collide
     * @returns how much time is left until collision
     */
    public double timeUntilCollision(Ball ball) {
        countdown = Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity); 
        return countdown;
    }

    /**
     * Simulates a ball-gadget collision. 
     * Moves the ball, updates the ball's velocity, and moves the ball again for however much time is left in that step.
     * @param ball the ball that will collide with the gadget
     * @param timeToGo how much time is left in this step
     * @param board needed for recursive calling
     */
    public void collide(Ball ball, double timeToGo, Board board) {
        ball.move(countdown);
        ball.velocity = Geometry.reflectWall(wall, ball.velocity, reflectCoeff);
        board.moveOneBall(ball, timeToGo-countdown);
    }

    /**
     * Nothing happens.
     */
    public void action() {
        
    }
    

    /**
     * Trigger other gadget's actions.
     */
    public void trigger(){
        for (Gadget gad : gadgetsToBeTriggered){
            gad.action();
        }
    }
}
