package phase1;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class LeftFlipper implements Gadget{
    private final Geometry.DoublePair coord;
    public LineSegment flip;
    public final String name;
    private final double reflectCoeff = 0.95;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private boolean isOn;
    private double countdown;
    
    public String getName() {
		return name;
	}
    
    /**
     * Creates a leftflipper
     * @param coord coordinate of the upper left corner
     * @param name 
     * @param orientation angle of 0, 90, 180, or 270
     */
    public LeftFlipper(Geometry.DoublePair coord, String name, Angle orientation){
        this.coord = coord;
        this.name = name;
        //LineSegment initflip = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2+2);
        if (orientation.equals(Angle.ZERO)){
            this.flip = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2+2);
            this.isOn = false;
        } else{
            this.flip = new LineSegment(coord.d1, coord.d2, coord.d1+2, coord.d2);
            this.isOn = true;
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
    public double timeUntilCollision(Ball ball){
        countdown = Geometry.timeUntilWallCollision(flip, ball.circle, ball.velocity); 
        return countdown;
    };
    
    /**
     * Simulates a ball-gadget collision. 
     * Moves the ball, updates the ball's velocity, and moves the ball again for however much time is left in that step.
     * @param ball the ball that will collide with the gadget
     * @param timeToGo how much time is left in this step
     * @param board needed for recursive calling
     */
    public void collide(Ball ball, double timeToGo, Board board){       
        ball.move(countdown);
        //need to add reflect rotating wall later.
        ball.velocity = Geometry.reflectWall(flip, ball.velocity, reflectCoeff);
        board.moveOneBall(ball, timeToGo-countdown);
    }
    
    /**
     * Toggles the flipper.
     */
    public void action(){
        if (!isOn){
            flip = Geometry.rotateAround(flip, new Vect(coord.d1, coord.d2), Angle.DEG_270);
            isOn = true;
        } else {
            flip = Geometry.rotateAround(flip, new Vect(coord.d1, coord.d2), Angle.DEG_90);
            isOn = false;
        }
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