/***********************************************************************************************
 * Pikachu Shooting Game (Mini Project)
 * CMSC 22 - WX1L
 * 
 * Description: Evolution Stone extends PowerUp and has a method activate that is used when Pikachu
 * collides with it. Once activated, it grants Pikachu immortality by evolving it to a Raichu. 
 * 
 * @author TINED, Erika Leanne
 *		   VERDERA, Glancy
 * 
 * @created_date 2023-05-15 18:04
 **********************************************************************************************/

package powerups;

import sprites.Player;

public class Power extends PowerUp {

	public Power(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUp.EVOLUTION_STONE_IMAGE);
	}

	public void activate(Player ship) {
		ship.setImmortality(true); // makes ship immortal
		ship.setImage(Player.getRaichuImg()); // change Pikachu icon to immortal

	}
}
