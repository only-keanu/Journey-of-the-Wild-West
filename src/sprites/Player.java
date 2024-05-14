package sprites;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import mainGame.*;
import powerups.PowerUp;

public class Player extends Sprite{
	private String name;
	private int strength;
	private boolean alive;
	private boolean immortality;
	private boolean shooting = false;
	private int heart_powerup;
	private int speed = 1;
	private int score = 0;
	

	private ArrayList<Bullet> bullets;
	private ArrayList<PowerUp> powerupsObtained = new ArrayList<PowerUp>();

	private final static int PLAYER_WIDTH = 25;
	private final static Image PLAYER1_IMAGE = new Image("player1.png", PLAYER_WIDTH, PLAYER_WIDTH, false, false);
	private final static Image PLAYER2_IMAGE = new Image("player1.png", PLAYER_WIDTH, PLAYER_WIDTH, false, false);
	private final static Image EVOLVE_IMAGE = new Image("evolve.png", PLAYER_WIDTH , PLAYER_WIDTH, false, false);
	public static Image p1_score = new Image("p1Score.png", PLAYER_WIDTH+25 , PLAYER_WIDTH+25, false, false);
	public static Image p2_score = new Image("p2Score.png", PLAYER_WIDTH+25 , PLAYER_WIDTH+25, false, false);
	
	public Player(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.heart_powerup = 0;
		this.strength = r.nextInt(51) + 100;
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.immortality = false;
		this.loadImage(Player.PLAYER1_IMAGE);
	}

	//method called if spacebar is pressed
	public void shoot(){
		if (shooting) return;
		shooting = true;
		GameTimer.shedule(500, () -> this.shooting = false);
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width);
		int y = (int) (this.y + this.height/2);
		//instantiating a new bullet and adding it to an arraylist
		Bullet b = new Bullet(x,y);
		bullets.add(b);
	}

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
	    if ((this.x + this.dx >= 0) && (this.x + this.dx <= GameStage.WINDOW_WIDTH - PLAYER_WIDTH)) {
	        double nextX = this.x + this.dx;
	        double nextY = this.y + this.dy;

	        // Check if the next position collides with obstacles
	        if (!checkCollisionWithObstacles(nextX, nextY)) {
	            this.x = nextX;
	        }
	    }
	    if ((this.y + this.dy >= 0) && (this.y + this.dy <= GameStage.WINDOW_HEIGHT - PLAYER_WIDTH)) {
	        double nextX = this.x + this.dx;
	        double nextY = this.y + this.dy;

	        // Check if the next position collides with obstacles
	        if (!checkCollisionWithObstacles(nextX, nextY)) {
	            this.y = nextY;
	        }
	    }
	}
	
	private boolean checkCollisionWithObstacles(double nextX, double nextY) {
	    for (Obstacle obstacle : GameTimer.obstacles) {
	        if (obstacle.collided(nextX, nextY, PLAYER_WIDTH, PLAYER_WIDTH)) {
	            return true; // Collision detected with obstacle
	        }
	    }
	    return false; // No collision with any obstacle
	}
	
	

	//getters
	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}

	public String getName(){
		return this.name;
	}

	public int getStrength(){
		return this.strength;
	}

	public boolean getImmortality(){
		return this.immortality;
	}

	public void increaseStrength(int num){
		this.strength += num;
	}

	public static int getPikachuWidth() {
		return PLAYER_WIDTH;
	}

	public static Image getPlayer1Image() {
		return PLAYER1_IMAGE;
	}

	public static Image getEvolveImg() {
		return EVOLVE_IMAGE;
	}

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//setters
	public void decreaseStrength(int num){
		this.strength -= num;
	}

	public void setStrength(int num){
		this.strength = num;
	}

	public void setImmortality(boolean b){
		this.immortality = b;
	}

	public void setImage(Image image) {
		this.loadImage(image);
	}

	public void die(){
		this.alive = false;
		this.visible = false;
	}

	public void addPowerUp(PowerUp p){
		this.powerupsObtained.add(p);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public static Image getPlayer2Image() {
		return PLAYER2_IMAGE;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
