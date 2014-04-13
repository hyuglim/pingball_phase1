package phase1;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;


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
			Socket socket = null;
			try {
				// block until a client connects
				socket = serverSocket.accept();
				
				 				
			} catch (Exception e) {
				System.out.println("client connection failed");
			} 
		}		
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
