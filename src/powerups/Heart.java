package powerups;

import sprites.Player;

public class Heart extends PowerUps {

	public Heart(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUps.HEART_IMAGE);
	}

	public void activate(Player p, int strength) {
		p.increaseStrength(strength); // increases strength
	}
}
