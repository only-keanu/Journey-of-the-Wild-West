package powerups;

public class Damage extends PowerUps {

	public Damage(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUps.DAMAGE_IMG);
	}

}
