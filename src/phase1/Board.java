package phase1;


import java.lang.Float;
import java.lang.Integer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import physics.*;

/**
 * Describes a board that is used for a pingball game. It is 20L x 20L in size.
 *
 */
public class Board {
	private String name;
	private Float gravity;
	private Float friction1;
	private Float friction2;
	private Map <Geometry.DoublePair, Gadget> listofGadgets;
	private Map<String, Ball> balls;


	/**
	 * a helper function that returns whatever string is at the right of an equal sign.
	 * @param equation
	 * @return the right hand side of an equation
	 */
	public String equate(String equation) {
		String [] sArray=equation.split("=");
		for (String s: sArray) {
			System.out.println(s);
		}
		return sArray[1];
	}


	//these are temporary methods we added to test our parser
	/*
	public static void main(String[] args) {
		String s="  absorber name=Abs x=0 y=19 width=20 height=1";
	//	Board b= new Board("try", (float) 0,(float) 0,(float) 0);
	 */
	/**
	 * @returns the name of the board
	 */
	public String getName() {
		return name;
	}

	/**
	 * @returns the gravity of the board
	 */
	public Float getGravity() {
		return gravity;
	}

	/**
	 * @returns friction1 coeffcient of the board
	 */
	public Float getFriction1() {
		return friction1;
	}

	/**
	 * @returns the friction2 coefficient of the board
	 */
	public Float getFriction2() {
		return friction2;
	}

	/**
	 * @returns the list of gadgets in the board
	 */
	public Map<Geometry.DoublePair, Gadget> getListofGadgets() {
		return listofGadgets;
	}

	/**
	 * @returns the balls in the board at this step
	 */
	public Map<String, Ball> getBalls() {
		return balls;
	}

	/**
	 * Reads the line and gets information about the board from it. 
	 * @param line one line of String read from the file
	 */
	public void matching (String line) {

		String id="";
		String firstWord= "\\s*([a-zA-Z0-9]+)\\s+";
		Pattern firstWordpat=Pattern.compile(firstWord);
		Matcher firstMatcher = firstWordpat.matcher(line);
		
		int counter=0;
		String word= "\\s*([a-zA-Z0-9]+\\s*=\\s*[a-zA-Z0-9\\.]+)\\s*";
		Pattern wordpat=Pattern.compile(word);
		Matcher matcher = wordpat.matcher(line);
		List <String> names=new ArrayList<String>();
		while(matcher.find()){
		    int count = matcher.groupCount();
		    counter+=count;
		    for(int i=0;i<count;i++){
		    	String s=matcher.group(i).replaceAll("\\s+", "").split("=")[1];
		    	names.add(s);
		    }
		}	
		if(firstMatcher.find()){
			id=firstMatcher.group(1);
		}
		if (id.equals("board")) {
			if (counter==2) {
				this.name=names.get(0);
				this.gravity=Float.parseFloat(names.get(1));
				this.friction1=(float) 0.025;
				this.friction2=(float) 0.025;
			}
			else {
				this.name=names.get(0);
				this.gravity=Float.parseFloat(names.get(1));
				this.friction1=Float.parseFloat(names.get(2));
				this.friction2=Float.parseFloat(names.get(3));
			}
			System.out.println("name " + name);
			System.out.println("gravity " + gravity);
			System.out.println("friction1 " + friction1);
			System.out.println("friction2 " + friction2);
		}
		/*
		if (id.equals("ball")) {

		}
		if (id.equals("squareBumper")) {

		}
		if (id.equals("circleBumper")) {

		}
		if (id.equals("triangleBumper")) {

		}
		if (id.equals("leftFlipper")) {

		}
		if (id.equals("rightFlipper")) {

		}
		if (id.equals("absorber")) {

		}
		if (id.equals("fire")) {

		}*/

	}


	/**
	 * Initializes a new Board and all the Gadgets in it from a .pb File.
	 * @param file
	 * @throws IOException
	 */
	public Board (File file) throws IOException {
		BufferedReader bfread=new BufferedReader(new FileReader (file));
		String line;
		while ((line=bfread.readLine())!=null) {
			if (line.equals("")) {
				continue;
			}
			if (line.startsWith("#")) { //ignores comments
				continue;
			}
			else {
				this.matching(line); //empty lines with just spaces will be ignored
			}
		}
		bfread.close();
	}

	/**
	 * Display the board! The rules are as follows:
	 * Ball: "*"
	 * Square bumper: "#"
	 * Circle bumper: "O"
	 * Triangle bumper: "/" for orientation 0 or 180, "\" for orientation 90 or 270
	 * Flipper: "|" when vertical, "-" when horizontal
	 * Absorber: "="
	 * Outer wall: "."
	 * @returns a String representation of the state of the board, including the ball's whereabouts. 
	 */
	public String display(){
		return "";
	}

	/**
	 * Update the list of balls that are on the board at the beginning of time step.
	 * @param b list of balls 
	 */
	public void updateBalls(Map<String, Ball> bls){
		balls = bls;
	}

	/**
	 * Simulates the movements of all balls in this board for one game step.
	 * @returns a message for the client 
	 */
	//this should be the method the client calls for each step
	public String moveAllBalls(){
		//String message;
		for (Ball b: balls.values()) {
			moveOneBall(b, 1.0); 
		}
		return "";
		//hmm we should think about balls colliding with each other.
	}

	/**
	 * Moves one ball in this board for however much time is left in this one game step
	 * @param b ball that is being moved
	 * @param timetoGo time left in this one step for this one ball
	 * @returns a message for the client
	 */
	public String moveOneBall(Ball b, double timetoGo) {
		Gadget gadgetToCollideFirst = null;
		String message = "";
		double timeUntilCollision = timetoGo;
		for (Gadget gad:listofGadgets.values()) {
			if (gad.timeUntilCollision(b)<timeUntilCollision) {
				gadgetToCollideFirst = gad;
				timeUntilCollision = gad.timeUntilCollision(b);
			}
		}
		if (gadgetToCollideFirst instanceof Wall){ // or better yet, check if the name of the gadget is top, bottom, etc
			//  if it's going to collide with a wall, send a message to client with the name of the wall
			// check if it's invisible or not, and react appropriately 
			return "left";
		}
		if (timeUntilCollision < timetoGo) {
			gadgetToCollideFirst.collide(b,timetoGo, this);
		} else{
			b.move(timetoGo);
			//handles the case when you don't collide with any gadgets
		}
		return message;
	}

	/* may or maynot need this helper function....
	/**
	 * updates the position of the ball
	 * first updates the velocity of the balls
	 * then uses updated velocity in the move method to update the postion
	 * of the ball
	 */
	/*public void collideGadget() { 
		for (Ball b: balls) {
			for (Geometry.DoublePair key: listofGadgets.keySet()) {
				listofGadgets.get(key).collide(b);
			}
			b.move();
		}
	}
	 */
	
	public static void main(String[] args) {
		try {
			Board b = new Board(new File("/home/jonathan/Documents/Sophomore_Spring/6.005/workspace/pingball-phase1/src/phase1/testboard.pb"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
