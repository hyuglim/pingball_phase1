package phase1;
import physics.*;

public class CircleBumper implements Gadget {
    private final Geometry.DoublePair coord;
    private final Circle circle;
    public final String name;
    private final double reflectCoeff = 1.0;
    
    public CircleBumper(Geometry.DoublePair coord, String name){
        this.coord = coord;
        this.name = name;
        this.circle = new Circle(coord.d1+0.5, coord.d2+0.5, 0.5);
    }
}
