package warmup;
import physics.*;

/**
 * TODO: put documentation for your class here
 */
public class Main {
    
    /**
     * TODO: describe your main function's command line arguments here
     */
    public static void main(String[] args) {
        Ball ball = new Ball(1.0);
        Board board = new Board();
        while(true){
            while (board.collideWith(ball)==null){
                try{Thread.sleep(100);}
                catch(Exception e){e.printStackTrace();}
                
                ball.move(board);
                
                System.out.println("position: "+ball.circle.getCenter().x()+" , "+ball.circle.getCenter().y());
                System.out.println("velocity: "+ball.velocity);
            }
            System.out.println(">>>>>>>>>>>>>>COLLISION!");
            
            ball.velocity = Geometry.reflectWall(board.collideWith(ball), ball.velocity);
            ball.move(board);
            
            System.out.println("position: "+ball.circle.getCenter().x()+" , "+ball.circle.getCenter().y());
            System.out.println("velocity: "+ball.velocity);
        }
    }
    
}