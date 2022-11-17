package application.textTreeView;

import application.rpgItem.RpgItem;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TextTreeView extends StackPane {
	private SimpleObjectProperty<RpgItem> rootItem;

	public RpgItem getRootItem() {
		return rootItem.get();
	};

	public void setRootItem(RpgItem newItem) {
		this.rootItem.set(newItem);
	}

	public ObjectProperty<RpgItem> rootItemProperty() {
		return rootItem;
	}

	private TitledPane rootPane = new TitledPane();

	public TextTreeView(RpgItem item) {
		rootItem = new SimpleObjectProperty<RpgItem>(item);
		getChildren().add(rootPane);
		UpdateTextTreeView(item);
		rootItem.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				System.out.println("TextTreeView root item changed. now " + rootItem.get().name);
				UpdateTextTreeView(rootItem.get());
			}
		});
	}

	protected void UpdateTextTreeView(RpgItem item) {
		System.out.println("UpdateTextTreeView called with item named " + item.name + ", root item named " + rootItem.get().name);
		rootPane.setText(Integer.toString(rootItem.get().stackSize) + "X \"" +
				rootItem.get().name + "\", " + rootItem.get().weight.getValue());
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
		for (RpgItem child : rootItem.get().contents) {
			contents.getChildren().add(new TextTreeView(child));
		}

		rootPane.setContent(contents);
	}

	String getRpgItemLabel() {
		return String.format("%dX \"%s\", %f %s (Total %f %s)", rootItem.get().stackSize, rootItem.get().name,
				rootItem.get().weight.getValue(), rootItem.get().weight.getUnitsAbbreviation(),
				rootItem.get().getTotalWeight().getValue(), rootItem.get().getTotalWeight().getUnitsAbbreviation());
	}
}
