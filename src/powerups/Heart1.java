/***********************************************************************************************
 * Pikachu Shooting Game (Mini Project)
 * CMSC 22 - WX1L
 * 
 * Description: Apple extends PowerUp and has a method activate that is used when Pikachu
 * collides with it. Once activated, it doubles Pikachu's strength. 
 * 
 * @author TINED, Erika Leanne
 *		   VERDERA, Glancy
 * 
 * @created_date 2023-05-15 18:07
 **********************************************************************************************/

package powerups;

import sprites.Player;

public class Heart1 extends PowerUp {

	public Heart1(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUp.APPLE_IMAGE);
	}

	public void activate(Player ship, int strength) {
		ship.increaseStrength(strength); // increases strength
	}
}
