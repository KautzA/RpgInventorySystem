package application.views.coordinatePicker;

import application.coordinate.Coordinate;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class CoordinatePicker extends StackPane {
	private final ListProperty<Coordinate> itemCoordinates = new SimpleListProperty<Coordinate>();

	public ObservableList<Coordinate> getItemCoordinates() {
		return itemCoordinates.get();
	}

	public void setItemCoordinates(ObservableList<Coordinate> newList) {
		itemCoordinates.set(newList);
	}

	public ListProperty<Coordinate> itemCoordinatesProperty() {
		return itemCoordinates;
	}

	private final SimpleIntegerProperty gridWidth;
	private final SimpleIntegerProperty gridHeight;

	private BorderPane rootPane = new BorderPane();

	public CoordinatePicker() {
		gridWidth = new SimpleIntegerProperty(10);
		gridHeight = new SimpleIntegerProperty(10);
		getChildren().add(rootPane);
		rootPane.setPrefSize(200, 200);
		UpdateDisplayed();
		itemCoordinates.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateDisplayed();
			}
		});
		gridWidth.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateDisplayed();
			}
		});
		gridHeight.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateDisplayed();
			}
		});
	}

	protected void UpdateDisplayed() {
		System.out.println("ExternalView activeItemUpdate");
		GridPane contents = new GridPane();
		for (int row = 0; row < gridHeight.get(); row++) {
			for (int col = 0; col < gridWidth.get(); col++) {
				// final ints for handler
				final int frow = row;
				final int fcol = col;
				CheckBox cell = new CheckBox();
				cell.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						Coordinate cellCoord = new Coordinate(frow, fcol);
						if (cell.isSelected()) {
							// Change from selected to deselected
							itemCoordinates.remove(cellCoord);
						} else {
							// Change from deselected to selected
							itemCoordinates.add(cellCoord);
						}
					}
				});
				contents.add(cell, row, col);
			}
		}
		rootPane.setCenter(contents);
	}

}
