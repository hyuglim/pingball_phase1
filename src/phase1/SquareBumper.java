package phase1;

import physics.*;

public class SquareBumper implements Gadget {
    public final String name;
    private final Geometry.DoublePair coord; //should be only integers
    public final LineSegment left;
    public final LineSegment top;
    public final LineSegment right;
    public final LineSegment bottom;
    private final double reflectCoeff = 1.0;

    public SquareBumper(Geometry.DoublePair coord, String name){
        this.coord = coord; //"center" coordinate is always the left top corner of the square.
        this.name = name;
        this.left = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2 + 1);
        this.top = new LineSegment(coord.d1, coord.d2, coord.d1 + 1, coord.d2);
        this.right = new LineSegment(coord.d1 + 1, coord.d2, coord.d1 + 1, coord.d2 + 1);
        this.bottom = new LineSegment(coord.d1 + 1, coord.d2 + 1, coord.d1, coord.d2 + 1);
    }
}
