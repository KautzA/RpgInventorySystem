package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.ItemWeightKg;
import application.rpgItem.ObservableRpgItem;
import application.rpgItemXmlParser.ObservableRpgItemXmlParser;
import application.views.textTreeView.TextTreeView;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	final ObjectProperty<ObservableRpgItem> rootRpgItem;
	final ObjectProperty<ObservableRpgItem> activeRpgItem;
	
	public Main() {
		ObservableRpgItem rootItem = new ObservableRpgItem("Default Item", new ItemWeightKg(1), new Coordinate(0,0), CardinalRotation.ZERO);
		this.rootRpgItem = new SimpleObjectProperty<ObservableRpgItem>(this, "rootRpgItem", rootItem);
		this.activeRpgItem = new SimpleObjectProperty<ObservableRpgItem>(this, "activeRpgItem", rootItem);
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			final FileChooser fileChooser = new FileChooser();
			primaryStage.setTitle("RpgInventorySystem");
			// Close the application when the main window is closed
			primaryStage.setOnCloseRequest( new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					Platform.exit();
					System.exit(0);
				}
			});

			Stage textWindow = makeTextView();
			VBox root = new VBox();
			HBox banner = new HBox();
			root.getChildren().add(banner);
			
			{
				Button textViewButton = new Button();
				textViewButton.setText("Open text view");
				textViewButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						textWindow.show();
					}
				});
				banner.getChildren().add(textViewButton);
			}
			
			{
				Button loadItemButton = new Button();
				loadItemButton.setText("Load new item");
				loadItemButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						fileChooser.getExtensionFilters().clear();
						fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
						File file = fileChooser.showOpenDialog(primaryStage);
						try {
							rootRpgItem.set(ObservableRpgItemXmlParser.parseXML(new FileInputStream(file)));
							System.out.println("Loaded RpgItem with name = " + rootRpgItem.get().getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				banner.getChildren().add(loadItemButton);
			}
			
			{
				Button saveItemButton = new Button();
				saveItemButton.setText("Save current item");
				saveItemButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						fileChooser.getExtensionFilters().clear();
						fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
						File file = fileChooser.showSaveDialog(primaryStage);
						try {
							ObservableRpgItemXmlParser.saveXML(rootRpgItem.get(), new FileOutputStream(file));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				banner.getChildren().add(saveItemButton);
			}
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	Stage makeTextView() {
		Stage textView = new Stage();
		textView.titleProperty().bind(Bindings.concat("Text view of \"",rootRpgItem.get().nameProperty(), "\""));
		rootRpgItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				textView.titleProperty().bind(Bindings.concat("Text view of \"",rootRpgItem.get().nameProperty(), "\""));
			}
		});
		
		
		ScrollPane root = new ScrollPane();
		root.setFitToWidth(true);
		TextTreeView contents = new TextTreeView(rootRpgItem.get());
		contents.rootItemProperty().bindBidirectional(rootRpgItem);
		root.setContent(contents);
		
		Scene scene = new Scene(root);
		textView.setScene(scene);
		return textView;
	}
}
