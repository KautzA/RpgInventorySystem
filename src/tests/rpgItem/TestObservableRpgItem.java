package tests.rpgItem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.IItemWeight;
import application.itemWeight.ItemWeightKg;
import application.rpgItem.ObservableRpgItem;

public class TestObservableRpgItem {
	ObservableRpgItem emptyItem;
	ObservableRpgItem emptyItemCopy;
	ObservableRpgItem containingItem;
	ObservableRpgItem containingItemCopy;
	ObservableRpgItem containingItemDifferentContents;

	@Before
	public void setupRpgItems() {
		IItemWeight weightA = new ItemWeightKg(0);

		this.emptyItem = new ObservableRpgItem("TestEmptyBag", weightA, new Coordinate(0, 0), CardinalRotation.ZERO);
		this.containingItem = new ObservableRpgItem("TestNonEmptyBag", "", "", 1, weightA, 1, Arrays.asList(emptyItem),
				new Coordinate(0, 0), CardinalRotation.ZERO, Arrays.asList(new Coordinate(0, 0)),
				Arrays.asList(new Coordinate(0, 0)));
		this.containingItemDifferentContents = new ObservableRpgItem("TestNonEmptyBag", "", "", 1, weightA, 1,
				Arrays.asList(containingItem), new Coordinate(0, 0), CardinalRotation.ZERO,
				Arrays.asList(new Coordinate(0, 0)), Arrays.asList(new Coordinate(0, 0)));

	}

	@Test
	public void testEqualsBasicSelf() {
		Assert.assertEquals(emptyItem, emptyItem);
	}

	@Test
	public void testNotEqualContiainingEmpty() {
		Assert.assertNotEquals(containingItem, emptyItem);
	}

	@Test
	public void testEqualsContainingSelf() {
		Assert.assertEquals(containingItem, containingItem);
	}

	@Test
	public void testNotEqualContiainingContents() {
		Assert.assertNotEquals(containingItem, containingItemDifferentContents);
	}

}
