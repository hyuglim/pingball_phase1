package phase1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TestingParser {
	
	@Test
	public void testBoard() {
		try {
			Board b = new Board(new File("C:\\Users\\Tom\\Desktop\\6.005\\pingball-phase1\\txtboard.pb"));
			assertTrue(b.getFriction1().equals((float) 0.025)); 
			assertTrue(b.getFriction2().equals((float) 0.025));
			assertTrue(b.getName().equals("ExampleB"));
			assertTrue(b.getGravity().equals((float) 10.0));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
