package phase1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import physics.*;

public class TriangularBumper implements Gadget {
    public final String name;
    private final Geometry.DoublePair coord; //should be only integers
    public final LineSegment leg1;
    public final LineSegment leg2;
    public final LineSegment hypotenuse;
    private final List<LineSegment> walls;
    private final double reflectCoeff = 1.0;
    public final Angle orientation;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();

    public TriangularBumper(Geometry.DoublePair coord, Angle angle, String name){
        this.coord = coord; //always upper-left corner.
        this.name = name;
        
        LineSegment initleg1 = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2+1);
        LineSegment initleg2 = new LineSegment(coord.d1, coord.d2, coord.d1+1, coord.d2);
        LineSegment inithyp = new LineSegment(coord.d1+1, coord.d2, coord.d1, coord.d2+1);
        
        this.leg1 = Geometry.rotateAround(initleg1, new Vect(coord.d1+0.5, coord.d2+0.5), angle);
        this.leg2 = Geometry.rotateAround(initleg2, new Vect(coord.d1+0.5, coord.d2+0.5), angle);
        this.hypotenuse = Geometry.rotateAround(inithyp, new Vect(coord.d1+0.5, coord.d2+0.5), angle);
        this.orientation = angle;
        walls = Arrays.asList(leg1, leg2, hypotenuse);
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