package warmup;
import physics.*;

/**
 * TODO: put documentation for your class here
 */
public class Main {
    
    /**
     * TODO: describe your main function's command line arguments here
     * 
     * If no collisions (collideWith returns null), delay 100 ms between frames
     * moves the ball along then print the updated version of the board
     * else, collision, instantly change the position of the ball
     */
    public static void main(String[] args) {
        Ball ball = new Ball(1.0);
        Board board = new Board();
        double maxX = -100;
        double maxY = -100;
        while(true){
            while (board.collideWith(ball)==null){
                try{Thread.sleep(80);} //delay in between frames
                catch(Exception e){e.printStackTrace();}
                
                ball.move(board.getWidth(),board.getHeight()); //gets the ball moving
                
                System.out.println(board.getBoardPic(ball));
                System.out.println("position: "+ball.circle.getCenter().x()+" , "+ball.circle.getCenter().y());
                //System.out.println("velocity: "+ball.velocity);
                
                if(ball.getPosX() > maxX) maxX = ball.getPosX();
                if(ball.getPosY() > maxY) maxY = ball.getPosY();
                System.out.println("max pos: " + maxX + "," + maxY);
                
            }
            
            // this means a collision happened!! change the position AND the velocity of the ball
            ball.velocity = Geometry.reflectWall(board.collideWith(ball), ball.velocity);
            ball.move(board.getWidth(),board.getHeight());
        }
    }
    
    
    
}