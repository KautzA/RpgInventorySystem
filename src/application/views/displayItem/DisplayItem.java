package application.views.displayItem;

import java.util.ArrayList;
import java.util.Arrays;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.ItemWeightFactory;
import application.rpgItem.ObservableRpgItem;
import application.views.coordinatePicker.CoordinatePicker;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DisplayItem extends StackPane {
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

	private ObservableList<String> weightUnits = FXCollections.observableList(Arrays.asList("kg", "lb", "slug"));

	private BorderPane rootPane = new BorderPane();

	private TextField controlName = new TextField();
	private TextField controlLink = new TextField();
	private TextArea controlDescription = new TextArea();
	private Spinner<Integer> controlStackSize = new Spinner<Integer>(0, 20, 0, 1);
	private Spinner<Double> controlWeightValue = new Spinner<Double>(0, 1000, 1, 0.1);
	private ComboBox<String> controlWeightUnit = new ComboBox<String>(weightUnits);
	private Spinner<Double> controlWeightScale = new Spinner<Double>(0, 2, 1, 0.25);
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

		updateDisplayItem();

		activeItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				updateDisplayItem();
				activeItem.get().addListener(new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						updateDisplayItem();
					}
				});
			}
		});
	}

	protected void updateDisplayItem() {
		System.out.println("DisplayItem update");
		controlName.setText(activeItem.get().getName());
		controlLink.setText(activeItem.get().getLink());
		controlDescription.setText(activeItem.get().getDescription());
		controlStackSize.getValueFactory().setValue(activeItem.get().getStackSize());
		controlWeightValue.getValueFactory().setValue((double) activeItem.get().getWeight().getValue());
		controlWeightValue.setEditable(true);
		controlWeightUnit.setValue(activeItem.get().getWeight().getUnitsAbbreviation());
		controlWeightScale.getValueFactory().setValue((double) activeItem.get().getContentsWeightScale());
		ObservableList<Coordinate> tempExternalPoints = FXCollections.observableArrayList();
		tempExternalPoints.addAll(activeItem.get().getExternalPoints());
		controlExternalPoints.setItemCoordinates(tempExternalPoints);
		ObservableList<Coordinate> tempInternalPoints = FXCollections.observableArrayList();
		tempInternalPoints.addAll(activeItem.get().getInternalPoints());
		controlInternalPoints.setItemCoordinates(tempInternalPoints);
	}

	protected Node makeButtonFooter() {
		FlowPane buttonFooter = new FlowPane();
		{
			Button saveButton = new Button();
			saveButton.setText("Save");
			buttonFooter.setMargin(saveButton, new Insets(5, 5, 5, 5));
			saveButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					activeItem.get().setStackSize(controlStackSize.getValue());
					activeItem.get().setName(controlName.getText());
					activeItem.get().setLink(controlLink.getText());
					activeItem.get().setDescription(controlDescription.getText());
					activeItem.get().setWeight(ItemWeightFactory.GetWeight(controlWeightUnit.getValue(), controlWeightValue.getValue().floatValue()));
					
					ObservableList<Coordinate> tempExternalPoints = FXCollections.observableArrayList();
					tempExternalPoints.addAll(controlExternalPoints.getItemCoordinates());
					activeItem.get().setExternalPoints(tempExternalPoints);
					ObservableList<Coordinate> tempInternalPoints = FXCollections.observableArrayList();
					tempInternalPoints.addAll(controlInternalPoints.getItemCoordinates());
					activeItem.get().setInternalPoints(tempInternalPoints);
					
					activeItem.set(activeItem.get());
				}
			});
			buttonFooter.getChildren().add(saveButton);
		}
		{
			Button resetButton = new Button();
			resetButton.setText("Reset");
			buttonFooter.setMargin(resetButton, new Insets(5, 5, 5, 5));
			resetButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					updateDisplayItem();
				}
			});
			buttonFooter.getChildren().add(resetButton);
		}
		return buttonFooter;
	}

	protected Node makeItemDisplay() {
		FlowPane itemControls = new FlowPane();

		itemControls.getChildren().add(LabelNode("Stack", controlStackSize));
		itemControls.getChildren().add(LabelNode("Name", controlName));
		itemControls.getChildren().add(LabelNode("Link", controlLink));
		itemControls.getChildren().add(LabelNode("Weight", controlWeightValue));
		itemControls.getChildren().add(LabelNode("Units", controlWeightUnit));
		itemControls.getChildren().add(LabelNode("Description", controlDescription));
		itemControls.getChildren().add(LabelNode("External", controlExternalPoints));
		itemControls.getChildren().add(LabelNode("Internal", controlInternalPoints));

		return itemControls;
	}

	protected Node LabelNode(String name, Node element) {
		VBox result = new VBox();
		VBox.setMargin(element, new Insets(5, 5, 5, 5));
		result.getChildren().add(new Label(name));
		result.getChildren().add(element);
		return result;
	}

	protected ObservableRpgItem getItemFromElements() {
		return new ObservableRpgItem(controlName.getText(), controlDescription.getText(), controlLink.getText(),
				controlStackSize.getValue(),
				ItemWeightFactory.GetWeight(controlWeightUnit.getValue(), controlWeightValue.getValue().floatValue()),
				controlWeightScale.getValue().floatValue(), new ArrayList<ObservableRpgItem>(), new Coordinate(0, 0),
				CardinalRotation.ZERO, controlExternalPoints.getItemCoordinates(),
				controlInternalPoints.getItemCoordinates());
	}
}
