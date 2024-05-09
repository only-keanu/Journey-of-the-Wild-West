package powerups;

import sprites.Player;

public class Power extends PowerUp {

	public Power(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUp.POWER_IMAGE);
	}

	public void activate(Player ship) {
		ship.setImmortality(true); // makes ship immortal
		ship.setImage(Player.getRaichuImg()); // change Pikachu icon to immortal

	}
}
