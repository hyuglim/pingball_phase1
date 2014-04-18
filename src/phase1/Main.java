package phase1;
import physics.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Board b = null;
        try{
            b = new Board(new File("C:\\Users\\Harlin\\pingball-phase1\\sampleBoard2-2.pb"));
            System.out.println(b.display());
        } catch(Exception e){
            e.printStackTrace();
        }
        while(true){
            try{
                Thread.sleep(50);
            } catch(Exception e){
                e.printStackTrace();
            }
            b.moveAllBalls();
            System.out.println(b.display());
        }
    }
    
}
