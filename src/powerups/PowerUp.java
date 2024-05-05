/***********************************************************************************************
 * Pikachu Shooting Game (Mini Project)
 * CMSC 22 - WX1L
 * 
 * Description: PowerUp is a sprite that has its own type, size, image, and the time that it was
 * spawned and time it was obtained. Power ups appear every 15 seconds and disappear after 5 seconds
 * if it is not picked up. Each power up has its own special ability once Pikachu collides with it.
 * 
 * @author TINED, Erika Leanne
 *		   VERDERA, Glancy
 * 
 * @created_date 2023-05-12 14:03
 **********************************************************************************************/

package powerups;

import java.util.Random;

import javafx.scene.image.Image;
import sprites.Sprite;

public class PowerUp extends Sprite {
	Random r = new Random();
	protected String powerupType;
	protected int timeSpawned;
	protected int timeObtained;

	public final static int POWERUP_SIZE = 30;
	public final static Image APPLE_IMAGE = new Image("heart1.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image EVOLUTION_STONE_IMAGE = new Image("power.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image POKEDEX_IMAGE = new Image("coffee.png", POWERUP_SIZE, POWERUP_SIZE, false, false);

	public static final String APPLE = "Apple";
	public static final String EVOLUTION_STONE = "Evolution Stone";
	public static final String POKEDEX = "Pokedex";

	public PowerUp (int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos);
		this.powerupType = powerupType;
		this.timeSpawned = time;

	}

	//getters
	public int getTimeSpawned() {
		return this.timeSpawned;
	}

	public int getTimeObtained() {
		return this.timeObtained;
	}

	public String getPowerUpType() {
		return this.powerupType;
	}

	//setters
	public void setTimeObtained(int time) {
		this.timeObtained = time;
	}
}
