package menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import mainGame.GameStage;
import javafx.scene.control.Button;


public class About {
	private Canvas canvas;
	private static GraphicsContext gc;
	private StackPane root;
	private Scene scene;
	private Stage stage;

	private static int pageCounter = 1;

	private final static Image ABOUT_PAGE = new Image ("about.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);
	private final static Image SOURCES_PAGE = new Image ("about2.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);

	About () {
		this.canvas = new Canvas (GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		About.gc = canvas.getGraphicsContext2D();
		this.root = new StackPane();
		this.scene = new Scene (root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	public void setStage (Stage stage) {
		this.stage = stage;
		this.stage.setTitle("About");
		this.stage.setScene(this.scene);
		this.stage.show();
		this.setProperties();
	}

	private void setProperties () {
		About.gc.drawImage(About.ABOUT_PAGE, 0, 0);

		//creating the buttons
		Button next = new Button();
		Button previous = new Button();
		Button home = new Button();

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
		next.setGraphic(nextView);
		next.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		next.setTranslateX(110);
		next.setTranslateY(225);

		//previous view
		previousView.setImage(previousButton);
		previousView.setFitHeight(40);
		previousView.setFitWidth(40);
		previous.setGraphic(previousView);
		previous.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		previous.setTranslateX(-110);
		previous.setTranslateY(225);

		//home view
		homeView.setImage(homeButton);
		homeView.setFitHeight(40);
		homeView.setFitWidth(80);
		home.setGraphic(homeView);
		home.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		home.setTranslateY(225);

		//adding the event handlers
		home.setOnMouseClicked(backToHome);
		next.setOnMouseClicked(nextPage);
		previous.setOnMouseClicked(previousPage);

		//adding to root
		this.root.getChildren().add(canvas);
		this.root.getChildren().add(next);
		this.root.getChildren().add(previous);
		this.root.getChildren().add(home);
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
		private final int MAX_PAGES = 2;
		public void handle(MouseEvent e) {
			if (About.pageCounter < this.MAX_PAGES) {
				About.pageCounter ++;
			}

			About.switchPage();
		}
	};

	//method that switches page to the previous one
	private EventHandler<MouseEvent> previousPage = new EventHandler<MouseEvent>(){
		private final int MIN_PAGES = 1;
		public void handle(MouseEvent e) {
			if (About.pageCounter > this.MIN_PAGES) {
				About.pageCounter --;
			}

			About.switchPage();
		}
	};
	
	

	//method for switching the page
	private static void switchPage() {
		if (About.pageCounter == 1) About.gc.drawImage(About.ABOUT_PAGE, 0, 0);
		else if (About.pageCounter == 2) About.gc.drawImage(About.SOURCES_PAGE, 0, 0);
	}
}
