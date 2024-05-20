package sprites;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import mainGame.*;
import powerups.PowerUps;

public class Player extends Sprite{
	private String name;
	private int hp;
	private int attackDamage;
	private boolean alive;
	private boolean immortality;
	private boolean shooting = false;
	private double speed = 1;
	private int score = 0;
	

	private ArrayList<Bullet> bullets;
	private ArrayList<PowerUps> powerupsObtained = new ArrayList<PowerUps>();
	public Set<KeyCode> pressedKeys1 = new HashSet<>();
	public Set<KeyCode> pressedKeys2 = new HashSet<>();

	private final static int PLAYER_WIDTH = 25;
	private final static Image PLAYER1_IMAGE = new Image("player1.png", PLAYER_WIDTH, PLAYER_WIDTH, false, false);
	private final static Image PLAYER2_IMAGE = new Image("player2.png", PLAYER_WIDTH, PLAYER_WIDTH, false, false);
	private final static Image EVOLVE_IMAGE = new Image("evolve.png", PLAYER_WIDTH , PLAYER_WIDTH, false, false);
	public static Image p1_score = new Image("p1Score.png", PLAYER_WIDTH+25 , PLAYER_WIDTH+25, false, false);
	public static Image p2_score = new Image("p2Score.png", PLAYER_WIDTH+25 , PLAYER_WIDTH+25, false, false);
	
	public Player(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.setAttackDamage(10);
		this.hp = r.nextInt(51) + 100;
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

	public int getHP(){
		return this.hp;
	}

	public boolean getImmortality(){
		return this.immortality;
	}

	public void increaseStrength(int num){
		this.hp += num;
	}

	public static int getPlayerWidth() {
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
	public void decreaseHP(int num){
		this.hp -= num;
	}

	public void setHP(int num){
		this.hp = num;
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

	public void addPowerUp(PowerUps p){
		this.powerupsObtained.add(p);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double d) {
		this.speed = d;
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

	
	//handles player movements
	public void handleKeyPressEvent(GameTimer gameTimer) {
	    gameTimer.theScene.setOnKeyPressed(e -> {
	        KeyCode code = e.getCode();
	        gameTimer.movePlayer(code);
	        System.out.println(gameTimer.activeBullet+" STATE.");
	        System.out.println(gameTimer.activeBullet2+" STATE.");
	        if(gameTimer.activeBullet == false) {
		        gameTimer.shootPlayer1(code);
	        }
	        if(gameTimer.activeBullet2 == false) {
	        	gameTimer.shootPlayer2(code);
	        }
	        if (code == KeyCode.W || code == KeyCode.A || code == KeyCode.S || code == KeyCode.D) {
	        pressedKeys1.add(code);
	        }
	        
	        if(code == KeyCode.NUMPAD8 || code == KeyCode.NUMPAD4 || code == KeyCode.NUMPAD5 || code == KeyCode.NUMPAD6){
	        pressedKeys2.add(code);
	        }
	    });
	
	    gameTimer.theScene.setOnKeyReleased(e -> {
	        KeyCode code = e.getCode();
	        if (code == KeyCode.W || code == KeyCode.A || code == KeyCode.S || code == KeyCode.D) {
		        pressedKeys1.remove(code);
		        }
	        if(code == KeyCode.NUMPAD8 || code == KeyCode.NUMPAD4 || code == KeyCode.NUMPAD5 || code == KeyCode.NUMPAD6){
	        	pressedKeys2.remove(code);
		        }
	        if (pressedKeys1.isEmpty()) {
	            gameTimer.stopPlayer1();
	        }
	        if(pressedKeys2.isEmpty()) {
	        	gameTimer.stopPlayer2();
	        }
	    });
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

}
