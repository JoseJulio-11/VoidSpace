package rbadia.voidspace.model;

import rbadia.voidspace.main.GameScreen;

public class BossShip extends EnemyShip {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Y_OFFSET = 5; // initial y distance of the ship from the bottom of the screen 
	private int bossShipWidth = 32;
	private int bossShipHeight = 53;

	public BossShip(GameScreen screen){
		super(screen);
		this.setLocation((screen.getWidth() - bossShipWidth)/2, screen.getY() + bossShipHeight + Y_OFFSET);

	}

}
