/** 
 * Paddle.java
 * @author Aarya Patel
 * @version 1.0
 * June 24, 2019
 * This is the Paddle class where all the paddle's mechanics are written 
 */
import processing.core.*;
import processing.core.PApplet;

public class Paddle {
	PApplet parent;

	// Vector position to store the x-y coordinate of the paddle
	private PVector pos;
	
	private final int MAP_WIDTH = 768;
	private final int MAP_HEIGHT = 512;
	
	// Constants to set the width and height of the paddles
	private final int WIDTH = 15;
	private final int HEIGHT = 80;
	
	//Stores which is the rightPaddle
	boolean isRightPaddle;

	/**
	 * Creates a Ball object
	 * @param boolean isRightPaddle - if the paddle is the right player
	 * @return void
	 */
	public Paddle(PApplet parent, boolean isRightPaddle) {
		//Set the parent to the pong applet
		this.parent = parent;
		
		//Set if the paddle is the right side 
		this.isRightPaddle = isRightPaddle;
		
		// Set the position of the rect depending on if it is the right or left player
		if (isRightPaddle) {
			pos = new PVector(MAP_WIDTH - 30, MAP_HEIGHT / 2 - 40);
		} else {
			pos = new PVector(20, MAP_HEIGHT / 2 - 40);
		}
	}
	
	
	/**
	 * Returns the x-coordinate of the paddle
	 * 
	 * @return int x - the x-coordinate of the paddle
	 */
	public int getX() {
		return (int) pos.x;
	}
	
	/**
	 * Returns the y-coordinate of the paddle
	 * 
	 * @return int y - the y-coordinate of the paddle
	 */
	public int getY() {
		return (int) pos.y;
	}

	/**
	 * Move the right player, movement is based on the mouse movement
	 * 
	 * @return void
	 */
	public void moveRightPlayer() {
		// Constrain the mouse's y-coordinate to the screen size
//		int y_val = constrain(parent.mouseY, 0, MAP_HEIGHT - HEIGHT);
		int y_val = parent.mouseY;
		
		if(y_val < 0) {
			y_val = 0;
		} else if(y_val > MAP_HEIGHT - HEIGHT) {
			y_val = MAP_HEIGHT - HEIGHT;
		}

		// Set the paddle's y-coordinate to the mouse's y-val
		pos.y = y_val;
	}

	/**
	 * Move the left player, movement is based on the 'w' and 's' keys
	 * 
	 * @return void
	 */
	public void moveLeftPlayer() {
		// Check if any keys are pressed
		if (parent.keyPressed) {

			// 'w': move up
			if (parent.key == 'w') {
				pos.y -= 15;
			}
			// 's': move down
			else if (parent.key == 's') {
				pos.y += 15;
			}
		}

		// Constrain the values of the paddle's y-coordiante to stay on the screen
//		pos.y = constrain(pos.y, 0, MAP_HEIGHT - 80);
		
		if(pos.y < 0) {
			pos.y = 0;
		} else if(pos.y > MAP_HEIGHT - HEIGHT) {
			pos.y = MAP_HEIGHT - HEIGHT;
		}
	}

	/** 
   * Determine the angle deflection of the ball given which segment it collides with 
   * @param int px - the pixel distance from the top of the paddle to the ball
   * @return float deg - the degree of the deflection
   */
  public float angleSegment(int px) {
    
    //Breaking the px into 8 segments: 0 -> 7
    //These segements represent 10 px blocks from the top of the paddle
    int seg = (int)(px/10);
    
    //Check which segment the ball hit and randomize the angle deflection
    switch(seg) {
    case 0:
      return parent.random(45, 60);
    case 1 :
      return parent.random(30, 45);
    case 2:
      return parent.random(15, 30);
    case 3:
      return parent.random(0, 15);
    case 4: 
      return parent.random(0, 15);
    case 5: 
      return parent.random(15, 30);
    case 6:
      return parent.random(30, 45);
    case 7:
      return parent.random(45, 60);
    default:
      return 0;
    }
  }

	/**
	 * Draw the rect given the x-y coordinates
	 * 
	 * @return void
	 */
	public void show() {
		// Set proper colors
		if(isRightPaddle) {
			parent.fill(181, 230, 29);
			parent.stroke(181, 230, 29);
		} else {
			parent.fill(0, 162, 232);
			parent.stroke(0, 162, 232);
		}
		// Draw the rectanle at the position specified
		parent.rect(pos.x, pos.y, WIDTH, HEIGHT);
	}
}