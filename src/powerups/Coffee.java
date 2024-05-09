
package powerups;

import java.util.ArrayList;

import sprites.Enemy;

public class Coffee extends PowerUp {

	public Coffee(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUp.COFFEE_IMG);
	}

	public void activate(ArrayList<Enemy> enemies) {
		for (Enemy p : enemies) {
			p.setSpeed(Enemy.MIN_SPEED); //sets all Pokeball speed to minimum
		} 
	}
}
