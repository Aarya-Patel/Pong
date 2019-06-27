
/** 
 * Pong.java
 * @author Aarya Patel
 * @version 1.0
 * June 24, 2019
 * This is the Pong class where the game logic is present 
 */

import processing.core.PApplet;
import java.io.File;
import javax.sound.sampled.*;
import processing.core.*;

public class Pong extends PApplet {
	/* Global Variables and objects */

	// Initializing player objects and Ball object
	Paddle leftPlayer;
	Paddle rightPlayer;
	Ball ball;

	// Score keeping variables for both players
	int leftScore = 0;
	int rightScore = 0;

	String winner;

	// These variables determine if the user at the start menu
	boolean atStartMenu = true;
	boolean isGameOver = false;

	// The start menu image
	PImage startMenu;

	// Sound files that play during the game
	String hit = "C:\\Users\\ANJANA PATEL\\Documents\\Eclipse Projects\\PongGame\\pong_hit.wav";

	/**
	 * The main method
	 * 
	 * @return void
	 */
	public static void main(String[] args) {
		PApplet.main("Pong");
	}

	/**
	 * The settings sets the size of the screen
	 * 
	 * @return void
	 */
	public void settings() {
		// Setting up the map dimensions
		size(768, 512);
	}

	/**
	 * The setup method assigns the respective variables their objects
	 * 
	 * @return void
	 */
	@Override
	public void setup() {
		// Setting up the map dimensions
		size(768, 512);

		// Assigning the player objects and the ball object
		leftPlayer = new Paddle(this, false);
		rightPlayer = new Paddle(this, true);
		ball = new Ball(this);

		// Assign the start menu image
		startMenu = loadImage("Pong.PNG");

		// Assign the sound files to the respective variables
		// hit = new SoundFile(this, "pong_hit.wav");
		// floorCeil = new SoundFile(this, "pong_floorCeiling.wav");
	}

	/**
	 * The draw method only runs continously. The game is put together in here.
	 * Checks where the user is at the startMenu or playing the game and displays
	 * the appropriate environment
	 * 
	 * @return void
	 */
	@Override
	public void draw() {

		// If the player is at the star menu display the image
		if (atStartMenu) {
			displayStartMenu();
		}
		// Otherwise begin the game
		else if (!isGameOver && !atStartMenu) {

			// Set the background colour to black
			background(0);

			// Set the appropriate color and stroke to display the middle line
			fill(255, 255, 255);
			stroke(255, 255, 255);
			rect(width / 2 - 1, 0, 2, height);

			// Display the score, will update automatically
			displayScore();

			// Add the players and objects the screen
			leftPlayer.show();
			rightPlayer.show();
			ball.show();

			// Move the ball
			ball.move();

			// Check if the ball is toucing the top or bottom
			ball.touchingTopBot();

			// Check if the ball has collided with the player paddles
			collision();

			// Move the paddles based on user input
			rightPlayer.moveRightPlayer();
			leftPlayer.moveLeftPlayer();

			// Once again check if the ball has collided with the paddles after being moved
			collision();

			// Update the score before respawning the ball
			updateScore();

			// Respawn the ball if the ball goes out of bounds and increment the scores
			ball.respawn();

			// Decrement the respawn delay
			ball.tick();

			isGameOver();

		} else {
			// Set the background colour to black
			background(0);
			
			//Display Score
			displayScore();

			// Add the players and objects the screen
			leftPlayer.show();
			rightPlayer.show();

			displayEndMenu();

		}
	}

	/**
	 * Displays the score
	 * 
	 * @return void
	 */
	public void displayScore() {
		fill(255, 255, 255);
		textSize(20);
		if (rightScore < 10) {
			text(rightScore, width / 2 + 25, 30);
		} else {
			text(rightScore, width / 2 + 15, 30);
		}
		text(leftScore, width / 2 - 38, 30);
	}

	/**
	 * Displays the startMenu
	 * 
	 * @return void
	 */
	public void displayStartMenu() {
		image(startMenu, 0, 0);
	}

	/**
	 * Displays the end Menu
	 * 
	 * @return void
	 */
	public void displayEndMenu() {
		fill(255, 255, 255);
		textSize(35);
		text(winner, width / 2 - 110, height / 2);
	}

	/**
	 * Checks if the mouse has been clicked and begins the game
	 * 
	 * @return void
	 */
	public void mouseClicked() {
		this.atStartMenu = false;
	}

	/**
	 * Checks if the score limit has been reached
	 * 
	 * @return void
	 */
	public void isGameOver() {
		// Check which player won and display the player accordingly
		if (rightScore == 10) {
			winner = "Player 2 Wins!";
			isGameOver = true;
		} else if (leftScore == 10) {
			winner = "Player 1 Wins!";
			isGameOver = true;
		}
	}

	/**
	 * Checks if the ball is in collision with any of the paddles
	 * 
	 * @return void
	 */
	public void collision() {
		// Random multipliers to make the velocity different everytime
		float xMultiplier = random(10, 14);
		float yMultiplier = random(10, 11);
		// Stores the angle of deflection in degrees when in contact with ball
		float deg;

		// Stores the pixels from the center of the ball to the paddles when the ball is
		// in contact with the paddle
		int pxFromBallToPaddle;

		// Check if the ball is moving left, towards left paddle
		if (ball.getX_dir() == -1) {

			// Since the ball is moving left, we need to detect if the ball's x-coordinate +
			// radius is in contact with the right side of the left paddle
			// The ball must be between the paddles at the least to account for the
			// x_velocity
			if (ball.getX() - RADIUS <= leftPlayer.getX() + 15 && ball.getX() - RADIUS >= leftPlayer.getX()) {

				// The ball must also be between the y-coordinates of the paddle
				if (ball.getY() >= leftPlayer.getY() && ball.getY() <= leftPlayer.getY() + 80) {

					// Play the audio
					play(hit);

					// Get the distance of the ball from the top of the paddle
					pxFromBallToPaddle = (int) (ball.getY() - leftPlayer.getY());

					// Get the respective angle deflection from the leftPaddle
					deg = leftPlayer.angleSegment(pxFromBallToPaddle);

					// Flip the x direction of the ball
					ball.flipX_dir();

					// Update the x_velocity by product of the xMultiplier and the x-component of
					// the angle deflection
					ball.setX_vel((float) (xMultiplier * (Math.cos(Math.toRadians(deg)))));

					// If the ball hit the paddle on the 5th section and below, then the direction
					// is down
					if (pxFromBallToPaddle / 10 >= 5) {
						ball.setY_dir(1);
					} else if (pxFromBallToPaddle / 10 <= 2) { // If the ball hit the paddle from the 3th section and
																// up, then direction is up
						ball.setY_dir(-1);
					}

					// Update the y_velocity by the product of the yMultiplier and the y-component
					// of the angle delfection
					ball.setY_vel((float) (yMultiplier * (Math.sin(Math.toRadians(deg)))));
				}
			}
		}
		// If the ball isn't moving left, then it must be moving right and so we must
		// check if the left side of the right paddle is in contact with the ball
		// The ball must be between the paddles at the least to account for the
		// x_velocity
		else if (ball.getX() + RADIUS >= rightPlayer.getX() && ball.getX() + RADIUS <= rightPlayer.getX() + 15) {

			// The ball must also be between the y-coordinates of the paddle
			if (ball.getY() >= rightPlayer.getY() && ball.getY() <= rightPlayer.getY() + 80) {

				// Play the audio
				play(hit);

				// Get the distance of the ball from the top of the paddle
				pxFromBallToPaddle = (int) (ball.getY() - rightPlayer.getY());

				// Get the respective angle deflection from the rightPaddle
				deg = rightPlayer.angleSegment(pxFromBallToPaddle);

				// Flip the x direction of the ball
				ball.flipX_dir();

				// Update the x_velocity by product of the xMultiplier and the x-component of
				// the angle deflection
				ball.setX_vel((float) (xMultiplier * (Math.cos(Math.toRadians(deg)))));

				// If the ball hit the paddle on the 5th section and below, then the direction
				// is down
				if (pxFromBallToPaddle / 10 >= 5) {
					ball.setY_dir(1);
				} else if (pxFromBallToPaddle / 10 <= 2) { // If the ball hit the paddle from the 3th section and up,
															// then direction is up
					ball.setY_dir(-1);
				}

				// Update the y_velocity by product of the yMultiplier and the y-component of
				// the angle deflection
				ball.setY_vel((float) (yMultiplier * (Math.sin(Math.toRadians(deg)))));
			}
		}
	}

	public void updateScore() {
		if (ball.getX() <= 5) {
			rightScore += 1;
		} else if (ball.getX() >= width - 5) {
			leftScore += 1;
		}
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
