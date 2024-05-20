package powerups;

public class Candy extends PowerUps {

	public Candy(int xPos, int yPos, String powerupType, int time) {
		super(xPos, yPos, powerupType, time);
		this.loadImage(PowerUps.CANDY_IMG);
	}

}
