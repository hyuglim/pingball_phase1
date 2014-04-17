package phase1;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class Wall implements Gadget{
    private LineSegment wall;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private double countdown;
    private final double reflectCoeff = 1.0;
    public String name;
    private int wallNum;
    
    /**
     * Creates an outer wall.
     * @param name indicates which wall it will be. has to be one of "left", "top", "right", or "bottom"
     */
    public Wall(int wallNum){
        this.wallNum = wallNum;
        switch(wallNum){
            case 0: name = "top"; wall = new LineSegment(-0.05,-0.05,20.05,-0.05); break;
            case 1: name = "bottom"; wall= new LineSegment(20.05,20.05,-0.05,20.05); break;
            case 2: name = "left"; wall = new LineSegment(-0.05,20.05,-0.05,-0.05); break;
            case 3: name = "right"; wall = new LineSegment(20.05,-0.05,20.05,20.05); break;
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
    
    public String getName(){
        return name;
    }
    
    public int getWallNum(){
        return wallNum;
    }
}
