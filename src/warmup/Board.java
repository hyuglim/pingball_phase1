package warmup;
import java.util.Arrays;
import java.util.List;

import physics.*;

public class Board {
    private final LineSegment top = new LineSegment(0,0,20,0);
    private final LineSegment right = new LineSegment(20,0,20,20);
    private final LineSegment bottom = new LineSegment(20,20,0,20);
    private final LineSegment left = new LineSegment(0,20,0,0);
    private final List<LineSegment> walls = Arrays.asList(top, right, bottom, left);
    public final double width = 20.0;
    public final double height = 20.0;
    
    public Board(){  }
    
    public LineSegment collideWith(Ball ball){
        for (LineSegment wall: walls){
            System.out.println(Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity));
            if (!(Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity)>0.0)){
                return wall;
            }
        }
        return null;
    }
    
}
