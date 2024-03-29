package phase1;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class CircleBumper implements Gadget {
    private final Tuple coord;
    private final Circle circle;
    public final String name;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private final double reflectionCoeff = 1.0;
    private double countdown;
    private int gravity;
    
    public String getName() {
		return name;
	}
    
    /**
     * Creates a circular bumper
     * @param coord coordinate of the upper left corner
     * @param name
     */
    public CircleBumper(Tuple coord, String name){
        this.coord = coord;
        this.name = name;
        this.circle = new Circle(coord.x+0.5, coord.y+0.5, 0.5);
        this.gravity=gravity;
    }
    
    /**
     * Find out how much time is left until ball-gadget collision.
     * @param ball the ball that may collide
     * @returns how much time is left until collision
     */
    public double timeUntilCollision(Ball ball){
        countdown = Geometry.timeUntilCircleCollision(circle, ball.circle, ball.velocity);
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
        ball.velocity = Geometry.reflectCircle(circle.getCenter(), ball.circle.getCenter(), ball.velocity, reflectionCoeff);
        ball.velocity=new Vect(ball.velocity.x(), ball.velocity.y()+gravity);
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
