package phase1;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class RightFlipper implements Gadget{
    public final Tuple coord;
    private final Vect pivot;
    private LineSegment flip;
    LineSegment initflip;
    private final String name;
    private final double reflectCoeff = 0.95;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private boolean isOn;
    private boolean isRotating;
    private double countdown;
    private float gravity;
    
    /**
     * @returns name of the gadget
     */
    public String getName() {
		return name;
	}
   
    /**
     * Creates a rightflipper
     * @param coord coordinate of the upper left corner
     * @param name 
     * @param orientation angle of 0, 90, 180, or 270
     */
    public RightFlipper(Tuple coord, String name, Angle orientation,float gravity){
        this.coord = coord;
        this.name = name;
        initflip = new LineSegment(coord.x+2, coord.y, coord.x+2, coord.y+2);
        this.flip = Geometry.rotateAround(initflip, new Vect(coord.x+1, coord.y+1), orientation);
        this.pivot = flip.p1();
        this.isOn = false;
        this.isRotating = false;
        this.gravity=gravity;
     }

    /**
     * Find out how much time is left until ball-gadget collision.
     * @param ball the ball that may collide
     * @returns how much time is left until collision
     */
    public double timeUntilCollision(Ball ball){
        double angularvelocity = 1080*0.05; //In our design, each timestep is 0.05 
        if (isRotating){
            countdown = Geometry.timeUntilRotatingWallCollision(flip, pivot, angularvelocity, ball.circle, ball.velocity);
        } else {
            countdown = Geometry.timeUntilWallCollision(flip, ball.circle, ball.velocity);
        }
        isRotating = false;
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
        double angularvelocity = 1080*0.05; //In our design, each timestep is 0.05 
        ball.move(countdown-0.5/ball.velocity.length());
        if (isRotating){
            ball.velocity = Geometry.reflectRotatingWall(flip, ball.circle.getCenter(), angularvelocity, ball.circle, ball.velocity, reflectCoeff);
        } else {
            ball.velocity = Geometry.reflectWall(flip, ball.velocity, reflectCoeff);
        }
        ball.velocity = new Vect(ball.velocity.x(), ball.velocity.y()+gravity);
        if (timeToGo-countdown >0){
            board.moveOneBall(ball, timeToGo-countdown+0.5/ball.velocity.length());
        }
        trigger();
    }

    
    /**
     * Add trigger-action hook to a gadget.
     * @param gadget the gadget whose action will be hooked
     */
    public void addTrigger(Gadget gadget){
        gadgetsToBeTriggered.add(gadget);
    }
    
    /**
     * Toggles the flipper
     */
    public void action(){
        if (!isOn){
            flip = Geometry.rotateAround(flip, pivot, Angle.DEG_90);
            isOn = true;
            isRotating = true;
        } else {
            flip = Geometry.rotateAround(flip, pivot, Angle.DEG_270);
            isOn = false;
            isRotating = true;
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
    
    /**
     * @returns how the flipper should be represented as a String.
     */
    public String[] showOrientation(){
        String[] result = new String[4];
        if (flip.toLine2D().ptSegDist(coord.x, coord.y+1.0)<=0.1){
            result[0] = "|";
            result[1] = " ";
            result[2] = "|";
            result[3] = " ";
        } else if (flip.toLine2D().ptSegDist(coord.x+1.0, coord.y+2.0)<=0.1){
            result[0] = " ";
            result[1] = " ";
            result[2] = "-";
            result[3] = "-";
        } else if (flip.toLine2D().ptSegDist(coord.x+2.0, coord.y+1.0)<=0.1){
            result[0] = " ";
            result[1] = "|";
            result[2] = " ";
            result[3] = "|";
        } else if (flip.toLine2D().ptSegDist(coord.x+1.0, coord.y)<=0.1){
            result[0] = "-";
            result[1] = "-";
            result[2] = " ";
            result[3] = " ";
        }
        return result;
    }
}
