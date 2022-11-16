package application.rpgItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import application.coordinate.Coordinate;
import application.coordinate.Rotation;
import application.itemWeight.IItemWeight;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableRpgItem {
	/** unique identifier for this RpgItem */
	public final UUID uuid;

	/** Display name of this item */
	private final StringProperty name;

	public String getName() {
		return name.get();
	}

	public void setName(String newName) {
		name.set(newName);
	}

	public StringProperty nameProperty() {
		return name;
	}

	/** Full description of this item */
	private final StringProperty description;

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String newDescription) {
		description.set(newDescription);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	/**
	 * Link to external source of information about this item such as a wiki entry
	 * or game manual page
	 */
	private final StringProperty link;

	public String getLink() {
		return link.get();
	}

	public void setLink(String newLink) {
		link.set(newLink);
	}

	public StringProperty linkProperty() {
		return link;
	}

	/**
	 * Number of duplicate items represented by this one. for example a stack of 50
	 * coins
	 */
	private final IntegerProperty stackSize;

	public int getStackSize() {
		return stackSize.get();
	}

	public void setStackSize(int newStackSize) {
		stackSize.set(newStackSize);
	}

	public IntegerProperty stackSizeProperty() {
		return stackSize;
	}

	/**
	 * Weight of a single item of this type, excluding contents or duplicates
	 */
	private final ObjectProperty<IItemWeight> weight;

	public IItemWeight getWeight() {
		return weight.get();
	}

	public void setWeight(IItemWeight newWeight) {
		weight.set(newWeight);
	}

	public ObjectProperty<IItemWeight> weightProperty() {
		return weight;
	}

	/** Scale factor for weights of items contained within this one */
	private final FloatProperty contentsWeightScale;

	public float getContentsWeightScale() {
		return contentsWeightScale.get();
	}

	public void setContentsWeightScale(float newScale) {
		contentsWeightScale.set(newScale);
	}

	public FloatProperty contentsWeightScaleProperty() {
		return contentsWeightScale;
	}

	/** The list of items contained within this item */
	private final ListProperty<ObservableRpgItem> contents;

	public ObservableList<ObservableRpgItem> getContents() {
		return contents.get();
	}

	public void setContents(ObservableList<ObservableRpgItem> newList) {
		contents.set(newList);
	}

	public ListProperty<ObservableRpgItem> contentsProperty() {
		return contents;
	}

	/** The offset of this item within the item containing it */
	private final ObjectProperty<Coordinate> externalOffset;

	public Coordinate getExternalOffset() {
		return externalOffset.get();
	}

	public void setExternalOffset(Coordinate newCoord) {
		externalOffset.set(newCoord);
	}

	public ObjectProperty<Coordinate> externalOffsetProperty() {
		return externalOffset;
	}

	/** The rotation of this item within the item containing it */
	private final ObjectProperty<Rotation> externalRotation;

	public Rotation getExternalRotation() {
		return externalRotation.get();
	}

	public void setExternalRotation(Rotation newRotation) {
		externalRotation.set(newRotation);
	}

	public ObjectProperty<Rotation> externalRotationProperty() {
		return externalRotation;
	}

	/** The points that this item occupies in the item containing it */
	private final ListProperty<Coordinate> externalPoints;

	public ObservableList<Coordinate> getExternalPoints() {
		return externalPoints.get();
	}

	public void setExternalPoints(ObservableList<Coordinate> newList) {
		externalPoints.set(newList);
	}

	public ListProperty<Coordinate> externalPointsProperty() {
		return externalPoints;
	}

	/** The points that objects contained within this one can occupy */
	private final ListProperty<Coordinate> internalPoints;

	public ObservableList<Coordinate> getInternalPoints() {
		return externalPoints.get();
	}

	public void setInternalPoints(ObservableList<Coordinate> newList) {
		externalPoints.set(newList);
	}

	public ListProperty<Coordinate> internalPointsProperty() {
		return externalPoints;
	}

	public ObservableRpgItem(String name, String description, String link, int stackSize, IItemWeight weight,
			float contentsWeightScale, ObservableList<ObservableRpgItem> contents, Coordinate externalOffset,
			Rotation externalRotation, ObservableList<Coordinate> externalPoints,
			ObservableList<Coordinate> internalPoints) {
		this.uuid = UUID.randomUUID();
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.link = new SimpleStringProperty(link);
		this.stackSize = new SimpleIntegerProperty(stackSize);
		this.weight = new SimpleObjectProperty<IItemWeight>(weight);
		this.contentsWeightScale = new SimpleFloatProperty(contentsWeightScale);
		this.contents = new SimpleListProperty<ObservableRpgItem>();
		this.externalOffset = new SimpleObjectProperty<Coordinate>(externalOffset);
		this.externalRotation = new SimpleObjectProperty<Rotation>(externalRotation);
		this.externalPoints = new SimpleListProperty<Coordinate>(externalPoints);
		this.internalPoints = new SimpleListProperty<Coordinate>(internalPoints);
	}

	public ObservableRpgItem(String name, String description, String link, int stackSize, IItemWeight weight,
			float contentsWeightScale, List<ObservableRpgItem> contents, Coordinate externalOffset,
			Rotation externalRotation, List<Coordinate> externalPoints, List<Coordinate> internalPoints) {
		this(name, description, link, stackSize, weight, contentsWeightScale, FXCollections.observableList(contents), externalOffset,
				externalRotation, FXCollections.observableList(externalPoints),
				FXCollections.observableList(internalPoints));
	}

	public ObservableRpgItem(String name, IItemWeight weight, Coordinate offset, Rotation rotation) {
		this(name, "", "", 1, weight, (float) 1.0, FXCollections.observableList(new ArrayList<ObservableRpgItem>()), offset,
				rotation, FXCollections.observableList(Arrays.asList(new Coordinate(0, 0))),
				FXCollections.observableList(new ArrayList<Coordinate>()));
	}

	public IItemWeight getTotalWeight() {
		return this.getGetSingleWeight().scale(this.getStackSize());
	}

	public IItemWeight getContentsScaledWeight() {
		IItemWeight accumulatedWeight = this.getWeight().scale(0); // get a weight of the same units as this item.
		Iterator<ObservableRpgItem> iter = this.contents.iterator();
		while (iter.hasNext()) {
			accumulatedWeight = accumulatedWeight.add(iter.next().getTotalWeight());
		}
		return accumulatedWeight.scale(this.getContentsWeightScale());
	}

	public IItemWeight getGetSingleWeight() {
		return this.getWeight().add(this.getContentsScaledWeight());
	}

	public boolean equals(Object other) {
		if (other instanceof ObservableRpgItem) {
			ObservableRpgItem otherItem = (ObservableRpgItem) other;
			if (!this.name.equals(otherItem.name)) {
				return false;
			}
			if (!this.description.equals(otherItem.description)) {
				return false;
			}
			if (!this.link.equals(otherItem.link)) {
				return false;
			}
			if (this.stackSize != otherItem.stackSize) {
				return false;
			}
			if (!this.weight.equals(otherItem.weight)) {
				return false;
			}
			if (this.contentsWeightScale != otherItem.contentsWeightScale) {
				return false;
			}
			if (!this.contents.equals(otherItem.contents)) {
				return false;
			} // requires contents in the same order
			if (!this.externalOffset.equals(otherItem.externalOffset)) {
				return false;
			}
			if (!this.externalRotation.equals(otherItem.externalRotation)) {
				return false;
			}
			if (!this.externalPoints.containsAll(otherItem.externalPoints)
					|| !otherItem.externalPoints.containsAll(this.externalPoints)) {
				return false;
			}
			if (!this.internalPoints.containsAll(otherItem.internalPoints)
					|| !otherItem.internalPoints.containsAll(this.internalPoints)) {
				return false;
			}

			return true;
		}
		if (other instanceof RpgItem) {
			RpgItem otherItem = (RpgItem) other;
			if (!this.getName().equals(otherItem.name)) {
				return false;
			}
			if (!this.getDescription().equals(otherItem.description)) {
				return false;
			}
			if (!this.getLink().equals(otherItem.link)) {
				return false;
			}
			if (this.getStackSize() != otherItem.stackSize) {
				return false;
			}
			if (!this.getWeight().equals(otherItem.weight)) {
				return false;
			}
			if (this.getContentsWeightScale() != otherItem.contentsWeightScale) {
				return false;
			}
			if (!this.getContents().equals(otherItem.contents)) {
				return false;
			} // requires contents in the same order
			if (!this.getExternalOffset().equals(otherItem.externalOffset)) {
				return false;
			}
			if (!this.getExternalRotation().equals(otherItem.externalRotation)) {
				return false;
			}
			if (!this.getExternalPoints().containsAll(otherItem.externalPoints)
					|| !otherItem.externalPoints.containsAll(this.getExternalPoints())) {
				return false;
			}
			if (!this.getInternalPoints().containsAll(otherItem.internalPoints)
					|| !otherItem.internalPoints.containsAll(this.getInternalPoints())) {
				return false;
			}

			return true;
		}
		return false;
	}
}
