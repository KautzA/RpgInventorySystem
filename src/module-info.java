module RpgInventorySystem {
	requires javafx.controls;
	requires java.xml;
	requires junit;
	
	opens application to javafx.graphics, javafx.fxml;
}
