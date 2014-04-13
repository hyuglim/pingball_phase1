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

    public Absorber(Geometry.DoublePair coord, String name, int width, int height){
        this.coord = coord; //"center" coordinate is always the left top corner of the square.
        this.name = name;
        this.left = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2 + height);
        this.top = new LineSegment(coord.d1, coord.d2, coord.d1 + width, coord.d2);
        this.right = new LineSegment(coord.d1 + width, coord.d2, coord.d1 + width, coord.d2 + height);
        this.bottom = new LineSegment(coord.d1 + width, coord.d2 + height, coord.d1, coord.d2 + height);
        walls = Arrays.asList(left, top, right, bottom);
    }
    
    public void addTrigger(Gadget gadget){
        gadgetsToBeTriggered.add(gadget);
    }
    
    public void collide(Ball ball){
        for (LineSegment wall: walls){
            if (!(Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity)>0)){
                ball.velocity = new Vect(0, 0);
                ball.circle = new Circle(coord.d1+bottom.length()-0.25, coord.d2+right.length()-0.25, 0.5);
            }
        }
    }
    
    public void action(){
        if (heldBalls.size()>0){
            for (Ball ball: heldBalls){
                ball.velocity = new Vect(0, 50);
            }
            heldBalls.clear();
        }
    }

}
