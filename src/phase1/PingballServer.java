package phase1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import physics.*;

public class PingballServer {

	private final ServerSocket serverSocket;
	//private AtomicInteger clientId = new AtomicInteger(0);


	public PingballServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}

	/**
	 * Run the server, listening for client connections and handling them.
	 * Never returns unless an exception is thrown.
	 * 
	 * @throws IOException if the main server socket is broken
	 *                     (IOExceptions from individual clients do *not* terminate serve())
	 */
	public void serve() throws IOException {

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
				System.out.println("client connection failed");
			} 
		}		
	}

	private void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		try{      	       	

			for (String line = in.readLine(); line != null; line = in.readLine()) { //reading client lines
				String output = handleRequest(line); 
				if (output != null) {
					
				}
			}
		} finally {
			out.close();
			in.close();
		}
	}

	private String handleRequest(String input) {

		String[] tokens = input.split(" ");

		//goes into the Server file
		if (tokens[0].equals("h")) {
			String[] firstArg = tokens[1].split("_");
			String leftBoard = firstArg[0];
			String[] secondArg = tokens[2].split("_");
			String rightBoard = secondArg[0];


		}
		if (tokens[0].equals("v")) {

		}

		if (tokens[0].equals("hit")) {
			double x = Double.parseDouble(tokens[1]);
			double y = Double.parseDouble(tokens[2]);

			// TODO: 
				// first, find the adjacent neighbor
			// second, see if the wall that is hit is invisible
			// third, if so, change the coordinate and send message to the adjacent neighbor



			Geometry.DoublePair loc = new Geometry.DoublePair(x, y);

		} 

		// Should never get here--make sure to return in each of the valid cases above.
		throw new UnsupportedOperationException();
	}


	//Main method checks for port argument and handles connection
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

	public static void runPingballServer(int port) throws IOException{
		PingballServer s = new PingballServer(port);
		s.serve();
	}
	//in serve, listen for a connection 

}
