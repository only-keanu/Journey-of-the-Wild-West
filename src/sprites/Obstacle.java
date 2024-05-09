package sprites;

import java.util.Random;

import sprites.Enemy;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import mainGame.GameStage;
import mainGame.GameTimer;

public class Obstacle extends Sprite {

	Random r = new Random();

	public final static Image OBSTACLE_IMAGE = new Image("obstacle.png",Enemy.ENEMY_WIDTH,Enemy.ENEMY_WIDTH,false,false);
	public final static int OBSTACLE_WIDTH = 25;


    public Obstacle(double x, double y) {
        super(x, y);
        this.loadImage(Obstacle.OBSTACLE_IMAGE);
    }


	public boolean collided(double x, double y, int i, int j) {
		// TODO Auto-generated method stub
		return Math.sqrt(Math.pow(this.x+i/2-x-j/2, 2)+Math.pow(this.y+i/2-y-j/2, 2)) <= i/2+j/2;
	}
    
}
