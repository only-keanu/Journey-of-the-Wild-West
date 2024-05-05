package sprites;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	private final int BULLET_SPEED = 5;
	private final static Image BULLET_IMAGE = new Image("bullet.png", Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	private final static int BULLET_WIDTH = 10;
	private double angle;
	private int damage;

	public Bullet(int x, int y){
		super(x,y);
		this.loadImage(Bullet.BULLET_IMAGE);
	}

	//method that will move/change the x position of the bullet
	public void moveRight(){
		this.x += BULLET_SPEED;

		if ((this.x) >= 800) {
			this.visible = false;
		}
	}
	public void moveLeft(){
		this.x -= BULLET_SPEED;

		if ((this.x) <= 0) {
			this.visible = false;
		}
	}
	public void moveUp(){
		this.y -= BULLET_SPEED;

		if ((this.y <= 0) ) {
			this.visible = false;
		}
	}
	public void moveDown(){
		this.y += BULLET_SPEED;

		if ((this.y) >= 500) {
			this.visible = false;
		}
	}

	//getters
	public int getDamage() {
		return this.damage;
	}

	//setters
	public void setDamage(int damage) {
		this.damage = damage;
	}



}