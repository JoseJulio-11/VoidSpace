package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

    private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/shipExplosion.wav"));
    private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/laser.wav"));
    private AudioClip enemyBulletSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/enemyLaser.wav"));
    private AudioClip gameTheme = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/undertaleTheme.wav"));
   
    
    /**
     * Plays sound for bullets fired by the ship.
     */
    public void playBulletSound(){
    	if(SOUND_ON){new Thread(new Runnable(){
			public void run() {
				bulletSound.play();
			}
		}).start();
    	}
    }
    
    /**
     * Plays sound for bullets fired by the enemy ships.
     */
    public void playEnemyBulletSound(){
    	if(SOUND_ON){new Thread(new Runnable(){
			public void run() {
				enemyBulletSound.play();
			}
		}).start();
    	}
    }
    
    /**
     * Plays sound for ship explosions.
     */
    public void playShipExplosionSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				shipExplosionSound.play();
    			}
    		}).start();
    	}
    }
    
    /**
     * Plays sound for asteroid explosions.
     */
    public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		
    	}
    }
    
    public void playGameTheme(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				gameTheme.loop();;
    			}
    		}).start();
    	}
    }
    
}
