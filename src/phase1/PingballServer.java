package phase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Server has two kinds of threads
 * The first kind continuously waits for the user to input join commands
 * The second kind communicates with the client through the socket
 * @author jonathan
 *
 */
public class PingballServer {

	private final ServerSocket serverSocket;
	//HashMap<String, Tuple> created when joining the boards is as follows:
	//
	//                 top      bot  left right   top   bot   left right
	//{ "board1" : ([neighbor1, n2, n3,   n4] , [true, true, true, true], board1's socket)
	//  "neighbor1": ([null, client1, null, null],[false, true, false, false], neighbor1's socket)
	//  "board3" : ([n1,  null,   null, n4]   , [true, false, true, false], board3's socket)
	//   ... }
	// Tuple has a list of strings and a list of booleans
	private ConcurrentHashMap<String, Triple<List<String>,List<Boolean>,Socket>> neighbors 
			= new ConcurrentHashMap<String, Triple<List<String>,List<Boolean>,Socket>>();

	
	/**
	 * 
	 * @param port
	 * @throws IOException
	 */
	public PingballServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		
	}
	
	
	/**
	 * join boards horizontally
	 * @param left
	 * @param right
	 * @throws IllegalArgumentException
	 */
	private void makeHorizNeighbors(String left, String right) throws IllegalArgumentException{
		if (!neighbors.contains(left) || !neighbors.contains(right)) {
			throw new IllegalArgumentException("cannot join uncreated board");
		}
		
		try {
			// send message to the left board
			setNeighborBoards(left, right);
			
			// send message to the right board
			setNeighborBoards(right, left);
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * join boards vertically
	 * @param top
	 * @param bottom
	 */
	private void makeVerticNeighbors(String top, String bottom) {
		if (!neighbors.contains(top) || !neighbors.contains(bottom)) {
			throw new IllegalArgumentException("cannot join uncreated board");
		}
		
		try {
			// send message to the top board
			setNeighborBoards(top, bottom);
			
			// send message to the right board
			setNeighborBoards(bottom, top);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void setNeighborBoards(String s1, String s2) throws IOException {
		List<String> adjBoardNames = neighbors.get(s1).getOne();
		List<Boolean> isInvisible = neighbors.get(s1).getTwo();
		adjBoardNames.set(3, s2);
		isInvisible.set(3, true);

		
	}
	
	/**
	 * the main joinBoards function
	 * @param command
	 */
	private void joinBoards(String command) {
		// sample input: h NAME_left NAME_right
		//               v NAME_top NAME_bottom
		String []words = command.split(" ");
		String first = words[1].split("_")[1];
		String second = words[2].split("_")[1];
		if (words[0].equals("h")) {
			makeHorizNeighbors(first, second);
		} else if (words[0].equals("v")) {
			makeVerticNeighbors(first, second);
		}				
	}

	/**
	 * Run the server, listening for client connections and handling them.
	 * Never returns unless an exception is thrown.
	 * 
	 * @throws IOException if the main server socket is broken
	 *                     (IOExceptions from individual clients do *not* terminate serve())
	 */
	public void serve() throws IOException {
		new Thread() {
			public void run() {
				try {
					
					while(true) {
						Scanner sc = new Scanner(System.in);
				        System.out.println("Enter a join command:");
				        String joinCommand = sc.nextLine();
				        joinBoards(joinCommand);
				        sc.close();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}.start();
		
		while (true) {
			final Socket socket;
			try {
				// block until a client connects
				socket = serverSocket.accept();
				new Thread(){
					public void run(){
						try {
							handleConnection(socket);
						} catch (IOException e) {
							System.out.println("handleConnection failed inside Server");
							e.printStackTrace();
						}
					}
				}.start();

			} catch (Exception e) {
				System.out.println("connection failed");
			} 
		}		
	}

	
	/**
	 * listen for messagees coming from the client
=======
<<<<<<< HEAD
=======
>>>>>>> 3e8b1c355c9e4358c09a0e9fe25a7d46a3fd0cd5


	/**
	 * listen for messagees coming from the client
<<<<<<< HEAD
>>>>>>> 08b7224585ca5231a073122f211da0918a9ed872
>>>>>>> 08567e14651fa91f82db6f0cc6e26ddcda58bfd7
=======
>>>>>>> 3e8b1c355c9e4358c09a0e9fe25a7d46a3fd0cd5
	 * @param socket
	 * @throws IOException
	 */
	private void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		try{      	       	
			
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				
				//When the client first connects, it passes in the name of the board
				String[] tokens = line.split(" ");
				if (tokens[0].equals("name")) {
					String name = tokens[1];
					if (!neighbors.contains(name)) {
						//top, bottom, left, right
						List<String> adjacents = Arrays.asList(null, null, null, null);
						List<Boolean> invisibles = Arrays.asList(false, false, false, false);
						Triple<List<String>, List<Boolean>, Socket> triple 
						= new Triple<List<String>, List<Boolean>,Socket>(adjacents, invisibles, socket);
								
						neighbors.put(name, triple);
					}
				}				
				
				String output = handleRequest(line); 
				if (output != null) {

				}
			}
		} finally {
			out.close();
			in.close();
		}
	}
	
	/**
	 * do something with the client messages
	 * such as choosing the neighbor clients to pass the ball to.
	 * @param input
	 * @return
	 */
	private String handleRequest(String input) {
		
		System.out.println("input from the client: " + input);
		String[] tokens = input.split(" ");
		
		//sample input: name 
		if(tokens[0].equals("name")) {
			return input;
		}
		
		// sample input: hit NAMEofBoard wallNum  NAMEofBall x y xVel yVel
		// wallNum is either 0,1,2,3 -> top, bottom, left, right
		if (tokens[0].equals("hit")) {
			String nameOfBoard = tokens[1];
			int wallNum = Integer.parseInt(tokens[2]);
			String nameOfBall = tokens[3];
					
			double x = Double.parseDouble(tokens[4]);
			double y = Double.parseDouble(tokens[5]);
			double xVel = Double.parseDouble(tokens[6]);
			double yVel = Double.parseDouble(tokens[7]); 	

			// TODO: 
			// first, find the adjacent neighbor
			// second, see if the wall that is hit is invisible
			// third, if so, change the coordinate and send message to the adjacent neighbor
			// sample output: "invisible NAMEofBall x y xVel yVel" or "visible"
			
			String neighbor = neighbors.get(nameOfBoard).getOne().get(wallNum);
			boolean hitInvisible= neighbors.get(nameOfBoard).getTwo().get(wallNum);
			Socket socketSender = neighbors.get(nameOfBoard).getThree();	
			Socket socketReceiver = neighbors.get(neighbor).getThree();
			PrintWriter outSender;
			PrintWriter outReceiver;
			try {
				outSender = new PrintWriter(socketSender.getOutputStream(), true);
				outReceiver = new PrintWriter(socketReceiver.getOutputStream(), true);
				if (hitInvisible) {
					String msgToSender = "delete " + nameOfBall + " " + x + " " + y + " " + xVel + " " + yVel;
					String msgToReceiver = "create " + nameOfBall + " " + x + " " + y + " " + xVel + " " + yVel;
					outSender.println(msgToSender);
					outReceiver.println(msgToReceiver);
				} else {
					outSender.println("visible");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} 

		// Should never get here--make sure to return in each of the valid cases above.
		throw new UnsupportedOperationException();
	}

	

	/**
	 * Main method checks for port argument and handles connection
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 10987; // default port

		Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));

		try {
			//command line parsing
			while ( ! arguments.isEmpty()) {
				String flag = arguments.remove();
				try {
					if (flag.equals("--port")) {
						port = Integer.parseInt(arguments.remove());
						System.out.println("port: " + port);
					}

				} catch (Exception e) {
					e.printStackTrace();
					throw new IllegalArgumentException();
				}
			}

		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			System.err.println(iae.getMessage());
			System.err.println("usage: PingballClient [--host HOST] [--port PORT] FILE");
			return;
		}

		try {
			runPingballServer(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * starts the server
	 * @param port
	 * @throws IOException
	 */
	public static void runPingballServer(int port) throws IOException{
		PingballServer s = new PingballServer(port);
		s.serve();
	}
	//in serve, listen for a connection 

}
