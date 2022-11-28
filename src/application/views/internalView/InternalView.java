package application.views.internalView;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

public class InternalView extends StackPane {
	private final SimpleObjectProperty<ObservableRpgItem> activeItem;

	List<Color> childColors = Arrays.asList(Color.BLUE, Color.YELLOW, Color.BROWN, Color.PURPLE, Color.GRAY,
			Color.SALMON);

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

	public InternalView(ObservableRpgItem item) {
		activeItem = new SimpleObjectProperty<ObservableRpgItem>(item);
		gridWidth = new SimpleIntegerProperty(10);
		gridHeight = new SimpleIntegerProperty(10);
		getChildren().add(rootPane);
		rootPane.setPrefSize(200, 200);
		updateInternalView();
		activeItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				updateInternalView();
				activeItem.get().addListener(new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						updateInternalView();
					}
				});
			}
		});
		gridWidth.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				updateInternalView();
			}
		});
		gridHeight.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				updateInternalView();
			}
		});
	}

	protected void updateInternalView() {
		System.out.println("InternalView activeItemUpdate");
		GridPane contents = new GridPane();
		for (int row = 0; row < gridHeight.get(); row++) {
			for (int col = 0; col < gridWidth.get(); col++) {
//				Button cell = new Button();
//				cell.setText("_");
				Rectangle cell = new Rectangle();
				cell.setFill(Color.TRANSPARENT);
				cell.setStroke(Color.BLACK);
				cell.setWidth(10);
				cell.setHeight(10);
				// cell.widthProperty().bind(contents.widthProperty().divide(gridWidth));
				// cell.heightProperty().bind(contents.heightProperty().divide(gridHeight));
				contents.add(cell, row, col);
			}
		}

		for (Coordinate point : activeItem.get().getInternalPoints()) {
			System.out.println("Rendering point (" + point.x + ", " + point.y + ")");
//			Button cell = new Button();
//			cell.setText("X");
			Rectangle cell = new Rectangle();
			cell.setFill(Color.GREEN);
			cell.setStroke(Color.BLACK);
			cell.setWidth(10);
			cell.setHeight(10);
			// cell.widthProperty().bind(contents.widthProperty().divide(gridWidth));
			// cell.heightProperty().bind(contents.heightProperty().divide(gridHeight));
			contents.add(cell, point.x, point.y);
		}

		Iterator<Color> colorIterator = childColors.iterator();
		for (ObservableRpgItem child : activeItem.get().getContents()) {
			Color childColor;
			if (colorIterator.hasNext()) {
				childColor = colorIterator.next();
			} else {
				childColor = Color.BLUE;
			}
			for (Coordinate childPoint : child.getOccupiedPoints()) {
				System.out.println("Rendering Sub-point for child (" + childPoint.x + ", " + childPoint.y + ")");
				Rectangle cell = new Rectangle();
				cell.setFill(childColor);
				if (activeItem.get().getInternalPoints().contains(childPoint)) { // This point exits within the contents
																					// properly
					cell.setStroke(Color.BLACK);
				} else {
					cell.setStroke(Color.RED);
				}
				cell.setWidth(10);
				cell.setHeight(10);
				contents.add(cell, childPoint.x, childPoint.y);
			}
		}
		rootPane.setCenter(contents);
	}
}
