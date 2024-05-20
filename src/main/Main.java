
package main;

import javafx.application.Application;
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
