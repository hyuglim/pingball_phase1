package phase1;

import java.lang.Float;
import java.lang.Integer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {
	
	private String name;
	private Float gravity;
	private Float friction1;
	private Float friction2;
	private List <Gadgets> listofGadgets;
	private Ball ball;
	
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
				//do square stuff
			}
			if (bumperId.equals("circleBumper")) {
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
			Integer xCord=Integer.parseInt(orientMatch.group(3).substring(2));
			Integer yCord=Integer.parseInt(orientMatch.group(4).substring(2));
			Integer orientation=Integer.parseInt(orientMatch.group(5).substring(12));
			if (orientId.equals("triangleBumper")) {
				//stuff
			}
			if (orientId.equals("leftFlipper")) {
				
			}
			if (orientId.equals("rightFlipper")) {
				
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
		
		//fire trigger=NAME action=NAME
		String firet="fire (.+?) (.+?)";
		Pattern firepat=Pattern.compile(firet);
		Matcher fireMatch=firepat.matcher(line);
		if (fireMatch.find()) {
			String fireTrigger=fireMatch.group(2).substring(8);
			String actionTrigger=fireMatch.group(3).substring(7);
		}
		
	}
	
	public Board (File file) throws IOException {
		
		BufferedReader bfread=new BufferedReader(new FileReader (file));
		String line;
		while ((line=bfread.readLine())!=null) {
			if (line.startsWith("#")) { //ignores comments
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
