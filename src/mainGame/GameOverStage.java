package mainGame;

import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import powerups.PowerUp;
import sprites.Player;
import sprites.Enemy;

public class GameOverStage {
	private Scene scene;
	private Stage stage;
	private StackPane root;
	private GraphicsContext gc;
	private Canvas canvas;

	private int timer;
	private int score;
	private int numHeart;
	private int numPowerUps;
	private int numCoffees;
	private int ICON_WIDTH = 40;
	
	public final Image LOST_BACKGROUND = new Image("lost.png");
	public final Image WIN_BACKGROUND = new Image("end.png");

	public GameOverStage(int numEnemies, int numHearts, int numPowerUps, int numCoffees, int currentTime) {
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, Color.BLACK);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		this.score = numEnemies;
		this.timer = currentTime;
		this.numHeart = numHearts;
		this.numPowerUps = numPowerUps;
		this.numCoffees = numCoffees;

		this.setProperties();
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Game Over!");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	void setProperties() {

		//setting the background
		if (this.timer == 60){ //if player wins
			this.gc.drawImage(WIN_BACKGROUND, 0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		}

		else { //if player loses
			this.gc.drawImage(LOST_BACKGROUND, 0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		}

		//setting the font style and showing the score
		Font theFont = Font.font("ArcadeClassic", FontWeight.EXTRA_BOLD, 18);
		this.gc.setFont(theFont);
		this.gc.setFill(Color.WHITE);
		this.gc.drawImage(Enemy.ENEMY_IMAGE, GameStage.WINDOW_WIDTH * 0.400, GameStage.WINDOW_HEIGHT * 0.65, ICON_WIDTH, ICON_WIDTH);
		this.gc.fillText("SCORE: " + String.valueOf(this.score), GameStage.WINDOW_WIDTH * 0.455, GameStage.WINDOW_HEIGHT * 0.7);


		this.gc.drawImage(PowerUp.HEART_IMAGE, GameStage.WINDOW_WIDTH * 0.375, GameStage.WINDOW_HEIGHT * 0.49, ICON_WIDTH, ICON_WIDTH);
		this.gc.fillText(String.valueOf(numHeart), GameStage.WINDOW_WIDTH * 0.39, GameStage.WINDOW_HEIGHT * 0.62);


		this.gc.drawImage(PowerUp.POWER_IMAGE, GameStage.WINDOW_WIDTH * 0.475, GameStage.WINDOW_HEIGHT * 0.49, ICON_WIDTH, ICON_WIDTH);
		this.gc.fillText(String.valueOf(numPowerUps), GameStage.WINDOW_WIDTH * 0.49, GameStage.WINDOW_HEIGHT * 0.62);


		this.gc.drawImage(PowerUp.COFFEE_IMG, GameStage.WINDOW_WIDTH * 0.575, GameStage.WINDOW_HEIGHT * 0.49, ICON_WIDTH, ICON_WIDTH);
		this.gc.fillText(String.valueOf(numCoffees), GameStage.WINDOW_WIDTH * 0.59, GameStage.WINDOW_HEIGHT * 0.62);


		//adding the exit button
		Button exitBtn = new Button("Exit");

		//setting the style of the exit button 
		exitBtn.setStyle("-fx-font: 18 ArcadeClassic;" 
				+ "-fx-font-weight: bold;"
				+ " -fx-background-color: #000000;"
				+ " -fx-text-fill: #ffffff;");
		exitBtn.setTranslateY(142);

		//when exit button is clicked, the program stops
		exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.exit(0);
			}
		});

		//adding of elements to the root
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(exitBtn);

	}	
}
