package mainGame;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameStage {
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;

	//the class constructor
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc, this.scene, this);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.root.getChildren().add(canvas);
		this.stage.setTitle("Journey of the Wild  West Game");
		this.stage.setScene(this.scene);
		this.gametimer.start();
		this.stage.show();
	}

	public void setGameOver(boolean player1Wins,int numEnemies, int numHearts, int numPowerUps, int numCoffees, int currentTime){
		
		// creates a GameOverStage
		GameOverStage gameOver = new GameOverStage(player1Wins, numEnemies, numHearts, numPowerUps, numCoffees, currentTime);
		gameOver.setStage(stage);
	}
}

