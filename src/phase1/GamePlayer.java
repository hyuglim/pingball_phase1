package phase1;

public class GamePlayer implements Runnable{

	private Board myBoard;
	
	public GamePlayer(Board b) {
		this.myBoard = b;
	}
	
	public void run() {
		
		while(true) {
		    try {
		        myBoard.moveAllBalls();
		        Thread.sleep(50);
		    } catch (Exception e){
		        e.printStackTrace();
		    }
		}
	}
}
