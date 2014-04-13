package phase1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;


//each client has a board

//main method takes file argument and makes a board from it.
//optionally take host and port, which means it should connect to the server
//and the server will 

//we need a helper method for reading a board file(.pb) 
//and initializing a Board object from that
public class PingballClient implements Runnable{
	
	private Socket client = null;
	private Board myBoard = null;
	private int id;
	private boolean isSinglePlayerMode = true;
	
	

	//multiplayer constructor
	PingballClient(Socket socket, Board board) {
		this.client = socket;
		this.myBoard = board;
		
	}
	
	//singleplayer constructor
	PingballClient(Board board) {
		this.myBoard = board;
	}
	
	
	@Override
	public void run() {
		if (isSinglePlayerMode) {
			singleRun();
		} else {
			// helper method -> call handleConnection and handleRequest
			// meanwhile request id set from the server and put the ball in server hashmap
		}		
	}
	
	private void singleRun() {
		//TODO: do things here
	}
	
	private void multiRun() {
		try{
			//if connection successful
			handleConnection(client);
			
			client.close();
			
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}
	}
	
	/**
     * Handle a single client connection. Returns when client disconnects.
     * 
     * @param socket socket where the client is connected
     * @throws IOException if connection has an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        try{
        	
        	String hello = "Welcome to Pingball. Let the board just sit there, and you can stare at the board";
        	out.println(hello);        	       	
        	
            for (String line = in.readLine(); line != null; line = in.readLine()) { //reading client lines
//                String output = handleRequest(line);
//                if (output != null) {
//                	// writing messages from the server to the client
//
//                	if (output.equals("bye")) { // bye -> close connection
//                		
//                	}  	
//                    
//                }
            }
        } finally {
            out.close();
            in.close();
        }
    }
	
	public int getId() {
		return id;
	}
	
	//called by Server to assign clients id
	public void setId(int id) {
		this.id = id;
	}
    
	public boolean isSinglePlayerMode() {
		return isSinglePlayerMode;
	}

	public void setSinglePlayerMode(boolean isSinglePlayerMode) {
		this.isSinglePlayerMode = isSinglePlayerMode;
	}
	
	public static void main(String[] args) {
		int port = 10987; // default port
		String host = ""; // if no host provided, go to single player mode
		File file = null;

		Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
		try {
			//command line parsing
			while ( ! arguments.isEmpty()) {
				String flag = arguments.remove();
				try {
					if (flag.equals("--host")) {
						host = arguments.remove();
						System.out.println("host: " + host);
					}
					if (flag.equals("--port")) {
						port = Integer.parseInt(arguments.remove());
						System.out.println("port: " + port);
					}
					if (arguments.size()==1) { //the last argument must be file
						file = new File(arguments.remove());
						if ( ! file.isFile()) {
							throw new IllegalArgumentException("file not found: \"" + file + "\"");
						}
					} 
				} catch (NoSuchElementException nsee) {
					throw new IllegalArgumentException("missing argument for " + flag);
				} catch (NumberFormatException nfe) {
					throw new IllegalArgumentException("unable to parse number for " + flag);
				}
			}
			
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			System.err.println(iae.getMessage());
			System.err.println("usage: PingballClient [--host HOST] [--port PORT] FILE");
			return;
		}
		
		System.out.println("port: " + port);
		System.out.println("host: " + host);
		System.out.println("file: " + file);
		
		
		
		//starting threads and shit
		
		//single player mode
		if (host.equals("")) {
			try {
				System.out.println("single player");
				Board board = new Board(file);
				PingballClient player = new PingballClient(board);
				player.setSinglePlayerMode(true);
				new Thread(player).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else { //multiplayer mode
			try {
				System.out.println("multiplayer");
				Board board = new Board(file);
				Socket socket = new Socket("localhost", port);
				PingballClient player = new PingballClient(socket, board);
				new Thread(player).start();
			} catch (Exception e) {
				e.printStackTrace();			
			}
		}				

		
	}
    
}
