package phase1;
 
 
import java.lang.Float;
import java.lang.Integer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
                String word= "\\s*([a-zA-Z0-9_-]+\\s*=\\s*[a-zA-Z0-9\\._-]+)\\s*"; //missing negative sign, underscore
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
                //board name=NAME gravity=FLOAT friction1=FLOAT friction2=FLOAT (last two optional
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
                }
                
                //ball name=NAME x=FLOAT y=FLOAT xVelocity=FLOAT yVelocity=FLOAT
                if (id.equals("ball")) {
                	String ballName=names.get(0);
                	Float xBall=Float.parseFloat(names.get(1));
                	Float yBall=Float.parseFloat(names.get(2));
                	Float xVel=Float.parseFloat(names.get(3));
                	Float yVel=Float.parseFloat(names.get(4));
                	balls.put(ballName, new Ball(ballName,xBall,yBall,xVel,yVel));
                }
                //squareBumper name=NAME x=INTEGER y=INTEGER
                if (id.equals("squareBumper")) {
                	String sName=names.get(0);
                	Integer xSquare=Integer.parseInt(names.get(1));
                	Integer ySquare=Integer.parseInt(names.get(2));
                	Geometry.DoublePair sCord=new Geometry.DoublePair((float) xSquare,(float) ySquare);
                	listofGadgets.put(sCord, new SquareBumper(sCord,sName));
                }
                //circleBumper name=NAME x=INTEGER y=INTEGER
                if (id.equals("circleBumper")) {
                	String cName=names.get(0);
                	Integer xCircle=Integer.parseInt(names.get(1));
                	Integer yCircle=Integer.parseInt(names.get(2));
                	Geometry.DoublePair cCord=new Geometry.DoublePair((float) xCircle,(float) yCircle);
                	listofGadgets.put(cCord, new CircleBumper(cCord,cName));
                }
                //triangleBumper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("triangleBumper")) {
                	String tName=names.get(0);
                	Integer xTriang=Integer.parseInt(names.get(1));
                	Integer yTriang=Integer.parseInt(names.get(2));
                	Integer oTriang=Integer.parseInt(names.get(3));
                	Geometry.DoublePair tCord=new Geometry.DoublePair((float) xTriang, (float) yTriang);
                	listofGadgets.put(tCord, new TriangularBumper(tCord, new Angle((float) oTriang), tName));
                }
                //leftFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("leftFlipper")) {
                	String lfName=names.get(0);
                	Integer xLF=Integer.parseInt(names.get(1));
                	Integer yLF=Integer.parseInt(names.get(2));
                	Integer oLF=Integer.parseInt(names.get(3));
                	Geometry.DoublePair lfCord=new Geometry.DoublePair((float) xLF, (float) yLF);
                	listofGadgets.put(lfCord, new LeftFlipper(lfCord, lfName, new Angle((float) oLF)));
                }
                //rightFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("rightFlipper")) {
                	String rfName=names.get(0);
                	Integer xRF=Integer.parseInt(names.get(1));
                	Integer yRF=Integer.parseInt(names.get(2));
                	Integer oRF=Integer.parseInt(names.get(3));
                	Geometry.DoublePair rfCord=new Geometry.DoublePair((float) xRF, (float) yRF);
                	listofGadgets.put(rfCord, new RightFlipper(rfCord, rfName, new Angle((float) oRF)));
                }
                //absorber name=NAME x=INTEGER y=INTEGER width=INTEGER height=INTEGER
                if (id.equals("absorber")) {
                	String aName=names.get(0);
                	Integer xAbsorb=Integer.parseInt(names.get(1));
                	Integer yAbsorb=Integer.parseInt(names.get(2));
                	Integer wAbsorb=Integer.parseInt(names.get(3));
                	Integer hAbsorb=Integer.parseInt(names.get(4));
                	Geometry.DoublePair aCord=new Geometry.DoublePair((float) xAbsorb,(float) yAbsorb);
                	listofGadgets.put(aCord, new Absorber(aCord,aName,wAbsorb,hAbsorb));
                }
                //fire trigger=NAME action=NAME
                if (id.equals("fire")) {
                	//what to do with fire trigger
                }
 
        }
 
 
        /**
         * Initializes a new Board and all the Gadgets in it from a .pb File.
         * @param file
         * @throws IOException
         */
        public Board (File file) throws IOException {
                BufferedReader bfread=new BufferedReader(new FileReader (file));
                String line;
                //initiate balls and gadgets
                balls=new HashMap <String, Ball>();
                listofGadgets=new HashMap <Geometry.DoublePair, Gadget>();
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
        
        /*
         * LEARN FROM TA TODAY:
         * OKAY TO SPAWN A BALL JUST AT THE BOARDER OF THE NEIGHBOUR BOARD AT THE START
         * OF THE NEXT TIMESTEP.
         * OKAY TO CALL TIME_TILL_COLLISION ON EACH BALL WITH RESPECT TO EACH GADGET
         * AND THEN KEEP TRACK OF TIME TO GO
         */
 
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
}
