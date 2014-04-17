package phase1;
import physics.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Board b = null;
        try{
            b = new Board(new File("C:\\Users\\Harlin\\pingball-phase1\\sampleBoard1.pb"));
            System.out.println(b.display());
            System.out.println(b.getPositionofGadgets());
        } catch(Exception e){
            e.printStackTrace();
        }
        LineSegment a = new LineSegment(0,0,2,0);
        while(true){
            try{
            Thread.sleep(100);}catch(Exception e){e.printStackTrace();}
            b.moveAllBalls();
           System.out.println(b.display());
        }
    }
    
}
