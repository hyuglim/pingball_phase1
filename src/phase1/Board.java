package phase1;

import java.lang.Float;
import java.lang.Integer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import physics.Geometry;

public class Board {
	
	private String name;
	private Float gravity;
	private Float friction1;
	private Float friction2;
	private Map <Geometry.DoublePair, Gadget> listofGadgets;
	private List<Ball> balls;
	
	public void matching (String line) {
		
		//board name=NAME gravity=FLOAT friction1=FLOAT friction2=FLOAT
		String boardname="(board) (.+?) (.+?) (.+?) (.+?)"; //friction might not be mentioned
		Pattern boardpat=Pattern.compile(boardname);
		Matcher boardMatch=boardpat.matcher(line);
		
		if (boardMatch.find()) {
			this.name=boardMatch.group(2).substring(5); //removes name=
			this.gravity=Float.parseFloat(boardMatch.group(3).substring(9));
			this.friction1=Float.parseFloat(boardMatch.group(4).substring(10));
			this.friction1=Float.parseFloat(boardMatch.group(5).substring(10));
			//sets the board
		}
		
		//board name=NAME gravity=FLOAT
		//fire trigger=NAME action=NAME
		String twoFields="(+.?) (.+?) (.+?)";
		Pattern twoPat=Pattern.compile(twoFields);
		Matcher twoMatch=twoPat.matcher(line);
		if (twoMatch.find()) {
			String twoId=twoMatch.group(1);
			if (twoId.equals("board")) {
				this.name=twoMatch.group(2).substring(5);
				this.gravity=Float.parseFloat(twoMatch.group(3).substring(9));
				this.friction1=(float) 0.025;
				this.friction2=(float) 0.025;
			}
			if (twoId.equals("fire")) {
				String fireTrigger=twoMatch.group(2).substring(8);
				String actionTrigger=twoMatch.group(3).substring(7);
			}
		}
		
		//ball name=NAME x=FLOAT y=FLOAT xVelocity=FLOAT yVelocity=FLOAT
		String ballname="ball (.+?) (.+?) (.+?) (.+?) (.+?)";
		Pattern ballpat=Pattern.compile(ballname);
		Matcher ballMatch=ballpat.matcher(line);
		
		if (ballMatch.find()) {
			String nameBall=ballMatch.group(2).substring(5);
			Float xBall=Float.parseFloat(ballMatch.group(3).substring(2));
			Float yBall=Float.parseFloat(ballMatch.group(4).substring(2));
			Float xVel=Float.parseFloat(ballMatch.group(5).substring(10));
			Float yVel=Float.parseFloat(ballMatch.group(6).substring(10));
			Ball ball=new Ball(nameBall,xBall,yBall,xVel,yVel);
			//do ball stuff
		}
		
		//squareBumper name=NAME x=INTEGER y=INTEGER
		//circleBumper name=NAME x=INTEGER y=INTEGER
		String bumper="(.+?) (.+?) (.+?) (.+?)";
		Pattern bumpPat=Pattern.compile(bumper);
		Matcher bumpMatch=bumpPat.matcher(line);
		if (bumpMatch.find()) {
			String bumperId=bumpMatch.group(1);
			String bumpName=bumpMatch.group(2).substring(5);
			Integer xCord=Integer.parseInt(bumpMatch.group(3).substring(2));
			Integer yCord=Integer.parseInt(bumpMatch.group(4).substring(2));
			if (bumperId.equals("squareBumper")) {
				Geometry.DoublePair cord=new Geometry.DoublePair((double) xCord,(double) yCord);
				listofGadgets.put(cord, new SquareBumper(cord, bumpName));
				//do square stuff
			}
			if (bumperId.equals("circleBumper")) {
				Geometry.DoublePair cord=new Geometry.DoublePair((double) xCord,(double) yCord);
				listofGadgets.put(cord, new CircleBumper(cord,bumpName));
				//do circle stuff
			}
		}
		
		//triangleBumper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
		//leftFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
		//rightFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270 
		String orientObject="(.+?) (.+?) (.+?) (.+?) (.+?)";
		Pattern orientPat=Pattern.compile(orientObject);
		Matcher orientMatch=orientPat.matcher(line);
		if (orientMatch.find()) {
			String orientId=orientMatch.group(1);
			String orientName=orientMatch.group(2).substring(5);
			Integer xCord=Integer.parseInt(orientMatch.group(3).substring(2));
			Integer yCord=Integer.parseInt(orientMatch.group(4).substring(2));
			Integer orientation=Integer.parseInt(orientMatch.group(5).substring(12));
			Geometry.DoublePair cord=new Geometry.DoublePair((double) xCord,(double) yCord);
			if (orientId.equals("triangleBumper")) {
				listofGadgets.put(cord, new TriangularBumper(cord,new Angle(orientation),orientName));
				//stuff
			}
			if (orientId.equals("leftFlipper")) {
				listofGadgets.put(cord, new )
			}
			if (orientId.equals("rightFlipper")) {
				listofGadgets.put(cord, )
			}
		}
		
		//absorber name=NAME x=INTEGER y=INTEGER width=INTEGER height=INTEGER
		String absorb="absorber (.+?) (.+?) (.+?) (.+?) (.+?)";
		Pattern absorbpat=Pattern.compile(absorb);
		Matcher absorbMatch=absorbpat.matcher(line);
		if (absorbMatch.find()) {
			String absorberName=absorbMatch.group(2).substring(5);
			Integer xAbsorb=Integer.parseInt(absorbMatch.group(3).substring(2));
			Integer yAbsorb=Integer.parseInt(absorbMatch.group(4).substring(2));
			Integer wAbsorb=Integer.parseInt(absorbMatch.group(5).substring(6));
			Integer hAbsorb=Integer.parseInt(absorbMatch.group(6).substring(7));
		}
		
	}
	
	public Board (File file) throws IOException {
		
		BufferedReader bfread=new BufferedReader(new FileReader (file));
		String line;
		while ((line=bfread.readLine())!=null) {
			if (line.startsWith("#")||line.equals("")) { //ignores comments, empty lines
				continue;
			}
			else {
				this.matching(line);
			}
		}
		bfread.close();
	}
	
	public Board( String name, Float gravity, Float fric1, Float fric2 ) {
		this.name=name;
		this.gravity=gravity;
		this.friction1=fric1;
		this.friction2=fric2;
		
	}
}
