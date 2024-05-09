package menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mainGame.GameStage;
import menu.MainMenu;

public class MainMenu {
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private Scene splashScene;


	//the class constructor
	public MainMenu() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
        
		this.stage = stage;

		this.root.getChildren().add(canvas);
		
		this.stage.getIcons().add(new Image("icon.png"));
		this.stage.setTitle("Journey of the Wild West");
		this.stage.setScene(this.scene);

		this.stage.show();

		this.initSplash(stage);
		stage.setScene(this.splashScene);

		stage.setResizable(false);

	}

	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
		root.getChildren().addAll(this.createCanvas(),this.createVBox());
		this.splashScene = new Scene(root);
	}

	private Canvas createCanvas() {
		Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Image background = new Image("/bg.png");
		gc.drawImage(background, 0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		return canvas;
	}

	private VBox createVBox() {
		// for buttons in center
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setPadding(new Insets(30));
		vbox.setSpacing(8);

		// button for start game
		Button b1 = new Button ("New Game");
		b1.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 18));
		b1.setTextFill(Color.WHITE);
		b1.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(3d), Insets.EMPTY)));
		b1.setOnMouseClicked(start);

		// button for about
		Button b2 = new Button ("Instructions");
		b2.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 18));
		b2.setTextFill(Color.WHITE);
		b2.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(3d), Insets.EMPTY)));
		b2.setOnMouseClicked(instruc);

		// button for instructions
		Button b3 = new Button ("About");
		b3.setFont(Font.font("ArcadeClassic", FontWeight.BOLD, 18));
		b3.setTextFill(Color.WHITE);
		b3.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(3d), Insets.EMPTY)));
		b3.setOnMouseClicked(about);

		// adds buttons to vbox
		vbox.getChildren().add(b1);
		vbox.getChildren().add(b2);
		vbox.getChildren().add(b3);

		return vbox;
	}

	//sets stage of about class if button is clicked
	private EventHandler<MouseEvent> about = new EventHandler<MouseEvent>(){
		public void handle(MouseEvent e) {
			About about = new About();
			about.setStage(stage);
		}
	};

	//sets stage of instructions class if button is clicked
	private EventHandler<MouseEvent> instruc = new EventHandler<MouseEvent>(){
		public void handle(MouseEvent e) {
			Instructions instruc = new Instructions();
			instruc.setStage(stage);
		}
	};

	//creates a gametimer class if start button is clicked
	private EventHandler<MouseEvent> start = new EventHandler<MouseEvent>(){
		public void handle(MouseEvent e) {
			setGame(stage);
		}
	};

	private void setGame(Stage stage) {
		stage.setScene(this.scene);
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		GameStage gameStage = new GameStage();
		gameStage.setStage(stage);
	}
}

