/** 
 * Ball.java
 * @author Aarya Patel
 * @version 1.0
 * June 24, 2019
 * This is the Ball class where all the ball's mechanics are written 
 */

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import processing.core.*;

public class Ball {
	
	//The parent PApplet in which the ball will be displayed
	PApplet parent;

	// Vector position to store the x-y coordinate of the ball
	private PVector pos = null;

	// X-Y velocity, intially a random value from 3-7
	private float x_vel;
	private float y_vel;

	// Paddle leftPlayer = new Pong().leftPlayer;
	// Paddle rightPlayer = new Pong().rightPlayer;
	/*
	 * X-Y directions that determine if the ball is moving up/down or right/left 1:
	 * right,down | -1: left,up
	 */
	private int x_dir = -1;
	private int y_dir = -1;

	// Respawn Timer
	private int respawnTimer = 0;

	private final int MAP_WIDTH = 768;
	private final int MAP_HEIGHT = 512;

	// Width of the ellipse
	private final int WIDTH = 11;
	// Radius of the ball
	private final int RADIUS = WIDTH / 2;
	
	//Sound file path 
	String floorCeil = "C:\\Users\\ANJANA PATEL\\Documents\\Eclipse Projects\\PongGame\\pong_floorCeiling.wav";


	/**
	 * Creates a Ball object
	 */
	public Ball(PApplet parent) {
		// Set the pong applet to this parent
		this.parent = parent;

		// Assign the vector and set its x-y position to the middle of the screen
		pos = new PVector();
		pos.x = MAP_WIDTH / 2;
		pos.y = MAP_HEIGHT / 2;

		x_vel = parent.random(3, 7);
		y_vel = parent.random(3, 7);
	}

	/**
	 * Returns the x-direction of the ball
	 * 
	 * @return int x_dir - the x-direction of the ball
	 */
	public int getX_dir() {
		return x_dir;
	}

	/**
	 * Returns the x-coordinate of the ball
	 * 
	 * @return int x - the x-coordinate of the ball
	 */
	public int getX() {
		return (int) pos.x;
	}

	/**
	 * Returns the y-coordinate of the ball
	 * 
	 * @return int y - the y-coordinate of the ball
	 */
	public int getY() {
		return (int) pos.y;
	}

	/**
	 * Flips the x-direction of the ball
	 *
	 * @return void
	 */
	public void flipX_dir() {
		this.x_dir *= -1;
	}
	
	/**
	 * Sets the y-direction of the ball
	 * @param int y_dir - the new y-direction of the ball
	 * @return void
	 */
	public void setY_dir(int y_dir) {
		this.y_dir = y_dir;
	}

	/**
	 * Sets the x-velocity of the ball
	 * @param int x_vel - the new x-velocity of the ball
	 * @return void
	 */
	public void setX_vel(float x_vel) {
		this.x_vel = x_vel;
	}
	
	/**
	 * Sets the y-velocity of the ball
	 * @param int y_vel - the new y-velocity of the ball
	 * @return void
	 */
	public void setY_vel(float y_vel) {
		this.y_vel = y_vel;
	}

	/**
	 * Move the ball in the x-y direction
	 * 
	 * @return void
	 */
	public void move() {
		// Can only move if the respawnTimer is 0
		if (respawnTimer == 0) {
			// Add the x_velocity to the existing x-coordinate
			pos.x += x_vel * x_dir;

			// Add the y_velocity to the existing y-coordinate
			pos.y += y_vel * y_dir;

			// Constrain the x-y coordinates to stay within the map
			// pos.x = constrain(pos.x, RADIUS, MAP_WIDTH - RADIUS);

			if (pos.x < 0) {
				pos.x = 0;
			} else if (pos.x > MAP_WIDTH - RADIUS) {
				pos.x = MAP_WIDTH - RADIUS;
			}

			// pos.y = constrain(pos.y, RADIUS, MAP_HEIGHT - RADIUS);

			if (pos.y < 0) {
				pos.y = 5;
			} else if (pos.y > MAP_HEIGHT - RADIUS) {
				pos.y = MAP_HEIGHT - RADIUS;
			}
		}
	}

	/**
	 * Decrements the respawnTimer so the ball can move
	 * 
	 * @return void
	 */
	public void tick() {
		if (respawnTimer > 0) {
			respawnTimer--;
		}
	}

	/**
	 * Check if the ball is touching the top or bottom of the screen and flip the
	 * y_direction of the ball
	 * 
	 * @return void
	 */
	public void touchingTopBot() {
		// Check if the ball is touching the top or bottom of the screen
		if (pos.y == 5 || pos.y == MAP_HEIGHT - 5) {
			// Play the audio
			play(floorCeil);

			// Changing y-direction
			y_dir *= -1;
		}
	}

	/**
	 * Respawn the ball if the ball moves out the screen and update the score
	 * 
	 * @return void
	 */
	public void respawn() {

		// Check if the ball moves out the screen
		if (pos.x <= 5 || pos.x >= MAP_WIDTH - 5) {

			// Reset the x-y coordinates to the centre of the screen
			pos.x = MAP_WIDTH / 2;
			pos.y = MAP_HEIGHT / 2;

			// Changing x-direction
			x_dir *= -1;

			// Randomize the velocities
			y_vel = parent.random(3, 10);
			x_vel = parent.random(3, 7);

			// Set the respawnTimer to 100 to cause delay without pausing the run
			respawnTimer = 100;
		}
	}

	/**
	 * Draws the ball at the x-y coordinate
	 * 
	 * @return void
	 */
	public void show() {
		// Set the correct colors
		parent.fill(255, 255, 255);
		parent.stroke(255, 255, 255);

		// Draw the ball at the x-y coordinate
		parent.ellipse(pos.x, pos.y, WIDTH, WIDTH);
	}
	
	/**
	 * Plays the audio of the desired file path
	 * 
	 * @return void
	 */
	public void play(String filepath) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filepath)));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}