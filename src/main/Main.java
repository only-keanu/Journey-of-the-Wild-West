
package main;

import java.io.File;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import mainGame.BackgroundMusicPlayer;
import menu.MainMenu;

public class Main extends Application {

	public static void main(String[] args)  {
		launch(args);
	}
 
	public void start(Stage stage){
		BackgroundMusicPlayer.getInstance().playMusic();
		MainMenu mainMenu = new MainMenu();
		mainMenu.setStage(stage); 
	}

}
