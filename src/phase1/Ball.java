package phase1;
import java.lang.Float;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class Ball {
    public Vect velocity;
    public Circle circle;
    public final String name;
    //private final Vect gravity = new Vect(0, -25); //will worry about gravity and friction at the end
    
	public Ball (String name, Float x, Float y, Float xVel, Float yVel) {
	    this.name = name;
	    this.circle = new Circle(x, y, 0.5);
	    this.velocity = new Vect(xVel, yVel);
	}
	
	public void move(){
	    //one step
	}
}
