package phase1;
import static org.junit.Assert.*;

import org.junit.*;

import java.io.File;

/**
 * For tests regarding reading of board.pb files and initialization of the Board, please 
 * see the TestingParser file.
 */
public class BoardTest {
    Board b;
    
    @Before
    public void beforeClss(){
        try{
            b = new Board(new File("C:\\Users\\Harlin\\pingball-phase1\\sampleBoard1.pb"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void testDisplay(){
        String display = b.display();
        assertTrue(display.contains("#"));
        assertTrue(display.contains("O"));
        assertTrue(display.contains("*"));
        assertTrue(display.contains("."));
        assertTrue(display.contains("|"));
        assertTrue(display.contains("="));
        System.out.println(display);
    }
    
    @Test
    public void testInsertBall(){
        assertFalse(b.getBalls().containsKey("ballsy"));
        b.insertBall("ballsy", (float)5.0, (float)5.0, (float)-6.0, (float)0.0);
        assertTrue(b.getBalls().containsKey("ballsy"));
    }
    
    @Test
    public void testDeleteBall(){
        assertTrue(b.getBalls().containsKey("Ball"));
        b.deleteBall("Ball");
        assertFalse(b.getBalls().containsKey("Ball"));
    }
    
    @Test
    public void testClearAllBalls(){
        assertFalse(b.getBallsSize()==0);
        b.clearAllBalls();
        assertTrue(b.getBallsSize()==0);
    }
    
    @Test
    public void testGiveNeighborsName(){
        b.giveNeighborsName(0, "harlin");
        assertEquals(b.neighbors[0], "harlin");
        b.giveNeighborsName(1, "Tom");
        assertEquals(b.neighbors[1], "Tom");
        b.giveNeighborsName(2, "Jonathan");
        assertEquals(b.neighbors[2], "Jonathan");
        b.giveNeighborsName(3, "Kevin Y Chen");
        assertEquals(b.neighbors[3], "Kevin Y Chen");
        String display = b.display();
        
        System.out.println(display);
    }
}
