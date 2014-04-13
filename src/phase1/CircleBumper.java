package phase1;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class CircleBumper implements Gadget {
    private final Geometry.DoublePair coord;
    private final Circle circle;
    public final String name;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private final double reflectionCoeff = 1.0;
    
    public CircleBumper(Geometry.DoublePair coord, String name){
        this.coord = coord;
        this.name = name;
        this.circle = new Circle(coord.d1+0.5, coord.d2+0.5, 0.5);
    }
    
    public void collide(Ball ball){
        if (!(Geometry.timeUntilCircleCollision(circle, ball.circle, ball.velocity)>0)){
            ball.velocity = Geometry.reflectCircle(circle.getCenter(), ball.circle.getCenter(), ball.velocity, reflectionCoeff);            
        }
    }
    
    public void addTrigger(Gadget gadget){
        gadgetsToBeTriggered.add(gadget);
    }
    
    public void action(){
        
    }
}
