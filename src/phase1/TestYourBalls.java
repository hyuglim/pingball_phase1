package phase1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestYourBalls {
	
	/**
	 * testing position of the ball depending on various speed and gravitational acceleration constant
	 * 
	 * input partition mentioned on top of each test.
	 */
	
	@Test
	//input partition 1: regular speed, no gravity
	public void testBalls1() {
		Ball b = new Ball("OhSoSexy", 0f,0f,100f,100f,0f);
		b.move(1);
		System.out.println(b.getPosition());
		assertTrue(b.getPosition().d1 == 5);
		assertTrue(b.getPosition().d2 == 5);
	}
	
	@Test
	//input partition 2: regular speed on x-axis, no gravity
	public void testBalls2() {
		Ball b = new Ball("OhSoBallzy", 0f,0f,100f,0f,0f);
		b.move(1);
		System.out.println(b.getPosition());
		assertTrue(b.getPosition().d1 == 5);
		assertTrue(b.getPosition().d2 == 0);
	}
	
	@Test
	//input partition 3: regular spped on y-axis, no gravity
	public void testBalls3() {
		Ball b = new Ball("OhSoHarry", 0f,0f,0f,20f,0f);
		b.move(1);
		System.out.println(b.getPosition());
		assertTrue(b.getPosition().d1 == 0);
		assertTrue(b.getPosition().d2 == 1);
	}
	
	@Test
	//input partition 4: regular speed on x, y axis, gravity
	public void testBalls4() {
		Ball b = new Ball("OhSoIsThatSo?", 0f,0f,0f,20f,0f);
		b.move(1);
		System.out.println(b.getPosition());
		assertTrue(b.getPosition().d1 == 0);
		assertTrue(b.getPosition().d2 == 1);
	}
	
	@Test
	//input partition 5: super speed on x and y
	public void testBall5() {
		Ball b = new Ball("OoohSoBig", 0f,0f,1000f,1000f,0f);
		b.move(1);
		System.out.println(b.getPosition());
		assertTrue(b.getPosition().d1 == 19);
		assertTrue(b.getPosition().d2 == 19);
	}
	
	@Test
	//input partition 6: zero x and y
	public void testBall6() {
		Ball b = new Ball("OhSoSmall", 0f,0f,0f,0f,0f);
		b.move(1);
		System.out.println(b.getPosition());
		assertTrue(b.getPosition().d1 == 0);
		assertTrue(b.getPosition().d2 == 0);
	}
	
	@Test
	//input partition 7: super speed and gravity
	public void testBall7() {
		Ball b = new Ball("OhSoManyBallssss", 0f,0f,1000f,1000f,50f);
		b.move(1);
		System.out.println(b.getPosition());
		assertTrue(b.getPosition().d1 == 19);
		assertTrue(b.getPosition().d2 == 19);
	}
	
	@Test
	//input partition 8: regular speed and super gravity
	public void testBall8() {
		Ball b = new Ball("OhSoImpressiveGrowth", 0f,0f,100f,100f,5000000000000000000000f);
		b.move(1);
		System.out.println("d"+b.getPosition());
		assertTrue(b.getPosition().d1 == 5);
		assertTrue(b.getPosition().d2 == 5);
	}
	
}
