//package phase1;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import physics.Angle;
//
//public class Parser {
//    
//    Parser(){
//        
//    }
//    public void matching (String line) {
//        
//        String id="";
//        String firstWord= "\\s*([a-zA-Z0-9]+)\\s+";
//        Pattern firstWordpat=Pattern.compile(firstWord);
//        Matcher firstMatcher = firstWordpat.matcher(line);
//       
//        int counter=0;
//        String word= "\\s*([a-zA-Z0-9_-]+\\s*=\\s*[a-zA-Z0-9\\._-]+)\\s*"; //missing negative sign, underscore
//        Pattern wordpat=Pattern.compile(word);
//        Matcher matcher = wordpat.matcher(line);
//        List <String> names=new ArrayList<String>();
//        while(matcher.find()){
//            int count = matcher.groupCount();
//            counter+=count;
//            for(int i=0;i<count;i++){
//                String s=matcher.group(i).replaceAll("\\s+", "").split("=")[1];
//                names.add(s);
//            }
//        }      
//        if(firstMatcher.find()){
//                id=firstMatcher.group(1);
//        }
//        //board name=NAME gravity=FLOAT friction1=FLOAT friction2=FLOAT (last two optional
//        if (id.equals("board")) {
//                if (counter==2) {
//                        name=names.get(0);
//                        gravity=Float.parseFloat(names.get(1))*0;
//                        System.out.println("gravity: "+gravity);
//                        this.friction1=(float) 0.025;
//                        this.friction2=(float) 0.025;
//                       
//                }
//                else {
//                        this.name=names.get(0);
//                        this.gravity=Float.parseFloat(names.get(1));
//                        gravity=Float.parseFloat(names.get(1))*0;
//                        System.out.println("gravity: "+gravity);
//                        this.friction1=Float.parseFloat(names.get(2));
//                        this.friction2=Float.parseFloat(names.get(3));
//                }
//        }
//       
//        //ball name=NAME x=FLOAT y=FLOAT xVelocity=FLOAT yVelocity=FLOAT
//        if (id.equals("ball")) {
//                String ballName=names.get(0);
//                Float xBall=Float.parseFloat(names.get(1));
//                Float yBall=Float.parseFloat(names.get(2));
//                Float xVel=Float.parseFloat(names.get(3));
//                Float yVel=Float.parseFloat(names.get(4));
//                balls.put(ballName, new Ball(ballName,xBall,yBall,xVel,yVel, gravity));
//        }
//        //squareBumper name=NAME x=INTEGER y=INTEGER
//        if (id.equals("squareBumper")) {
//                String sName=names.get(0);
//                Integer xSquare=Integer.parseInt(names.get(1));
//                Integer ySquare=Integer.parseInt(names.get(2));
//                //Geometry.DoublePair sCord=new Geometry.DoublePair((float) xSquare,(float) ySquare);
//                //positionofGadgets.put(sCord, new SquareBumper(sCord,sName));
//                Tuple sCord =new Tuple(xSquare, ySquare);
//                SquareBumper sBump= new SquareBumper(sCord,sName,this.getGravity());
//                positionofGadgets.put(sCord, sBump);
//                nameofGadgets.put(sName, sBump);
//            state[xSquare+1][ySquare+1] = "#";
//        }
//        //circleBumper name=NAME x=INTEGER y=INTEGER
//        if (id.equals("circleBumper")) {
//                String cName=names.get(0);
//                Integer xCircle=Integer.parseInt(names.get(1));
//                Integer yCircle=Integer.parseInt(names.get(2));
//                //Geometry.DoublePair cCord=new Geometry.DoublePair((float) xCircle,(float) yCircle);
//                //positionofGadgets.put(cCord, new CircleBumper(cCord,cName));
//                Tuple cCord =new Tuple(xCircle, yCircle);
//                CircleBumper cBump = new CircleBumper(cCord,cName);
//            positionofGadgets.put(cCord, cBump);
//            nameofGadgets.put(cName, cBump);
//            state[xCircle+1][yCircle+1] = "O";
//        }
//        //triangleBumper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
//        if (id.equals("triangleBumper")) {
//                String tName=names.get(0);
//                Integer xTriang=Integer.parseInt(names.get(1));
//                Integer yTriang=Integer.parseInt(names.get(2));
//                Integer oTriang=Integer.parseInt(names.get(3));
//                //Geometry.DoublePair tCord=new Geometry.DoublePair((float) xTriang, (float) yTriang);
//                //positionofGadgets.put(tCord, new TriangularBumper(tCord, new Angle((float) oTriang), tName));
//                Tuple tCord =new Tuple(xTriang, yTriang);
//                TriangularBumper tBump = new TriangularBumper(tCord, new Angle((float) oTriang), tName,this.getGravity());
//            positionofGadgets.put(tCord, tBump);
//            nameofGadgets.put(tName, tBump);
//            if (oTriang.equals(0)||oTriang.equals(180)){
//                state[xTriang+1][yTriang+1] = "/";
//            } else {
//                state[xTriang+1][yTriang+1] = "\\";
//            }
//        }
//        //leftFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
//        if (id.equals("leftFlipper")) {
//                String lfName=names.get(0);
//                Integer xLF=Integer.parseInt(names.get(1));
//                Integer yLF=Integer.parseInt(names.get(2));
//                Integer oLF=Integer.parseInt(names.get(3));
//                //Geometry.DoublePair lfCord=new Geometry.DoublePair((float) xLF, (float) yLF);
//                Tuple lfCord =new Tuple(xLF, yLF);
//                LeftFlipper lfBump = new LeftFlipper(lfCord, lfName, new Angle((float) oLF),this.getGravity());
//            positionofGadgets.put(lfCord, lfBump);
//            nameofGadgets.put(lfName, lfBump);
//            if (oLF.equals(0)){
//                state[xLF+1][yLF+1] = "|";
//                state[xLF+1][yLF+2] = "|";
//            } else if(oLF.equals(90)){
//                state[xLF+1][yLF+1] = "-";
//                state[xLF+2][yLF+1] = "-";
//            } else if(oLF.equals(180)){
//                state[xLF+2][yLF+1] = "|";
//                state[xLF+2][yLF+2] = "|";
//            } else{
//                state[xLF+1][yLF+2] = "-";
//                state[xLF+2][yLF+2] = "-";
//            }
//        }
//        //rightFlipper name=NAME x=INTEGER y=INTEGER orientation=0|90|180|270
//        if (id.equals("rightFlipper")) {
//                String rfName=names.get(0);
//                Integer xRF=Integer.parseInt(names.get(1));
//                Integer yRF=Integer.parseInt(names.get(2));
//                Integer oRF=Integer.parseInt(names.get(3));
//                //Geometry.DoublePair rfCord=new Geometry.DoublePair((float) xRF, (float) yRF);
//            Tuple rfCord =new Tuple(xRF, yRF);
//            RightFlipper rfBump = new RightFlipper(rfCord, rfName, new Angle((float) oRF),this.getGravity());
//                positionofGadgets.put(rfCord, rfBump);
//                nameofGadgets.put(rfName, rfBump);
//            if (oRF.equals(0)){
//                state[xRF+2][yRF+1] = "|";
//                state[xRF+2][yRF+2] = "|";
//            } else if(oRF.equals(90)){
//                state[xRF+1][yRF+2] = "-";
//                state[xRF+2][yRF+2] = "-";
//            } else if(oRF.equals(180)){
//                state[xRF+1][yRF+1] = "|";
//                state[xRF+1][yRF+2] = "|";
//            } else{
//                state[xRF+1][yRF+1] = "-";
//                state[xRF+2][yRF+1] = "-";
//            }
//        }
//       // absorber name=NAME x=INTEGER y=INTEGER width=INTEGER height=INTEGER
//        if (id.equals("absorber")) {
//                String aName=names.get(0);
//                Integer xAbsorb=Integer.parseInt(names.get(1));
//                Integer yAbsorb=Integer.parseInt(names.get(2));
//                Integer wAbsorb=Integer.parseInt(names.get(3));
//                Integer hAbsorb=Integer.parseInt(names.get(4));
//                //Geometry.DoublePair aCord=new Geometry.DoublePair((float) xAbsorb,(float) yAbsorb);
//            Tuple aCord =new Tuple(xAbsorb, yAbsorb);
//            Absorber aBump = new Absorber(aCord,aName,wAbsorb,hAbsorb,this.getGravity());
//                positionofGadgets.put(aCord, aBump);
//                nameofGadgets.put(aName, aBump);
//                for(int w = 0; w <wAbsorb; w++){
//                    for (int h = 0; h < hAbsorb; h++){
//                        state[xAbsorb+1+w][yAbsorb+1+h] = "=";        
//                    }
//                }                                    
//        }
//        //fire trigger=NAME action=NAME
//        if (id.equals("fire")) {
//                //what to do with fire trigger
//            String trigName = names.get(0);
//            String actName = names.get(1);
//            //System.out.println(trigName + " " + actName);
//            Gadget trigGad = nameofGadgets.get(trigName);
//            Gadget actGad = nameofGadgets.get(actName);
//            trigGad.addTrigger(actGad);
//        }
//}
//
//}
