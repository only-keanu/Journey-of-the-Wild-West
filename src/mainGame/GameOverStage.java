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
	private int numApple;
	private int numEvolutionStone;
	private int numPokedex;

	public final Image LOST_BACKGROUND = new Image("lost.png");
	public final Image WIN_BACKGROUND = new Image("end.png");

	public GameOverStage(int numPokeballs, int numApple, int numEvolutionStone, int numPokedex, int currentTime) {
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, Color.BLACK);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		this.score = numPokeballs;
		this.timer = currentTime;
		this.numApple = numApple;
		this.numEvolutionStone = numEvolutionStone;
		this.numPokedex = numPokedex;

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
		Font theFont = Font.font("Verdana", FontWeight.EXTRA_BOLD, 18);
		this.gc.setFont(theFont);
		this.gc.setFill(Color.BLACK);
		this.gc.drawImage(Enemy.POKEBALL_IMAGE, GameStage.WINDOW_WIDTH * 0.400, GameStage.WINDOW_HEIGHT * 0.65, Player.getPikachuWidth()-12, Player.getPikachuWidth()-12);
		this.gc.fillText("SCORE: " + String.valueOf(this.score), GameStage.WINDOW_WIDTH * 0.455, GameStage.WINDOW_HEIGHT * 0.7);


		this.gc.drawImage(PowerUp.APPLE_IMAGE, GameStage.WINDOW_WIDTH * 0.375, GameStage.WINDOW_HEIGHT * 0.49, Player.getPikachuWidth()-12, Player.getPikachuWidth()-12);
		this.gc.fillText(String.valueOf(numApple), GameStage.WINDOW_WIDTH * 0.39, GameStage.WINDOW_HEIGHT * 0.62);


		this.gc.drawImage(PowerUp.EVOLUTION_STONE_IMAGE, GameStage.WINDOW_WIDTH * 0.475, GameStage.WINDOW_HEIGHT * 0.49, Player.getPikachuWidth()-12, Player.getPikachuWidth()-12);
		this.gc.fillText(String.valueOf(numEvolutionStone), GameStage.WINDOW_WIDTH * 0.49, GameStage.WINDOW_HEIGHT * 0.62);


		this.gc.drawImage(PowerUp.POKEDEX_IMAGE, GameStage.WINDOW_WIDTH * 0.575, GameStage.WINDOW_HEIGHT * 0.49, Player.getPikachuWidth()-12, Player.getPikachuWidth()-12);
		this.gc.fillText(String.valueOf(numPokedex), GameStage.WINDOW_WIDTH * 0.59, GameStage.WINDOW_HEIGHT * 0.62);


		//adding the exit button
		Button exitBtn = new Button("Exit");

		//setting the style of the exit button 
		exitBtn.setStyle("-fx-font: 18 Verdana;" 
				+ "-fx-font-weight: bold;"
				+ " -fx-background-color: #000000;"
				+ " -fx-text-fill: #FFFFFF;");
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
