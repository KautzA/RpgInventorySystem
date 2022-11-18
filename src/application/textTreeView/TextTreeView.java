package application.textTreeView;

import application.rpgItem.ObservableRpgItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

	private TitledPane rootPane = new TitledPane();

	public TextTreeView(ObservableRpgItem item) {
		rootItem = new SimpleObjectProperty<ObservableRpgItem>(item);
		getChildren().add(rootPane);
		UpdateTextTreeView();
		rootItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				UpdateTextTreeView();
				rootItem.get().addListener(new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						UpdateTextTreeView();
					}
				});
			}
		});
	}

	protected void UpdateTextTreeView() {
		rootPane.textProperty().bind(Bindings.concat(rootItem.get().stackSizeProperty(), "X \"",
				rootItem.get().nameProperty(), "\", ", rootItem.get().weightProperty().get().getValue()));
		VBox contents = new VBox();
		contents.setPadding(new Insets(0, 0, 0, 5));
		{
			Button testButton = new Button("testButton");
			testButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.println(getRpgItemLabel());

				}
			});
			contents.getChildren().add(testButton);
		}
		for (ObservableRpgItem child : rootItem.get().getContents()) {
			TextTreeView subview = new TextTreeView(child);
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
