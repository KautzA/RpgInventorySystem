package application.textTreeView;

import application.rpgItem.RpgItem;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class TextTreeView {
	public static Node MakeTextTreeView(RpgItem item) {
		TitledPane corePane = new TitledPane();
		corePane.setText(getRpgItemLabel(item));
		VBox contents = new VBox();
		contents.setPadding(new Insets(0, 0, 0, 5));
		for (RpgItem child : item.contents) {
			contents.getChildren().add(MakeTextTreeView(child));
		}
		
		corePane.setContent(contents);
		return corePane;
	}

	static String getRpgItemLabel(RpgItem item) {
		return String.format("%dX \"%s\", %f %s (Total %f %s)", item.stackSize, item.name, item.weight.getValue(),
				item.weight.getUnitsAbbreviation(), item.getTotalWeight().getValue(),
				item.getTotalWeight().getUnitsAbbreviation());
	}
}
