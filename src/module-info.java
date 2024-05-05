module cmscproj {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.media;
	
	opens main to javafx.graphics, javafx.fxml;
}
