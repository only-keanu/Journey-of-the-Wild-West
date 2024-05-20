package powerups;

import sprites.Player;

public class Power extends PowerUps {

	public Power(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUps.POWER_IMAGE);
	}

	public void activate(Player p) {
		p.setImmortality(true); // makes ship immortal
		p.setImage(Player.getEvolveImg()); // change player icon to immortal

	}
}
