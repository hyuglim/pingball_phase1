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

import physics.*;

import physics.Geometry;

public class Board {
	
	private String name;
	private Float gravity;
	private Float friction1;
	private Float friction2;
	private Map <Geometry.DoublePair, Gadget> listofGadgets;
	private List<Ball> balls;
	
	public String equate(String equation) {
		String [] sArray=equation.split("=");
		return sArray[1];
	}
	
	public void test() {
		String s="board name=sampleBoard2_2 gravity=20.0 friction1=0.020 friction2=0.020";
		this.matching(s);
	}
	
	public void matching (String line) {
		
		String[] sArray=line.split(" ");
		
		//board name=NAME gravity=FLOAT
		//fire trigger=NAME action=NAME
		String id=sArray[0];
		if (sArray.length==3) {
			String word1=sArray[1];
			String word2=sArray[2];
			if (id.equals("fire")) {
				String trigName=this.equate(word1);
				String actName=this.equate(word2);
			}
			if (id.equals("board")) {
				String boardName=this.equate(word1);
				Float boardGravity=Float.parseFloat(this.equate(word2));
				this.name=boardName;
				this.gravity=boardGravity;
				this.friction1=(float) 0.025;
				this.friction2=(float) 0.025;
			}
		}
		
		//squareBumper name=NAME x=INTEGER y=INTEGER
		//circleBumper name=NAME x=INTEGER y=INTEGER
		if (sArray.length==4) {
			String word1=sArray[1];
			String word2=sArray[2];
			String word3=sArray[3];
			String bumpName=this.equate(word1);
			Integer xCord=Integer.parseInt(this.equate(word2));
			Integer yCord=Integer.parseInt(this.equate(word3));
			Geometry.DoublePair cord=new Geometry.DoublePair(xCord, yCord);
			if (id.equals("squareBumper")) {
				listofGadgets.put(cord, new SquareBumper(cord,bumpName));
			}
			if (id.equals("circleBumper")) {
				listofGadgets.put(cord, new CircleBumper(cord, bumpName));
			}
		}
		
		//board name=NAME gravity=FLOAT friction1=FLOAT friction2=FLOAT
		//triangleBumper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
		//leftFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
				
		//rightFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
		if (sArray.length==5) {
			String word1=this.equate(sArray[1]);
			String word2=this.equate(sArray[2]);
			String word3=this.equate(sArray[3]);
			String word4=this.equate(sArray[4]);
			if (id.equals("board")) {
				String boardName=word1;
				Float boardGravity=Float.parseFloat(word2);
				Float friction1=Float.parseFloat(word3);
				Float friction2=Float.parseFloat(word4);
				this.name=boardName;
				this.gravity=boardGravity;
				this.friction1=friction1;
				this.friction2=friction2;
			}
			if (id.equals("triangleBumper")) {
				String triangName=word1;
				Integer xTriang=Integer.parseInt(word2);
				Integer yTriang=Integer.parseInt(word3);
				Integer oTriang=Integer.parseInt(word4);
				Geometry.DoublePair cord=new Geometry.DoublePair(xTriang, yTriang);
				listofGadgets.put(cord, new TriangularBumper(cord, new Angle((double) oTriang), triangName));
			}
			if (id.equals("leftFlipper")) {
				String lflipperName=word1;
				Integer xLfipper=Integer.parseInt(word2);
				Integer yLflipper=Integer.parseInt(word3);
				Integer oLflipper=Integer.parseInt(word4);
				Geometry.DoublePair cord=new Geometry.DoublePair(xLfipper, yLflipper);
				listofGadgets.put(cord, new LeftFlipper(cord,lflipperName, new Angle((double) oLflipper)));
			}
			if (id.equals("rightFlipper")) {
				String rflipperName=word1;
				Integer xRfipper=Integer.parseInt(word2);
				Integer yRflipper=Integer.parseInt(word3);
				Integer oRflipper=Integer.parseInt(word4);
				Geometry.DoublePair cord=new Geometry.DoublePair(xRfipper, yRflipper);
				listofGadgets.put(cord, new RightFlipper(cord,rflipperName, new Angle((double) oRflipper)));
			}

		}
		//absorber name=NAME x=INTEGER y=INTEGER width=INTEGER height=INTEGER
		//ball name=NAME x=FLOAT y=FLOAT xVelocity=FLOAT yVelocity=FLOAT
		if (sArray.length==6) {
			String word1=this.equate(sArray[1]);
			String word2=this.equate(sArray[2]);
			String word3=this.equate(sArray[3]);
			String word4=this.equate(sArray[4]);
			String word5=this.equate(sArray[5]);
			if (id.equals("absorber")) {
				String absorbName=word1;
				Integer xAbsorb=Integer.parseInt(word2);
				Integer yAbsorb=Integer.parseInt(word3);
				Integer wAbsorb=Integer.parseInt(word4);
				Integer hAbsorb=Integer.parseInt(word5);
				Geometry.DoublePair cord=new Geometry.DoublePair(xAbsorb,yAbsorb);
				listofGadgets.put(cord, new Absorber(cord,absorbName,wAbsorb,hAbsorb));
			}
			if (id.equals("ball")) {
				String ballName=word1;
				Float xBall=Float.parseFloat(word2);
				Float yBall=Float.parseFloat(word3);
				Float xVel=Float.parseFloat(word4);
				Float yVel=Float.parseFloat(word5);
			}
			
		}
	}
	
	public Board (File file) throws IOException {
		
		BufferedReader bfread=new BufferedReader(new FileReader (file));
		String line;
		while ((line=bfread.readLine())!=null) {
			//may need to remove a number of spaces before all the words
			while (line.substring(0, 1).equals(" ")) {
				line=line.substring(1);
			}
			//may need to remove a number of spaces after all the words
			while (line.substring(line.length()-1, line.length()).equals(" ")) {
				line=line.substring(0, line.length()-1);
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
	
	
	
}
