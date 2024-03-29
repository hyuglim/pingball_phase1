package phase1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import physics.*;

public class TriangularBumper implements Gadget {
    public final String name;
    private final Tuple coord; //should be only integers
    public final LineSegment leg1;
    public final LineSegment leg2;
    public final LineSegment hypotenuse;
    private final List<LineSegment> walls;
    private final double reflectCoeff = 1.0;
    public final Angle orientation;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private double countdown;
    private LineSegment wallThatWillCollide;
    private float gravity;
    
    public String getName() {
		return name;
	}

    /**
     * Creates a triangular bumper.
     * @param coord coordinate of the upper left corner
     * @param angle orientation of the bumper. 0, 90, 180 or 270
     * @param name 
     */
    public TriangularBumper(Tuple coord, Angle angle, String name,float gravity){
        this.coord = coord; //always upper-left corner.
        this.name = name;
        this.orientation = angle;
        
        LineSegment initleg1 = new LineSegment(coord.x, coord.y, coord.x, coord.y+1);
        LineSegment initleg2 = new LineSegment(coord.x, coord.y, coord.x+1, coord.y);
        LineSegment inithyp = new LineSegment(coord.x+1, coord.y, coord.x, coord.y+1);
        Vect roationCenter= new Vect(coord.x+0.5, coord.y+0.5);
        this.leg1 = Geometry.rotateAround(initleg1, roationCenter, angle);
        this.leg2 = Geometry.rotateAround(initleg2, roationCenter, angle);
        this.hypotenuse = Geometry.rotateAround(inithyp, roationCenter, angle);
        this.gravity=gravity;
        
        walls = Arrays.asList(leg1, leg2, hypotenuse);
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
        countdown = Integer.MAX_VALUE;
        double time;
        wallThatWillCollide = null;
        for (LineSegment wall: walls){
            time = Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity);
            if (-1< time && time < countdown) {
                countdown = time;
                wallThatWillCollide = wall;
            }
        }
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
        ball.move(countdown-0.5/ball.velocity.length());
        ball.velocity = Geometry.reflectWall(wallThatWillCollide, ball.velocity, reflectCoeff);
        ball.velocity=new Vect(ball.velocity.x(), ball.velocity.y()+gravity);
        if (timeToGo-countdown >0){
            board.moveOneBall(ball, timeToGo-countdown+0.5/ball.velocity.length());
        }
        trigger();
    }
    
    /**
     * Nothing happens.
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