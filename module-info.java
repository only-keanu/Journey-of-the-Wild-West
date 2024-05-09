module cmscproj {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	
	opens main to javafx.graphics, javafx.fxml;
}
