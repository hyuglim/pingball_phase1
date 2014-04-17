package phase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Communicator implements Runnable{
	private Socket clientSocket = null;
	private Board board = null;
	
	/**
	 * Each communicator thread has a socket and a board
	 * @param socket
	 * @param board
	 */
	public Communicator(Socket socket, Board board) {
		this.clientSocket = socket;
		this.board = board;
	}
	

	public void run() {
		try{
			//if connection successful
			handleConnection(clientSocket);
			clientSocket.close();
			
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}
	}
	
	/**
	 * getting message from the server
	 * @param socket
	 * @throws IOException
	 */
	private void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		try{      	
			//tell the server name of the board
			out.println("name " + board.getName());
			
			new Thread(){
				public void run() {
					if(true)//if ball hits a wall
						synchronized(out){
							// sample output: hit NAMEofBoard wallNum  NAMEofBall x y xVel yVel
							out.println("hit NAMEOFBOARD 0 BALL 1 2 1 2 ");
						}						
				}
			}.start();
			
			//while server inputstream isn't closed
			for (String line = in.readLine(); line != null; line = in.readLine()) { 
				String output = handleRequest(line); 
				if (output != null) {
					// should never get here; client never closes
					if (output.equals("server says I should not exist anymore...")) {
						return;
					}
					
					if(output.equals("hello")) {
						synchronized(out) {
							out.println("client!");
						}						
					}
				}
				
				/**SOME METHOD IN BOARD TO CHECK IF A WALL IS HIT
				 * 
				 * if (board.hitAWall()) {
				 * 		String message = "" + whichBall + " " + whichWall
				 * 		out.println(message);
				 * }
				 */
				
				
				out.println("send message to the server");
			}
		} finally {
			out.close();
			in.close();
		}
	}
	
	/**
	 * 
	 * @param input
	 * @return a String telling the Client what to do
	 */
	private String handleRequest(String input) {

		String[] tokens = input.split(" ");

		//CONFIRMING IF BALL HIT AN INVISIBLE WALL
		if(tokens[0].equals("delete")) {
			// sample input: invisible NAMEofBALL x y xVel yVel
			String nameOfBall = tokens[1];
			double x = Double.parseDouble(tokens[2]);
			double y = Double.parseDouble(tokens[3]);
			double xVel = Double.parseDouble(tokens[4]);
			double yVel = Double.parseDouble(tokens[5]);
			// TODO: get rid of the ball with that name     
			
			
			return null;
		}
		if(tokens[0].equals("visible")) {
			// DON'T DO ANYTHING. BUSINESS AS USUAL            		                		
		}
		if(tokens[0].equals("newBall")) {
			double x = Double.parseDouble(tokens[1]);
			double y = Double.parseDouble(tokens[2]);
			// TODO: ADD A NEW BALL AT X,Y LOC IN THE CLIENT  
			return null;
		}   
		
		//DEBUGGING
		if(tokens[0].equals("hello")) {
			return "world";
		}
		
		

		// Should never get here--make sure to return in each of the valid cases above.
		throw new UnsupportedOperationException();
	}
}
