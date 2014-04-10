package warmup;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.List;

import physics.Geometry;
import physics.LineSegment;

public class Board {
	private final LineSegment top = new LineSegment(0,0,20,0);
	private final LineSegment right = new LineSegment(20,0,20,20);
	private final LineSegment bottom = new LineSegment(20,20,0,20);
	private final LineSegment left = new LineSegment(0,20,0,0);
	private final List<LineSegment> walls = Arrays.asList(top, right, bottom, left);
	
	public final int width = 20;
	public final int height = 20;
	
	//The way Harlin designed the min/max position vals in Ball class gives
	//min position value == 1, and max position value == 19 for the ball
	//When width == height == 20, we actually need 20 + 1 spaces in the board
	//because I designate index 0 and 20 of the 2D array to contain the boundary,
	//the actual dimension of the board is 20 + 1, this gives indices 1-19 for the
	//ball to move around.
	public final int picsWidth = 20 + 1; 
	public final int picsHeight = 20 + 1;
	private final String [][]pics = new String[picsHeight][picsWidth];
	//!important this should later be changed to a CONCURRENTHASHMAP with TUPLEs as keys
	
	private int prevBallX;
	private int prevBallY;
	
	
	/**
	 * Exactly what it says bro.
	 */
	private void initBoard() {//draw the boundaries

		for (int i = 0; i < picsHeight; i++) { //rows
			for (int j = 0; j < picsWidth; j++) { //columns
				if (i==0 || i==picsHeight-1 || j==0 || j==picsHeight-1) {
					pics[i][j] = "."; //outer wall
				} else {
					pics[i][j] = " ";
				}
			}
		}
	}
	
	//sets up the boundaries through initBoard
	//sets the location of the ball to be at the centre
	public Board(){
		initBoard();
		prevBallX = 10; //init pos of the ball in Ball class
		prevBallY = 10;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}

	public LineSegment collideWith(Ball ball){
		for (LineSegment wall: walls){
			if (!(Geometry.timeUntilWallCollision(wall, ball.circle, ball.velocity)>0.0)){
				return wall;
			}
		}
		return null;
	}
	
	/**
	 * Draws a ball in appropriate loc and erases
	 * the prev ball in the board
	 * @param ball
	 */
	public void updateBoardPic(Ball ball) {
		double x = ball.getPosX();
		double y = ball.getPosY();
		
		int i = (int) Math.round(x);
		int j = (int) Math.round(y);
		
		pics[i][j] = "o";
		pics[prevBallX][prevBallY] = " ";
		
		prevBallX = i;
		prevBallY = j;
	}
	
	/**
	 * 	
	 * @param ball
	 * @return a String representation of the board and the ball
	 */
	public String getBoardPic(Ball ball) {
		StringBuilder sb = new StringBuilder();
		updateBoardPic(ball);
		for (int i = 0; i < picsHeight; i++) { //rows
			for (int j = 0; j < picsWidth; j++) { //columns
				sb.append(" ");
				sb.append(pics[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
