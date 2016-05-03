package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BossShip;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_BOSS_SHIP_DELAY = 500;

	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastBossShipTime;
	private long lastEnemyBulletTime;

	private Rectangle asteroidExplosion;
	private Rectangle shipExplosion;
	private Rectangle bossShipExplosion;

	private JLabel shipsValueLabel;
	private JLabel asteroidsDestroyedValueLabel;
	private JLabel pointsValueLabel;
	private JLabel levelValueLabel;
	private JLabel shipsDestroyedValueLabel;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;

	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();
		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();
		EnemyShip[] enemyShips = gameLogic.getEnemyShips();
		BossShip bossShip = gameLogic.getBossShip();
		Asteroid[] asteroids = gameLogic.getAsteroids();
		List<Bullet> bullets = gameLogic.getBullets();
		List<EnemyBullet> enemyBullets = gameLogic.getEnemyBullets();


		// set original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 50 random stars
		drawStars(50);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			if((currentTime - lastBossShipTime) < NEW_BOSS_SHIP_DELAY){
				graphicsMan.drawShipExplosion(bossShipExplosion, g2d, this);
			}
			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}
		
//		switch(status.getLevel()){
//		case 1: 
//			//draw an asteroids
//			for(int i=0;i<asteroids.length;i++)
//				this.createAsteroid(asteroids[1], 1, i);
//			break;
//		case 2:
//			//draw two asteroids 
//			for(int i=0;i<asteroids.length;i++)
//				this.createAsteroid(asteroids[2], 1, i);
//			break;
//		case 3:
//			//draw two asteroids with more speed
//			for(int i=0;i<asteroids.length;i++)
//				this.createAsteroid(asteroids[2], 2, i);
//			break;
//		}

		//draw the asteroids
		for(int i=0;i<asteroids.length;i++)
		this.createAsteroid(asteroids[i], 1, i);

		//draw enemy ship		
		this.createEnemyShip(enemyShips[0], "LEFT", 1, 0);
		this.createEnemyShip(enemyShips[1], "RIGHT", 1, 1);
		this.createEnemyShip(enemyShips[2], "LEFT", 1, 2);
		this.createEnemyShip(enemyShips[3], "MIDDLE", 1, 3);
		this.createEnemyShip(enemyShips[4], "RIGHT", 1, 4);

		//draw boss
		if(status.getLevel() % 5 == 0 && status.getLevel() != 0)
			this.createBossShip(bossShip, "Middle", 1 );

		// draw bullets
		drawBullets(bullets);
		drawEnemyBullets(enemyBullets);

		// check bullet-asteroid collisions
		for (int i=0;i<asteroids.length;i++)
			this.bulletAsteroidCollision(bullets, asteroids[i], i);
		
		// check bullet-enemyShip collisions
		for(int i=0; i<enemyShips.length;i++)
			this.bulletEnemyShipCollision(bullets, enemyShips[i],i);
		
		//check enemyBullet-ship collisions
		this.enemyBulletShipCollision(enemyBullets, ship);

		// check bullet-bossShip collisions
		this.bulletBossShipCollision(bullets, bossShip);

		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);
		}
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}

		// check ship-asteroid collisions
		for(int i=0;i<asteroids.length;i++)
			this.shipAsteroidCollision(ship, asteroids[i], i);

		//check ship-enemy ship collisions
		for(int i=0;i<enemyShips.length;i++)
			this.shipEnemyShipCollision(ship, enemyShips[i],i);

		//check ship-boss ship collisions
		this.shipBossShipCollision(ship, bossShip);
		
		

		
		
		//update ships destroyed label
		shipsDestroyedValueLabel.setText(Long.toString(status.getEnemyShipsDestroyed()));

		// update asteroids destroyed label
		asteroidsDestroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		//update points earned
		pointsValueLabel.setText(Integer.toString(status.getPoints()));

		//update the current level
		levelValueLabel.setText(Integer.toString(status.getLevel()));

	}
	
	public void createAsteroid(Asteroid newAsteroid, int speedMultiplier, int pos){
		// draw asteroid
		if(!status.isNewAsteroidAt(pos)){
			// draw the asteroid until it reaches the bottom of the screen
			if(newAsteroid.getY() + newAsteroid.getSpeed() < this.getHeight()){
				newAsteroid.translate(1, newAsteroid.getSpeed() * speedMultiplier);
				graphicsMan.drawAsteroid(newAsteroid, g2d, this);
			}			
			else{
				newAsteroid.setLocation(rand.nextInt(getWidth() - newAsteroid.width), 0);
			}
		} else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroidAt(pos, false);
				newAsteroid.setLocation(rand.nextInt(getWidth() - newAsteroid.width), 0);
			}
			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}
	}
	
	public void createEnemyShip(EnemyShip newEnemyShip, String direction, int speedMultiplier, int pos){
		if(!status.isNewEnemyShipAt(pos)){
			// draw the enemy ship until it reaches the bottom of the screen
			if(newEnemyShip.getY() + (newEnemyShip.getSpeed()+1)*2 < this.getHeight()){
				if(direction.equals("LEFT")){
					//the enemy ships go to the left direction
					newEnemyShip.translate(-newEnemyShip.getSpeed(), newEnemyShip.getSpeed() * speedMultiplier);
					graphicsMan.drawEnemyShip(newEnemyShip, g2d, this);
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastEnemyBulletTime) > 1000/1){
						lastEnemyBulletTime = currentTime;
						gameLogic.fireEnemyBullet();
					}
				}
				else if(direction.equals("RIGHT")){
					//the enemy ships go to the right direction
					newEnemyShip.translate(newEnemyShip.getSpeed(), newEnemyShip.getSpeed() * speedMultiplier);
					graphicsMan.drawEnemyShip(newEnemyShip, g2d, this);
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastEnemyBulletTime) > 1000/1){
						lastEnemyBulletTime = currentTime;
						gameLogic.fireEnemyBullet();
					}
				}
				else if(direction.equals("MIDDLE")){
					//the enemy ships go straight
					newEnemyShip.translate(0, newEnemyShip.getSpeed() * speedMultiplier);
					graphicsMan.drawEnemyShip(newEnemyShip, g2d, this);
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastEnemyBulletTime) > 1000/1){
						lastEnemyBulletTime = currentTime;
						gameLogic.fireEnemyBullet();
					}
				}
			}
			else{
				newEnemyShip.setLocation(rand.nextInt(getWidth() - newEnemyShip.width), 0);
			}
		} else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new enemy ship
				lastAsteroidTime = currentTime;
				status.setNewEnemyShipAt(pos , false);
				newEnemyShip.setLocation(rand.nextInt(getWidth() - newEnemyShip.width), 0);
			}
			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}
	}
	
	public void createBossShip(BossShip newBossShip, String direction, int speedMultiplier) {

		if (!status.isNewBossShip()) {
			// draw it in its current location
			if (newBossShip.getY() + newBossShip.getSpeed() < this.getHeight()) {
				if (direction.equals("Middle")) {

					//the enemy ships go to the left direction
					newBossShip.translate(0, newBossShip.getSpeed() * speedMultiplier);
					graphicsMan.drawBossShip(newBossShip, g2d, this);
				} else {
					newBossShip.setLocation(rand.nextInt(getWidth() - newBossShip.width), 0);
				}
			} else {
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY) {
					// draw a new enemy ship
					lastAsteroidTime = currentTime;
					status.setNewBossShip(false);
					newBossShip.setLocation(rand.nextInt(getWidth() - newBossShip.width), 0);
				}
				else {
					// draw explosion
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}
			}
		}
	}	
	

	public void bulletAsteroidCollision(List<Bullet> bullets, Asteroid newAsteroid, int asteroidPos){
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(newAsteroid.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
				//increase number of points
				status.setPoints(status.getPoints() + 100);
				if(status.getAsteroidsDestroyed() % 5 == 0){
					//increase level each time you destroy 5 asteroids
					status.setLevel(status.getLevel() + 1);
				}

				// "remove" newAsteroid
				asteroidExplosion = new Rectangle(
						newAsteroid.x,
						newAsteroid.y,
						newAsteroid.width,
						newAsteroid.height);
				newAsteroid.setLocation(-newAsteroid.width, -newAsteroid.height);
				status.setNewAsteroidAt(asteroidPos, true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}
	
	public void bulletEnemyShipCollision(List<Bullet> bullets, EnemyShip newEnemyShip, int enemyPos){
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(newEnemyShip.intersects(bullet)){
				// increase enemy ships destroyed count
				status.setEnemyShipsDestroyed(status.getEnemyShipsDestroyed() + 1);
				//increase number of points
				status.setPoints(status.getPoints() + 250);						

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						newEnemyShip.x,
						newEnemyShip.y,
						newEnemyShip.width,
						newEnemyShip.height);
				newEnemyShip.setLocation(-newEnemyShip.width, -newEnemyShip.height);
				status.setNewEnemyShipAt(enemyPos, true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}
	
	public void enemyBulletShipCollision(List<EnemyBullet> enemyBullets, Ship ship){
		for(int i = 0; i < enemyBullets.size(); i++){
			EnemyBullet enemyBullet = enemyBullets.get(i);
			if(enemyBullet.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);	

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

				// play ship explosion sound
				soundMan.playShipExplosionSound();				
			}
		}
	}

	public void bulletBossShipCollision(List<Bullet> bullets, BossShip newBossShip){
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(newBossShip.intersects(bullet)){
				// increase enemy ships destroyed count
				status.setBossShipsDestroyed(status.getBossShipsDestroyed() + 1);
				//increase number of points
				status.setPoints(status.getPoints() + 250);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						newBossShip.x,
						newBossShip.y,
						newBossShip.width,
						newBossShip.height);
				newBossShip.setLocation(-newBossShip.width, -newBossShip.height);
				status.setNewBossShip(true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}
	
	public void shipAsteroidCollision(Ship ship, Asteroid newAsteroid, int asteroidPos){
		if(newAsteroid.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);

			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion = new Rectangle(
					newAsteroid.x,
					newAsteroid.y,
					newAsteroid.width,
					newAsteroid.height);
			newAsteroid.setLocation(-newAsteroid.width, -newAsteroid.height);
			status.setNewAsteroidAt(asteroidPos, true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}
	}	
	
	public void shipEnemyShipCollision(Ship targetShip, EnemyShip targetEnemyShip, int pos){
		if(targetEnemyShip.intersects(targetShip)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);

			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion = new Rectangle(
					targetEnemyShip.x,
					targetEnemyShip.y,
					targetEnemyShip.width,
					targetEnemyShip.height);
			targetEnemyShip.setLocation(-targetEnemyShip.width, -targetEnemyShip.height);
			status.setNewEnemyShipAt(pos,true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					targetShip.x,
					targetShip.y,
					targetShip.width,
					targetShip.height);
			targetShip.setLocation(this.getWidth() + targetShip.width, -targetShip.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}
	}

	public void shipBossShipCollision(Ship targetShip, BossShip targetBossShip){
		if(targetBossShip.intersects(targetShip)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);

			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" boss ship
			asteroidExplosion = new Rectangle(
					targetBossShip.x,
					targetBossShip.y,
					targetBossShip.width,
					targetBossShip.height);
			targetBossShip.setLocation(-targetBossShip.width, -targetBossShip.height);
			status.setNewAsteroid(true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					targetShip.x,
					targetShip.y,
					targetShip.width,
					targetShip.height);
			targetShip.setLocation(this.getWidth() + targetShip.width, -targetShip.height);
			status.setNewBossShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}
	}	
	

	public void drawBullets(List<Bullet> bullets){
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
	}
	
	public void drawEnemyBullets(List<EnemyBullet> enemyBullets){
		for(int i=0; i<enemyBullets.size(); i++){
			EnemyBullet enemyBullet = enemyBullets.get(i);
			graphicsMan.drawEnemyBullet(enemyBullet, g2d, this);
			
			boolean remove = gameLogic.moveEnemyBullet(enemyBullet);
			if(remove){
				enemyBullets.remove(i);
				i--;
			}
		}
	}

	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER! TRY AGAIN!";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "Void Space";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		asteroidsDestroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
		shipsDestroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
		pointsValueLabel.setText(Integer.toString(status.getPoints()));
		levelValueLabel.setText(Integer.toString(status.getLevel()));
	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param asteroidsDestroyedValueLabel the label to set
	 */
	public void setAsteroidsDestroyedValueLabel(JLabel asteroidsDestroyedValueLabel) {
		this.asteroidsDestroyedValueLabel = asteroidsDestroyedValueLabel;
	}
	
	/**
	 * Sets the label that displays the value for enemy ships destroyed.
	 * @param shipsDestroyedValueLabel the label to set
	 */
	public void setShipsDestroyedValueLabel(JLabel shipsDestroyedValueLabel){
		this.shipsDestroyedValueLabel = shipsDestroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}

	/**
	 * Sets the label that displays the points accumulated
	 * @param pointsValueLabel the label to set
	 */
	public void setPointsValueLabel(JLabel pointsValueLabel) {
		this.pointsValueLabel = pointsValueLabel;
	}

	/**
	 * Sets the label that displays the level that you're currently in
	 * @param levelValueLabel the label to set
	 */
	public void setLevelValueLabel(JLabel levelValueLabel){
		this.levelValueLabel = levelValueLabel;
	}	
	
}
