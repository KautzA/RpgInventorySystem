module RpgInventorySystem {
	requires javafx.controls;
	requires java.xml;
	requires junit;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
