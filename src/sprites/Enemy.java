package sprites;

import java.util.Random;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import mainGame.GameStage;
import mainGame.GameTimer;
import sprites.Sprite;
import sprites.Player;

public class Enemy extends Sprite {

    Random r = new Random();

    public final static Image ENEMY_IMAGE = new Image("enemy.png", Enemy.ENEMY_WIDTH, Enemy.ENEMY_WIDTH, false, false);
    public final static int ENEMY_WIDTH = 25;
    public final static double MIN_SPEED = 0.1;
    public final static double MAX_SPEED = 1;
    double randomValue = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * r.nextDouble();

    protected boolean moveRight;
    protected double initialSpeed;
    protected double speed;
    protected boolean alive;
    protected int damage;

    private Player player; // Reference to the player object

    public Enemy(double x, double y, Player player) {
        super(x, y);
        this.alive = true;
        this.loadImage(Enemy.ENEMY_IMAGE);
        Random r = new Random();
        this.speed = randomValue;
        this.initialSpeed = this.speed;
        this.damage = r.nextInt(11) + 30;
        this.moveRight = true;
        this.player = player; // Assigning the player reference
    }

    private boolean checkCollisionWithObstacles(double nextX, double nextY) {
        // Check collision with each obstacle
        for (Obstacle obstacle : GameTimer.obstacles) {
            if (obstacle.collided(nextX, nextY, ENEMY_WIDTH, ENEMY_WIDTH)) {
                return true;
            }
        }
        return false;
    }

    public boolean collided(double x, double y, double w1, double w2) {
        // Check if the distance between the center of the the 2 enemies is less than the total width (diameter)
        return Math.sqrt(Math.pow(this.x + w1 / 2 - x - w2 / 2, 2) + Math.pow(this.y + w1 / 2 - y - w2 / 2, 2)) <= w1 / 2 + w2 / 2;
    }

    // Method to update the movement of the enemy to follow the player
    public void move() {
        if (player != null && player.isAlive()) {
            double distance = Math.sqrt(Math.pow(this.x - this.player.getX(), 2) + Math.pow(this.y - this.player.getY(), 2));

            // Move the enemy towards the player with a certain speed
            double angle = Math.atan2(this.player.getY() - this.y, this.player.getX() - this.x);

            double nextX = this.x + Math.cos(angle) * this.speed;
            double nextY = this.y + Math.sin(angle) * this.speed;

            // Check if the next position collides with obstacles
            if (!checkCollisionWithObstacles(nextX, nextY)) {
                this.x = nextX;
                this.y = nextY;
            } else {
                // If collision with an obstacle occurs, adjust the next position
                double obstacleAngle = Math.atan2(nextY - this.y, nextX - this.x);
                double obstacleDistance = Math.sqrt(Math.pow(nextX - this.x, 2) + Math.pow(nextY - this.y, 2));
                double obstacleX = this.x + Math.cos(obstacleAngle) * (obstacleDistance - ENEMY_WIDTH);
                double obstacleY = this.y + Math.sin(obstacleAngle) * (obstacleDistance - ENEMY_WIDTH);
                
                // Update the enemy's position to be just before the obstacle
                this.x = obstacleX;
                this.y = obstacleY;
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

    public void setSpeed(double maxEnemySpeed) {
        this.speed = maxEnemySpeed;
    }
}
