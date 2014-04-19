package phase1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TestingParser {
	
	@Test
	public void testBoard1() {
		try {
			Board b = new Board(new File("C:\\Users\\Tom\\Desktop\\6.005\\pingball-phase1\\sampleBoard1.pb"));
			assertTrue(b.getFriction1().equals((float) 0.020)); 
			assertTrue(b.getFriction2().equals((float) 0.020));
			assertTrue(b.getName().equals("sampleBoard1"));
			assertTrue(b.getGravity().equals((float) 1.0));//0.05*20
			String s="";
			for (Gadget g: b.getPositionofGadgets().values()) {
				s=s.concat(g.getName());
			}
			for (int i=0;i<8;i++) {
				String temp="Square".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
			for (int i=12;i<20;i++) {
				String temp="Square".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
			for (int i=4;i<7;i++) {
				String temp="Circle".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
			for (int i=12;i<16;i++) {
				String temp="Circle".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
			for (int i=1;i<2;i++) {
				String temp="Tri".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testBoard2() {
		try {
			Board b = new Board(new File("C:\\Users\\Tom\\Desktop\\6.005\\pingball-phase1\\sampleBoard2-1.pb"));
			assertTrue(b.getFriction1().equals((float) 0.020)); 
			assertTrue(b.getFriction2().equals((float) 0.020));
			assertTrue(b.getName().equals("sampleBoard2_1"));
			assertTrue(b.getGravity().equals((float) 1.0));
			String s="";
			//checks for all the right tags
			for (Gadget g: b.getPositionofGadgets().values()) {
				s=s.concat(g.getName());
			}
			for (int i=0;i<16;i++) {
				String temp="Square".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
			for (int i=10;i<16;i++) {
				String temp="Circle".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBoard22() {
		try {
			Board b = new Board(new File("C:\\Users\\Tom\\Desktop\\6.005\\pingball-phase1\\sampleBoard2-2.pb"));
			assertTrue(b.getFriction1().equals((float) 0.020)); 
			assertTrue(b.getFriction2().equals((float) 0.020));
			assertTrue(b.getName().equals("sampleBoard2_2"));
			assertTrue(b.getGravity().equals((float) 1.0));
			
			String s="";
			//checks for all the right tags
			for (Gadget g: b.getPositionofGadgets().values()) {
				s=s.concat(g.getName());
			}
			for (int i=4;i<20;i++) {
				String temp="Square".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
			for (int i=4;i<10;i++) {
				String temp="Circle".concat(Integer.toString(i));
				assertTrue(s.contains(temp));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBoard3() {
		try {
			Board b = new Board(new File("C:\\Users\\Tom\\Desktop\\6.005\\pingball-phase1\\sampleBoard3.pb"));
			assertTrue(b.getFriction1().equals((float) 0.025)); 
			assertTrue(b.getFriction2().equals((float) 0.025));
			assertTrue(b.getName().equals("ExampleB"));
			assertTrue(b.getGravity().equals((float) 0.5));
			String s="";
			//checks for all the right tags
			for (Gadget g: b.getPositionofGadgets().values()) {
				s=s.concat(g.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBoard4() {
		try {
			Board b = new Board(new File("C:\\Users\\Tom\\Desktop\\6.005\\pingball-phase1\\sampleBoard4.pb"));
			assertTrue(b.getFriction1().equals((float) 0.025)); 
			assertTrue(b.getFriction2().equals((float) 0.025));
			assertTrue(b.getName().equals("ExampleA"));
			assertTrue(b.getGravity().equals((float) 1.0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	

}
