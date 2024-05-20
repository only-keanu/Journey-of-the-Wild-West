
package mainGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;
import sprites.Bullet;
import sprites.Enemy;
import sprites.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import powerups.Beer;
import powerups.Candy;
import powerups.Coffee;
import powerups.Damage;
import powerups.Heart;
import powerups.Power;
import powerups.PowerUps;
import sprites.*;

public class GameTimer extends AnimationTimer{

	Random r = new Random();
	GraphicsContext gc;
	public Scene theScene;
	public static Player player1;
	public static Player player2;
	private GameStage gameStage;

	private int invulnerabilityTime; // keeps track of length of time player1 is immortal
	private int initialTime; // records the time when enemy started going at max speed
	private int addedPlayer1STR; // records player1's strength to be added when power up is obtained
	private int addedPlayer2STR; // records Player2's strength to be added when power up is obtained
	private Boolean isEnemyMaxSpeed = false; // to keep track when enemy should have max speed

	// variables for the timer
	private Timer timer;
	private int currentTime;
	private int countDownTimer = TIME_LIMIT;

	// arrayList of Sprites

	List<Player> players = new ArrayList<>();
	public ArrayList<Enemy> enemies;
	public static ArrayList<Obstacle> obstacles;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> bullets2;
	private ArrayList<PowerUps> powerups;
	private Boss boss;

	// counters
	private int numEnemies = 0;
	private int numHearts = 0;
	private int numPowers = 0;
	private int numCoffee = 0;
	private int numBeer = 0;
	private int numCandy = 0;
	private int numDamage = 0;

	// variables for rendering a description on the screen for 1 second
	private boolean showingDescription;
	private double descriptionX; // this variable stores the X value of the description rendered on screen
	private double descriptionY; // this variable stores the Y value of the description rendered on screen
	private String descriptionString; // this variable stores the string of the description rendered on screen
	private int descriptionTime; // this variable stores the time the render was first shown
	private Color descriptionColor; // this variable stores the color

	// activation of power up
	private PowerUps powerupActivated; // this variable stores the power up currently being used
	private boolean powerupDeactivated; // is used when invoking the method when enemy speed is set to maximum

	// constants
	public final static int TIME_LIMIT = 100;
	public static final int INITIAL_ENEMIES = 7;
	public static final int MAX_ENEMIES = 3;
	public static final int ICON_WIDTH = 40;

	// time duration constants (in seconds)
	private static final int TEMP_IMMORTALITY = 1; // temporary immortality when player1 is hit by Boss
	private static final int DESCRIPTION_LENGTH = 1; // time for description to show on screen
	private static final int ENEMY_MAX_DURATION = 3; // duration of time for enemies to be at maximum speed
	private static final int POWERUP_LENGTH = 5; // power up activation length
	private static final int POWERUP_LIFE = 5; // duration of time before power up disappears
	private static final int POWERUP_APPEARANCE = 10; // duration of time before power up appears
	private static final int ENEMY_MAX_TIME = 15; // keeps track of every what second enemies should be at maximum speed
	private static final int BOSS_TIME_APPEARANCE = 30; // time before Boss appears

	//loading the images
	private final Image BACKGROUND_IMG = new Image("bg_game.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT,false, false);
	private final Image LIFE_ICON = new Image("HEART.png");
	private final Image COUNTDOWN_ICON = new Image("timer.gif");
	public final static Image HEART1_ICON = new Image("heart1_unactivated.png");
	public final static Image POWERUP_ICON = new Image("power_unactivated.png");
	public final static Image COFFEE_ICON = new Image("coffee_unactivated.png");
	
	//KeyCode
	KeyCode code1;
	KeyCode code2;
	public boolean activeBullet = false;
	public boolean activeBullet2 = false;
	
	GameTimer(GraphicsContext gc, Scene theScene, GameStage stage){ //Handles Graphic and GameStage
		
		this.gc = gc;
		this.theScene = theScene;
		this.gameStage = stage;
		
		// instantiate player1
		this.player1 = new Player("player1", 100, 100);
		this.player2 = new Player("Player2",500,300);
		players.add(player1); // Add player1 to the list
		players.add(player2); // Add player2 to the list
		final int PLAYER2_WIDTH = 25;
		final Image PLAYER2 = new Image("player2.png", PLAYER2_WIDTH, PLAYER2_WIDTH, false, false);

		player2.setImage(PLAYER2);

		// instantiate the ArrayList of Enemies
		this.enemies = new ArrayList<Enemy>();

		// instantiate the ArrayList of PowerUps
		this.powerups = new ArrayList<PowerUps>();

		// instantiate the ArrayList of Bullets
		this.bullets = GameTimer.player1.getBullets();
		this.bullets2 = GameTimer.player2.getBullets();
		
		// instantiate the ArrayList of Obstacles
		GameTimer.obstacles = new ArrayList<Obstacle>();
		
		// call the spawnEnemies method to spawn the initial 7 enemies at the start
		this.spawnEnemies(); 
		
		this.spawnObstacles(player1,player2);

		// call method to handle key click event
		this.player1.handleKeyPressEvent(this);

		// calls the startTimer method to start the timer
		this.startTimer();

		this.start();
	}

	@Override
	public void handle(long currentNanoTime) { //HANDLES ANIMATION RENDERS
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		gc.drawImage(BACKGROUND_IMG, 0, 0);

		// calls the methods to move for player1, bullets, and enemies
		this.player1.move();
		this.player2.move();
		this.moveBullets();
		this.moveEnemies();

		// calls the methods to render player1, enemies, bullets, and power ups
		this.player1.render(this.gc);
		this.player2.render(this.gc);
		
		this.renderObstacles();
		this.renderEnemy();
		this.renderBullets();
		this.renderPowerUps();

		// calls the status bar to the screen
		this.statusBar();
		
		this.gameStage.drawHealthBar(this);
		
	
		// calls the get power ups to check if any power up should  be activated
		this.activatePowerUps();

		// checks if there are descriptions to render
		this.showDescription();

		// winning or losing condition
		if (currentTime == TIME_LIMIT || !player1.isAlive() || !player2.isAlive()) {
			boolean player1Win = true;
			if(player1.isAlive()&&player2.isAlive()) {
				this.timer.stop();
				stop();
				gameStage.setGameOver(player1Win, numEnemies, numHearts, numPowers, numCoffee, currentTime);
			}
			if(player1.isAlive() && !player2.isAlive()) {
				this.timer.stop();
				stop();
				gameStage.setGameOver(player1Win, numEnemies, numHearts, numPowers, numCoffee, currentTime);
			}
			if(player1.getScore()>player2.getScore()) {
				this.timer.stop();
				stop();
				gameStage.setGameOver(player1Win, numEnemies, numHearts, numPowers, numCoffee, currentTime);
			}
			else {
				this.timer.stop();
				stop();
				gameStage.setGameOver(!player1Win, numEnemies, numHearts, numPowers, numCoffee, currentTime);
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------
	This method starts the game timer in seconds. */
	private void startTimer() {

		int delay = 1000; // delay is 1 second

		ActionListener runTimer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				// increments 1 to current time in seconds
				currentTime++;
				System.out.println(currentTime);

				// decrements 1 from count down timer
				countDownTimer--;

				// checks time if there should be enemies, power ups, or a Boss to spawn
				//spawnenemys();
				spawnPowerUps();
				spawnBoss();

				// checks time if speed of enemy should be set to max
				enemySpeed();
			}
		};

		// every 1 second the timer will perform the actions in runTimer
		this.timer = new Timer(delay, runTimer);

		// starts the timer
		this.timer.start();

	}

	private void statusBar() {

		// set font type, weight, size, and color
		Font theFont = Font.font("ArcadeClassic", FontWeight.EXTRA_BOLD, 15);
		this.gc.setFont(theFont);
		this.gc.setFill(Color.BLACK);

		// player1 strength information
//		this.gc.drawImage(LIFE_ICON, GameStage.WINDOW_WIDTH * 0.02, GameStage.WINDOW_HEIGHT * 0.045, ICON_WIDTH, ICON_WIDTH);
//		this.gc.fillText("STRENGTH: " + String.valueOf(player1.getStrength()), GameStage.WINDOW_WIDTH * 0.065, GameStage.WINDOW_HEIGHT * 0.085);

		// countdown timer
		this.gc.drawImage(COUNTDOWN_ICON, GameStage.WINDOW_WIDTH * 0.5, GameStage.WINDOW_HEIGHT * 0.025, ICON_WIDTH, ICON_WIDTH);
		this.gc.fillText("TIME: " + String.valueOf(countDownTimer), GameStage.WINDOW_WIDTH * 0.55, GameStage.WINDOW_HEIGHT * 0.085);

		// score 1
		this.gc.drawImage(Player.p1_score, GameStage.WINDOW_WIDTH * 0.15, GameStage.WINDOW_HEIGHT * 0.04, ICON_WIDTH, ICON_WIDTH);
		this.gc.fillText("SCORE : " + String.valueOf(player1.getScore()), GameStage.WINDOW_WIDTH * 0.20, GameStage.WINDOW_HEIGHT * 0.085);

		// score 2
		this.gc.drawImage(Player.p2_score, GameStage.WINDOW_WIDTH * 0.80, GameStage.WINDOW_HEIGHT * 0.04, ICON_WIDTH, ICON_WIDTH);
		this.gc.fillText("SCORE : " + String.valueOf(player2.getScore()), GameStage.WINDOW_WIDTH * 0.85, GameStage.WINDOW_HEIGHT * 0.085);
		// shows Boss health if Boss is on screen
//		this.gc.drawImage(Boss.BOSS_IMAGE, GameStage.WINDOW_WIDTH * 0.56, GameStage.WINDOW_HEIGHT * 0.04, ICON_WIDTH, ICON_WIDTH);

//		if (bossAppear) {
//			if (boss.getHealth() > 0) {
//				this.gc.fillText("HEALTH: " + String.valueOf(boss.getHealth()), GameStage.WINDOW_WIDTH * 0.615, GameStage.WINDOW_HEIGHT * 0.085);
//			} else this.gc.fillText("DEFEATED!", GameStage.WINDOW_WIDTH * 0.615, GameStage.WINDOW_HEIGHT * 0.085);

//			// otherwise it shows "NO APPEARANCE"
//		} else this.gc.fillText("NO APPEARANCE", GameStage.WINDOW_WIDTH * 0.615, GameStage.WINDOW_HEIGHT * 0.085);

		// power ups
//		this.gc.drawImage(HEART1_ICON, GameStage.WINDOW_WIDTH * 0.79, GameStage.WINDOW_HEIGHT * 0.04, ICON_WIDTH, ICON_WIDTH);
//		this.gc.fillText(String.valueOf(numHearts), GameStage.WINDOW_WIDTH * 0.81, GameStage.WINDOW_HEIGHT * 0.15);
//		this.gc.drawImage(POWERUP_ICON, GameStage.WINDOW_WIDTH * 0.86, GameStage.WINDOW_HEIGHT * 0.04, ICON_WIDTH, ICON_WIDTH);
//		this.gc.fillText(String.valueOf(numPowers), GameStage.WINDOW_WIDTH * 0.88, GameStage.WINDOW_HEIGHT * 0.15);
//		this.gc.drawImage(COFFEE_ICON, GameStage.WINDOW_WIDTH * 0.93, GameStage.WINDOW_HEIGHT * 0.04, ICON_WIDTH, ICON_WIDTH);
//		this.gc.fillText(String.valueOf(numCoffee), GameStage.WINDOW_WIDTH * 0.95, GameStage.WINDOW_HEIGHT * 0.15);
	}

	/* -----------------------------------------------------------------------------------------------------
	The following methods render the sprites */
	
	// Method to render obstacles
	public void renderObstacles() {
	    for (Obstacle obstacle : obstacles) {
	        obstacle.render(gc);
	    }
	}

	// method that will render/draw the enemies to the canvas
	private void renderEnemy() {
		for (int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.render(gc);
		}
	}

	// method that will render/draw the bullets to the canvas
	private void renderBullets() {
		for (Bullet b : this.bullets){
			b.render(this.gc);
		}
		for (Bullet b2 : this.bullets2){
			b2.render(this.gc);
		}
		
	}

	// method that will render/draw the power ups to the canvas
	private void renderPowerUps() {
		for (PowerUps p : this.powerups){
			p.render(this.gc);
		}
	}

	/* ---------------------------------------------------------------------------------------------
	This method spawns power ups every 10 seconds and despawns it if it is not picked by 5 seconds */
	private void spawnPowerUps() {
		if (currentTime % POWERUP_APPEARANCE == 0 && currentTime != TIME_LIMIT) {

			// generates a random number that determines what power up type is used
			int i = r.nextInt(6);

			// generates a random x and y coordinate but makes sure it appears on the left side of the screen
			int x = r.nextInt((GameStage.WINDOW_WIDTH/2) - PowerUps.POWERUP_SIZE);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - PowerUps.POWERUP_SIZE);

			// creates a power up based on the random number generated and adds it to the power up ArrayList
			if (i == 0) {
				Heart p = new Heart (x, y, PowerUps.HEART, currentTime);
				this.powerups.add(p);
			} else if (i == 1) {
				Power p = new Power (x, y, PowerUps.POWERUP, currentTime);
				this.powerups.add(p);

			} else if (i == 2) {
				Coffee p = new Coffee (x, y, PowerUps.COFFEE, currentTime);
				this.powerups.add(p);
			} else if (i == 3) {
				Beer p = new Beer (x, y, PowerUps.BEER, currentTime);
				this.powerups.add(p);
			} else if (i == 4) {
				Candy p = new Candy (x, y, PowerUps.CANDY, currentTime);
				this.powerups.add(p);
			} else if (i == 5) {
				Damage p = new Damage (x, y, PowerUps.DAMAGE, currentTime);
				this.powerups.add(p);
			}
			
				
		}

		// checks if the power up should be visible or not
		for(int i = 0; i < this.powerups.size(); i++) {
			PowerUps p = this.powerups.get(i);

			// if power up is visible and time elapsed is equal to power up life then it is removed from the list
			if ((currentTime - p.getTimeSpawned()) == POWERUP_LIFE) {
				this.powerups.remove(i);
			}
		}
	}

	public static void shedule(long time, Runnable r){
		new Thread(() -> {
			try {
				Thread.sleep(time);
				r.run();
			} catch (InterruptedException ex){
				ex.printStackTrace();
			}
		}).start();
	}
	
	
	/* ---------------------------------------------------------------------------------------------
	This method checks if the power up collides with player1. If it is, its power is activated. */
	public void activatePowerUps() {

		// loops through the power ups
		for(int i = 0; i < this.powerups.size(); i++) {
			PowerUps p = this.powerups.get(i);

			// collision of player1 and power up
			if (p.collidesWith(this.player1)) {

				this.powerupActivated = p; // makes the power up in use as the current power up
				p.setTimeObtained(currentTime); // sets the power up's time obtained to the current time

				// checks which power up type was obtained and activates it

				// HEART POWER UP - doubles the strength of the ship
				if (p.getPowerUpType().equals(PowerUps.HEART)) {
					Heart p1 = (Heart) p;
					this.addedPlayer1STR = player1.getHP(); // stores player1's current strength in a variable
					p1.activate(player1, this.addedPlayer1STR); // activates heart power up
					this.numHearts++; // adds 1 to number of HEART1s

					// EVOLUTION STONE POWER UP - makes player1 immortal for 3 seconds
				} else if (p.getPowerUpType().equals(PowerUps.POWERUP)) {
					Power p1 = (Power) p;
					p1.activate(player1); // activates evolution stone power up
					this.numPowers++; // adds 1 to number of evolution stones

					// COFFEE POWER UP - speeds up for 3 seconds
				} else if (p.getPowerUpType().equals(PowerUps.COFFEE)) {
					player1.setSpeed(3);
					this.numCoffee++; // adds 1 to number of coffee

					// BEER POWER UP - slows down the enemies for 3 seconds
				} else if (p.getPowerUpType().equals(PowerUps.BEER)) {
					this.numBeer++; // adds 1 to number of beer
					player2.setSpeed(0.3);

				} else if (p.getPowerUpType().equals(PowerUps.CANDY)) {
					this.numCandy++; // adds 1 to number of beer
					player1.setSpeed(3);
					player2.setSpeed(0.3);

				} else if (p.getPowerUpType().equals(PowerUps.DAMAGE)) {
					this.numDamage++; // adds 1 to number of beer
					player1.setAttackDamage(15);
					this.powerupDeactivated = true;

				}

				this.setDescription(this.powerupActivated.getX(), this.powerupActivated.getY(), this.powerupActivated.getPowerUpType() + " Activated!", currentTime, true, Color.GREEN);
				player1.addPowerUp(p); // adds the power up to the ArrayList of power ups player1 has obtained
				powerups.remove(p); // removes the power up from screen
			}
			
			//player 2 collision
			
			else if (p.collidesWith(GameTimer.player2)) {

				this.powerupActivated = p; // makes the power up in use as the current power up
				p.setTimeObtained(currentTime); // sets the power up's time obtained to the current time

				// checks which power up type was obtained and activates it

				// HEART POWER UP - doubles the strength of the ship
				if (p.getPowerUpType().equals(PowerUps.HEART)) {
					Heart p1 = (Heart) p;
					this.addedPlayer2STR = player2.getHP(); // stores player1's current strength in a variable
					p1.activate(player2, this.addedPlayer2STR); // activates heart power up
					this.numHearts++; // adds 1 to number of HEART1s

					// EVOLUTION STONE POWER UP - makes player1 immortal for 3 seconds
				} else if (p.getPowerUpType().equals(PowerUps.POWERUP)) {
					Power p1 = (Power) p;
					p1.activate(player2); // activates evolution stone power up
					this.numPowers++; // adds 1 to number of evolution stones

					// COFFEE POWER UP - slows down the enemies for 3 seconds
				} else if (p.getPowerUpType().equals(PowerUps.COFFEE)) {
					this.numCoffee++; // adds 1 to number of coffee
					player2.setSpeed(3);
					
					// BEER POWER UP - slows down the enemies for 3 seconds
				} else if (p.getPowerUpType().equals(PowerUps.BEER)) {
					this.numBeer++; // adds 1 to number of beer
					player1.setSpeed(0.3);
					this.powerupDeactivated = true;
					
				} else if (p.getPowerUpType().equals(PowerUps.CANDY)) {
					this.numCandy++; // adds 1 to number of beer
					player2.setSpeed(3);
					player1.setSpeed(0.3);

				} else if (p.getPowerUpType().equals(PowerUps.DAMAGE)) {
					this.numDamage++; // adds 1 to number of beer
					player2.setAttackDamage(15);
					this.powerupDeactivated = true;

				}

				this.setDescription(this.powerupActivated.getX(), this.powerupActivated.getY(), this.powerupActivated.getPowerUpType() + " Activated!", currentTime, true, Color.GREEN);
				player2.addPowerUp(p); // adds the power up to the ArrayList of power ups player1 has obtained
				powerups.remove(p); // removes the power up from screen
			}
		}

		//POWER UP DURATION & DRAWINGS

		// checks if there is a power up currently in use
		if (this.powerupActivated != null) {

			//the heart shows the added strength
			if (this.powerupActivated.getPowerUpType().equals(PowerUps.HEART)) {

				if (currentTime - this.powerupActivated.getTimeObtained() != 2) {

					// heart icon has color to show it is activated
					this.gc.drawImage(PowerUps.HEART_IMAGE, GameStage.WINDOW_WIDTH * 0.79, GameStage.WINDOW_HEIGHT * 0.04, Player.getPlayerWidth()-12, Player.getPlayerWidth()-12);

					// added strength is shown on screen
					this.setDescription(player1.getX(), player1.getY(), "+" + this.addedPlayer1STR + " Strength", currentTime, true, Color.GREEN);

				} else {
					this.powerupActivated = null; //power up in use is now null
				}
			}

			//the evolution stone power up makes player1 immortal after 5 seconds
			else if (this.powerupActivated.getPowerUpType().equals(PowerUps.POWERUP)) {

				// makes sure power up lasts only for 5 seconds
				if (currentTime - this.powerupActivated.getTimeObtained() != POWERUP_LENGTH) {

					// evolution stone icon has color to show it is activated
					this.gc.drawImage(PowerUps.POWER_IMAGE, GameStage.WINDOW_WIDTH * 0.86, GameStage.WINDOW_HEIGHT * 0.04, Player.getPlayerWidth()-12, Player.getPlayerWidth()-12);

				} else {
					player1.setImage(Player.getPlayer1Image()); // change player1 icon from pl to player1
					player1.setImmortality(false); // reverts immortality to false after 5 seconds
					player2.setImage(Player.getPlayer2Image()); // change player1 icon from pl to player1
					player2.setImmortality(false); // reverts immortality to false after 5 seconds
					this.powerupActivated = null; // power up in use is now null
				}
			}

			//the beer returns the enemy speed back to initial speed after 5 seconds
			else if (this.powerupActivated.getPowerUpType().equals(PowerUps.BEER)) {

				// makes sure power up lasts only for 5 seconds
				if (currentTime - this.powerupActivated.getTimeObtained() != POWERUP_LENGTH) {

					//sets all enemies to initial speed, even the newly-created ones
					Beer p = (Beer) powerupActivated;
					p.activate(this.enemies, players);

					// beer icon has color to show it is activated
					this.gc.drawImage(PowerUps.BEER_IMG, GameStage.WINDOW_WIDTH * 0.93, GameStage.WINDOW_HEIGHT * 0.04, Player.getPlayerWidth()-12, Player.getPlayerWidth()-12);

				} else {
					for (Enemy p : this.enemies) {
						p.setSpeed(p.getInitialSpeed()); // sets enemy speed back to its initial speed
					}
					this.powerupDeactivated = false; // deactivates coffee watch power up
					this.powerupActivated = null; // power up in use is now null
					player1.setSpeed(1);
					player2.setSpeed(1);
				}
			}
			//candy returns the enemy speed back to initial speed after 5 seconds
			else if ((this.powerupActivated.getPowerUpType().equals(PowerUps.CANDY) || (this.powerupActivated.getPowerUpType().equals(PowerUps.COFFEE)))) {

				// makes sure power up lasts only for 5 seconds
				if (currentTime - this.powerupActivated.getTimeObtained() != POWERUP_LENGTH) {


				} else {
					for (Enemy p : this.enemies) {
						p.setSpeed(p.getInitialSpeed()); // sets enemy speed back to its initial speed
					}
					this.powerupDeactivated = false; // deactivates coffee watch power up
					this.powerupActivated = null; // power up in use is now null
					player1.setSpeed(1);
					player2.setSpeed(1);
				}
			} 
		}
	}

	/* ---------------------------------------------------------------------------------------------
	This method spawns/instantiates enemies at a random x, y location */
	
	private void spawnEnemies() {
	    Thread spawner = new Thread(() -> {
	        try {
	            Random random = new Random();
	            Set<String> occupiedPositions = new HashSet<>(); // Store occupied positions

	            while (true) {
	                // Spawn 5 enemies at a time
	                for (int i = 0; i < 1; i++) {
	                    int x;
	                    int y;
	                    
	                    // Determine whether to spawn enemy on the left, right, top, or bottom
	                    int side = random.nextInt(3); // Random number between 0 and 3
	                    
	                    switch (side) {
	                        case 0: // Spawn on the left side
	                            x = 0;
	                            y = random.nextInt(GameStage.WINDOW_HEIGHT);
	                            break;
	                        case 1: // Spawn on the right side
	                            x = GameStage.WINDOW_WIDTH - Enemy.ENEMY_WIDTH;
	                            y = random.nextInt(GameStage.WINDOW_HEIGHT);
	                            break;
	                        case 2: // Spawn at the top
	                            x = random.nextInt(GameStage.WINDOW_WIDTH);
	                            y = 0;
	                            break;
	                        case 3: // Spawn at the bottom
	                            x = random.nextInt(GameStage.WINDOW_WIDTH);
	                            y = GameStage.WINDOW_HEIGHT - Enemy.ENEMY_WIDTH;
	                            break;
	                        default:
	                            throw new IllegalStateException("Unexpected value: " + side);
	                    }
	                    
	                    String positionKey = x + "," + y;

	                    // Check if the position is already occupied
	                    if (!occupiedPositions.contains(positionKey)) {
	                        Player closestPlayer = findClosestPlayer(x, y); // Find the closest player
	                        this.enemies.add(new Enemy(x, y, closestPlayer)); // Spawn enemy targeting the closest player
	                        occupiedPositions.add(positionKey); // Add the new position to occupied positions
	                    }
	                }

	                Thread.sleep(1500); // Adjust as needed to control the spawning rate
	            }
	        } catch (InterruptedException ex) {
	            ex.printStackTrace();
	        }
	    });
	    spawner.setDaemon(true);
	    spawner.start();
	}
	
	private void spawnObstacles(Player p1, Player p2) {
        Thread spawner = new Thread(() -> {
            try {
                Random random = new Random();
                Set<String> occupiedPositions = new HashSet<>(); // Store occupied positions

                while (true) {
                    // Spawn 5 obstacles at a time
                    for (int i = 0; i < 10; i++) {
                        double x = random.nextDouble() * GameStage.WINDOW_WIDTH;
                        double y = random.nextDouble() * GameStage.WINDOW_HEIGHT;

                        Obstacle newObstacle = new Obstacle(x, y);

                        // Check if the obstacle collides with either player
                        if (!p1.collidesWith(newObstacle) && !p2.collidesWith(newObstacle)) {
                            GameTimer.obstacles.add(newObstacle); // Spawn obstacle
                            occupiedPositions.add(x + "," + y); // Add the new position to occupied positions
                        }
                    }

                    Thread.sleep(1500); // Adjust as needed to control the spawning rate

                    // Remove spawned obstacles after every 5 seconds
                    for (int j = 0; j < 10; j++) {
                        Thread.sleep(1000); // Sleep for 1 second
                    }

                    GameTimer.obstacles.clear(); // Remove all spawned obstacles
                    occupiedPositions.clear(); // Clear occupied positions
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        spawner.setDaemon(true);
        spawner.start();
    }
	
	// Method to add obstacles
	public void addObstacle(double x, double y) {
	    Obstacle obstacle = new Obstacle(x, y);
	    obstacles.add(obstacle);
	}

	private Player findClosestPlayer(int enemyX, int enemyY) {
	    double minDistance = Double.MAX_VALUE;
	    Player closestPlayer = null;
	    
	    // Iterate through all players to find the closest one
	    for (Player player : players) {
	        double distance = Math.sqrt(Math.pow(enemyX - player.getX(), 2) + Math.pow(enemyY - player.getY(), 2));
	        if (distance < minDistance) {
	            minDistance = distance;
	            closestPlayer = player;
	        }
	    }
	    return closestPlayer;
	}

	
	private void spawnenemys() {

		// spawns 7 enemies at the beginning of the game
		if(currentTime == 0) {
			for(int i = 0; i < GameTimer.INITIAL_ENEMIES; i++){

				// generates a random x and y coordinate but makes sure it appears on the right side of the screen
				int x = 400 + r.nextInt(((GameStage.WINDOW_WIDTH)/2) - Enemy.ENEMY_WIDTH);
				int y = r.nextInt(GameStage.WINDOW_HEIGHT - Enemy.ENEMY_WIDTH);

				Enemy p = new Enemy(x,y, player1); // creates an enemy
				this.enemies.add(p); // adds it to arrayList of enemies
			}

			// spawns 3 more enemies every 5 seconds until the time limit
		} else if (currentTime % 5 == 0 && currentTime != TIME_LIMIT) {
			for(int i = 0; i < GameTimer.MAX_ENEMIES; i++){
				int x = 400 + r.nextInt((GameStage.WINDOW_WIDTH)/2 - Enemy.ENEMY_WIDTH);
				int y = r.nextInt(GameStage.WINDOW_HEIGHT - Enemy.ENEMY_WIDTH);
				Enemy p = new Enemy(x,y, player1);
				this.enemies.add(p);
			}
		}

	}

	/* ---------------------------------------------------------------------------------------------
	This method spawns/instantiates a Boss at a random x, y location */
	private void spawnBoss() {

		// spawns a Boss if time elapsed is 30 seconds
		if(currentTime == BOSS_TIME_APPEARANCE) {
			int x = 400 + r.nextInt(((GameStage.WINDOW_WIDTH)/2) - Boss.BOSS_WIDTH); // so that fishes appear only at the right side of the screen
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - Boss.BOSS_WIDTH);

			this.boss = new Boss(x,y); // creates a boss
			this.enemies.add(this.boss); // adds it to arrayList of enemies
		}
	}

	/* ---------------------------------------------------------------------------------------------
	This method moves the bullets shot by player1 */
	private void moveBullets() {

		// create a local arrayList of Bullets for the bullets 'shot' by player1
		ArrayList<Bullet> bList = this.bullets;
		ArrayList<Bullet> bList2 = this.bullets2;

		// loop through the bullet1 list and check whether a bullet is still visible
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);

			if(b.getVisible()) { // if bullet is visible, it will move
				if(code1==KeyCode.A) {
					activeBullet = true;
					b.moveLeft();
					System.out.println(b.getVisible()+" visibility state .");
				}
				else if (code1==KeyCode.D) {

					activeBullet = true;
					b.moveRight();
					System.out.println(b.getVisible()+" visibility state .");
				}
				else if (code1==KeyCode.W) {

					activeBullet = true;
					b.moveUp();
					System.out.println(b.getVisible()+" visibility state .");
				}
				else if (code1==KeyCode.S) {

					activeBullet = true;
					b.moveDown();
					System.out.println(b.getVisible()+" visibility state .");
					System.out.println(b.getY()+" POS .");
				}
				
			} else { // else, it is removed

				activeBullet = false;
				System.out.println(activeBullet+" state.");
				bList.remove(i);
			}
		}
		// loop through the bullet2 list and check whether a bullet is still visible

		for(int i = 0; i < bList2.size(); i++){
			Bullet b2 = bList2.get(i);

			if(b2.getVisible()) { // if bullet is visible, it will move
				if(code2==KeyCode.A) {
					activeBullet2 = true;
					b2.moveLeft();
				}
				else if (code2==KeyCode.D) {
					activeBullet2 = true;
					b2.moveRight();
				}
				else if (code2==KeyCode.W) {
					activeBullet2 = true;
					b2.moveUp();
				}
				else if (code2==KeyCode.S) {
					activeBullet2 = true;
					b2.moveDown();
				}
				
			} else { // else, it is removed
				activeBullet2 = false;
				bList2.remove(i);
			}
		}

		// collision of bullet1 and enemy
		for (Bullet b : this.bullets) { // loops through the bullet list
			if(b.collidesWith(player2)) {
				b.setVisible(false);
				player2.decreaseHP(player1.getAttackDamage()); 
				// shows the damage incurred by player1 to player2
				this.setDescription(player2.getX(), player2.getY(), "-" + player1.getAttackDamage() + " HP", currentTime, true, Color.RED);
			}
			// checks if Player 2 has enough health
			if(player2.getHP() <= 0) { // if Player 2 health is 0 or below, it dies
				player2.setHP(0);
				player2.die();
			}
			for (Enemy p : this.enemies) { // loops through the enemy list

				// if the bullet collides with the enemy, then the enemy disappears
				if (b.collidesWith(p)) {

					// if the enemy is a Boss
					if (p instanceof Boss) {
						b.setVisible(false); //the bullet will not be visible
						activeBullet = false; //deactivateBullet
						Boss boss = (Boss) p; 
						b.setDamage(player1.getHP()); // damage of bullet is player1's current strength
						boss.decreaseHealth(b.getDamage()); // decreases health by damage of player1

						//prints damage
						this.setDescription(boss.getX(), boss.getY(), "-" + b.getDamage() + " Damage", currentTime, true, Color.RED); //shows +1 as score

						// if Boss has 0 health, it will die
						if (boss.getHealth() <= 0) {
							setDescription(p.getX(), p.getY(), "DEAD!", currentTime, true, Color.RED);
							boss.setAlive(false); // Boss will be set to dead
						}

					} else {
						player1.setScore(player1.getScore()+1);
						p.setAlive(false); // fish will be set to dead
						b.setVisible(false); // bullet will no longer be visible
						activeBullet = false;

						this.setDescription(p.getX(), p.getY(), "+1 Score", currentTime, true, Color.GREEN); //shows +1 as score
					}
				}
			}
			
			for (Obstacle o : this.obstacles) { // loops through the obstacle list

				// if the bullet collides with the obstacle, then the obstacle disappears
				if (b.collidesWith(o)) {
						b.setVisible(false); // bullet will no longer be visible
						activeBullet = false;
				}
			}
		}
		
		//bullet2 collision
		for (Bullet b2 : this.bullets2) { // loops through the bullet list
			if(b2.collidesWith(player1)) {
				b2.setVisible(false);
				player1.decreaseHP(player2.getAttackDamage());
				this.setDescription(player1.getX(), player1.getY(), "-" + player2.getAttackDamage() + " HP", currentTime, true, Color.RED);
			}
			// checks if player1 has enough health
			if(player1.getHP() <= 0) { // if player1's health is 0 or below, it dies
				player1.setHP(0);
				player1.die();
			}
			for (Enemy p : this.enemies) { // loops through the enemy list

				// if the bullet collides with the enemy, then the enemy disappears
				if (b2.collidesWith(p)) {

					// if the enemy is a Boss
					if (p instanceof Boss) {
						b2.setVisible(false); //the bullet will not be visible
						Boss boss = (Boss) p; 
						b2.setDamage(player2.getHP()); // damage of bullet is player1's current strength
						boss.decreaseHealth(b2.getDamage()); // decreases health by damage of player1

						//prints damage
						this.setDescription(boss.getX(), boss.getY(), "-" + b2.getDamage() + " Damage", currentTime, true, Color.RED); //shows +1 as score

						// if Boss has 0 health, it will die
						if (boss.getHealth() <= 0) {
							setDescription(p.getX(), p.getY(), "DEAD!", currentTime, true, Color.RED);
							boss.setAlive(false); // Boss will be set to dead
						}

					} else {
						player2.setScore(player2.getScore()+1);
						p.setAlive(false); // fish will be set to dead
						b2.setVisible(false); // bullet will no longer be visible

						this.setDescription(p.getX(), p.getY(), "+1 Score", currentTime, true, Color.GREEN); //shows +1 as score
					}
				}
			}
			
			for (Obstacle o : GameTimer.obstacles) { // loops through the obstacle list

				// if the bullet collides with the obstacle, then the obstacle disappears
				if (b2.collidesWith(o)) {
						b2.setVisible(false); // bullet will no longer be visible
						activeBullet = false;
				}
			}
		}
	}

	/* ---------------------------------------------------------------------------------------------
	This method moves the enemies */
	private void moveEnemies(){

		for(int i = this.enemies.size() - 1; i >= 0; i--) { // loop through the enemy ArrayList in reverse order
		    Enemy p = this.enemies.get(i);

		    // if enemy is alive then it will move; otherwise, it is removed
		    if(p.isAlive())
		        p.move();
		    else this.enemies.remove(i);
		}


		for (Enemy p: this.enemies) { // loops through all the enemies to see its collisions

			//for player1 and enemy collision
			if (p.collidesWith(GameTimer.player1)) {

				// if enemy is just a regular enemy then it dies
				if (!(p instanceof Boss)) {
					p.setAlive(false); // the enemy will die
//					player1.setScore(player1.getScore()+1);
				}


				// if player1 is not immortal then its strength will decrease by the enemy/Boss damage
				if (!player1.getImmortality()) { // checks if player1 is immortal or not
					player1.decreaseHP(10); // player1 will decrease according to strength of enemy

					// if player1 collides with Boss, the Boss grants temporary immortality to incur less damage
					if (p instanceof Boss) {
						player1.setImmortality(true);
						this.invulnerabilityTime = currentTime;
					}

					// shows the damage incurred by player1
					this.setDescription(p.getX(), p.getY(), "-" + p.getDamage() + " HP", currentTime, true, Color.RED);

					// if player1 is immortal then no damage occurs and shows +1 to score
				} else if (player1.getImmortality() && !(p instanceof Boss)) this.setDescription(p.getX(), p.getY(), "+1 Score", currentTime, true, Color.GREEN);

				// checks if player1 has enough health
				if(player1.getHP() <= 0) { // if player1's health is 0 or below, it dies
					player1.setHP(0);
					player1.die();
				}
			}
			
			//Player2 and Enemy Collision
			if (p.collidesWith(GameTimer.player2)) {

				// if enemy is just a regular enemy then it dies
				if (!(p instanceof Boss)) {
					p.setAlive(false); // the enemy will die
//					this.numEnemies++; // adds 1 to number of enemies defeated
//					player2.setScore(player2.getScore()+1);
				}


				// if player1 is not immortal then its strength will decrease by the enemy/Boss damage
				if (!player2.getImmortality()) { // checks if player1 is immortal or not
					player2.decreaseHP(10); // player1 will decrease according to strength of enemy

					// if player1 collides with Boss, the Boss grants temporary immortality to incur less damage
					if (p instanceof Boss) {
						player2.setImmortality(true);
						this.invulnerabilityTime = currentTime;
					}

					// shows the damage incurred by player1
					this.setDescription(p.getX(), p.getY(), "-" + p.getDamage() + " Strength", currentTime, true, Color.RED);

					// if player1 is immortal then no damage occurs and shows +1 to score
				} else if (player2.getImmortality() && !(p instanceof Boss)) this.setDescription(p.getX(), p.getY(), "+1 Score", currentTime, true, Color.GREEN);

				// checks if player1 has enough health
				if(player2.getHP() <= 0) { // if player1's health is 0 or below, it dies
					player2.setHP(0);
					player2.die();
				}
			}
		}

		// temporary immortality for 1 second after hitting Boss for less glitch damage
		if (player1.getImmortality()) {
			if (currentTime - invulnerabilityTime == TEMP_IMMORTALITY) {
				player1.setImmortality(false);
			}
		}
		
		// temporary immortality for 1 second after hitting Boss for less glitch damage
				if (player2.getImmortality()) {
					if (currentTime - invulnerabilityTime == TEMP_IMMORTALITY) {
						player2.setImmortality(false);
					}
				}
	}

	/* ---------------------------------------------------------------------------------------------
	This method speeds the movement of the enemy to maximum for a 3 second duration every 15 seconds */
	private void enemySpeed() {

		if (currentTime % ENEMY_MAX_TIME == 0 && currentTime != TIME_LIMIT) {
			this.isEnemyMaxSpeed = true; // activates movement of enemy at max speed
			this.initialTime = currentTime; // time when speed changes is recorded
		}

		if (this.isEnemyMaxSpeed) { // if true then it proceeds to change movement of enemy to max speed
			if(currentTime - this.initialTime != ENEMY_MAX_DURATION) { // if time elapsed is not 3 then enemy speed is still equal to max
				if (!powerupDeactivated) { // checks if time is being slowed down through the power up
					for (Enemy p: this.enemies) { // sets speed of all enemy to max
						p.setSpeed(Enemy.MAX_SPEED);
					}
				}

			} else {
				this.isEnemyMaxSpeed = false; // makes the enemy stop moving at max speed once 3 seconds have elapsed
				for (Enemy p: this.enemies) { // return speed of enemy to its initial speed
					p.setSpeed(p.getInitialSpeed());
				}
			}
		}
	}

	/* ---------------------------------------------------------------------------------------------
	This method sets the description to be shown on the screen */
	private void setDescription(double d, double e, String string, int time, Boolean b, Color color) {
		this.descriptionX = d;
		this.descriptionY = e;
		this.descriptionString = string;
		this.descriptionTime = time;
		this.showingDescription = b;
		this.descriptionColor = color;
	}

	/* ---------------------------------------------------------------------------------------------
	This method renders/draws the description to the screen for 1 second */
	private void showDescription() {
		if (showingDescription) {
			if (currentTime - descriptionTime != DESCRIPTION_LENGTH && currentTime != TIME_LIMIT) {
				Font theFont = Font.font("Verdana",FontWeight.BOLD, 13);
				this.gc.setFont(theFont);
				this.gc.setFill(descriptionColor);
				this.gc.fillText(descriptionString, descriptionX, descriptionY);

			} else showingDescription = false;
		}
	}

	/* ---------------------------------------------------------------------------------------------
	This method moves the ship depending on the key pressed */
	public void movePlayer(KeyCode ke) {
		if (ke == KeyCode.W) GameTimer.player1.setDY(-player1.getSpeed());
	    if (ke == KeyCode.A) GameTimer.player1.setDX(-player1.getSpeed());
	    if (ke == KeyCode.S) GameTimer.player1.setDY(player1.getSpeed());
	    if (ke == KeyCode.D) GameTimer.player1.setDX(player1.getSpeed());

		
		//player 2
		if(ke==KeyCode.NUMPAD8) GameTimer.player2.setDY(-player2.getSpeed());

		if(ke==KeyCode.NUMPAD4) GameTimer.player2.setDX(-player2.getSpeed());

		if(ke==KeyCode.NUMPAD5) GameTimer.player2.setDY(player2.getSpeed());

		if(ke==KeyCode.NUMPAD6) GameTimer.player2.setDX(player2.getSpeed());
		
	}
	
	public void shootPlayer1(KeyCode ke) {
		if(ke==KeyCode.Y) {
			this.player1.shoot();
			code1 = KeyCode.W; 
		}
		
		if(ke==KeyCode.H) {
			this.player1.shoot();
			code1 = KeyCode.S;
		}
		
		if(ke==KeyCode.G) {
			this.player1.shoot();
			code1 = KeyCode.A;
		}
		
		if(ke==KeyCode.J) {
			this.player1.shoot();
			code1 = KeyCode.D;
		}

		System.out.println(ke+" key pressed.");
	}
	
	public void shootPlayer2(KeyCode ke) {
		
		if(ke==KeyCode.UP) {
			this.player2.shoot();
			code2 = KeyCode.W; 
		}
		
		if(ke==KeyCode.DOWN) {
			this.player2.shoot();
			code2 = KeyCode.S;
		}
		
		if(ke==KeyCode.LEFT) {
			this.player2.shoot();
			code2 = KeyCode.A;
		}
		
		if(ke==KeyCode.RIGHT) {
			this.player2.shoot();
			code2 = KeyCode.D;
		}
		System.out.println(ke+" key pressed.");
	}

	/* ---------------------------------------------------------------------------------------------
	This method stops player1's movement and set it DX and DY to 0 */
	public void stopPlayer1() {
	    this.player1.setDX(0);
	    this.player1.setDY(0);
	}
	
	//This method stops the player2's movement and set it DX and DY to 0 */
	public void stopPlayer2() {
	    this.player2.setDX(0);
	    this.player2.setDY(0);
	}
	

	
	
}
