package application.views.textTreeView;

import application.rpgItem.ObservableRpgItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TextTreeView extends StackPane {
	private SimpleObjectProperty<ObservableRpgItem> rootItem;

	public ObservableRpgItem getRootItem() {
		return rootItem.get();
	};

	public void setRootItem(ObservableRpgItem newItem) {
		this.rootItem.set(newItem);
	}

	public ObjectProperty<ObservableRpgItem> rootItemProperty() {
		return rootItem;
	}

	private SimpleObjectProperty<ObservableRpgItem> activeItem;
	
	public ObservableRpgItem getActiveItem() {
		return activeItem.get();
	};

	public void setActiveItem(ObservableRpgItem newItem) {
		this.activeItem.set(newItem);
	}

	public ObjectProperty<ObservableRpgItem> activeItemProperty() {
		return activeItem;
	}
	private TitledPane rootPane = new TitledPane();

	public TextTreeView(ObservableRpgItem item) {
		rootItem = new SimpleObjectProperty<ObservableRpgItem>(item);
		activeItem = new SimpleObjectProperty<ObservableRpgItem>(item);
		getChildren().add(rootPane);
		updateTextTreeView();
		rootItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				updateTextTreeView();
				rootItem.get().addListener(new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						updateTextTreeView();
					}
				});
			}
		});
	}

	public void updateTextTreeView() {
		rootPane.setText(getRpgItemLabel());
		VBox contents = new VBox();
		contents.setPadding(new Insets(0, 0, 0, 5));
		{
			Button testButton = new Button("Set Active");
			testButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.println(getRpgItemLabel());
					activeItem.set(rootItem.get());

				}
			});
			contents.getChildren().add(testButton);
		}
		for (ObservableRpgItem child : rootItem.get().getContents()) {
			TextTreeView subview = new TextTreeView(child);
			subview.activeItem.bindBidirectional(this.activeItem);
			contents.getChildren().add(subview);

		}

		rootPane.setContent(contents);
	}

	String getRpgItemLabel() {
		return String.format("%dX \"%s\", %f %s (Total %f %s)", rootItem.get().getStackSize(), rootItem.get().getName(),
				rootItem.get().getWeight().getValue(), rootItem.get().getWeight().getUnitsAbbreviation(),
				rootItem.get().getTotalWeight().getValue(), rootItem.get().getTotalWeight().getUnitsAbbreviation());
	}
}
