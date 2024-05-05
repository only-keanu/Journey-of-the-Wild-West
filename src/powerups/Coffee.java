/***********************************************************************************************
 * Pikachu Shooting Game (Mini Project)
 * CMSC 22 - WX1L
 * 
 * Description: Pokedex extends PowerUp and has a method activate that is used when Pikachu
 * collides with it. Once activated, it slows down all Pokeballs to minimum speed. 
 * 
 * @author TINED, Erika Leanne
 *		   VERDERA, Glancy
 * 
 * @created_date 2023-05-15 18:08
 **********************************************************************************************/

package powerups;

import java.util.ArrayList;

import sprites.Enemy;

public class Coffee extends PowerUp {

	public Coffee(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUp.POKEDEX_IMAGE);
	}

	public void activate(ArrayList<Enemy> enemies) {
		for (Enemy p : enemies) {
			p.setSpeed(Enemy.MIN_POKEBALL_SPEED); //sets all Pokeball speed to minimum
		} 
	}
}
