package powerups;

import java.util.Random;

import javafx.scene.image.Image;
import sprites.Sprite;

public class PowerUps extends Sprite {
	Random r = new Random();
	protected String powerupType;
	protected int timeSpawned;
	protected int timeObtained;

	//power up images
	public final static int POWERUP_SIZE = 30;
	public final static Image HEART_IMAGE = new Image("heart.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image POWER_IMAGE = new Image("power.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image COFFEE_IMG = new Image("coffee.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image BEER_IMG = new Image("beer.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image CANDY_IMG = new Image("candy.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image DAMAGE_IMG = new Image("damage.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	
	//power up names
	public static final String HEART = "Heart";
	public static final String POWERUP = "Powerup";
	public static final String COFFEE = "Coffee";
	public static final String BEER = "Beer";
	public static final String CANDY = "Candy";
	public static final String DAMAGE = "Damages";

	public PowerUps (int xPos, int yPos, String powerupType, int time) {
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
