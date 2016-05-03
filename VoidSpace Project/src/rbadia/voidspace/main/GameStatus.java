package rbadia.voidspace.main;

public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;
	
	// status variables
	private boolean newShip;
	private boolean newAsteroid;
	private boolean newEnemyShip;
	private boolean[] newEnemyShips = new boolean[5];
	private boolean[] newAsteroids = new boolean[5];
	private boolean newBossShip;
	private boolean newRapidFire;
	private long asteroidsDestroyed = 0;
	private long enemyShipsDestroyed = 0;
    private long bossShipDestroyed = 0;
	private int shipsLeft;
	private int pointsEarned;
	private int level;
	
	public GameStatus(){
		
	}
	
	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}

	public void setLengthShips(int length){
		newEnemyShips=new boolean[length];
	}

	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	
	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}
	
	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}
	
	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}
	
	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized boolean isNewEnemyShipAt(int pos){
		return newEnemyShips[pos];
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized boolean isNewAsteroidAt(int pos){
		return newAsteroids[pos];
	}

	public synchronized void setNewAsteroidAt(int pos, boolean newAsteroid){
		newAsteroids[pos] = newAsteroid;
	}

	public synchronized void setNewEnemyShipAt(int pos, boolean newEnemyShip) {
		newEnemyShips[pos]=newEnemyShip;
	}
	
	/**
	 * Indicates if a new enemy ship should be created/drawn.
	 * @return if a new enemy ship should be created/drawn
	 */
	public synchronized boolean isNewEnemyShip(){
		return newEnemyShip;
	}
	
	public synchronized void setNewEnemyShip(boolean newEnemyShip){
		this.newEnemyShip = newEnemyShip;
	}

	/**
	 * Indicates if a new boss ship should be created/drawn.
	 * @return if a new boss ship should be created/drawn.
	 */
	public synchronized boolean isNewBossShip() {
		return newBossShip;
	}

	public synchronized void setNewBossShip(boolean newBossShip) {		
		this.newBossShip = newBossShip;
	}
	
	public synchronized boolean isNewRapidFire(){
		return newRapidFire;
	}

	public synchronized void setNewRapidFire(boolean newRapidFire) { 
		this.newRapidFire = newRapidFire;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}

	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}
	
	/**
	 * Returns the number of enemies destroyed. 
	 * @return the number of enemies destroyed
	 */
	public synchronized long getEnemyShipsDestroyed() {
		return enemyShipsDestroyed;
	}

	public synchronized void setEnemyShipsDestroyed(long enemiesDestroyed) {
		this.enemyShipsDestroyed = enemiesDestroyed;
	}

	/**
	 * Returns the number of boss ships destroyed
	 * @return the number of boss ships destroyed
     */
	public synchronized long getBossShipsDestroyed() { 
		return bossShipDestroyed;
	}

	public synchronized void setBossShipsDestroyed(long bossShipDestroyed) {
		this.bossShipDestroyed = bossShipDestroyed;
	}

	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}	

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}
	
	/**
	 * Returns the points accumulated
	 * @return the points accumulated
	 */
	public synchronized int getPoints() {
		return pointsEarned;
	}
	
	public synchronized void setPoints(int points) {
		this.pointsEarned = points;
	}
	
	/**
	 * Returns the current level
	 * @return the current level
	 */
	public int getLevel() {
		return level;
	}
	
	public synchronized void setLevel(int level) {
		this.level = level;
	}

	public void setNewAsteroid(boolean b) {
	}
}
