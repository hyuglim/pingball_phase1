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
	
	private void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		try{      	
			//tell the server name of the board
			out.println("name " + board.getName());
			
			//while server inputstream isn't closed
			for (String line = in.readLine(); line != null; line = in.readLine()) { 
				String output = handleRequest(line); 
				if (output != null) {
					// should never get here; client never closes
					if (output.equals("server says I should not exist anymore...")) {
						return;
					}
					
					if(output.equals("hello")) {
						out.println("client!");
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

	private String handleRequest(String input) {

		String[] tokens = input.split(" ");

		//CONFIRMING IF BALL HIT AN INVISIBLE WALL
		if(tokens[0].equals("invisible")) {
			// sample input: invisible NAME
			String nameOfBall = tokens[1];
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
