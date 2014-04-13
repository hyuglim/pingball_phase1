package phase1;
import java.lang.Float;

import physics.*;

public class Ball {
    public Vect velocity;
    public Circle circle;
    public final String name;
    
	public Ball (String name, Float x, Float y, Float xVel, Float yVel) {
	    this.name = name;
	    this.circle = new Circle(x, y, 0.5);
	    this.velocity = new Vect(xVel, yVel);
	}
}
