package sprites;

import java.util.Random;

import sprites.Enemy;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import mainGame.GameStage;
import mainGame.GameTimer;

public class Enemy extends Sprite {

	Random r = new Random();

	public final static Image POKEBALL_IMAGE = new Image("enemy.png",Enemy.POKEBALL_WIDTH,Enemy.POKEBALL_WIDTH,false,false);
	public final static int POKEBALL_WIDTH = 25;
	public final static double MIN_POKEBALL_SPEED = 1;
	public final static double MAX_POKEBALL_SPEED = 1;

	//attribute that will determine if a fish will initially move to the right
	protected boolean moveRight;
	protected double initialSpeed;
	protected double speed;
	protected boolean alive;
	protected int damage;
	
    private Player player; // Reference to the player object


    public Enemy(double x, double y, Player player) {
        super(x, y);
        this.alive = true;
        this.loadImage(Enemy.POKEBALL_IMAGE);
        Random r = new Random();
        this.speed = MAX_POKEBALL_SPEED;
        this.initialSpeed = this.speed;
        this.damage = r.nextInt(11) + 30;
        this.moveRight = true;
        this.player = player; // Assigning the player reference
    }
    private boolean checkCollision(){
		for (int i = 0; i < GameTimer.enemies.size(); i++){
			Enemy e = GameTimer.enemies.get(i);
			if (e != this){
				if (e.collided(this.x, this.y, 25, 25)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean collided(double x, double y, double w1, double w2){
		// Check if the distance between the center of the the 2 enemies is less than the total width (diameter)
		return Math.sqrt(Math.pow(this.x+w1/2-x-w2/2, 2)+Math.pow(this.y+w1/2-y-w2/2, 2)) <= w1/2+w2/2;
	}

    // Method to update the movement of the enemy to follow the player
    public void move() {
        if (player != null && player.isAlive()) {
    		double distance = Math.sqrt(Math.pow(this.x-this.player.getX(), 2)+Math.pow(this.y-this.player.getY(), 2));

            // Move the enemy towards the player with a certain speed
			double angle = Math.atan2(this.player.getY()-this.y, this.player.getX()-this.x);

            this.x += Math.cos(angle) * this.speed;
            this.y += Math.sin(angle) * this.speed;
            
            if (checkCollision()){
				this.x -= Math.cos(angle)*this.speed;
			}
            
            if (checkCollision()){
				this.y -= Math.sin(angle)*this.speed;
			}

            // Optional: Adjust enemy speed based on distance to the player
            // This can make the movement smoother

            // Damage to player if within a certain distance

        }
    }

	//getters
	public int getDamage() {
		return this.damage;
	}

	public double getInitialSpeed() {
		return this.initialSpeed;
	}

	public boolean isAlive() {
		return this.alive;
	}


	//setters
	public void setAlive(boolean b) {
		this.alive = b;
	}

	public void setSpeed(double maxPokeballSpeed) {
		this.speed = maxPokeballSpeed ;
	}
}
