
package powerups;

import java.util.ArrayList;

import sprites.Enemy;

public class Coffee extends PowerUps {

	public Coffee(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUps.COFFEE_IMG);
	}

	public void activate(ArrayList<Enemy> enemies) {
		for (Enemy p : enemies) {
			p.setSpeed(Enemy.MIN_SPEED); 
		} 
	}
}
