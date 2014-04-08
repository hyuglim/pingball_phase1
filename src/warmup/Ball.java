package warmup;
import physics.*;

public class Ball {
    public Vect velocity;
    public Circle circle;
    
    public Ball(double speed){
        this.velocity = new Vect(new Angle(1), speed);
        this.circle = new Circle(10,10,1);
    }
    
    public void move(Board board){
        double newX = circle.getCenter().x() + velocity.x();
        if (newX+circle.getRadius() > board.width) newX = board.width-circle.getRadius();
        if (newX-circle.getRadius() < 0) newX = 0+circle.getRadius();
        
        double newY = circle.getCenter().y() + velocity.y();
        if (newY+circle.getRadius() > board.height) newY = board.height-circle.getRadius();
        if (newY-circle.getRadius() < 0) newY = 0+circle.getRadius();
        
        this.circle = new Circle(newX, newY, circle.getRadius());
    }
}
