package application.itemEditView;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.ItemWeightKg;
import application.rpgItem.RpgItem;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ItemEditView extends StackPane {
	private ObjectProperty<RpgItem> activeItem;

	public RpgItem getActiveItem() {
		return activeItem.get();
	}

	public void setActiveItem(RpgItem newItem) {
		activeItem.set(newItem);
	}

	public ObjectProperty<RpgItem> activeItemProperty() {
		return activeItem;
	}

	TextField name = new TextField();
	TextArea description = new TextArea();
	TextField link = new TextField();
	Spinner<Integer> stackSize = new Spinner<>(0, 10.0, 0, 1);
	Spinner<Float> weightValue;
	Spinner<String> weightUnit;
	Spinner<Float> contentsWeightScale;
	

	public ItemEditView(RpgItem baseItem) {
		activeItem = new SimpleObjectProperty<RpgItem>(baseItem);
		BorderPane rootPane = new BorderPane();

		FlowPane editPane = new FlowPane();
		editPane.getChildren().add(makeLabeledBox("Stack Size", stackSize));
		editPane.getChildren().add(makeLabeledBox("name", name));
		editPane.getChildren().add(makeLabeledBox("description", description));
		
		HBox buttons = new HBox();
		Button saveButton = new Button();
		saveButton.setText("save");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				activeItem.set(getRpgItemFromFields());
			}
		});
		Button reloadButton = new Button();
		reloadButton.setText("reload");
		reloadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateFields();
			}
		});
		
		rootPane.setCenter(editPane);
		rootPane.setBottom(buttons);
		getChildren().add(rootPane);
	}

	public ItemEditView() {
		this(new RpgItem("", new ItemWeightKg(1), new Coordinate(0, 0), CardinalRotation.ZERO));
	}

	protected static VBox makeLabeledBox(String label, Node element) {
		VBox result = new VBox();
		result.getChildren().add(new Label(label));
		result.getChildren().add(element);
		return result;
	}
	
	protected void updateFields() {
		name.setText(activeItem.get().name);
		description.setText(activeItem.get().description);
		link.setText(activeItem.get().link);
	}

	public RpgItem getRpgItemFromFields() {
		// TODO: replace method stub
		return null;
	}
}
