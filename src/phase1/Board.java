package phase1;
import java.lang.Float;
import java.lang.Integer;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import physics.*;

/**
 * Describes a board that is used for a pingball game. It is 20L x 20L in size.
 * @author Harlin
 *
 */
public class Board {
        private String name;
        private Float gravity;
        private Float friction1;
        private Float friction2;
        private Map<Tuple, Gadget> positionofGadgets = new ConcurrentHashMap <Tuple, Gadget>();
        private Map<String, Gadget> nameofGadgets = new ConcurrentHashMap <String, Gadget>();
        private Map<String, Ball> balls = new ConcurrentHashMap <String, Ball>();
        private String[][] state = new String[22][22];
        private final List<Wall> walls = Arrays.asList(new Wall(0), new Wall(1), new Wall(2), new Wall(3));
        private final String[] neighbors= new String[4];
        
        /**
         * 
         * @return number of balls
         */
        public int getBallsSize() {
        	return balls.size();
        }
        
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
         * @returns the balls in the board at this step
         */
        public Map<String, Ball> getBalls() {
                return balls;
        }
        
        /**
         * clears all the balls associated with this board.
         */
        public void clearAllBalls(){
            balls.clear();
        }
        
        /**
         * @returns the positionofGadgets
         */
        public Map<Tuple, Gadget> getPositionofGadgets(){
            return positionofGadgets;
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
                                name=names.get(0);
                                gravity=Float.parseFloat(names.get(1));
                                //scale gravity to our time step of 50ms
                                gravity= gravity*((float)0.05);
                                this.friction1=(float) 0.025;
                                this.friction2=(float) 0.025;
                               
                        }
                        else {
                                this.name=names.get(0);
                                this.gravity=Float.parseFloat(names.get(1));
                                //scale gravity to our time step of 50ms
                                gravity= gravity*((float)0.05);
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
                        balls.put(ballName, new Ball(ballName,xBall,yBall,xVel,yVel, gravity));
                }
                
                //squareBumper name=NAME x=INTEGER y=INTEGER
                if (id.equals("squareBumper")) {
                    String sName=names.get(0);
                    Integer xSquare=Integer.parseInt(names.get(1));
                    Integer ySquare=Integer.parseInt(names.get(2));
                    Tuple sCord =new Tuple(xSquare, ySquare);
                    SquareBumper sBump= new SquareBumper(sCord,sName,this.getGravity());
                    positionofGadgets.put(sCord, sBump);
                    nameofGadgets.put(sName, sBump);
                }
                //circleBumper name=NAME x=INTEGER y=INTEGER
                if (id.equals("circleBumper")) {
                    String cName=names.get(0);
                    Integer xCircle=Integer.parseInt(names.get(1));
                    Integer yCircle=Integer.parseInt(names.get(2));
                    Tuple cCord =new Tuple(xCircle, yCircle);
                    CircleBumper cBump = new CircleBumper(cCord,cName);
                    positionofGadgets.put(cCord, cBump);
                    nameofGadgets.put(cName, cBump);
                }
                //triangleBumper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("triangleBumper")) {
                    String tName=names.get(0);
                    Integer xTriang=Integer.parseInt(names.get(1));
                    Integer yTriang=Integer.parseInt(names.get(2));
                    Integer oTriang=Integer.parseInt(names.get(3));
                    Tuple tCord =new Tuple(xTriang, yTriang);
                    TriangularBumper tBump = new TriangularBumper(tCord, new Angle((float) oTriang), tName,this.getGravity());
                    positionofGadgets.put(tCord, tBump);
                    nameofGadgets.put(tName, tBump);
                }
                //leftFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("leftFlipper")) {
                    String lfName=names.get(0);
                    Integer xLF=Integer.parseInt(names.get(1));
                    Integer yLF=Integer.parseInt(names.get(2));
                    Integer oLF=Integer.parseInt(names.get(3));
                    Tuple lfCord =new Tuple(xLF, yLF);
                    LeftFlipper lfBump = new LeftFlipper(lfCord, lfName, new Angle((float) oLF),this.getGravity());
                    positionofGadgets.put(lfCord, lfBump);
                    nameofGadgets.put(lfName, lfBump);
                }
                //rightFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("rightFlipper")) {
                    String rfName=names.get(0);
                    Integer xRF=Integer.parseInt(names.get(1));
                    Integer yRF=Integer.parseInt(names.get(2));
                    Integer oRF=Integer.parseInt(names.get(3));
                    Tuple rfCord =new Tuple(xRF, yRF);
                    RightFlipper rfBump = new RightFlipper(rfCord, rfName, new Angle((float) oRF),this.getGravity());
                        positionofGadgets.put(rfCord, rfBump);
                        nameofGadgets.put(rfName, rfBump);
                }
               // absorber name=NAME x=INTEGER y=INTEGER width=INTEGER height=INTEGER
                if (id.equals("absorber")) {
                    String aName=names.get(0);
                    Integer xAbsorb=Integer.parseInt(names.get(1));
                    Integer yAbsorb=Integer.parseInt(names.get(2));
                    Integer wAbsorb=Integer.parseInt(names.get(3));
                    Integer hAbsorb=Integer.parseInt(names.get(4));
                    Tuple aCord =new Tuple(xAbsorb, yAbsorb);
                    Absorber aBump = new Absorber(aCord,aName,wAbsorb,hAbsorb,this.getGravity());
                        positionofGadgets.put(aCord, aBump);
                        nameofGadgets.put(aName, aBump);                                  
                }
                //fire trigger=NAME action=NAME
                if (id.equals("fire")) {
                    String trigName = names.get(0);
                    String actName = names.get(1);
                    Gadget trigGad = nameofGadgets.get(trigName);
                    Gadget actGad = nameofGadgets.get(actName);
                    trigGad.addTrigger(actGad);
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
                positionofGadgets.put(new Tuple(-1, 21), walls.get(0));
                positionofGadgets.put(new Tuple(-1, -1), walls.get(1));
                positionofGadgets.put(new Tuple(21, -1), walls.get(2));
                positionofGadgets.put(new Tuple(21, 21), walls.get(3));
                while ((line=bfread.readLine())!=null) {
                  //empty lines with just spaces will be ignored
                        if (line.equals("")) {
                                continue;
                        }
                        if (line.startsWith("#")) { //ignores comments
                                continue;
                        }
                        else {
                                this.matching(line); 
                        }
                }
                bfread.close();
        }
        
        
        /**
         * Updates the state of the board at the moment, including the orientation of flippers
         * and position of balls.
         */
        public void updateState(){          
            for (Tuple pos: positionofGadgets.keySet()){
                Gadget gad = positionofGadgets.get(pos);
                if (gad.getClass().equals(SquareBumper.class)){
                        //draw squarebumpers
                        state[pos.x+1][pos.y+1] = "#";
                    } else if (gad.getClass().equals(CircleBumper.class)){
                        //print circlebumpers
                        state[pos.x+1][pos.y+1] = "O";
                    } else if (gad.getClass().equals(TriangularBumper.class)){
                        //print triangular bumpers
                        if (((TriangularBumper)gad).orientation.equals(Angle.ZERO)||((TriangularBumper)gad).orientation.equals(Angle.DEG_180)){
                            state[pos.x+1][pos.y+1] = "/";
                        } else {
                            state[pos.x+1][pos.y+1] = "\\";
                        }
                    } else if (gad.getClass().equals(Absorber.class)){
                        //print absorbers
                        for(int w = 0; w <((Absorber)gad).width; w++){
                            for (int h = 0; h < ((Absorber)gad).height; h++){
                                state[pos.x+1+w][pos.y+1+h] = "=";        
                            }
                        } 
                    } else {
                        //draw the flippers
                        String[] box = null;
                        if (gad.getClass().equals(LeftFlipper.class)){
                            LeftFlipper LFgad = (LeftFlipper) gad;
                            box = LFgad.showOrientation();
                        } else if (gad.getClass().equals(RightFlipper.class)){
                            RightFlipper RFgad = (RightFlipper) gad;
                            box = RFgad.showOrientation();
                        }
                        //flippers are a bitch.
                        if (box!=null){
                            state[pos.x+1][pos.y+1] = box[0];
                            state[pos.x+2][pos.y+1] = box[1];
                            state[pos.x+1][pos.y+2] = box[2];
                            state[pos.x+2][pos.y+2] = box[3];
                        }        
                    }
                }
      
            for (int j =-1; j<21; j++){
                for(int i=-1; i<21; i++){
                    //draw the outside walls
                    if (i==-1 || j==-1 || i==20 || j==20){
                        state[i+1][j+1] = ".";
                        //if this board has neighbors, show the name.
                        if (neighbors[0]!=null){
                            state[i+1][-1] = String.valueOf(neighbors[0].charAt(i+1));
                        }
                        if (neighbors[1]!=null){
                            state[i+1][20] = String.valueOf(neighbors[1].charAt(i+1));
                        }
                        if (neighbors[2]!=null){
                            state[-1][j+1] = String.valueOf(neighbors[2].charAt(j+1));
                        }
                        if (neighbors[3]!=null){
                            state[20][j+1] = String.valueOf(neighbors[3].charAt(j+1));
                        }
                    } else if(state[i+1][j+1] ==null || state[i+1][j+1].equals("*")){
                        //clear non-gadget space and previous ball positions
                        state[i+1][j+1] = " ";
                    }
                }
            }
            
            for (Ball b: balls.values()){
                //draw the balls. should be the last step.
                Geometry.DoublePair pos = b.getPosition();
                state[(int)pos.d1+1][(int)pos.d2+1] = "*";
            }
        }
        
        /**
         * Show which boards are adjacent to this board.
         * @param wallNum is either 0, 1, 2, 3 -> each corresponding to top, bottom, left, right walls
         */
        
        /**
         * Tells the board who its new neighbor is.
         * @param wallNum indicates which wall has become invisible
         * @param neighbor name of the neighboring board
         */
        public void giveNeighborsName(int wallNum, String neighbor){
            neighbors[wallNum] = neighbor;
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
            String result ="";
            updateState();
            for (int j =-1; j<21; j++){
                for(int i=-1; i<21; i++){
                    result += state[i+1][j+1];
                }
                result += "\r\n";
            }
                return result;
        }
 
        /**
         * Takes a ball out of this board.
         * @param ballname name of the Ball that is to be removed from this board.
         */
        public void deleteBall(String ballname){
            balls.remove(ballname);
        }
        
        /**
         * Inserts a ball into the board.
         * @param ballname name of the Ball
         * @param x starting x-coordinate of the ball
         * @param y starting y-coordinate of the ball
         * @param xVel starting x-velocity of the ball
         * @param yVel starting y-velocity of the ball
         */
        public void insertBall(String ballname, Float x, Float y, Float xVel, Float yVel){
            balls.put(ballname, new Ball(ballname, x, y, xVel, yVel, gravity));
        }
 
        /**
         * Simulates the movements of all balls in this board for one game step.
         */
        public void moveAllBalls(){
            for (Ball b: balls.values()) {
                //elongates time step due to friction.
                moveOneBall(b, 1.0+this.friction1 +this.friction2);
            }
        }
 
        /**
         * Moves one ball in this board for however much time is left in this one game step
         * @param b ball that is being moved
         * @param timetoGo time left in this one step for this one ball
         */
        public void moveOneBall(Ball b, double timetoGo) {
            if (timetoGo<=0) return;
            
            Gadget gadgetToCollideFirst = null;
            double timeUntilCollision = timetoGo;
            
            //loop through the gadgets and find the closest gadget in the path.
            for (Gadget gad:positionofGadgets.values()) {
                if (gad.timeUntilCollision(b) < timeUntilCollision) {
                    gadgetToCollideFirst = gad;
                    timeUntilCollision = gad.timeUntilCollision(b);
                }
            }

            if (timeUntilCollision < timetoGo){
                //call collide method of the gadget
                gadgetToCollideFirst.collide(b, timetoGo, this);
            } else{
                //when the ball doesn't collide with any gadgets, just move in empty space
                b.move(timetoGo);
            }
        }
 
        /**
         * Communicate with the client thread whether a ball is hitting a wall or not.
         * The return message should either be empty(for no collision)
         * or be in the format of: 
         * "hit NAMEofBoard wallNum  NAMEofBall x y xVel yVel"
         * where wallNum is either 0, 1, 2, 3 -> each corresponding to top, bottom, left, right walls
         * @returns message for the client thread
         */
        public String whichWallGotHit(){
            String message ="";
            for (Ball b: balls.values()){
                for (Wall w: walls){
                    if (!(w.timeUntilCollision(b) > 1)){
                        message += "hit "+ name+ " "+ w.getWallNum()+ " " + b.name+ " " + 
                    + b.getPosition().d1+ " "+ b.getPosition().d2+ " " +20*b.velocity.x()+ " "+ 20*b.velocity.y()+ "\n"; 
                    }
                }
            }            
            return message;     
        }
}
