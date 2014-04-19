package phase1;

import org.junit.Test;

import physics.Angle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGadget {
	
	@Test
	public void TestAbsorb() {
		Absorber absorb=new Absorber(new Tuple (1,0), "test", 1,1,0);
		Ball b=new Ball("ball", (float) 1,(float) 0,(float) -1,(float) 0, (float) 0);
		Ball b2=new Ball("ball", (float) 2,(float) 0,(float) -1,(float) 0, (float) 0);
		Ball b3=new Ball("ball", (float) 3,(float) 0,(float) -1,(float) 0, (float) 0);
		System.out.println(absorb.timeUntilCollision(b));
//		System.out.println(absorb.timeUntilCollision(b2));
//		System.out.println(absorb.timeUntilCollision(b3));
//		assertTrue(absorb.timeUntilCollision(b)==1.0);
	}
	
	@Test
	public void TestCirc() {
//		CircleBumper circB=new CircleBumper(new Tuple(0,0),"circ");
//		Ball b=new Ball("ball",(float) 1,(float) 0, (float) -1, (float) 0, (float) 0);
//		System.out.println(circB.timeUntilCollision(b));
//		assertTrue(circB.timeUntilCollision(b)==0); //takes no time to get there
//		Ball b2=new Ball("ball",(float) 2,(float) 0, (float) -1, (float) 0, (float) 0);
//		System.out.println(circB.timeUntilCollision(b2));
//		assertTrue(circB.timeUntilCollision(b2)==1.0);
	}
	
	@Test
	public void TestTriangle() {
		TriangularBumper triangB=new TriangularBumper(new Tuple (0,0), new Angle((float)0), "test", (float) 0);
		assertTrue(triangB.getName().equals("test"));
		Ball b=new Ball("ball", (float) 1, (float) 0, (float) -1, (float) 0, (float) 0);
		System.out.println(triangB.timeUntilCollision(b));
	}
	
//	public String getName();
//	
//    public void addTrigger(Gadget gadget);
//    
//    public double timeUntilCollision(Ball ball);
//    
//    public void collide(Ball ball, double timeToGo, Board board);
}
