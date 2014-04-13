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

    public SquareBumper(Geometry.DoublePair coord, String name){
        this.coord = coord; //"center" coordinate is always the left top corner of the square.
        this.name = name;
        this.left = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2 + 1);
        this.top = new LineSegment(coord.d1, coord.d2, coord.d1 + 1, coord.d2);
        this.right = new LineSegment(coord.d1 + 1, coord.d2, coord.d1 + 1, coord.d2 + 1);
        this.bottom = new LineSegment(coord.d1 + 1, coord.d2 + 1, coord.d1, coord.d2 + 1);
        walls = Arrays.asList(left, top, right, bottom);
    }
    
    public void collide(Ball ball){
        for (LineSegment wall: walls){
            if (!(Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity)>0)){
                ball.velocity = Geometry.reflectWall(wall, ball.velocity, reflectCoeff);
            }
        }
    }
    
    public void addTrigger(Gadget gadget){
        gadgetsToBeTriggered.add(gadget);
    }
    
    public void action(){
        
    }
}
