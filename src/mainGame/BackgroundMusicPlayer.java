package mainGame;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class BackgroundMusicPlayer {
    private static final BackgroundMusicPlayer INSTANCE = new BackgroundMusicPlayer();
    private MediaPlayer mediaPlayer;

    private BackgroundMusicPlayer() {
        // Load background audio
        String path = "test.mp3";  
        Media backgroundMusic = new Media(new File(path).toURI().toString());  
        mediaPlayer = new MediaPlayer(backgroundMusic);
        
        // Set audio to loop indefinitely
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();
    }

    public static BackgroundMusicPlayer getInstance() {
        return INSTANCE;
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }

    public void playMusic() {
        mediaPlayer.play();
    }

    // Other methods to control balance, etc. can be added here
}
