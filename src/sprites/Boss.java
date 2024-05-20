package sprites;

import javafx.scene.image.Image;
import mainGame.GameStage;

public class Boss extends Enemy {
	private static Player player;

	protected int health;

	public static final int BOSS_WIDTH = 55;
	public static final int BOSS_DAMAGE = 50;
	public static final int BOSS_HEALTH = 200;
	public final static Image BOSS_IMAGE = new Image("boss.png", BOSS_WIDTH, BOSS_WIDTH, false, false);
	private boolean moveUp;
	private final static int VERTICAL_SPEED = 2;
	private int verticalSpeed;

	public Boss(int x, int y) {
		super(x, y, player);
		this.loadImage(BOSS_IMAGE);
		this.damage = BOSS_DAMAGE;
		this.health = BOSS_HEALTH;
		this.moveUp = r.nextBoolean();
		this.verticalSpeed = VERTICAL_SPEED;
	}

	// method that changes the x position of the boss
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
