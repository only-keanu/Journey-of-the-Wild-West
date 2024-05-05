/***********************************************************************************************
 * Pikachu Shooting Game (Mini Project)
 * CMSC 22 - WX1L
 * 
 * Description: Masterball extends a Pokeball and is the boss of the game. It has its own
 * width, damage, health, horizontal speed, vertical speed, and image. Unlike the Pokeball,
 * it can move both horizontally and vertically. Once it collides with Pikachu, it decreases
 * Pikachu's health by 50. 
 * 
 * @author TINED, Erika Leanne
 *		   VERDERA, Glancy
 * 
 * @created_date 2023-05-13 10:25
 **********************************************************************************************/

package sprites;

import javafx.scene.image.Image;
import mainGame.GameStage;

public class Boss extends Enemy {
	private static Player player;

	protected int health;

	public static final int MASTERBALL_WIDTH = 55;
	public static final int MASTERBALL_DAMAGE = 50;
	public static final int MASTERBALL_HEALTH = 3000;
	public final static Image MASTERBALL_IMAGE = new Image("boss.png", MASTERBALL_WIDTH, MASTERBALL_WIDTH, false, false);
	private boolean moveUp;
	private final static int VERTICAL_SPEED = 2;
	private int verticalSpeed;

	public Boss(int x, int y) {
		super(x, y, player);
		this.loadImage(MASTERBALL_IMAGE);
		this.damage = MASTERBALL_DAMAGE;
		this.health = MASTERBALL_HEALTH;
		this.moveUp = r.nextBoolean();
		this.verticalSpeed = VERTICAL_SPEED;
	}

	// method that changes the x position of the Masterball
	public void move(){

		if (this.moveRight == true){
			if (this.x+this.dx >= 0 ){
				this.x -= this.speed;
			}else{
				this.x += this.speed;
				this.moveRight = false;
			}
		} else{
			this.x += this.speed;
			if (this.x+this.dx >= GameStage.WINDOW_WIDTH-this.width){
				this.x -= this.speed;
				this.moveRight = true;
			}
		}

		if (this.moveUp == true){
			if (this.y+this.dy >= 0 ){
				this.y -= this.verticalSpeed;
			}else{
				this.y += this.verticalSpeed;
				this.moveUp = false;
			}
		} else{
			this.y += this.verticalSpeed;
			if (this.y+this.dy >= GameStage.WINDOW_HEIGHT-this.width){
				this.y -= this.verticalSpeed;
				this.moveUp = true;
			}
		}
	}

	//getters
	public int getHealth() {
		return this.health;
	}

	//setters
	public void decreaseHealth(int damage) {
		this.health -= damage;
	}

}
