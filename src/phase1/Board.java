
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
        private Map<Tuple, Gadget> positionofGadgets=new HashMap <Tuple, Gadget>();
        private Map<String, Gadget> nameofGadgets = new HashMap <String, Gadget>();
        private Map<String, Ball> balls;
        private String[][] state = new String[22][22];


        List<Wall> walls = Arrays.asList(new Wall(0),new Wall(1),new Wall(2),new Wall(3));
        

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
        public Map<Tuple, Gadget> getPositionofGadgets() {
                return positionofGadgets;
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
                        //Geometry.DoublePair sCord=new Geometry.DoublePair((float) xSquare,(float) ySquare);
                        //positionofGadgets.put(sCord, new SquareBumper(sCord,sName));
                        Tuple sCord =new Tuple(xSquare, ySquare);
                        SquareBumper sBump= new SquareBumper(sCord,sName);
                        positionofGadgets.put(sCord, sBump);
                        nameofGadgets.put(sName, sBump);
                    state[xSquare+1][ySquare+1] = "#";
                }
                //circleBumper name=NAME x=INTEGER y=INTEGER
                if (id.equals("circleBumper")) {
                        String cName=names.get(0);
                        Integer xCircle=Integer.parseInt(names.get(1));
                        Integer yCircle=Integer.parseInt(names.get(2));
                        //Geometry.DoublePair cCord=new Geometry.DoublePair((float) xCircle,(float) yCircle);
                        //positionofGadgets.put(cCord, new CircleBumper(cCord,cName));
                        Tuple cCord =new Tuple(xCircle, yCircle);
                        CircleBumper cBump = new CircleBumper(cCord,cName);
                    positionofGadgets.put(cCord, cBump);
                    nameofGadgets.put(cName, cBump);
                    state[xCircle+1][yCircle+1] = "O";
                }
                //triangleBumper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("triangleBumper")) {
                        String tName=names.get(0);
                        Integer xTriang=Integer.parseInt(names.get(1));
                        Integer yTriang=Integer.parseInt(names.get(2));
                        Integer oTriang=Integer.parseInt(names.get(3));
                        //Geometry.DoublePair tCord=new Geometry.DoublePair((float) xTriang, (float) yTriang);
                        //positionofGadgets.put(tCord, new TriangularBumper(tCord, new Angle((float) oTriang), tName));
                        Tuple tCord =new Tuple(xTriang, yTriang);
                        TriangularBumper tBump = new TriangularBumper(tCord, new Angle((float) oTriang), tName);
                    positionofGadgets.put(tCord, tBump);
                    nameofGadgets.put(tName, tBump);
                    if (oTriang.equals(0)||oTriang.equals(180)){
                        state[xTriang+1][yTriang+1] = "/";
                    } else {
                        state[xTriang+1][yTriang+1] = "\\";
                    }
                }
                //leftFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("leftFlipper")) {
                        String lfName=names.get(0);
                        Integer xLF=Integer.parseInt(names.get(1));
                        Integer yLF=Integer.parseInt(names.get(2));
                        Integer oLF=Integer.parseInt(names.get(3));
                        //Geometry.DoublePair lfCord=new Geometry.DoublePair((float) xLF, (float) yLF);
                        Tuple lfCord =new Tuple(xLF, yLF);
                        LeftFlipper lfBump = new LeftFlipper(lfCord, lfName, new Angle((float) oLF));
                    positionofGadgets.put(lfCord, lfBump);
                    nameofGadgets.put(lfName, lfBump);
                    if (oLF.equals(0)){
                        state[xLF+1][yLF+1] = "|";
                        state[xLF+1][yLF+2] = "|";
                    } else if(oLF.equals(90)){
                        state[xLF+1][yLF+1] = "-";
                        state[xLF+2][yLF+1] = "-";
                    } else if(oLF.equals(180)){
                        state[xLF+2][yLF+1] = "|";
                        state[xLF+2][yLF+2] = "|";
                    } else{
                        state[xLF+1][yLF+2] = "-";
                        state[xLF+2][yLF+2] = "-";
                    }
                }
                //rightFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
                if (id.equals("rightFlipper")) {
                        String rfName=names.get(0);
                        Integer xRF=Integer.parseInt(names.get(1));
                        Integer yRF=Integer.parseInt(names.get(2));
                        Integer oRF=Integer.parseInt(names.get(3));
                        //Geometry.DoublePair rfCord=new Geometry.DoublePair((float) xRF, (float) yRF);
                    Tuple rfCord =new Tuple(xRF, yRF);
                    RightFlipper rfBump = new RightFlipper(rfCord, rfName, new Angle((float) oRF));
                        positionofGadgets.put(rfCord, rfBump);
                        nameofGadgets.put(rfName, rfBump);
                    if (oRF.equals(0)){
                        state[xRF+2][yRF+1] = "|";
                        state[xRF+2][yRF+2] = "|";
                    } else if(oRF.equals(90)){
                        state[xRF+1][yRF+2] = "-";
                        state[xRF+2][yRF+2] = "-";
                    } else if(oRF.equals(180)){
                        state[xRF+1][yRF+1] = "|";
                        state[xRF+1][yRF+2] = "|";
                    } else{
                        state[xRF+1][yRF+1] = "-";
                        state[xRF+2][yRF+1] = "-";
                    }
                }
                //absorber name=NAME x=INTEGER y=INTEGER width=INTEGER height=INTEGER
                if (id.equals("absorber")) {
//                        String aName=names.get(0);
//                        Integer xAbsorb=Integer.parseInt(names.get(1));
//                        Integer yAbsorb=Integer.parseInt(names.get(2));
//                        Integer wAbsorb=Integer.parseInt(names.get(3));
//                        Integer hAbsorb=Integer.parseInt(names.get(4));
//                        //Geometry.DoublePair aCord=new Geometry.DoublePair((float) xAbsorb,(float) yAbsorb);
//                    Tuple aCord =new Tuple(xAbsorb, yAbsorb);
//                    Absorber aBump = new Absorber(aCord,aName,wAbsorb,hAbsorb);
//                        positionofGadgets.put(aCord, aBump);
//                        nameofGadgets.put(aName, aBump);
//                        for(int w = 0; w <wAbsorb; w++){
//                            for (int h = 0; h < hAbsorb; h++){
//                                state[xAbsorb+1+w][yAbsorb+1+h] = "=";        
//                            }
//                        }
                }
                //fire trigger=NAME action=NAME
                if (id.equals("fire")) {
                        //what to do with fire trigger
//                    String trigName = names.get(0);
//                    String actName = names.get(1);
//                    //System.out.println(trigName + " " + actName);
//                    Gadget trigGad = nameofGadgets.get(trigName);
//                    Gadget actGad = nameofGadgets.get(actName);
//                    trigGad.addTrigger(actGad);
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
                for (int j =-1; j<21; j++){
                    for(int i=-1; i<21; i++){
                        if (i==-1 || j==-1 || i==20 || j==20){
                            state[i+1][j+1] = ".";
                        } else {
                            state[i+1][j+1]=" ";
                        }
                    }
                }
                //initiate balls and gadgets
                balls=new HashMap <String, Ball>();
                positionofGadgets.put(new Tuple(-1, 21), walls.get(0));
                positionofGadgets.put(new Tuple(-1, -1), walls.get(1));
                positionofGadgets.put(new Tuple(21, -1), walls.get(2));
                positionofGadgets.put(new Tuple(21, 21), walls.get(3));
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
 
        public void updateState(){
            //System.out.println("in updatestate");           
            for (Gadget gad: positionofGadgets.values()){
                Tuple coord =null;
                String[] box = null;
                if (gad.getClass().equals(LeftFlipper.class)){
                    LeftFlipper LFgad = (LeftFlipper) gad;
                    coord = LFgad.coord;
                    box = LFgad.showOrientation();
                } else if (gad.getClass().equals(RightFlipper.class)){
                    RightFlipper RFgad = (RightFlipper) gad;
                    coord = RFgad.coord;
                    box = RFgad.showOrientation();
                }
                if (coord != null && box!=null){
                    state[coord.x+1][coord.y+1] = box[0];
                    state[coord.x+2][coord.y+1] = box[1];
                    state[coord.x+1][coord.y+2] = box[2];
                    state[coord.x+2][coord.y+2] = box[3];
                }
            }
            
            for (Ball b: balls.values()){
                Geometry.DoublePair pos = b.getPosition();
                state[(int)pos.d1+1][(int)pos.d2+1] = "*";
            }
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
            //System.out.println("in display");
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
         * Update the list of balls that are on the board at the beginning of time step.
         * @param b list of balls
         */
        public void deleteBall(String ballname){
            balls.remove(ballname);
        }
        
        public void insertBall(String ballname, Float x, Float y, Float xVel, Float yVel){
            balls.put(ballname, new Ball(ballname, x, y, xVel, yVel));
        }
 
        /**
         * Simulates the movements of all balls in this board for one game step.
         * @returns a message for the client
         */
        //this should be the method the client calls for each step
        public void moveAllBalls(){
                //String message;
            System.out.println("in moveallballs");
            for (Ball b: balls.values()) {
            	System.out.println("ball being used: "+b);
                moveOneBall(b, 1.0);
            }
            //return "";
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
        public void moveOneBall(Ball b, double timetoGo) {
            //System.out.println("in moveoneball "+b.name+" "+timetoGo);
            if (timetoGo<=0) return;
        	System.out.println(b.getPosition());
            Geometry.DoublePair pos = b.getPosition();
            state[(int)pos.d1+1][(int)pos.d2+1] = " ";
            

           
            
            Gadget gadgetToCollideFirst = null;
            String message = "";
            double timeUntilCollision = timetoGo;
            
            for (Gadget gad:positionofGadgets.values()) {
                if (gad.timeUntilCollision(b) < timeUntilCollision) {
                    gadgetToCollideFirst = gad;
                    timeUntilCollision = gad.timeUntilCollision(b);
                }
            }
            //System.out.println("Closest gadget: "+gadgetToCollideFirst);
            //System.out.println("time until collsion: "+timeUntilCollision);
//                if (gadgetToCollideFirst instanceof Wall){ // or better yet, check if the name of the gadget is top, bottom, etc
//                        //  if it's going to collide with a wall, send a message to client with the name of the wall
//                        // check if it's invisible or not, and react appropriately
//                        return "left";
//                }
            if (timeUntilCollision < timetoGo){
                gadgetToCollideFirst.collide(b, timetoGo, this);
            } else{
                //when the ball doesn't collide with any gadgets
                b.move(timetoGo);
            }
           // System.out.println(display());
            //return message;
        }
 
        /**
         *  format: hit NAMEofBoard wallNum  NAMEofBall x y xVel yVel
         *  wallNum is either 0,1,2,3 -> top, bottom, left, right
         * @return output message
         */
        public String whichWallGotHit(){
            String message ="";
            for (Ball b: balls.values()){
                for (Wall w: walls){
                    if (!(w.timeUntilCollision(b) > 1)){
                        message += "hit "+ name+ " "+ w.getWallNum()+ " " + b.name+ " " + 
                    + b.getPosition().d1+ " "+ b.getPosition().d2+ " " +b.velocity.x()+ " "+ b.velocity.y()+ "\n"; 
                    }
                }
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
                        for (Geometry.DoublePair key: positionofGadgets.keySet()) {
                                positionofGadgets.get(key).collide(b);
                        }
                        b.move();
                }
        }
         */
}
