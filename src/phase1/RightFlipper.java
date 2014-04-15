package phase1;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class RightFlipper implements Gadget{
    private final Geometry.DoublePair coord;
    public LineSegment flip;
    public final String name;
    private final double reflectCoeff = 0.95;
    private List<Gadget> gadgetsToBeTriggered = new ArrayList<Gadget>();
    private boolean isOn;
   
    public RightFlipper(Geometry.DoublePair coord, String name, Angle orientation){
        this.coord = coord;
        this.name = name;
        //LineSegment initflip = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2+2);
        if (orientation.equals(Angle.ZERO)){
            this.flip = new LineSegment(coord.d1+2, coord.d2, coord.d1+2, coord.d2+2);
            this.isOn = false;
        } else{
            this.flip = new LineSegment(coord.d1+2, coord.d2, coord.d1, coord.d2);
            this.isOn = true;
        }
    }
    
    /* (non-Javadoc)
     * @see phase1.Gadget#collide(phase1.Ball)
     */
    public void collide(Ball ball){
        if (!(Geometry.timeUntilWallCollision(flip, ball.circle, ball.velocity)>0)){
            ball.velocity = Geometry.reflectWall(flip, ball.velocity, reflectCoeff);
        }
    }
    
    /* (non-Javadoc)
     * @see phase1.Gadget#addTrigger(phase1.Gadget)
     */
    public void addTrigger(Gadget gadget){
        gadgetsToBeTriggered.add(gadget);
    }
    
    /* (non-Javadoc)
     * @see phase1.Gadget#action()
     */
    public void action(){
        if (!isOn){
            flip = Geometry.rotateAround(flip, new Vect(coord.d1, coord.d2), Angle.DEG_90);
            isOn = true;
        } else {
            flip = Geometry.rotateAround(flip, new Vect(coord.d1, coord.d2), Angle.DEG_270);
            isOn = false;
        }
    }
}
