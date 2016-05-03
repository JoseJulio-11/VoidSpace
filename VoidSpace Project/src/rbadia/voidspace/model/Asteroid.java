package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Asteroid extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 5;
	
	private int asteroidWidth = 32;
	private int asteroidHeight = 32;
	private int speed;
	private Rectangle explosion;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - asteroidWidth), 0);
		this.setSize(asteroidWidth, asteroidHeight);
		speed = randomNumber(1,DEFAULT_SPEED);
		explosion = new Rectangle(this.x,this.y, this.width, this.height);
	}
	
	public int getAsteroidWidth() {
		return asteroidWidth;
	}
	public int getAsteroidHeight() {
		return asteroidHeight;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}

	private int randomNumber(int from, int to){
		int random=rand.nextInt(to+1);
		while(random<from)
			random=rand.nextInt(to+1);
		return random;

	}


	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}

	/**
	 * Returns the default asteroid explosion.
	 * @return the default asteroid explosion
	 */
	public Rectangle getExplosion() {return explosion;}


	/**
	 * Set the current asteroid explosion.
	 * @param rect the asteroid explosion
     */
	public void setExplosion(Rectangle rect){
		explosion=rect;
	}
}
