package phase1;

public class GamePlayer implements Runnable{

	private Board myBoard;
	
	/**
	 * takes in 
	 * @param Board b
	 */
	public GamePlayer(Board b) {
		this.myBoard = b;
	}
	
	/**
	 * plays the game on the board
	 */
	public void run() {		
		while(true) {
		    try {
		        myBoard.moveAllBalls();
		        System.out.println("number of balls: " + myBoard.getBallsSize());
		        Thread.sleep(50);
		    } catch (Exception e){
		        e.printStackTrace();
		    }
		}
	}
}
