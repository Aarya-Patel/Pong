import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AngleSegmentTest {

	@Test
	void test() {
		Paddle pad = new Paddle(new Pong(), true);
		// Store the output
		float degree;

		// Traverse through all possible outcomes for the ball to the paddle from the
		// top
		// Since the height of the paddle is 80, our loop runs 80 times
		for (int i = 0; i < 80; i++) {
			// Pixels in range(0,9) result in a range from(45,60)
			if (i >= 0 && i <= 9) {
				degree = pad.angleSegment(i);
				assertTrue(degree >= 45 && degree <= 60);
			}
			// Pixels in range(10,19) result in a range from(30,45)
			else if (i >= 10 && i <= 19) {
				degree = pad.angleSegment(i);
				assertTrue(degree >= 30 && degree <= 45);
			}
			// Pixels in range(20,29) result in a range from(15,30)
			else if (i >= 20 && i <= 29) {
				degree = pad.angleSegment(i);
				assertTrue(degree >= 15 && degree <= 30);
			}
			// Pixels in range(30,49) result in a range from(0,15)
			else if (i >= 30 && i <= 49) {
				degree = pad.angleSegment(i);
				assertTrue(degree >= 0 && degree <= 15);
			}
			// Pixels in range(50,59) result in a range from(15,30)
			else if (i >= 50 && i <= 59) {
				degree = pad.angleSegment(i);
				assertTrue(degree >= 15 && degree <= 30);
			}
			// Pixels in range(60,69) result in a range from(30,45)
			else if (i >= 60 && i <= 69) {
				degree = pad.angleSegment(i);
				assertTrue(degree >= 30 && degree <= 45);
			}
			// Pixels in range(70,79) result in a range from(45,60)
			else if (i >= 70 && i <= 79) {
				degree = pad.angleSegment(i);
				assertTrue(degree >= 45 && degree <= 60);
			}
		}

	}
}
