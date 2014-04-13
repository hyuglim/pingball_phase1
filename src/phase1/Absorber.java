package phase1;

import physics.Geometry;
import physics.LineSegment;

public class Absorber implements Gadget {
    public final String name;
    private final Geometry.DoublePair coord; //should be only integers
    public final LineSegment left;
    public final LineSegment top;
    public final LineSegment right;
    public final LineSegment bottom;

    public Absorber(Geometry.DoublePair coord, String name, int width, int height){
        this.coord = coord; //"center" coordinate is always the left top corner of the square.
        this.name = name;
        this.left = new LineSegment(coord.d1, coord.d2, coord.d1, coord.d2 + height);
        this.top = new LineSegment(coord.d1, coord.d2, coord.d1 + width, coord.d2);
        this.right = new LineSegment(coord.d1 + width, coord.d2, coord.d1 + width, coord.d2 + height);
        this.bottom = new LineSegment(coord.d1 + width, coord.d2 + height, coord.d1, coord.d2 + height);
    }

	public void trigger() {
		// TODO Auto-generated method stub
		
	}

	public void action() {
		// TODO Auto-generated method stub
		
	}

}
