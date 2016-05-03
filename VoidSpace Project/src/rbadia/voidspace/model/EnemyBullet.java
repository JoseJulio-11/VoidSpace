package rbadia.voidspace.model;

import java.awt.Rectangle;

public class EnemyBullet extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bulletWidth = 8;
	private int bulletHeight = 8;
	private int speed = 12;

	/**
	 * Creates a new bullet above the ship, centered on it
	 * @param ship
	 */
	public EnemyBullet(EnemyShip enemyShip) {
		this.setLocation(enemyShip.x + enemyShip.width/2 - bulletWidth/2,
				enemyShip.y + enemyShip.height/2 - bulletHeight);
		this.setSize(bulletWidth, bulletHeight);
	}

	/**
	 * Return the enemy bullet's speed
	 * @return the bullet's speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the speed of the enemy bullets
	 * @param speed the speed ofthe enemy bullets
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}	

}
