package phase1;
import physics.*;

public class RightFlipper implements Gadget{
    private final Geometry.DoublePair coord;
    public final LineSegment flip;
    public final String name;
    private final double reflectCoeff = 0.95;
    
    public RightFlipper(Geometry.DoublePair coord, String name, Angle orientation){
        this.coord = coord;
        this.name = name;
        //LineSegment initflip = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2+2);
        if (orientation.equals(Angle.ZERO)){
            this.flip = new LineSegment(coord.d1+2, coord.d2, coord.d1+2, coord.d2+2);
        } else{
            this.flip = new LineSegment(coord.d1+2, coord.d2, coord.d1, coord.d2);
        }
    }
}
