package application.views.displayItem;

import java.util.ArrayList;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.ItemWeightKg;
import application.rpgItem.ObservableRpgItem;
import application.views.coordinatePicker.CoordinatePicker;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DisplayItem extends StackPane{
	private final SimpleObjectProperty<ObservableRpgItem> activeItem;

	public ObservableRpgItem getActiveItem() {
		return activeItem.get();
	};

	public void setActiveItem(ObservableRpgItem newItem) {
		this.activeItem.set(newItem);
	}

	public ObjectProperty<ObservableRpgItem> activeItemProperty() {
		return activeItem;
	}

	private BorderPane rootPane = new BorderPane();
	
	private TextField controlName = new TextField();
	private TextField controlLink = new TextField();
	private TextArea controlDescription = new TextArea();
	private Spinner<Integer> controlStackSize = new Spinner<Integer>(0, 20, 0, 1);
	private Spinner<Float> controlWeightValue = new Spinner<Float>(0, 20, 1, 0.1);
	private Spinner<String> controlWeightUnit = new Spinner<String>();
	private Spinner<Float> controlWeightScale = new Spinner<Float>(0, 1, 1, 0.25);
	private CoordinatePicker controlExternalPoints = new CoordinatePicker();
	private CoordinatePicker controlInternalPoints = new CoordinatePicker();
	

	public DisplayItem(ObservableRpgItem item) {
		activeItem = new SimpleObjectProperty<ObservableRpgItem>(item);
		getChildren().add(rootPane);
		rootPane.setPrefSize(200, 200);
		
		Node buttonFooter = makeButtonFooter();
		rootPane.setBottom(buttonFooter);
		
		Node itemDisplay = makeItemDisplay();
		rootPane.setCenter(itemDisplay);
		
		UpdateDisplayItem();
		
		activeItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateDisplayItem();
				activeItem.get().addListener(new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						UpdateDisplayItem();
					}
				});
			}
		});
	}

	protected void UpdateDisplayItem() {
		System.out.println("ExternalView activeItemUpdate");
		controlName.setText(activeItem.get().getName());
		controlLink.setText(activeItem.get().getLink());
		controlDescription.setText(activeItem.get().getDescription());
		controlExternalPoints.setItemCoordinates(activeItem.get().getExternalPoints());
		controlInternalPoints.setItemCoordinates(activeItem.get().getInternalPoints());
	}
	
	protected Node makeButtonFooter() {
		FlowPane buttonFooter = new FlowPane();
		{
			Button saveButton = new Button();
			saveButton.setText("Save");
			saveButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					activeItem.set(getItemFromElements());
				}
			});
			buttonFooter.getChildren().add(saveButton);
		}
		{
			Button resetButton = new Button();
			resetButton.setText("Reset");
			resetButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					UpdateDisplayItem();
				}
			});
			buttonFooter.getChildren().add(resetButton);
		}
		return buttonFooter;
	}

	protected Node makeItemDisplay() {
		FlowPane itemControls = new FlowPane();
		
		itemControls.getChildren().add(LabelNode("name", controlName));
		itemControls.getChildren().add(LabelNode("link", controlLink));
		itemControls.getChildren().add(LabelNode("description", controlDescription));
		itemControls.getChildren().add(LabelNode("External", controlExternalPoints));
		itemControls.getChildren().add(LabelNode("Internal", controlInternalPoints));
		
		return itemControls;
	}
	
	protected Node LabelNode(String name, Node element) {
		VBox result = new VBox();
		result.getChildren().add( new Label(name));
		result.getChildren().add(element);
		return result;
	}
	
	protected ObservableRpgItem getItemFromElements() {
		return new ObservableRpgItem(controlName.getText(),
				controlDescription.getText(),
				controlLink.getText(),
				controlStackSize.getValue(),
				new ItemWeightKg(0),
				controlWeightScale.getValue(),
				new ArrayList<ObservableRpgItem>(),
				new Coordinate(0,0),
				CardinalRotation.ZERO,
				controlExternalPoints.getItemCoordinates(),
				controlInternalPoints.getItemCoordinates()
				);
	}
}
