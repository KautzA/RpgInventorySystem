package application.gridViews;

import application.rpgItem.ObservableRpgItem;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
public class ExternalGrid extends SubScene{
	private static SimpleObjectProperty<ObservableRpgItem> rootItem;

	
	public ExternalGrid(Parent root, ObservableRpgItem rootRpgItem, double width, double height)
	{
		super(root, width, height);
		rootItem = new SimpleObjectProperty<ObservableRpgItem>(rootRpgItem);
		renderText();
	}
	
	public static void renderText()
	{
		Text t1 = new Text();
		
		rootItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				t1.setText(rootItem.get().getName());
			}
		});
		
		t1.setText(rootItem.get().getName());
		
	}
	
	
}
