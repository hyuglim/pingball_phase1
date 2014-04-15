package phase1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import physics.*;

public class Absorber implements Gadget {
    public final String name;
    private final Geometry.DoublePair coord; //should be only integers
    public final LineSegment left;
    public final LineSegment top;
    public final LineSegment right;
    public final LineSegment bottom;
    private List<LineSegment> walls;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    public List<Ball> heldBalls = new ArrayList<Ball>();
    private LineSegment wallThatWillCollide;
    private double countdown;

    /**
     * Creates an absorber.
     * @param coord coordinate of the upper left corner
     * @param name name of the absorber
     * @param width width of the absorber 
     * @param height height of the absorber
     */
    public Absorber(Geometry.DoublePair coord, String name, int width, int height){
        this.coord = coord; //"center" coordinate is always the left top corner of the square.
        this.name = name;
        this.left = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2 + height);
        this.top = new LineSegment(coord.d1, coord.d2, coord.d1 + width, coord.d2);
        this.right = new LineSegment(coord.d1 + width, coord.d2, coord.d1 + width, coord.d2 + height);
        this.bottom = new LineSegment(coord.d1 + width, coord.d2 + height, coord.d1, coord.d2 + height);
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
     * Move the ball to the lower right corner, and hold it there.
     * @param ball the ball that will collide with the gadget
     * @param timeToGo how much time is left in this step
     * @param board needed for recursive calling
     */
    public void collide(Ball ball, double timeToGo, Board board){       
        ball.move(countdown);
        ball.velocity = new Vect(0, 0);
        ball.circle = new Circle(coord.d1+bottom.length()-0.25, coord.d2+right.length()-0.25, 0.5);
    }
    
    /**
     * Shoots the balls out of the corner.
     */
    public void action(){
        if (heldBalls.size()>0){
            for (Ball ball: heldBalls){
                ball.velocity = new Vect(0, 50);
            }
            heldBalls.clear();
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
