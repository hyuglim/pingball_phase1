package phase1;
import java.lang.Float;
import java.util.ArrayList;
import java.util.List;

import physics.*;

/**
 * @author Harlin
 * Describes a ball that is used in a pingball game.
 */
public class Ball {
    public Vect velocity;
    public Circle circle;
    public final String name;
    //private final Vect gravity = new Vect(0, -25); //will worry about gravity and friction at the end
    
    /**
     * Creates a Ball instance.
     * @param name Name of the ball
     * @param x x-coordinate of the center of the ball
     * @param y y-coordinate of the center of the ball
     * @param xVel velocity of the ball in the x-direction
     * @param yVel velocity of the ball in the y-direction
     */
	public Ball (String name, Float x, Float y, Float xVel, Float yVel) {
	    this.name = name;
	    this.circle = new Circle(x, y, 0.5);
	    this.velocity = new Vect(xVel, yVel);
	}
	
	/**
	 * Move the ball for one step. Returns nothing.
	 */
	public void move(){
	    double stepSize = 0.05;
        double newX = circle.getCenter().x() + stepSize*velocity.x();
        double newY = circle.getCenter().y() + stepSize*velocity.y();
        this.circle = new Circle(newX, newY, circle.getRadius());
	}
}