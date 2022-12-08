package application.views.readOnlyView;

import java.util.Arrays;

import application.rpgItem.ObservableRpgItem;
import application.views.coordinatePicker.CoordinatePicker;
import application.views.externalView.ExternalView;
import application.views.internalView.InternalView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ReadOnlyView extends StackPane {
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
	private ExternalView displayExternalView;
	private InternalView displayInternalView;

	public ReadOnlyView(ObservableRpgItem item) {
		activeItem = new SimpleObjectProperty<ObservableRpgItem>(item);
		displayExternalView = new ExternalView(item);
		displayExternalView.activeItemProperty().bind(activeItem);
		displayInternalView = new InternalView(item);
		displayInternalView.activeItemProperty().bind(activeItem);
		getChildren().add(rootPane);
		rootPane.setPrefSize(200, 200);
		controlDescription.setPrefSize(400, 200); ///change the size of description ym
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
		System.out.println("ExternalView activeItemUpdate");
		controlName.setText(activeItem.get().getName());
		controlName.setEditable(false);
		controlLink.setText(activeItem.get().getLink());
		controlLink.setEditable(false);
		controlDescription.setText(activeItem.get().getDescription());
		controlDescription.setEditable(false);
		controlStackSize.getValueFactory().setValue(activeItem.get().getStackSize());
		controlStackSize.setDisable(true);;
		controlWeightValue.getValueFactory().setValue((double) activeItem.get().getWeight().getValue());
		controlWeightValue.setDisable(true);
		controlWeightUnit.setValue(activeItem.get().getWeight().getUnitsAbbreviation());
		controlWeightUnit.setDisable(true);
		controlWeightScale.getValueFactory().setValue((double) activeItem.get().getContentsWeightScale());
		controlWeightScale.setDisable(true);
	}

	protected Node makeItemDisplay() {
		FlowPane itemControls = new FlowPane();

		itemControls.getChildren().add(labelNode("Stack", controlStackSize));
		itemControls.getChildren().add(labelNode("Name", controlName));
		itemControls.getChildren().add(labelNode("Link", controlLink));
		itemControls.getChildren().add(labelNode("Weight", controlWeightValue));
		itemControls.getChildren().add(labelNode("Units", controlWeightUnit));
		itemControls.getChildren().add(labelNode("Description", controlDescription));
		itemControls.getChildren().add(labelNode("External", displayExternalView));
		itemControls.getChildren().add(labelNode("Internal", displayInternalView));

		return itemControls;
	}

	protected Node labelNode(String name, Node element) {
		VBox result = new VBox();
		VBox.setMargin(element, new Insets(5, 5, 5, 5));
		result.getChildren().add(new Label(name));
		result.getChildren().add(element);
		return result;
	}
}
