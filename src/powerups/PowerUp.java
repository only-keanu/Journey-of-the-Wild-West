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
	public final static Image HEART_IMAGE = new Image("heart1.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image POWER_IMAGE = new Image("power.png", POWERUP_SIZE, POWERUP_SIZE, false, false);
	public final static Image COFFEE_IMG = new Image("coffee.png", POWERUP_SIZE, POWERUP_SIZE, false, false);

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
