package menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import mainGame.GameStage;
import javafx.scene.control.Button;

public class Instructions {
	private Canvas canvas;
	private static GraphicsContext gc;

	private StackPane root;
	private Scene scene;
	private Stage stage;

	private static int pageCounter = 1;

	private final static Image INSTRUCTION_PAGE1 = new Image ("instructions1.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);
	private final static Image INSTRUCTION_PAGE2 = new Image ("instructions2.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);
	private final static Image INSTRUCTION_PAGE3 = new Image ("instructions2.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);

	Instructions () {
		this.canvas = new Canvas (GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.root = new StackPane();
		this.scene = new Scene (root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	public void setStage (Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Game Instructions");
		this.stage.setScene(this.scene);
		this.stage.show();
		this.setProperties();
	}

	private void setProperties () {
		Instructions.gc.drawImage(Instructions.INSTRUCTION_PAGE1, 0, 0);

		//creating the buttons
		Button nextBtn = new Button();
		Button previousBtn = new Button();
		Button homeBtn = new Button();

		//creating image and image views
		Image nextButton = new Image ("next.png");
		ImageView nextView = new ImageView();
		Image previousButton = new Image ("previous.png");
		ImageView previousView = new ImageView();
		Image homeButton = new Image ("home.png");
		ImageView homeView = new ImageView();

		//setting the images and styles

		//next view
		nextView.setImage(nextButton);
		nextView.setFitHeight(40);
		nextView.setFitWidth(40);
		nextBtn.setGraphic(nextView);
		nextBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		nextBtn.setTranslateX(110);
		nextBtn.setTranslateY(225);

		//previous view
		previousView.setImage(previousButton);
		previousView.setFitHeight(40);
		previousView.setFitWidth(40);
		previousBtn.setGraphic(previousView);
		previousBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		previousBtn.setTranslateX(-110);
		previousBtn.setTranslateY(225);

		//home view
		homeView.setImage(homeButton);
		homeView.setFitHeight(40);
		homeView.setFitWidth(80);
		homeBtn.setGraphic(homeView);
		homeBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		homeBtn.setTranslateY(225);

		//adding the event handlers
		homeBtn.setOnMouseClicked(backToHome);
		nextBtn.setOnMouseClicked(nextPage);
		previousBtn.setOnMouseClicked(previousPage);

		//adding to root
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(nextBtn);
		this.root.getChildren().add(previousBtn);
		this.root.getChildren().add(homeBtn);
	}

	//this method returns to the menu
	private EventHandler<MouseEvent> backToHome = new EventHandler<MouseEvent>(){
		public void handle(MouseEvent e) {
			MainMenu home = new MainMenu();
			home.setStage(stage);
		}
	};

	//this method switches the page to the next one
	private EventHandler<MouseEvent> nextPage = new EventHandler<MouseEvent>(){
		private final int MAX_PAGES = 3;
		public void handle(MouseEvent e) {
			if (Instructions.pageCounter < this.MAX_PAGES) {
				Instructions.pageCounter ++;
			}

			Instructions.switchPage();
		}
	};

	//method that switches page to the previous one
	private EventHandler<MouseEvent> previousPage = new EventHandler<MouseEvent>(){
		private final int MIN_PAGES = 1;
		public void handle(MouseEvent e) {
			if (Instructions.pageCounter > this.MIN_PAGES) {
				Instructions.pageCounter --;
			}

			Instructions.switchPage();
		}
	};

	//method for switching the page
	private static void switchPage() {
		if (Instructions.pageCounter == 1) Instructions.gc.drawImage(Instructions.INSTRUCTION_PAGE1, 0, 0);
		else if (Instructions.pageCounter == 2) Instructions.gc.drawImage(Instructions.INSTRUCTION_PAGE2, 0, 0);
		else if (Instructions.pageCounter == 3) Instructions.gc.drawImage(Instructions.INSTRUCTION_PAGE3, 0, 0);
	}
}