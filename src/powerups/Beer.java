
package powerups;

import java.util.ArrayList;
import java.util.List;

import sprites.Enemy;
import sprites.Player;

public class Beer extends PowerUps {

	public Beer(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUps.BEER_IMG);
	}

	public void activate(ArrayList<Enemy> enemies,List<Player> players) {
		for (Enemy p : enemies) {
			p.setSpeed(Enemy.MIN_SPEED); 
		} 
	}
}
