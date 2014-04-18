package phase1;
import java.lang.Float;
import java.util.ArrayList;
import java.util.List;

import physics.*;

/**
 * Describes a ball that is used in a pingball game. It has a diameter of 1L.
 */
public class Ball {
    public Vect velocity;
    public Circle circle;
    public final String name;
    public boolean inAbsorber = false;
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
	    this.velocity = new Vect(0.05*xVel, 0.05*yVel);
	}
	
	/**
	 * Move the ball for a certain amount of time.
	 * @param time how long the ball should be moving for
	 */
	public void move(double time){
	    //double stepSize = 0.05;
        double newX = circle.getCenter().x() + time*velocity.x();
        double newY = circle.getCenter().y() + time*velocity.y();
        this.circle = new Circle(newX, newY, 0.5);
	}
	
	public Geometry.DoublePair getPosition(){
	    return new Geometry.DoublePair(Math.floor(circle.getCenter().x()),Math.floor(circle.getCenter().y()));
	}
}