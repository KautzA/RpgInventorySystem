package application.views.externalView;

import application.coordinate.Coordinate;
import application.rpgItem.ObservableRpgItem;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ExternalView extends StackPane {
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

	private final SimpleIntegerProperty gridWidth;
	private final SimpleIntegerProperty gridHeight;

	private BorderPane rootPane = new BorderPane();

	public ExternalView(ObservableRpgItem item) {
		activeItem = new SimpleObjectProperty<ObservableRpgItem>(item);
		gridWidth = new SimpleIntegerProperty(10);
		gridHeight = new SimpleIntegerProperty(10);
		getChildren().add(rootPane);
		rootPane.setPrefSize(200, 200);
		UpdateExternalView();
		activeItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateExternalView();
				activeItem.get().addListener(new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						UpdateExternalView();
					}
				});
			}
		});
		gridWidth.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateExternalView();
			}
		});
		gridHeight.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateExternalView();
			}
		});
	}

	protected void UpdateExternalView() {
		System.out.println("ExternalView activeItemUpdate");
		GridPane contents = new GridPane();
		for (int row = 0; row < gridHeight.get(); row ++) {
			for (int col = 0; col < gridWidth.get(); col ++) {
//				Button cell = new Button();
//				cell.setText("_");
				Rectangle cell = new Rectangle();
				cell.setFill(Color.TRANSPARENT);
				cell.setStroke(Color.BLACK);
				cell.setWidth(10);
				cell.setHeight(10);
				//cell.widthProperty().bind(contents.widthProperty().divide(gridWidth));
				//cell.heightProperty().bind(contents.heightProperty().divide(gridHeight));
				contents.add(cell, row, col);
			}
		}
		
		for (Coordinate point : activeItem.get().getExternalPoints()) {
			System.out.println("Rendering point (" + point.x + ", " + point.y + ")");
//			Button cell = new Button();
//			cell.setText("X");
			Rectangle cell = new Rectangle();
			cell.setFill(Color.RED);
			cell.setStroke(Color.BLACK);
			cell.setWidth(10);
			cell.setHeight(10);
			//cell.widthProperty().bind(contents.widthProperty().divide(gridWidth));
			//cell.heightProperty().bind(contents.heightProperty().divide(gridHeight));
			contents.add(cell, point.x, point.y);
		}
		rootPane.setCenter(contents);
	}
}
