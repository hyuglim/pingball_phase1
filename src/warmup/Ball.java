package warmup;
import physics.*;


public class Ball {
    public Vect velocity;
    public Circle circle;
    private double posX = 10; //initial pos. Added for convenience
    private double posY = 10; 
    
    public Ball(double speed){
        this.velocity = new Vect(new Angle(1), speed); //1=L
        this.circle = new Circle(10,10,1);
    }
    
    //if conditions represent the case when the ball bounces off of the wall
    //better to to have modularity so not have board call on ball and ball 
    //also calling on board
    public void move(int w, int h){
        double newX = circle.getCenter().x() + velocity.x();
        if (newX+circle.getRadius() > w) newX = w-circle.getRadius();
        if (newX-circle.getRadius() < 0) newX = 0+circle.getRadius();
        
        double newY = circle.getCenter().y() + velocity.y();
        if (newY+circle.getRadius() > h) newY = h-circle.getRadius();
        if (newY-circle.getRadius() < 0) newY = 0+circle.getRadius();
        
        this.circle = new Circle(newX, newY, circle.getRadius());
        
        this.posX = newX;
        this.posY = newY;
    }
    
    public double getPosX(){return posX;}
    public double getPosY(){return posY;}
    
}
