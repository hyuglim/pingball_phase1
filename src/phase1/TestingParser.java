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
			assertTrue(b.getGravity().equals((float) 20.0));
			String tag1="Square";
			String tag2="Circle";
			String tag3="Tri";
			String tag4="Flip";
			String tag5="Abs";
			for (Gadget g: b.getListofGadgets().values()) {
				System.out.println(g.getName());
				assertTrue(g.getName().contains(tag1)||g.getName().contains(tag2)||g.getName().contains(tag3)||g.getName().contains(tag4)||g.getName().contains(tag5));
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
			assertTrue(b.getGravity().equals((float) 20.0));
			String tag1="Square";
			String tag2="Circle";
			String tag3="Tri";
			String tag4="Flip";
			String tag5="Abs";
			for (Gadget g: b.getListofGadgets().values()) {
				assertTrue(g.getName().contains(tag1)||g.getName().contains(tag2)||g.getName().contains(tag3)||g.getName().contains(tag4)||g.getName().contains(tag5));
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
			assertTrue(b.getGravity().equals((float) 20.0));
			String tag1="Square";
			String tag2="Circle";
			String tag3="Tri";
			String tag4="Flip";
			String tag5="Abs";
			for (Gadget g: b.getListofGadgets().values()) {
				assertTrue(g.getName().contains(tag1)||g.getName().contains(tag2)||g.getName().contains(tag3)||g.getName().contains(tag4)||g.getName().contains(tag5));
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
			assertTrue(b.getGravity().equals((float) 10.0));
			String tag1="Square";
			String tag2="Circle";
			String tag3="Tri";
			String tag4="Flip";
			String tag5="Abs";
			for (Gadget g: b.getListofGadgets().values()) {
				assertTrue(g.getName().contains(tag1)||g.getName().contains(tag2)||g.getName().contains(tag3)||g.getName().contains(tag4)||g.getName().contains(tag5));
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
			assertTrue(b.getGravity().equals((float) 20.0));
			String tag1="Square";
			String tag2="Circle";
			String tag3="Tri";
			String tag4="Flip";
			String tag5="Abs";
			for (Gadget g: b.getListofGadgets().values()) {
				assertTrue(g.getName().contains(tag1)||g.getName().contains(tag2)||g.getName().contains(tag3)||g.getName().contains(tag4)||g.getName().contains(tag5));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	

}
