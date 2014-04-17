package phase1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import physics.*;

public class SquareBumper implements Gadget {
    public final String name;
    private final Geometry.DoublePair coord; //should be only integers
    public final LineSegment left;
    public final LineSegment top;
    public final LineSegment right;
    public final LineSegment bottom;
    private final List<LineSegment> walls;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private final double reflectCoeff = 1.0;
    private LineSegment wallThatWillCollide;
    private double countdown;
    
    public String getName() {
		return name;
	}
    
    /**
     * Creates a square bumper
     * @param coord coordinate of the upper left corner
     * @param name
     */
    public SquareBumper(Geometry.DoublePair coord, String name){
        this.coord = coord; //"center" coordinate is always the left top corner of the square.
        this.name = name;
        this.left = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2 + 1);
        this.top = new LineSegment(coord.d1, coord.d2, coord.d1 + 1, coord.d2);
        this.right = new LineSegment(coord.d1 + 1, coord.d2, coord.d1 + 1, coord.d2 + 1);
        this.bottom = new LineSegment(coord.d1 + 1, coord.d2 + 1, coord.d1, coord.d2 + 1);
        walls = Arrays.asList(left, top, right, bottom);
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
        double min = Integer.MAX_VALUE;
        double time;
        for (LineSegment wall: walls){
            time = Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity);
            if (time < min) {
                min = time;
                wallThatWillCollide = wall;
            }
        }
        countdown = min;
        return min;
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
        ball.velocity = Geometry.reflectWall(wallThatWillCollide, ball.velocity, reflectCoeff);
        board.moveOneBall(ball, timeToGo-countdown);
    }
    
    /**
   * nothing happens.
   */
    public void action(){
        
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
