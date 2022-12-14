package application.rpgItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import application.coordinate.Coordinate;
import application.coordinate.Rotation;
import application.itemWeight.IItemWeight;

public class RpgItem {
	/** unique identifier for this RpgItem */
	public final UUID uuid;
	/** Display name of this item */
	public String name;
	/** Full description of this item */
	public String description;
	/** 
	 * Link to external source of information about this item
	 * such as a wiki entry or game manual page
	 */
	public String link;
	/** 
	 * Number of duplicate items represented by this one.
	 * for example a stack of 50 coins
	 */
	public int stackSize;
	/**
	* Weight of a single item of this type, excluding contents or duplicates
	*/
	public IItemWeight weight;
	/** Scale factor for weights of items contained within this one */
	public float contentsWeightScale;
	/** The list of items contained within this item */
	public ArrayList<RpgItem> contents;
	/** The offset of this item within the item containing it */
	public Coordinate externalOffset;
	/** The rotation of this item within the item containing it */
	public Rotation externalRotation;
	/** The points that this item occupies in the item containing it */
	public ArrayList<Coordinate> externalPoints;
	/** The points that objects contained within this one can occupy */
	public ArrayList<Coordinate> internalPoints;
	
	public RpgItem(String name,
				   String description,
				   String link,
				   int stackSize,
				   IItemWeight weight, 
				   float contentsWeightScale,
				   List<RpgItem> contents,
				   Coordinate externalOffset,
				   Rotation externalRotation,
				   List<Coordinate> externalPoints,
				   List<Coordinate> internalPoints
				   ) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.link = link;
		this.stackSize = stackSize;
		this.weight = weight;
		this.contentsWeightScale = contentsWeightScale;
		this.contents = new ArrayList<RpgItem>();
		this.contents.addAll(contents);
		this.externalOffset = new Coordinate(externalOffset);
		this.externalRotation = externalRotation;
		this.externalPoints = new ArrayList<Coordinate>();
		this.externalPoints.addAll(externalPoints);
		this.internalPoints = new ArrayList<Coordinate>();
		this.internalPoints.addAll(internalPoints);
	}
	
	public RpgItem(String name, IItemWeight weight, Coordinate offset, Rotation rotation) {
		this(name,
			 "", 
			 "", 
			 1, 
			 weight, 
			 (float)1.0, 
			 new ArrayList<RpgItem>(),
			 offset, 
			 rotation, 
			 new ArrayList<Coordinate>(Arrays.asList(new Coordinate(0,0))),
			 new ArrayList<Coordinate>()
			 );
	}
	
	public IItemWeight getTotalWeight() {
		return this.getGetSingleWeight().scale(this.stackSize);
	}
	
	public IItemWeight getContentsScaledWeight() {
		IItemWeight accumulatedWeight = this.weight.scale(0); // get a weight of the same units as this item.
		Iterator<RpgItem> iter = this.contents.iterator();
		while (iter.hasNext()) {
			accumulatedWeight = accumulatedWeight.add(iter.next().getTotalWeight());
		}
		return accumulatedWeight.scale(this.contentsWeightScale);
	}
	
	public IItemWeight getGetSingleWeight() {
		return this.weight.add(this.getContentsScaledWeight());
	}
	
	public boolean equals(Object other) {
		if (other instanceof RpgItem) {
			RpgItem otherItem = (RpgItem)other;
			if (!this.name.equals(otherItem.name)) { return false; }
			if (!this.description.equals(otherItem.description)) { return false; }
			if (!this.link.equals(otherItem.link)) { return false; }
			if (this.stackSize != otherItem.stackSize) { return false; }
			if (!this.weight.equals(otherItem.weight)) { return false; }
			if (this.contentsWeightScale != otherItem.contentsWeightScale) { return false; }
			if (!this.contents.equals(otherItem.contents)) { return false; } // requires contents in the same order
			if (!this.externalOffset.equals(otherItem.externalOffset)) { return false; }
			if (!this.externalRotation.equals(otherItem.externalRotation)) { return false; }
			if (!this.externalPoints.containsAll(otherItem.externalPoints)
				|| !otherItem.externalPoints.containsAll(this.externalPoints)) { return false; }
			if (!this.internalPoints.containsAll(otherItem.internalPoints)
					|| !otherItem.internalPoints.containsAll(this.internalPoints)) { return false; }
			
			return true;
		}
		return false;
	}
}
