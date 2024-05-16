package powerups;

import sprites.Player;

public class Heart1 extends PowerUp {

	public Heart1(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUp.HEART_IMAGE);
	}

	public void activate(Player p, int strength) {
		p.increaseStrength(strength); // increases strength
	}
}
